/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.unit

import kotlin.time.Instant
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.AptosPage
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.names.AptosName
import xyz.mcxross.kaptos.names.DefaultNameDataSource
import xyz.mcxross.kaptos.names.DefaultNameService
import xyz.mcxross.kaptos.names.NameDataSource
import xyz.mcxross.kaptos.names.NameExpirationPolicy
import xyz.mcxross.kaptos.names.NameQuery
import xyz.mcxross.kaptos.names.NameRecord
import xyz.mcxross.kaptos.names.toRecord
import xyz.mcxross.kaptos.generated.GetNamesQuery
import xyz.mcxross.kaptos.transaction.MoveArgument

class NameServiceTest :
  StringSpec({
    "names normalize the suffix and preserve subdomain order" {
      AptosName.parse("sub.domain.apt") shouldBe
        AptosName(domain = "domain", subdomain = "sub")
      AptosName.parse("domain").toString() shouldBe "domain"
      shouldThrow<IllegalArgumentException> { AptosName.parse("UPPER.apt") }
      shouldThrow<IllegalArgumentException> { AptosName.parse("a.b.c.apt") }
    }

    "target and primary-name builders encode optional subdomains idiomatically" {
      val transactions = RecordingTransactionService()
      val service =
        DefaultNameService(
          FakeNameDataSource(),
          transactions,
          AccountAddress.fromString("0xabc"),
        )

      service
        .buildSetTarget(
          sender = AccountAddress.ONE,
          name = "sub.domain.apt",
          target = AccountAddress.fromString("0xa"),
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val setTarget =
        transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      setTarget.call.module.toString() shouldBe
        "${AccountAddress.fromString("0xabc")}::router"
      setTarget.call.function.toString() shouldBe "set_target_addr"
      setTarget.call.arguments shouldBe
        listOf(
          MoveArgument.StringValue("domain"),
          MoveArgument.Option(MoveArgument.StringValue("sub")),
          MoveArgument.Address(AccountAddress.fromString("0xa")),
        )

      service
        .buildClearTarget(AccountAddress.ONE, "domain")
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      transactions.lastPayload
        .shouldBeInstanceOf<TransactionPayload.EntryFunction>()
        .call
        .arguments shouldBe
        listOf(
          MoveArgument.StringValue("domain"),
          MoveArgument.Option(null),
        )

      service
        .buildSetPrimary(AccountAddress.ONE)
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      transactions.lastPayload
        .shouldBeInstanceOf<TransactionPayload.EntryFunction>()
        .call
        .function
        .toString() shouldBe "clear_primary_name"
    }

    "domain and subdomain registration enforce the official expiration rules" {
      val transactions = RecordingTransactionService()
      val service =
        DefaultNameService(
          FakeNameDataSource(expiration = 1_000uL),
          transactions,
          AccountAddress.fromString("0xabc"),
        )

      service
        .buildRegister(
          sender = AccountAddress.ONE,
          name = "domain.apt",
          expiration = NameExpirationPolicy.Domain,
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val domain = transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      domain.call.function.toString() shouldBe "register_domain"
      domain.call.arguments shouldBe
        listOf(
          MoveArgument.StringValue("domain"),
          MoveArgument.U64(31_536_000u),
          MoveArgument.Option(null),
          MoveArgument.Option(null),
        )

      service
        .buildRegister(
          sender = AccountAddress.ONE,
          name = "sub.domain",
          expiration = NameExpirationPolicy.FollowDomain,
          transferable = true,
        )
        .shouldBeInstanceOf<AptosResult.Success<UnsignedTransaction.Simple>>()
      val subdomain =
        transactions.lastPayload.shouldBeInstanceOf<TransactionPayload.EntryFunction>()
      subdomain.call.function.toString() shouldBe "register_subdomain"
      subdomain.call.arguments[2] shouldBe MoveArgument.U64(1_000u)
      subdomain.call.arguments[3] shouldBe MoveArgument.U8(1u)
      subdomain.call.arguments[4] shouldBe MoveArgument.Bool(true)

      service
        .buildRegister(
          sender = AccountAddress.ONE,
          name = "sub.domain",
          expiration = NameExpirationPolicy.Independent(1_001uL),
        )
        .shouldBeInstanceOf<AptosResult.Failure>()
        .error
        .shouldBeInstanceOf<AptosError.Validation>()
    }

    "view responses decode nullable addresses, unsigned expiry, and primary names" {
      var request = 0
      val responses =
        listOf(
          """[{"vec":["0xa"]}]""",
          """[{"vec":[]}]""",
          """["18446744073709551615"]""",
          """[{"vec":["sub"]},{"vec":["domain"]}]""",
        )
      val engine =
        MockEngine { httpRequest ->
          httpRequest.url.encodedPath shouldBe "/v1/view"
          respond(
            content = responses[request++],
            status = HttpStatusCode.OK,
            headers =
              headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
          )
        }
      val client =
        HttpClient(engine) {
          install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
      val config =
        TransportConfig(
          AptosSettings(fullNode = "https://api.example.com/v1", client = client)
        )
      val dataSource =
        DefaultNameDataSource(config, AccountAddress.fromString("0xabc"))

      dataSource
        .owner(AptosName.parse("domain"))
        .shouldBeInstanceOf<AptosResult.Success<AccountAddress?>>()
        .value shouldBe AccountAddress.fromString("0xa")
      dataSource
        .target(AptosName.parse("domain"))
        .shouldBeInstanceOf<AptosResult.Success<AccountAddress?>>()
        .value shouldBe null
      dataSource
        .expiration(AptosName.parse("domain"))
        .shouldBeInstanceOf<AptosResult.Success<ULong>>()
        .value shouldBe ULong.MAX_VALUE
      dataSource
        .primaryName(AccountAddress.ONE)
        .shouldBeInstanceOf<AptosResult.Success<String?>>()
        .value shouldBe "sub.domain"

      config.close()
      client.close()
    }

    "unsupported networks return typed errors until a contract is configured" {
      val config = TransportConfig(AptosSettings(network = Network.DEVNET))
      val service = DefaultNameService(config, RecordingTransactionService())

      service
        .owner("domain")
        .shouldBeInstanceOf<AptosResult.Failure>()
        .error
        .shouldBeInstanceOf<AptosError.UnsupportedFeature>()

      config.close()
    }

    "name queries return stable typed pages and preserve the selected scope" {
      val request = PageRequest(offset = 20, limit = 10)
      val record =
        NameRecord(
          name = AptosName.parse("sub.domain"),
          expiration = null,
          domainExpiration = null,
          isActive = true,
          isPrimary = false,
          lastTransactionVersion = 9uL,
          owner = AccountAddress.ONE,
          registeredAddress = AccountAddress.ONE,
          subdomainExpirationPolicy = 0uL,
          tokenName = "sub.domain.apt",
          tokenStandard = "v2",
        )
      val page = AptosPage(listOf(record), totalCount = 35, request = request)
      val dataSource = FakeNameDataSource(queryPage = page)
      val service =
        DefaultNameService(
          dataSource,
          RecordingTransactionService(),
          AccountAddress.fromString("0xabc"),
        )

      service
        .getAccountSubdomains(AccountAddress.ONE, request)
        .shouldBeInstanceOf<AptosResult.Success<AptosPage<NameRecord>>>()
        .value
        .hasNextPage shouldBe true
      dataSource.lastQuery shouldBe NameQuery.AccountSubdomains(AccountAddress.ONE)

      service
        .getName("sub.domain.apt")
        .shouldBeInstanceOf<AptosResult.Success<NameRecord?>>()
        .value shouldBe record
      dataSource.lastQuery shouldBe NameQuery.Exact(AptosName.parse("sub.domain"))
    }

    "generated indexer rows convert to typed addresses versions and instants" {
      val record =
        GetNamesQuery.Current_aptos_name(
            domain = "domain",
            domain_expiration_timestamp = "2027-01-01T00:00:00",
            expiration_timestamp = "2026-12-01T00:00:00Z",
            is_active = true,
            is_primary = false,
            last_transaction_version = "18446744073709551615",
            owner_address = "0x1",
            registered_address = "0x2",
            subdomain = "sub",
            subdomain_expiration_policy = "1",
            token_name = "sub.domain.apt",
            token_standard = "v2",
          )
          .toRecord()

      record.name shouldBe AptosName.parse("sub.domain")
      record.expiration shouldBe Instant.parse("2026-12-01T00:00:00Z")
      record.domainExpiration shouldBe Instant.parse("2027-01-01T00:00:00Z")
      record.lastTransactionVersion shouldBe ULong.MAX_VALUE
      record.owner shouldBe AccountAddress.ONE
      record.registeredAddress shouldBe AccountAddress.fromString("0x2")
      record.subdomainExpirationPolicy shouldBe 1uL
    }
  })

private class FakeNameDataSource(
  private val expiration: ULong = 1_000uL,
  private val queryPage: AptosPage<NameRecord>? = null,
) : NameDataSource {
  var lastQuery: NameQuery? = null

  override suspend fun owner(name: AptosName): AptosResult<AccountAddress?> =
    AptosResult.Success(null)

  override suspend fun target(name: AptosName): AptosResult<AccountAddress?> =
    AptosResult.Success(null)

  override suspend fun expiration(name: AptosName): AptosResult<ULong> =
    AptosResult.Success(expiration)

  override suspend fun primaryName(
    accountAddress: AccountAddress
  ): AptosResult<String?> = AptosResult.Success(null)

  override suspend fun query(
    query: NameQuery,
    page: PageRequest,
  ): AptosResult<AptosPage<NameRecord>> {
    lastQuery = query
    return queryPage?.let { AptosResult.Success(it) }
      ?: AptosResult.Failure(AptosError.UnsupportedFeature("No query page configured"))
  }
}
