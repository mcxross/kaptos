package xyz.mcxross.kaptos.transport.ktor

import io.ktor.client.HttpClient
import xyz.mcxross.kaptos.transport.AptosTransport

/** Adapt an application-owned Ktor client for SDK injection. Closing Aptos does not close it. */
fun HttpClient.asAptosTransport(): AptosTransport = AptosTransport(this)

/** Explicit Ktor integration for modules that make their own endpoint requests. */
fun AptosTransport.ktorClient(): HttpClient = client
