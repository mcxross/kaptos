package xyz.mcxross.kaptos.sample

import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.runBlocking

fun main() = runBlocking {
  val aptos = Aptos(AptosConfig(AptosSettings(network = Network.LOCAL)))

  val alice =
    Account.fromPrivateKey(
      Ed25519PrivateKey("0x8f1d206066beca00e71b358f4d1a6013e046c78574625f8d5b68990f96e5a05e")
    )

  val bob = Account.generate()

  println("Bob's address: ${bob.accountAddress}")

  aptos.fundAccount(alice.accountAddress, 100_000_000)

  val txn =
    aptos.transferDigitalAssetTransaction(
      alice,
      AccountAddress("0x5c6ef08ebef8b9a409935e6e4531b42a9caca6f50b4e8d9cc00fd0f7f2206332"),
      bob.accountAddress,
    )

  val commitedTransaction = aptos.signAndSubmitTransaction(alice, txn)

  val executedTransaction =
    aptos.waitForTransaction(HexInput(commitedTransaction.expect("Transaction failed").hash))

  println(executedTransaction)
}
