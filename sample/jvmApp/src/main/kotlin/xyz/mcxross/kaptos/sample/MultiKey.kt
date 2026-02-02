package xyz.mcxross.kaptos.sample

import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.account.MultiKeyAccount
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.util.runBlocking

fun main() = runBlocking {
  val key0 =
    Account.fromPrivateKey(
      Ed25519PrivateKey("0xa7e183adbf853de5681e17f2bd96276dcbbd243c9897019df9c9091913a588c2")
    )
  val key1 =
    Account.fromPrivateKey(
      Ed25519PrivateKey("0x8931ea8bda16d9bb1d99211099a19f359e49162fd182d438a8a223a3efee6bd8")
    )

  val key2 = Account.generate()

  val mk = MultiKey(listOf(key0.publicKey, key1.publicKey, key2.publicKey), 2)
  val acc = MultiKeyAccount(mk, listOf(key0, key1))

  val msg = HexInput.fromByteArray("simple message".toByteArray())
  val sig = acc.sign(msg)

  println(acc.verifySignature(HexInput.fromByteArray("simple message".toByteArray()), sig))
}
