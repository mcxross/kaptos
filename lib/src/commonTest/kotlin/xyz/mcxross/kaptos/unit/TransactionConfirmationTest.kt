package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.*
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transport.ktor.asAptosTransport

class TransactionConfirmationTest :
  StringSpec({
    "temporary not-found and pending responses continue to a committed transaction" {
      confirmationClient({ attempt ->
        when (attempt) {
          1 ->
            HttpStatusCode.NotFound to
              """{"message":"not observed yet","error_code":"transaction_not_found"}"""
          2 ->
            HttpStatusCode.OK to
              """{"type":"pending_transaction","hash":"$confirmationHash","sender":"0x1","sequence_number":"0","max_gas_amount":"200000","gas_unit_price":"100","expiration_timestamp_secs":"9999999999"}"""
          else -> HttpStatusCode.OK to confirmedBody()
        }
      }) { client, attempts ->
        val result = client.transactions.waitForTransaction(confirmationHash)
        result
          .shouldBeInstanceOf<AptosResult.Success<TransactionResponse>>()
          .value
          .shouldBeInstanceOf<UserTransactionResponse>()
          .success shouldBe true
        attempts() shouldBe 3
      }
    }
    "authorization failures retain their error code and are not retried" {
      confirmationClient({
        HttpStatusCode.Unauthorized to """{"message":"denied","error_code":"unauthorized"}"""
      }) { client, attempts ->
        client.transactions
          .waitForTransaction(confirmationHash)
          .shouldBeInstanceOf<AptosResult.Failure>()
          .error
          .shouldBeInstanceOf<AptosError.Api>()
          .errorCode shouldBe "unauthorized"
        attempts() shouldBe 1
      }
    }
    "unobserved transactions expire with a timeout rather than an indexer error" {
      confirmationClient({
        HttpStatusCode.NotFound to
          """{"message":"not observed yet","error_code":"transaction_not_found"}"""
      }) { client, _ ->
        client.transactions
          .waitForTransaction(confirmationHash, WaitForTransactionOptions(timeoutSecs = 1))
          .shouldBeInstanceOf<AptosResult.Failure>()
          .error
          .shouldBeInstanceOf<AptosError.Timeout>()
      }
    }
    "deadline includes a slow fullnode request" {
      confirmationClient({
        delay(5_000)
        HttpStatusCode.OK to confirmedBody()
      }) { client, attempts ->
        client.transactions
          .waitForTransaction(confirmationHash, WaitForTransactionOptions(timeoutSecs = 1))
          .shouldBeInstanceOf<AptosResult.Failure>()
          .error
          .shouldBeInstanceOf<AptosError.Timeout>()
        attempts() shouldBe 1
      }
    }
    "parent cancellation propagates" {
      confirmationClient({
        delay(5_000)
        HttpStatusCode.OK to confirmedBody()
      }) { client, _ ->
        shouldThrow<TimeoutCancellationException> {
          withTimeout(50) { client.transactions.waitForTransaction(confirmationHash) }
        }
      }
    }
    "committed aborts respect checkSuccess" {
      confirmationClient({ HttpStatusCode.OK to confirmedBody(false) }) { client, _ ->
        client.transactions
          .waitForTransaction(confirmationHash)
          .shouldBeInstanceOf<AptosResult.Failure>()
          .error
          .shouldBeInstanceOf<AptosError.Api>()
          .errorCode shouldBe "transaction_execution_failed"
        client.transactions
          .waitForTransaction(confirmationHash, WaitForTransactionOptions(checkSuccess = false))
          .shouldBeInstanceOf<AptosResult.Success<TransactionResponse>>()
          .value
          .shouldBeInstanceOf<UserTransactionResponse>()
          .success shouldBe false
      }
    }
  })

private suspend fun confirmationClient(
  response: suspend (Int) -> Pair<HttpStatusCode, String>,
  test: suspend (Aptos, () -> Int) -> Unit,
) {
  var count = 0
  val http =
    HttpClient(
      MockEngine { request ->
        request.url.encodedPath shouldBe "/v1/transactions/by_hash/$confirmationHash"
        val (status, body) = response(++count)
        respond(
          body,
          status,
          headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )
      }
    ) {
      install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    }
  val client =
    Aptos(
      AptosConfig(
        network = Network.LOCAL,
        endpoints = AptosEndpoints(fullNode = "https://test.invalid/v1"),
        transport = http.asAptosTransport(),
      )
    )
  try {
    test(client) { count }
  } finally {
    client.close()
    http.close()
  }
}

private val confirmationHash = "0x" + "12".repeat(32)

private fun confirmedBody(success: Boolean = true) =
  """{"type":"user_transaction","version":"1","hash":"$confirmationHash","state_change_hash":"$confirmationHash","event_root_hash":"$confirmationHash","state_checkpoint_hash":null,"gas_used":"2","success":$success,"vm_status":"executed or aborted","accumulator_root_hash":"$confirmationHash","sender":"0x1","sequence_number":"0","max_gas_amount":"200000","gas_unit_price":"100","expiration_timestamp_secs":"9999999999","events":[],"timestamp":"1"}"""
