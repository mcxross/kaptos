package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
import xyz.mcxross.kaptos.transaction.instances.ChainId
import xyz.mcxross.kaptos.transaction.instances.RawTransaction
import xyz.mcxross.kaptos.transport.ktor.asAptosTransport

class TransactionValidationTest :
  StringSpec({
    "submit and hash reject the same invalid authenticator topology before transport" {
      var requests = 0
      val http =
        HttpClient(
          MockEngine {
            requests++
            error("must not submit invalid topology")
          }
        )
      val aptos = Aptos(AptosConfig(network = Network.LOCAL, transport = http.asAptosTransport()))
      try {
        val raw =
          RawTransaction(
            AccountAddress.ONE,
            0u,
            TransactionPayload.entryFunction("0x1::m::f"),
            2000u,
            1u,
            99u,
            ChainId(4u),
          )
        val auth = AccountAuthenticator.NoAccount
        val cases =
          listOf(
            Triple(UnsignedTransaction.Simple(raw), emptyList(), auth),
            Triple(UnsignedTransaction.Simple(raw), listOf(auth), null),
            Triple(UnsignedTransaction.FeePayer(raw), emptyList(), null),
            Triple(UnsignedTransaction.MultiAgent(raw, emptyList()), emptyList(), null),
            Triple(
              UnsignedTransaction.MultiAgent(raw, listOf(AccountAddress.ONE)),
              listOf(auth),
              null,
            ),
            Triple(
              UnsignedTransaction.MultiAgent(raw, listOf(AccountAddress.TWO, AccountAddress.TWO)),
              listOf(auth, auth),
              null,
            ),
          )
        for ((transaction, secondary, payer) in cases) {
          val submitted =
            aptos.transactions
              .submit(transaction, auth, secondary, payer)
              .shouldBeInstanceOf<AptosResult.Failure>()
          val hashed =
            aptos.transactions
              .userTransactionHash(transaction, auth, secondary, payer)
              .shouldBeInstanceOf<AptosResult.Failure>()
          submitted.error shouldBe hashed.error
        }
        aptos.transactions
          .simulate(
            UnsignedTransaction.MultiAgent(raw, emptyList()),
            xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey(ByteArray(32)),
          )
          .shouldBeInstanceOf<AptosResult.Failure>()
        aptos.transactions
          .externalFeePayerRequest(
            UnsignedTransaction.FeePayer(raw, listOf(AccountAddress.ONE)),
            auth,
            listOf(auth),
          )
          .shouldBeInstanceOf<AptosResult.Failure>()
        requests shouldBe 0
      } finally {
        aptos.close()
        http.close()
      }
    }
  })
