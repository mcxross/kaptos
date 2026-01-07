package xyz.mcxross.kaptos.sample

import xyz.mcxross.kaptos.account.SingleKeyAccount
import xyz.mcxross.kaptos.model.SigningSchemeInput

fun main() {
  val singleKey = SingleKeyAccount.generate(SigningSchemeInput.Ed25519)
  println(singleKey)

  val account = SingleKeyAccount.generate(SigningSchemeInput.Secp256k1)

  println(account.privateKey)
  println(account.publicKey)
  println(account.accountAddress)
}
