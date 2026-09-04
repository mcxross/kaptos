/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.keyless

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Ed25519Signature
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.model.TransactionOptions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.model.UnsignedTransaction
import xyz.mcxross.kaptos.transaction.MoveArgument
import xyz.mcxross.kaptos.util.NetworkToNodeAPI

/** Optional URL and headers for one Keyless backend. */
data class KeylessEndpointConfig(
  val url: String? = null,
  val headers: Map<String, String> = emptyMap(),
)

/** Pepper/prover endpoints and transport ownership for [KeylessService]. */
data class KeylessClientConfig(
  val pepperService: KeylessEndpointConfig = KeylessEndpointConfig(),
  val proverService: KeylessEndpointConfig = KeylessEndpointConfig(),
  val commonHeaders: Map<String, String> = emptyMap(),
  val httpClient: HttpClient? = null,
)

/** Controls whether account derivation waits for its Groth16 proof. */
enum class ProofFetchMode {
  Await,
  Background,
}

/** Receives proof-fetch lifecycle changes when background fetching is enabled. */
typealias ProofFetchCallback = suspend (ProofFetchStatus) -> Unit

/** Standard and federated Aptos Keyless account lifecycle operations. */
interface KeylessService : AutoCloseable {
  /** Obtains the account pepper from the configured pepper service. */
  suspend fun getPepper(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    uidKey: String = "sub",
    derivationPath: String? = null,
  ): AptosResult<ByteArray>

  /** Obtains `pepper_base` for federated derivation and proof inputs. */
  suspend fun getPepperBase(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    uidKey: String = "sub",
    derivationPath: String? = null,
  ): AptosResult<ByteArray>

  /** Obtains and decodes a Groth16 proof from the configured prover. */
  suspend fun getProof(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    pepper: ByteArray? = null,
    uidKey: String = "sub",
  ): AptosResult<ZeroKnowledgeSignature>

  /** Derives a standard Keyless account, optionally fetching its proof in the background. */
  suspend fun deriveStandardAccount(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    pepper: ByteArray? = null,
    uidKey: String = "sub",
    proofFetch: ProofFetchMode = ProofFetchMode.Await,
    onProofFetch: ProofFetchCallback? = null,
  ): AptosResult<KeylessAccount>

  /** Derives a federated Keyless account controlled by the JWK registry at [jwkAddress]. */
  suspend fun deriveFederatedAccount(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    jwkAddress: AccountAddressInput,
    pepper: ByteArray? = null,
    uidKey: String = "sub",
    proofFetch: ProofFetchMode = ProofFetchMode.Await,
    onProofFetch: ProofFetchCallback? = null,
  ): AptosResult<FederatedKeylessAccount>

  /** Fetches the on-chain Keyless configuration, optionally bypassing the cache. */
  suspend fun configuration(refresh: Boolean = false): AptosResult<KeylessConfiguration>

  /** Fetches framework or federated JWKs, optionally bypassing the cache. */
  suspend fun jwks(
    jwkAddress: AccountAddressInput? = null,
    refresh: Boolean = false,
  ): AptosResult<Map<String, List<MoveJwk>>>

  /** Verifies a Keyless signature locally against a selected JWK and on-chain configuration. */
  suspend fun verify(
    publicKey: xyz.mcxross.kaptos.core.crypto.AccountPublicKey,
    message: ByteArray,
    signature: KeylessSignature,
    jwk: MoveJwk? = null,
    configuration: KeylessConfiguration? = null,
  ): AptosResult<Boolean>

  /** Validates proof presence, expiry, ephemeral-key expiry, and on-chain configuration limits. */
  suspend fun checkValidity(account: AbstractKeylessAccount): AptosResult<Unit>

  /** Builds a federated JWK update transaction for [issuer]. */
  suspend fun buildFederatedJwkUpdate(
    sender: AccountAddressInput,
    issuer: String,
    jwksUrl: String? = null,
    options: TransactionOptions = TransactionOptions(),
  ): AptosResult<UnsignedTransaction.Simple>
}

/** Concise factory: `val keyless = client.keyless()`; it reuses the client's shared transport. */
fun Aptos.keyless(config: KeylessClientConfig = KeylessClientConfig()): KeylessService =
  own(DefaultKeylessService(this, config))

