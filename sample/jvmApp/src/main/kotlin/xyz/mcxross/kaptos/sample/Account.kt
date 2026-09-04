package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.model.SigningSchemeInput

fun accountLifecycle() = runBlocking {
  aptos {
    val singleKey = singleKeyAccount()
    val account = singleKeyAccount(SigningSchemeInput.Secp256k1)
    println(singleKey)
    println("Public key: ${account.publicKey}")
    println("Address: ${account.accountAddress}")
  }
}
