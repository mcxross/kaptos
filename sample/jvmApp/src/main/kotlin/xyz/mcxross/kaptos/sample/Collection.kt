package xyz.mcxross.kaptos.sample

import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.util.runBlocking

fun main() = runBlocking {
  val aptos = Aptos(AptosConfig(AptosSettings(network = Network.TESTNET)))

  val alice =
    Account.fromPrivateKey(
      Ed25519PrivateKey("0x0bbb276ea6b8d0551f2d9a4002e009fe51f615f6ad027c9625b7eeda3fb2be58")
    )

  println(alice.accountAddress)

  val bob = Account.generate()

  aptos.fundAccount(alice.accountAddress, 100_000_000)

  val collectionTxn = aptos.createCollectionTransaction(alice, "McXross")

  val propertyTxn =
    aptos.addDigitalAssetPropertyTransaction(
      alice,
      propertyKey = "bio",
      propertyType = PropertyType.STRING,
      propertyValue = PropertyValue.StringValue("simple things..."),
      digitalAssetAddress = alice.accountAddress,
    )
  val SBTTxn =
    aptos.mintDigitalAssetTransaction(
      alice,
      collection = "McXross",
      description = "Aptos Test NFT 1",
      name = "Aptos Test NFT 1",
      uri = "https://aptos.dev",
    )

  val collection =
    aptos.removeDigitalAssetPropertyTransaction(
      alice,
      propertyKey = "name",
      digitalAssetAddress =
        AccountAddress("0xa72945789e14f8c8e85a6e43c7358a6d28e4eff031f5337510b120f3f3527aa2"),
    )

  val commitedTransaction = aptos.simulateTransaction.simple(alice.publicKey, collectionTxn)

  println(commitedTransaction)
}
