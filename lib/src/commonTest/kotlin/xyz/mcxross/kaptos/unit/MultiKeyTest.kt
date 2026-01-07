package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.core.crypto.Secp256k1PublicKey
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.util.runBlocking

data object MultiKeyTestObject {
  val publicKeys: List<String> =
    listOf(
      "049a6f7caddff8064a7dd5800e4fb512bf1ff91daee965409385dfa040e3e63008ab7ef566f4377c2de5aeb2948208a01bcee2050c1c8578ce5fa6e0c3c507cca2",
      "7a73df1afd028e75e7f9e23b2187a37d092a6ccebcb3edff6e02f93185cbde86",
      "17fe89a825969c1c0e5f5e80b95f563a6cb6240f88c4246c19cb39c9535a1486",
    )

  val signaturesRequired: Int = 2

  val address = "0x738a998ac1f69db4a91fc5a0152f792c98ad87354c65a2a842a118d7a17109b1"

  val authKey = "0x738a998ac1f69db4a91fc5a0152f792c98ad87354c65a2a842a118d7a17109b1"

  val bitMap = arrayOf(160, 0, 0, 0)
  val stringBytes =
    "0x030141049a6f7caddff8064a7dd5800e4fb512bf1ff91daee965409385dfa040e3e63008ab7ef566f4377c2de5aeb2948208a01bcee2050c1c8578ce5fa6e0c3c507cca200207a73df1afd028e75e7f9e23b2187a37d092a6ccebcb3edff6e02f93185cbde86002017fe89a825969c1c0e5f5e80b95f563a6cb6240f88c4246c19cb39c9535a148602"
}

class MultiKeyTest {

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
    val pks =
      listOf(
        Secp256k1PublicKey(MultiKeyTestObject.publicKeys[0]),
        Ed25519PublicKey(MultiKeyTestObject.publicKeys[1]),
        Ed25519PublicKey(MultiKeyTestObject.publicKeys[2]),
      )

    val mk = MultiKey(pks = pks, signaturesRequired = 2)

    val bitmap = mk.createBitmap(listOf(0, 2))

    assertContentEquals(bitmap, MultiKeyTestObject.bitMap.map { it.toByte() }.toByteArray())
  }
}