internal class DefaultKeylessService(
  private val aptos: Aptos,
  private val serviceConfig: KeylessClientConfig,
) : KeylessService {
  private val httpClient =
    serviceConfig.httpClient ?: aptos.transportClient
  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
  private var cachedConfiguration: KeylessConfiguration? = null
  private val cachedJwks = mutableMapOf<String, Map<String, List<MoveJwk>>>()

  override suspend fun getPepper(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    uidKey: String,
    derivationPath: String?,
  ): AptosResult<ByteArray> =
    postSensitive<PepperRequest, PepperResponse>(
      endpoint = ::pepperUrl,
      path = "fetch",
      endpointHeaders = serviceConfig.pepperService.headers,
      body = pepperRequest(jwt, ephemeralKeyPair, uidKey, derivationPath),
    ).flatMap { response ->
      cryptoResult("Pepper service returned invalid pepper") {
        decodeHex(response.pepper).also {
          require(it.size == KEYLESS_PEPPER_LENGTH) {
            "Pepper must be $KEYLESS_PEPPER_LENGTH bytes"
          }
        }
      }
    }

  override suspend fun getPepperBase(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    uidKey: String,
    derivationPath: String?,
  ): AptosResult<ByteArray> =
    postSensitive<PepperRequest, PepperBaseResponse>(
      endpoint = ::pepperUrl,
      path = "signature",
      endpointHeaders = serviceConfig.pepperService.headers,
      body = pepperRequest(jwt, ephemeralKeyPair, uidKey, derivationPath),
    ).flatMap { response ->
      cryptoResult("Pepper service returned invalid pepper_base") {
        decodeHex(response.signature).also { require(it.size == 48) { "pepper_base must be 48 bytes" } }
      }
    }

  override suspend fun getProof(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    pepper: ByteArray?,
    uidKey: String,
  ): AptosResult<ZeroKnowledgeSignature> {
    val actualPepper =
      pepper?.let { AptosResult.Success(it.copyOf()) }
        ?: getPepper(jwt, ephemeralKeyPair, uidKey)
    if (actualPepper is AptosResult.Failure) return actualPepper
    val pepperBytes = (actualPepper as AptosResult.Success).value
    if (pepperBytes.size != KEYLESS_PEPPER_LENGTH) {
      return AptosResult.Failure(
        AptosError.Validation("pepper must be $KEYLESS_PEPPER_LENGTH bytes")
      )
    }
    val config = configuration()
    if (config is AptosResult.Failure) return config
    val keylessConfig = (config as AptosResult.Success).value
    val claims =
      try {
        parseJwt(jwt, uidKey)
      } catch (error: Throwable) {
        return AptosResult.Failure(AptosError.Validation("Invalid JWT", error))
      }
    val issuedAt =
      claims.issuedAt
        ?: return AptosResult.Failure(AptosError.Validation("JWT is missing numeric 'iat'"))
    if (issuedAt < 0 || ephemeralKeyPair.expiryDateSecs > Long.MAX_VALUE.toULong()) {
      return AptosResult.Failure(AptosError.Validation("JWT/ephemeral expiry is out of range"))
    }
    val lifespan = ephemeralKeyPair.expiryDateSecs.toLong() - issuedAt
    if (lifespan < 0 || lifespan.toULong() > keylessConfig.maxExpirationHorizonSecs) {
      return AptosResult.Failure(
        AptosError.Validation("Ephemeral key lifespan exceeds the on-chain Keyless maximum")
      )
    }

    return postSensitive<ProverRequest, ProverResponse>(
      endpoint = ::proverUrl,
      path = "prove",
      endpointHeaders = serviceConfig.proverService.headers,
      body =
        ProverRequest(
          jwt = jwt,
          ephemeralPublicKey = ephemeralKeyPair.ephemeralPublicKeyBcs().hex(),
          expiryDateSecs = ephemeralKeyPair.expiryDateSecs,
          expirationHorizonSecs = keylessConfig.maxExpirationHorizonSecs,
          blinder = ephemeralKeyPair.blinder.hex(),
          uidKey = uidKey,
          pepper = pepperBytes.hex(),
        ),
    ).flatMap { response ->
      cryptoResult("Prover returned an invalid Keyless proof") {
        val trainingBytes = decodeHex(response.trainingWheelsSignature)
        val trainingReader = KeylessBcsReader(trainingBytes)
        require(trainingReader.uleb128() == 0u) { "Only Ed25519 training signatures are supported" }
        val trainingSignature = Ed25519Signature(trainingReader.bytes())
        trainingReader.ensureFinished()
        ZeroKnowledgeSignature(
          proof =
            Groth16Proof(
              decodeHex(response.proof.a),
              decodeHex(response.proof.b),
              decodeHex(response.proof.c),
            ),
          expirationHorizonSecs = keylessConfig.maxExpirationHorizonSecs,
          trainingWheelsSignature = trainingSignature,
        )
      }
    }
  }

  override suspend fun deriveStandardAccount(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    pepper: ByteArray?,
    uidKey: String,
    proofFetch: ProofFetchMode,
    onProofFetch: ProofFetchCallback?,
  ): AptosResult<KeylessAccount> =
    derive(
        jwt,
        ephemeralKeyPair,
        pepper,
        uidKey,
        proofFetch,
        onProofFetch,
        null,
      )
      .flatMap { account ->
        if (account is KeylessAccount) AptosResult.Success(account)
        else AptosResult.Failure(AptosError.Serialization("Expected a standard Keyless account"))
      }
      .also { result ->
        if (result is AptosResult.Success) aptos.own(result.value)
      }

  override suspend fun deriveFederatedAccount(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    jwkAddress: AccountAddressInput,
    pepper: ByteArray?,
    uidKey: String,
    proofFetch: ProofFetchMode,
    onProofFetch: ProofFetchCallback?,
  ): AptosResult<FederatedKeylessAccount> {
    val parsedAddress =
      try {
        AccountAddress.from(jwkAddress)
      } catch (error: Throwable) {
        return AptosResult.Failure(AptosError.Validation("Invalid federated JWK address", error))
      }
    return derive(
        jwt,
        ephemeralKeyPair,
        pepper,
        uidKey,
        proofFetch,
        onProofFetch,
        parsedAddress,
      )
      .flatMap { account ->
        if (account is FederatedKeylessAccount) AptosResult.Success(account)
        else AptosResult.Failure(AptosError.Serialization("Expected a federated Keyless account"))
      }
      .also { result ->
        if (result is AptosResult.Success) aptos.own(result.value)
      }
  }

  override suspend fun configuration(refresh: Boolean): AptosResult<KeylessConfiguration> {
    if (!refresh) cachedConfiguration?.let { return AptosResult.Success(it) }
    val config = fullnodeGet<MoveResource<KeylessConfigurationResponse>>(
      "accounts/0x1/resource/0x1::keyless_account::Configuration"
    )
    if (config is AptosResult.Failure) return config
    val vk = fullnodeGet<MoveResource<Groth16VerificationKeyResponse>>(
      "accounts/0x1/resource/0x1::keyless_account::Groth16VerificationKey"
    )
    if (vk is AptosResult.Failure) return vk
    return cryptoResult("Invalid on-chain Keyless configuration") {
      val configData = (config as AptosResult.Success).value.data
      val vkData = (vk as AptosResult.Success).value.data
      KeylessConfiguration(
        verificationKey =
          Groth16VerificationKey(
            alphaG1 = decodeHex(vkData.alphaG1),
            betaG2 = decodeHex(vkData.betaG2),
            deltaG2 = decodeHex(vkData.deltaG2),
            gammaAbcG1 = vkData.gammaAbcG1.map(::decodeHex),
            gammaG2 = decodeHex(vkData.gammaG2),
          ),
        maxExpirationHorizonSecs = vkU64(configData.maxExpirationHorizonSecs),
        trainingWheelsPublicKey =
          configData.trainingWheelsPublicKey.values.firstOrNull()?.let(::decodeHex)
            ?.let(::Ed25519PublicKey),
        maxExtraFieldBytes = configData.maxExtraFieldBytes,
        maxJwtHeaderBase64Bytes = configData.maxJwtHeaderBase64Bytes,
        maxIssuerBytes = configData.maxIssuerBytes,
        maxCommittedEphemeralPublicKeyBytes = configData.maxCommittedEphemeralPublicKeyBytes,
      ).also { cachedConfiguration = it }
    }
  }

  override suspend fun jwks(
    jwkAddress: AccountAddressInput?,
    refresh: Boolean,
  ): AptosResult<Map<String, List<MoveJwk>>> {
    val address =
      try {
        jwkAddress?.let(AccountAddress::from)
      } catch (error: Throwable) {
        return AptosResult.Failure(AptosError.Validation("Invalid federated JWK address", error))
      }
    val cacheKey = address?.toString() ?: "0x1"
    if (!refresh) cachedJwks[cacheKey]?.let { return AptosResult.Success(it) }
    val resourceName = if (address == null) "PatchedJWKs" else "FederatedJWKs"
    val resource =
      fullnodeGet<MoveResource<JwksResource>>(
        "accounts/${address ?: "0x1"}/resource/0x1::jwks::$resourceName"
      )
    return resource.flatMap { response ->
      cryptoResult("Invalid on-chain JWK resource") {
        response.data.jwks.entries.associate { entry ->
          val issuer = decodeHex(entry.issuer).decodeToString(throwOnInvalidSequence = true)
          issuer to entry.jwks.map { MoveJwk.fromBcs(decodeHex(it.variant.data)) }
        }.also { cachedJwks[cacheKey] = it }
      }
    }
  }

  override suspend fun verify(
    publicKey: xyz.mcxross.kaptos.core.crypto.AccountPublicKey,
    message: ByteArray,
    signature: KeylessSignature,
    jwk: MoveJwk?,
    configuration: KeylessConfiguration?,
  ): AptosResult<Boolean> {
    val actualConfiguration =
      configuration?.let { AptosResult.Success(it) } ?: configuration()
    if (actualConfiguration is AptosResult.Failure) return actualConfiguration
    val actualJwk =
      jwk?.let { AptosResult.Success(it) } ?: findJwk(publicKey, signature.keyId)
    if (actualJwk is AptosResult.Failure) return actualJwk
    val now = Clock.System.now().epochSeconds
    return AptosResult.Success(
      verifyKeylessSignature(
        publicKey,
        message,
        signature,
        (actualJwk as AptosResult.Success).value,
        (actualConfiguration as AptosResult.Success).value,
        now.coerceAtLeast(0).toULong(),
      )
    )
  }

  override suspend fun checkValidity(account: AbstractKeylessAccount): AptosResult<Unit> {
    if (account.isExpired()) {
      return AptosResult.Failure(AptosError.Crypto("Keyless account's ephemeral key has expired"))
    }
    val proof = account.awaitProof()
    if (proof is AptosResult.Failure) return proof.mapFailure()
    val config = configuration()
    if (config is AptosResult.Failure) return config
    account.verificationKeyHash?.let { expected ->
      if (!expected.contentEquals((config as AptosResult.Success).value.verificationKey.hash())) {
        return AptosResult.Failure(
          AptosError.Crypto("The on-chain Keyless verification key has rotated")
        )
      }
    }
    val claims =
      try {
        parseJwt(account.jwt, account.uidKey)
      } catch (error: Throwable) {
        return AptosResult.Failure(AptosError.Validation("Invalid account JWT", error))
      }
    return findJwk(account.publicKey, claims.keyId).flatMap { AptosResult.Success(Unit) }
  }

  override suspend fun buildFederatedJwkUpdate(
    sender: AccountAddressInput,
    issuer: String,
    jwksUrl: String?,
    options: TransactionOptions,
  ): AptosResult<UnsignedTransaction.Simple> {
    val url = jwksUrl ?: defaultJwksUrl(issuer)
    val parsed =
      try {
        Url(url)
      } catch (error: Throwable) {
        return AptosResult.Failure(AptosError.Validation("Invalid JWKS URL", error))
      }
    if (parsed.protocol.name != "https") {
      return AptosResult.Failure(AptosError.Validation("Federated JWKS URL must use HTTPS"))
    }
    val keys =
      try {
        val response = httpClient.get(parsed)
        if (!response.status.isSuccess()) {
          return AptosResult.Failure(
            AptosError.Api("JWKS request failed", response.status.value.toString())
          )
        }
        response.body<ExternalJwks>().keys
      } catch (error: Throwable) {
        return AptosResult.Failure(AptosError.Transport("Unable to fetch federated JWKS", error))
      }
    if (keys.isEmpty() || keys.size > 32 || keys.any { !it.isValid() }) {
      return AptosResult.Failure(
        AptosError.Validation("JWKS must contain 1..32 complete RSA keys")
      )
    }
    val payload =
      TransactionPayload.entryFunction(
        function = "0x1::jwks::update_federated_jwk_set",
        arguments =
          listOf(
            MoveArgument.StringValue(issuer),
            keys.moveStrings { keyId },
            keys.moveStrings { algorithm },
            keys.moveStrings { exponent },
            keys.moveStrings { modulus },
          ),
      )
    return aptos.transactions.build(sender, payload, options)
  }

  override fun close() {
    scope.cancel()
  }

  private suspend fun derive(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    pepper: ByteArray?,
    uidKey: String,
    proofFetch: ProofFetchMode,
    onProofFetch: ProofFetchCallback?,
    jwkAddress: AccountAddress?,
  ): AptosResult<AbstractKeylessAccount> {
    val actualPepper =
      pepper?.let { AptosResult.Success(it.copyOf()) }
        ?: getPepper(jwt, ephemeralKeyPair, uidKey)
    if (actualPepper is AptosResult.Failure) return actualPepper
    val pepperBytes = (actualPepper as AptosResult.Success).value
    val claims =
      try {
        parseJwt(jwt, uidKey)
      } catch (error: Throwable) {
        return AptosResult.Failure(AptosError.Validation("Invalid JWT", error))
      }
    val config = configuration()
    if (config is AptosResult.Failure) return config
    val verificationKeyHash = (config as AptosResult.Success).value.verificationKey.hash()
    val pendingProof = scope.async { getProof(jwt, ephemeralKeyPair, pepperBytes, uidKey) }
    val proof = if (proofFetch == ProofFetchMode.Await) pendingProof.await() else null
    if (proof is AptosResult.Failure) return proof
    if (proofFetch == ProofFetchMode.Background && onProofFetch != null) {
      scope.launch {
        when (val result = pendingProof.await()) {
          is AptosResult.Success -> onProofFetch(ProofFetchStatus.Success)
          is AptosResult.Failure -> onProofFetch(ProofFetchStatus.Failed(result.error))
        }
      }
    }
    val key =
      if (jwkAddress == null) KeylessPublicKey.fromJwt(jwt, pepperBytes, uidKey)
      else FederatedKeylessPublicKey.fromJwt(jwt, pepperBytes, jwkAddress, uidKey)
    val originalAddress = lookupOriginalAddress(key.authKey().deriveAddress())
    if (originalAddress is AptosResult.Failure) return originalAddress
    val address = (originalAddress as AptosResult.Success).value
    val resolvedProof = (proof as? AptosResult.Success)?.value
    return AptosResult.Success(
      if (key is FederatedKeylessPublicKey) {
        FederatedKeylessAccount(
          key,
          address,
          jwt,
          uidKey,
          claims.uid,
          claims.audience,
          pepperBytes,
          ephemeralKeyPair,
          resolvedProof,
          if (proofFetch == ProofFetchMode.Background) pendingProof else null,
          verificationKeyHash,
        )
      } else {
        KeylessAccount(
          key as KeylessPublicKey,
          address,
          jwt,
          uidKey,
          claims.uid,
          claims.audience,
          pepperBytes,
          ephemeralKeyPair,
          resolvedProof,
          if (proofFetch == ProofFetchMode.Background) pendingProof else null,
          verificationKeyHash,
        )
      }
    )
  }

  private suspend fun findJwk(
    publicKey: xyz.mcxross.kaptos.core.crypto.AccountPublicKey,
    keyId: String,
  ): AptosResult<MoveJwk> {
    val issuer: String
    val address: AccountAddress?
    when (publicKey) {
      is KeylessPublicKey -> {
        issuer = publicKey.issuer
        address = null
      }
      is FederatedKeylessPublicKey -> {
        issuer = publicKey.keylessPublicKey.issuer
        address = publicKey.jwkAddress
      }
      else -> return AptosResult.Failure(AptosError.Validation("Not a Keyless public key"))
    }
    return jwks(address).flatMap { values ->
      values[issuer]?.firstOrNull { it.keyId == keyId }?.let { AptosResult.Success(it) }
        ?: AptosResult.Failure(
          AptosError.Crypto("No JWK '$keyId' is registered for issuer '$issuer'")
        )
    }
  }

  private suspend fun lookupOriginalAddress(authenticationKey: AccountAddress): AptosResult<AccountAddress> {
    val originating =
      fullnodeGet<MoveResource<OriginatingAddress>>(
        "accounts/0x1/resource/0x1::account::OriginatingAddress"
      )
    if (originating is AptosResult.Failure) return originating
    val handle = (originating as AptosResult.Success).value.data.addressMap.handle
    return try {
      val response =
        httpClient.post("${fullnodeUrl()}/tables/$handle/item") {
          contentType(ContentType.Application.Json)
          fullnodeHeaders().forEach { (name, value) -> headers.append(name, value) }
          setBody(
            TableRequest(
              keyType = "address",
              valueType = "address",
              key = authenticationKey.toString(),
            )
          )
        }
      if (response.status == HttpStatusCode.NotFound) AptosResult.Success(authenticationKey)
      else if (!response.status.isSuccess()) {
        AptosResult.Failure(AptosError.Api("Originating-address lookup failed", response.status.value.toString()))
      } else {
        AptosResult.Success(AccountAddress.fromString(response.body<JsonPrimitive>().content))
      }
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Transport("Unable to look up original account address", error))
    }
  }

  private suspend inline fun <reified T> fullnodeGet(path: String): AptosResult<T> =
    try {
      val response = httpClient.get("${fullnodeUrl()}/$path") {
        fullnodeHeaders().forEach { (name, value) -> headers.append(name, value) }
      }
      if (!response.status.isSuccess()) {
        AptosResult.Failure(AptosError.Api("Fullnode request failed", response.status.value.toString()))
      } else AptosResult.Success(response.body())
    } catch (error: Throwable) {
      AptosResult.Failure(AptosError.Transport("Fullnode request failed", error))
    }

  private suspend inline fun <reified Request : Any, reified Response> postSensitive(
    endpoint: () -> String,
    path: String,
    endpointHeaders: Map<String, String>,
    body: Request,
  ): AptosResult<Response> =
    try {
      val response = httpClient.post("${endpoint().trimEnd('/')}/$path") {
        contentType(ContentType.Application.Json)
        serviceConfig.commonHeaders.forEach { (name, value) -> headers.append(name, value) }
        endpointHeaders.forEach { (name, value) ->
          headers.remove(name)
          headers.append(name, value)
        }
        setBody(body)
      }
      if (!response.status.isSuccess()) {
        AptosResult.Failure(AptosError.Api("Keyless service request failed", response.status.value.toString()))
      } else AptosResult.Success(response.body())
    } catch (error: Throwable) {
      AptosResult.Failure(
        if (error is IllegalArgumentException) {
          AptosError.Validation("Invalid Keyless service configuration", error)
        } else {
          AptosError.Transport("Keyless service request failed", error)
        }
      )
    }

  private fun pepperRequest(
    jwt: String,
    ephemeralKeyPair: EphemeralKeyPair,
    uidKey: String,
    derivationPath: String?,
  ): PepperRequest =
    PepperRequest(
      jwt = jwt,
      ephemeralPublicKey = ephemeralKeyPair.ephemeralPublicKeyBcs().hex(),
      expiryDateSecs = ephemeralKeyPair.expiryDateSecs,
      blinder = ephemeralKeyPair.blinder.hex(),
      uidKey = uidKey,
      derivationPath = derivationPath,
    )

  private fun pepperUrl(): String =
    serviceConfig.pepperService.url ?: defaultServiceUrl("pepper")

  private fun proverUrl(): String =
    serviceConfig.proverService.url ?: defaultServiceUrl("prover")

  private fun defaultServiceUrl(service: String): String {
    val network =
      when (aptos.settings.network) {
        Network.LOCAL -> "devnet"
        Network.NETNA -> "devnet"
        Network.CUSTOM ->
          throw IllegalArgumentException("Custom networks require an explicit $service service URL")
        else -> aptos.settings.network.name.lowercase()
      }
    return "https://api.$network.aptoslabs.com/keyless/$service/v0"
  }

  private fun fullnodeUrl(): String =
    aptos.settings.endpoints.fullNode
      ?: NetworkToNodeAPI[aptos.settings.network.name.lowercase()]
      ?: throw IllegalArgumentException("Custom networks require a fullnode URL")

  private fun fullnodeHeaders(): Map<String, String> =
    aptos.settings.commonHeaders + aptos.settings.fullNode.headers

  private fun defaultJwksUrl(issuer: String): String =
    if (issuer.matches(Regex("^https://securetoken\\.google\\.com/[A-Za-z0-9._-]+$"))) {
      "https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com"
    } else "${issuer.trimEnd('/')}/.well-known/jwks.json"
}

