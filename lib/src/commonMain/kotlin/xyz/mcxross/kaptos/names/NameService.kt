/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.names

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xyz.mcxross.kaptos.client.postAptosFullNodeAndGetData
import xyz.mcxross.kaptos.client.getGraphqlClient
import xyz.mcxross.kaptos.account.toAptosError
import xyz.mcxross.kaptos.generated.GetNamesQuery
import xyz.mcxross.kaptos.internal.handleQuery
import xyz.mcxross.kaptos.internal.toResult
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.AptosPage
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.PageRequest
import xyz.mcxross.kaptos.model.RequestOptions
import xyz.mcxross.kaptos.model.Result
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.MoveArgument
import xyz.mcxross.kaptos.transaction.TransactionService
import xyz.mcxross.kaptos.model.types.OrderBy
import xyz.mcxross.kaptos.model.types.booleanFilter
import xyz.mcxross.kaptos.model.types.currentAptosNamesFilter
import xyz.mcxross.kaptos.model.types.currentAptosNamesOrder
import xyz.mcxross.kaptos.model.types.stringFilter
import xyz.mcxross.kaptos.util.toOptional

/** Validated Aptos Name Service name, without the optional `.apt` suffix. */
data class AptosName(
  val domain: String,
  val subdomain: String? = null,
) {
  init {
    require(isValidSegment(domain)) { invalidSegmentMessage(domain) }
    require(subdomain == null || isValidSegment(subdomain)) {
      invalidSegmentMessage(subdomain.orEmpty())
    }
  }

  val value: String
    get() = listOfNotNull(subdomain, domain).joinToString(".")

  override fun toString(): String = value

  companion object {
    fun parse(value: String): AptosName {
      val normalized = value.removeSuffix(".apt")
      val parts = normalized.split('.')
      require(parts.size in 1..2) {
        "Aptos names contain one domain and at most one subdomain"
      }
      return if (parts.size == 1) AptosName(domain = parts[0])
      else AptosName(domain = parts[1], subdomain = parts[0])
    }

    fun isValidSegment(value: String): Boolean =
      value.length in 3..63 &&
        value.firstOrNull()?.isLetterOrDigit() == true &&
        value.lastOrNull()?.isLetterOrDigit() == true &&
        value.all { it in 'a'..'z' || it.isDigit() || it == '-' }

    private fun invalidSegmentMessage(value: String): String =
      "Invalid Aptos name segment '$value'; use 3-63 lowercase letters, digits, or interior hyphens"
  }
}

/** Expiration behavior for ANS registration. */
sealed interface NameExpirationPolicy {
  /** Register a top-level domain for the currently supported one-year duration. */
  data object Domain : NameExpirationPolicy

  /** Make a subdomain expire with its parent domain. */
  data object FollowDomain : NameExpirationPolicy

  /** Give a subdomain its own expiry, expressed as Unix seconds. */
  data class Independent(val expirationTimestampSecs: ULong) : NameExpirationPolicy
}

/** Stable public ANS row; Apollo-generated response classes stay internal. */
data class NameRecord(
  val name: AptosName,
  val expiration: Instant?,
  val domainExpiration: Instant?,
  val isActive: Boolean?,
  val isPrimary: Boolean?,
  val lastTransactionVersion: ULong?,
  val owner: AccountAddress?,
  val registeredAddress: AccountAddress?,
  val subdomainExpirationPolicy: ULong?,
  val tokenName: String?,
  val tokenStandard: String?,
)

/** Aptos Name Service operations exposed as `client.names`. */
interface NameService {
  /** Resolves the current owner of [name]. */
  suspend fun owner(name: String): AptosResult<AccountAddress?>

  /** Resolves the address currently targeted by [name]. */
  suspend fun target(name: String): AptosResult<AccountAddress?>

  /** Returns the on-chain expiration timestamp in Unix seconds. */
  suspend fun expiration(name: String): AptosResult<ULong>

