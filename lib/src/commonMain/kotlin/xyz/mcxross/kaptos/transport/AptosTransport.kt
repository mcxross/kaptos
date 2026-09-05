package xyz.mcxross.kaptos.transport

/** An SDK transport handle. Injection does not transfer ownership of the underlying client. */
class AptosTransport internal constructor(internal val client: io.ktor.client.HttpClient)