@Serializable
private data class PepperRequest(
  @SerialName("jwt_b64") val jwt: String,
  @SerialName("epk") val ephemeralPublicKey: String,
  @SerialName("exp_date_secs") val expiryDateSecs: ULong,
  @SerialName("epk_blinder") val blinder: String,
  @SerialName("uid_key") val uidKey: String,
  @SerialName("derivation_path") val derivationPath: String? = null,
)

@Serializable private data class PepperResponse(val pepper: String)

@Serializable private data class PepperBaseResponse(val signature: String)

@Serializable
private data class ProverRequest(
  @SerialName("jwt_b64") val jwt: String,
  @SerialName("epk") val ephemeralPublicKey: String,
  @SerialName("exp_date_secs") val expiryDateSecs: ULong,
  @SerialName("exp_horizon_secs") val expirationHorizonSecs: ULong,
  @SerialName("epk_blinder") val blinder: String,
  @SerialName("uid_key") val uidKey: String,
  val pepper: String,
)

@Serializable
private data class ProverResponse(
  val proof: ProofResponse,
  @SerialName("public_inputs_hash") val publicInputsHash: String? = null,
  @SerialName("training_wheels_signature") val trainingWheelsSignature: String,
)

@Serializable private data class ProofResponse(val a: String, val b: String, val c: String)

