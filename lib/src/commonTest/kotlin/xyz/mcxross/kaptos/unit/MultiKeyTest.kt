package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.account.MultiKeyAccount
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Secp256k1PublicKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningSchemeInput
import xyz.mcxross.kaptos.util.runBlocking

data object MultiKeyTestObject {
  val publicKeys: List<String> =
    listOf(
      "049a6f7caddff8064a7dd5800e4fb512bf1ff91daee965409385dfa040e3e63008ab7ef566f4377c2de5aeb2948208a01bcee2050c1c8578ce5fa6e0c3c507cca2",
      "7a73df1afd028e75e7f9e23b2187a37d092a6ccebcb3edff6e02f93185cbde86",
      "17fe89a825969c1c0e5f5e80b95f563a6cb6240f88c4246c19cb39c9535a1486",
    )

  val signaturesRequired: Int = 2

  val address: String = "0xd2b929b11e53fd69fd09d283fcea941337558060f5711c6a32261a77d9038270"

  val authKey: String = "0xd2b929b11e53fd69fd09d283fcea941337558060f5711c6a32261a77d9038270"

  val stringBytes: String =
    "0x030141049a6f7caddff8064a7dd5800e4fb512bf1ff91daee965409385dfa040e3e63008ab7ef566f4377c2de5aeb2948208a01bcee2050c1c8578ce5fa6e0c3c507cca200207a73df1afd028e75e7f9e23b2187a37d092a6ccebcb3edff6e02f93185cbde86002017fe89a825969c1c0e5f5e80b95f563a6cb6240f88c4246c19cb39c9535a148602"

  val bitMap = arrayOf(160, 0, 0, 0)
}

class MultiKeyTest {

  private fun createTsFixtureMultiKey(): MultiKey =
    MultiKey(
      listOf(
        Secp256k1PublicKey(MultiKeyTestObject.publicKeys[0]),
        Ed25519PublicKey(MultiKeyTestObject.publicKeys[1]),
        Ed25519PublicKey(MultiKeyTestObject.publicKeys[2]),
      ),
      MultiKeyTestObject.signaturesRequired,
    )

  @Test
  fun `should throw when number of required signatures is less then 1`() = runBlocking {
    assertFailsWith(IllegalArgumentException::class) {
      MultiKey(
        listOf(
          Secp256k1PublicKey(HexInput(MultiKeyTestObject.publicKeys[0])),
          Ed25519PublicKey(HexInput(MultiKeyTestObject.publicKeys[1])),
          Ed25519PublicKey(HexInput(MultiKeyTestObject.publicKeys[2])),
        ),
        0,
      )
    }
  }

  @Test
  fun `should create bitmap correctly`() = runBlocking {
    val mk = createTsFixtureMultiKey()
    val bitmap = mk.createBitmap(listOf(0, 2))
    assertContentEquals(bitmap, MultiKeyTestObject.bitMap.map { it.toByte() }.toByteArray())
  }

  @Test
  fun `should serialize multikey bytes like ts sdk`() = runBlocking {
    val mk = createTsFixtureMultiKey()
    assertEquals(MultiKeyTestObject.stringBytes, mk.toString())
  }

  @Test
  fun `should derive multikey auth key and address like ts sdk`() = runBlocking {
    val mk = createTsFixtureMultiKey()
    val authKey = mk.authKey()
    assertEquals(MultiKeyTestObject.authKey, authKey.toString())
    assertEquals(MultiKeyTestObject.address, authKey.deriveAddress().toString())
  }

  @Test
  fun `should derive multikey account address from auth key`() = runBlocking {
    val account1 = Account.generate(SigningSchemeInput.Ed25519)
    val account2 = Account.generate(SigningSchemeInput.Secp256k1)
    val account3 = Account.generate(SigningSchemeInput.Ed25519)

    val multiKey = MultiKey(listOf(account1.publicKey, account2.publicKey, account3.publicKey), 2)

    val multiKeyAccount = MultiKeyAccount(multiKey, listOf(account1, account3))
    assertEquals(
      multiKey.authKey().deriveAddress().toString(),
      multiKeyAccount.accountAddress.toString(),
    )

    val message = HexInput.fromByteArray("hello".encodeToByteArray())
    val signature = multiKeyAccount.sign(message)

    assertTrue(multiKeyAccount.verifySignature(message, signature))
  }
}
