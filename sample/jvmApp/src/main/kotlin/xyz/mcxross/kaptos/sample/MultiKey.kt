package xyz.mcxross.kaptos.sample

import kotlinx.coroutines.runBlocking
import xyz.mcxross.kaptos.account.MultiKeyAccount
import xyz.mcxross.kaptos.aptos
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.model.HexInput

fun multiKeyAccount() = runBlocking {
  aptos {
    val key0 = account()
    val key1 = account()
    val key2 = account()
    val mk = MultiKey(listOf(key0.publicKey, key1.publicKey, key2.publicKey), 2)
    val acc = MultiKeyAccount(mk, listOf(key0, key1))
    val message = HexInput.fromByteArray("simple message".encodeToByteArray())
    val signature = acc.sign(message)
    println("MultiKey signature valid: ${acc.verifySignature(message, signature)}")
  }
}