@Serializable private data class MoveResource<T>(val data: T)

@Serializable
private data class KeylessConfigurationResponse(
  @SerialName("max_commited_epk_bytes") val maxCommittedEphemeralPublicKeyBytes: Int,
  @SerialName("max_exp_horizon_secs") val maxExpirationHorizonSecs: String,
  @SerialName("max_extra_field_bytes") val maxExtraFieldBytes: Int,
  @SerialName("max_iss_val_bytes") val maxIssuerBytes: Int,
  @SerialName("max_jwt_header_b64_bytes") val maxJwtHeaderBase64Bytes: Int,
  @SerialName("training_wheels_pubkey") val trainingWheelsPublicKey: MoveVector,
)

@Serializable private data class MoveVector(@SerialName("vec") val values: List<String>)

@Serializable
private data class Groth16VerificationKeyResponse(
  @SerialName("alpha_g1") val alphaG1: String,
  @SerialName("beta_g2") val betaG2: String,
  @SerialName("delta_g2") val deltaG2: String,
  @SerialName("gamma_abc_g1") val gammaAbcG1: List<String>,
  @SerialName("gamma_g2") val gammaG2: String,
)

@Serializable private data class JwksResource(val jwks: JwkEntries)

@Serializable private data class JwkEntries(val entries: List<IssuerJwks>)