  /** Returns the account's primary name, if one is configured. */
  suspend fun primaryName(accountAddress: AccountAddressInput): AptosResult<String?>

  /** Returns the exact indexed name record. */
  suspend fun getName(name: String): AptosResult<NameRecord?>

  /** Returns all domains and subdomains associated with an account. */
  suspend fun getAccountNames(
    accountAddress: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosResult<AptosPage<NameRecord>>

  /** Returns top-level domains associated with an account. */
  suspend fun getAccountDomains(
    accountAddress: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosResult<AptosPage<NameRecord>>

  /** Returns subdomains associated with an account. */
  suspend fun getAccountSubdomains(
    accountAddress: AccountAddressInput,
    page: PageRequest = PageRequest(),
  ): AptosResult<AptosPage<NameRecord>>

  /** Returns indexed subdomains belonging to [domain]. */
  suspend fun getDomainSubdomains(
    domain: String,
    page: PageRequest = PageRequest(),
  ): AptosResult<AptosPage<NameRecord>>

  /** Builds a transaction that assigns [target] to [name]. */
  suspend fun buildSetTarget(
    sender: AccountAddressInput,
    name: String,
    target: AccountAddressInput,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a transaction that removes the address target from [name]. */
  suspend fun buildClearTarget(
    sender: AccountAddressInput,
    name: String,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Set a primary name, or clear it when [name] is null. */
  suspend fun buildSetPrimary(
    sender: AccountAddressInput,
    name: String? = null,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a domain or subdomain registration transaction. */
  suspend fun buildRegister(
    sender: AccountAddressInput,
    name: String,
    expiration: NameExpirationPolicy,
    transferable: Boolean = false,
    target: AccountAddressInput? = null,
    owner: AccountAddressInput? = null,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>

  /** Builds a renewal transaction for an existing name. */
  suspend fun buildRenew(
    sender: AccountAddressInput,
    name: String,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>
}

internal interface NameDataSource {
  suspend fun owner(name: AptosName): AptosResult<AccountAddress?>

  suspend fun target(name: AptosName): AptosResult<AccountAddress?>

  suspend fun expiration(name: AptosName): AptosResult<ULong>

  suspend fun primaryName(accountAddress: AccountAddress): AptosResult<String?>

  suspend fun query(
    query: NameQuery,
    page: PageRequest,
  ): AptosResult<AptosPage<NameRecord>> =
    AptosResult.Failure(AptosError.UnsupportedFeature("ANS indexer lookup is not configured"))
}

internal sealed interface NameQuery {
  data class Exact(val name: AptosName) : NameQuery

  data class Account(val address: AccountAddress) : NameQuery

  data class AccountDomains(val address: AccountAddress) : NameQuery

  data class AccountSubdomains(val address: AccountAddress) : NameQuery

  data class DomainSubdomains(val domain: String) : NameQuery
}

internal class DefaultNameService(
  private val dataSource: NameDataSource,
  private val transactions: TransactionService,
  private val contractAddress: AccountAddress?,
) : NameService {
  constructor(
    config: TransportConfig,
    transactions: TransactionService,
    contractAddress: AccountAddress? = null,
  ) : this(
    dataSource =
      (contractAddress ?: defaultContractAddress(config.network))?.let {
        DefaultNameDataSource(config, it)
      } ?: UnsupportedNameDataSource(config.network),
    transactions = transactions,
    contractAddress = contractAddress ?: defaultContractAddress(config.network),
  )

  override suspend fun owner(name: String): AptosResult<AccountAddress?> =
    withName(name, dataSource::owner)

  override suspend fun target(name: String): AptosResult<AccountAddress?> =
    withName(name, dataSource::target)

  override suspend fun expiration(name: String): AptosResult<ULong> =
    withName(name, dataSource::expiration)

  override suspend fun primaryName(
    accountAddress: AccountAddressInput
  ): AptosResult<String?> =
    try {
      dataSource.primaryName(AccountAddress.from(accountAddress))
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid account address", error))
    }

  override suspend fun getName(name: String): AptosResult<NameRecord?> =
    try {
      when (val page = dataSource.query(NameQuery.Exact(AptosName.parse(name)), PageRequest(limit = 1))) {
        is AptosResult.Failure -> page
        is AptosResult.Success -> AptosResult.Success(page.value.items.firstOrNull())
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid Aptos name", error))
    }

  override suspend fun getAccountNames(
    accountAddress: AccountAddressInput,
    page: PageRequest,
  ): AptosResult<AptosPage<NameRecord>> =
    queryAccount(accountAddress, page, NameQuery::Account)

  override suspend fun getAccountDomains(
    accountAddress: AccountAddressInput,
    page: PageRequest,
  ): AptosResult<AptosPage<NameRecord>> =
    queryAccount(accountAddress, page, NameQuery::AccountDomains)

  override suspend fun getAccountSubdomains(
    accountAddress: AccountAddressInput,
    page: PageRequest,
  ): AptosResult<AptosPage<NameRecord>> =
    queryAccount(accountAddress, page, NameQuery::AccountSubdomains)

  override suspend fun getDomainSubdomains(
    domain: String,
    page: PageRequest,
  ): AptosResult<AptosPage<NameRecord>> =
    try {
      val parsed = AptosName.parse(domain)
      if (parsed.subdomain != null) {
        AptosResult.Failure(
          AptosError.Validation("getDomainSubdomains requires a top-level domain")
        )
      } else {
        dataSource.query(NameQuery.DomainSubdomains(parsed.domain), page)
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid Aptos domain", error))
    }

  override suspend fun buildSetTarget(
    sender: AccountAddressInput,
    name: String,
    target: AccountAddressInput,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildWithName(sender, name, "set_target_addr", options) { parsed ->
      parsed.arguments() + MoveArgument.Address(AccountAddress.from(target))
    }

  override suspend fun buildClearTarget(
    sender: AccountAddressInput,
    name: String,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    buildWithName(sender, name, "clear_target_addr", options) { it.arguments() }

  override suspend fun buildSetPrimary(
    sender: AccountAddressInput,
    name: String?,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    if (name == null) {
      build(sender, "clear_primary_name", emptyList(), options)
    } else {
      buildWithName(sender, name, "set_primary_name", options) { it.arguments() }
    }

  override suspend fun buildRegister(
    sender: AccountAddressInput,
    name: String,
    expiration: NameExpirationPolicy,
    transferable: Boolean,
    target: AccountAddressInput?,
    owner: AccountAddressInput?,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    try {
      val parsed = AptosName.parse(name)
      val targetArgument = MoveArgument.Option(target?.let { MoveArgument.Address(AccountAddress.from(it)) })
      val ownerArgument = MoveArgument.Option(owner?.let { MoveArgument.Address(AccountAddress.from(it)) })
      when (expiration) {
        NameExpirationPolicy.Domain -> {
          if (parsed.subdomain != null) {
            return AptosResult.Failure(
              AptosError.Validation("Subdomains require a subdomain expiration policy")
            )
          }
          build(
            sender = sender,
            function = "register_domain",
            arguments =
              listOf(
                MoveArgument.StringValue(parsed.domain),
                MoveArgument.U64(SECONDS_PER_YEAR),
                targetArgument,
                ownerArgument,
              ),
            options = options,
          )
        }
        NameExpirationPolicy.FollowDomain,
        is NameExpirationPolicy.Independent -> {
          val subdomain =
            parsed.subdomain
              ?: return AptosResult.Failure(
                AptosError.Validation("A subdomain expiration policy requires a subdomain")
              )
          val parentExpiration =
            when (val result = dataSource.expiration(AptosName(parsed.domain))) {
              is AptosResult.Failure -> return result
              is AptosResult.Success -> result.value
            }
          val expirationTimestamp =
            if (expiration is NameExpirationPolicy.Independent) {
              expiration.expirationTimestampSecs
            } else {
              parentExpiration
            }
          if (expirationTimestamp > parentExpiration) {
            return AptosResult.Failure(
              AptosError.Validation(
                "Subdomain expiration cannot be later than its parent domain"
              )
            )
          }
          build(
            sender = sender,
            function = "register_subdomain",
            arguments =
              listOf(
                MoveArgument.StringValue(parsed.domain),
                MoveArgument.StringValue(subdomain),
                MoveArgument.U64(expirationTimestamp),
                MoveArgument.U8(if (expiration is NameExpirationPolicy.FollowDomain) 1u else 0u),
                MoveArgument.Bool(transferable),
                targetArgument,
                ownerArgument,
              ),
            options = options,
          )
        }
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid ANS registration", error))
    }

  override suspend fun buildRenew(
    sender: AccountAddressInput,
    name: String,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    try {
      val parsed = AptosName.parse(name)
      if (parsed.subdomain != null) {
        AptosResult.Failure(AptosError.Validation("Subdomains cannot be renewed"))
      } else {
        build(
          sender = sender,
          function = "renew_domain",
          arguments =
            listOf(
              MoveArgument.StringValue(parsed.domain),
              MoveArgument.U64(SECONDS_PER_YEAR),
            ),
          options = options,
        )
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid ANS renewal", error))
    }

  private suspend fun buildWithName(
    sender: AccountAddressInput,
    name: String,
    function: String,
    options: TransactionOptions,
    arguments: (AptosName) -> List<MoveArgument>,
  ): AptosResult<UnsignedTransaction.Simple> =
    try {
      build(sender, function, arguments(AptosName.parse(name)), options)
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid Aptos name", error))
    }

  private suspend fun build(
    sender: AccountAddressInput,
    function: String,
    arguments: List<MoveArgument>,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> =
    try {
      val configuredContract =
        contractAddress
          ?: return AptosResult.Failure(unsupportedNetworkError())
      transactions.build(
        sender = sender,
        payload =
          TransactionPayload.entryFunction(
            function = "$configuredContract::router::$function",
            arguments = arguments,
          ),
        options = options,
      )
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid ANS transaction", error))
    }

  private suspend fun <T> withName(
    name: String,
    block: suspend (AptosName) -> AptosResult<T>,
  ): AptosResult<T> =
    try {
      block(AptosName.parse(name))
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid Aptos name", error))
    }

  private suspend fun queryAccount(
    accountAddress: AccountAddressInput,
    page: PageRequest,
    query: (AccountAddress) -> NameQuery,
  ): AptosResult<AptosPage<NameRecord>> =
    try {
      dataSource.query(query(AccountAddress.from(accountAddress)), page)
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Validation("Invalid account address", error))
    }

  private fun AptosName.arguments(): List<MoveArgument> =
    listOf(
      MoveArgument.StringValue(domain),
      MoveArgument.Option(subdomain?.let(MoveArgument::StringValue)),
    )

  private companion object {
    const val SECONDS_PER_YEAR: ULong = 31_536_000u
  }
}

private class UnsupportedNameDataSource(
  private val network: Network,
) : NameDataSource {
  override suspend fun owner(name: AptosName): AptosResult<AccountAddress?> = failure()

  override suspend fun target(name: AptosName): AptosResult<AccountAddress?> = failure()

  override suspend fun expiration(name: AptosName): AptosResult<ULong> = failure()

  override suspend fun primaryName(
    accountAddress: AccountAddress
  ): AptosResult<String?> = failure()

  override suspend fun query(
    query: NameQuery,
    page: PageRequest,
  ): AptosResult<AptosPage<NameRecord>> = failure()

  private fun <T> failure(): AptosResult<T> = AptosResult.Failure(unsupportedNetworkError(network))
}

internal class DefaultNameDataSource(
  private val config: TransportConfig,
  private val contractAddress: AccountAddress,
) : NameDataSource {
  override suspend fun owner(name: AptosName): AptosResult<AccountAddress?> =
    addressOption("get_owner_addr", name)

  override suspend fun target(name: AptosName): AptosResult<AccountAddress?> =
    addressOption("get_target_addr", name)

  override suspend fun expiration(name: AptosName): AptosResult<ULong> =
    when (val result = view<List<String>>("get_expiration", name.viewArguments())) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        result.value.singleOrNull()?.toULongOrNull()?.let { AptosResult.Success(it) }
          ?: AptosResult.Failure(
            AptosError.Serialization("Invalid ANS expiration view response")
          )
    }

  override suspend fun primaryName(
    accountAddress: AccountAddress
  ): AptosResult<String?> =
    when (
      val result =
        view<List<MoveOptionWire<String>>>(
          "get_primary_name",
          listOf(JsonPrimitive(accountAddress.toString())),
        )
    ) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        try {
          require(result.value.size == 2) { "Expected domain and subdomain options" }
          val subdomain = result.value[0].vec.singleOrNull()
          val domain = result.value[1].vec.singleOrNull()
          AptosResult.Success(domain?.let { listOfNotNull(subdomain, it).joinToString(".") })
        } catch (error: Throwable) {
          AptosResult.Failure(
            AptosError.Serialization("Invalid ANS primary-name view response", error)
          )
        }
    }

  override suspend fun query(
    query: NameQuery,
    page: PageRequest,
  ): AptosResult<AptosPage<NameRecord>> {
    val filter =
      currentAptosNamesFilter {
        isActive = booleanFilter { eq = true }
        when (query) {
          is NameQuery.Exact -> {
            domain = stringFilter { eq = query.name.domain }
            subdomain = stringFilter { eq = query.name.subdomain.orEmpty() }
          }
          is NameQuery.Account ->
            ownerAddress = stringFilter { eq = query.address.toString() }
          is NameQuery.AccountDomains -> {
            ownerAddress = stringFilter { eq = query.address.toString() }
            subdomain = stringFilter { eq = "" }
          }
          is NameQuery.AccountSubdomains -> {
            ownerAddress = stringFilter { eq = query.address.toString() }
            subdomain = stringFilter { neq = "" }
          }
          is NameQuery.DomainSubdomains -> {
            domain = stringFilter { eq = query.domain }
            subdomain = stringFilter { neq = "" }
          }
        }
      }
    val order =
      currentAptosNamesOrder { lastTransactionVersion = OrderBy.DESC }
    return when (
      val result =
        handleQuery {
            getGraphqlClient(config)
              .query(
                GetNamesQuery(
                  where_condition = filter.toOptional(),
                  offset = page.offset.toOptional(),
                  limit = page.limit.toOptional(),
                  order_by = listOf(order).toOptional(),
                )
              )
          }
          .toResult()
    ) {
      is Result.Err -> AptosResult.Failure(result.error.toAptosError())
      is Result.Ok ->
        try {
          val data =
            result.value
              ?: return AptosResult.Failure(
                AptosError.Serialization("ANS indexer returned no data")
              )
          AptosResult.Success(
            AptosPage(
              items = data.current_aptos_names.map(GetNamesQuery.Current_aptos_name::toRecord),
              totalCount = data.current_aptos_names_aggregate.aggregate?.count ?: 0,
              request = page,
            )
          )
        } catch (error: Throwable) {
          AptosResult.Failure(AptosError.Serialization("Invalid ANS indexer row", error))
        }
    }
  }

  private suspend fun addressOption(
    function: String,
    name: AptosName,
  ): AptosResult<AccountAddress?> =
    when (val result = view<List<MoveOptionWire<String>>>(function, name.viewArguments())) {
      is AptosResult.Failure -> result
      is AptosResult.Success ->
        try {
          val option = result.value.single().vec
          require(option.size <= 1) { "Invalid Move option" }
          AptosResult.Success(option.singleOrNull()?.let(AccountAddress::fromString))
        } catch (error: Throwable) {
          AptosResult.Failure(AptosError.Serialization("Invalid ANS address response", error))
        }
    }

  private suspend inline fun <reified T> view(
    function: String,
    arguments: List<JsonElement>,
  ): AptosResult<T> =
    when (
      val result =
        postAptosFullNodeAndGetData<T, NameViewRequest>(
            RequestOptions.PostAptosRequestOptions(
              aptosConfig = config,
              originMethod = "names.$function",
              path = "view",
              body =
                NameViewRequest(
                  function = "$contractAddress::router::$function",
                  arguments = arguments,
                ),
            )
          )
          .toResult()
    ) {
      is Result.Err -> AptosResult.Failure(result.error.toAptosError())
      is Result.Ok -> AptosResult.Success(result.value)
    }

  private fun AptosName.viewArguments(): List<JsonElement> =
    listOf(JsonPrimitive(domain), subdomain?.let(::JsonPrimitive) ?: JsonNull)
}

@Serializable
private data class NameViewRequest(
  val function: String,
  @SerialName("type_arguments") val typeArguments: List<String> = emptyList(),
  val arguments: List<JsonElement>,
)

@Serializable
private data class MoveOptionWire<T>(val vec: List<T>)

internal fun GetNamesQuery.Current_aptos_name.toRecord(): NameRecord {
  val domainName = requireNotNull(domain) { "ANS row is missing its domain" }
  return NameRecord(
    name = AptosName(domain = domainName, subdomain = subdomain?.ifEmpty { null }),
    expiration = expiration_timestamp.toInstantOrNull(),
    domainExpiration = domain_expiration_timestamp.toInstantOrNull(),
    isActive = is_active,
    isPrimary = is_primary,
    lastTransactionVersion = last_transaction_version.toU64OrNull(),
    owner = owner_address?.let(AccountAddress::fromString),
    registeredAddress = registered_address?.let(AccountAddress::fromString),
    subdomainExpirationPolicy = subdomain_expiration_policy.toU64OrNull(),
    tokenName = token_name,
    tokenStandard = token_standard,
  )
}

private fun Any?.toU64OrNull(): ULong? {
  val raw = this?.toString()?.trim('"') ?: return null
  return raw.toULongOrNull() ?: throw IllegalArgumentException("Invalid Aptos u64: $raw")
}

private fun Any?.toInstantOrNull(): Instant? {
  val raw = this?.toString()?.trim('"') ?: return null
  val hasOffset = raw.endsWith('Z') || OFFSET_SUFFIX.containsMatchIn(raw)
  return Instant.parse(if (hasOffset) raw else "${raw}Z")
}

private val OFFSET_SUFFIX = Regex("[+-]\\d{2}:\\d{2}$")

private fun defaultContractAddress(network: Network): AccountAddress? =
  when (network) {
    Network.MAINNET ->
      AccountAddress.fromString(
        "0x867ed1f6bf916171b1de3ee92849b8978b7d1b9e0a8cc982a3d19d535dfd9c0c"
      )
    Network.TESTNET ->
      AccountAddress.fromString(
        "0x5f8fd2347449685cf41d4db97926ec3a096eaf381332be4f1318ad4d16a8497c"
      )
    Network.LOCAL ->
      AccountAddress.fromString(
        "0x585fc9f0f0c54183b039ffc770ca282ebd87307916c215a3e692f2f8e4305e82"
      )
    else -> null
  }

private fun unsupportedNetworkError(network: Network? = null): AptosError.UnsupportedFeature =
  AptosError.UnsupportedFeature(
    "ANS is not configured${network?.let { " for $it" }.orEmpty()}; provide namesContractAddress"
  )