@Serializable private data class IssuerJwks(val issuer: String, val jwks: List<MoveAny>)

@Serializable private data class MoveAny(val variant: MoveAnyVariant)

@Serializable private data class MoveAnyVariant(val data: String)

@Serializable private data class OriginatingAddress(@SerialName("address_map") val addressMap: TableHandle)

@Serializable private data class TableHandle(val handle: String)

@Serializable
private data class TableRequest(
  @SerialName("key_type") val keyType: String,
  @SerialName("value_type") val valueType: String,
  val key: String,
)

@Serializable private data class ExternalJwks(val keys: List<ExternalJwk>)

@Serializable
private data class ExternalJwk(
  @SerialName("kid") val keyId: String = "",
  @SerialName("alg") val algorithm: String = "",
  @SerialName("e") val exponent: String = "",
  @SerialName("n") val modulus: String = "",
) {
  fun isValid(): Boolean =
    keyId.isNotBlank() && algorithm.isNotBlank() && exponent.isNotBlank() && modulus.isNotBlank()
}

private fun List<ExternalJwk>.moveStrings(value: ExternalJwk.() -> String): MoveArgument.Vector =
  MoveArgument.Vector(map { MoveArgument.StringValue(it.value()) })

private fun vkU64(value: String): ULong =
  value.toULongOrNull() ?: throw IllegalArgumentException("Invalid on-chain u64: $value")

private inline fun <T> cryptoResult(message: String, block: () -> T): AptosResult<T> =
  try {
    AptosResult.Success(block())
  } catch (error: Throwable) {
    AptosResult.Failure(AptosError.Crypto(message, error))
  }

private inline fun <T, R> AptosResult<T>.flatMap(transform: (T) -> AptosResult<R>): AptosResult<R> =
  when (this) {
    is AptosResult.Success -> transform(value)
    is AptosResult.Failure -> this
  }

private fun <T> AptosResult.Failure.mapFailure(): AptosResult<T> = AptosResult.Failure(error)
