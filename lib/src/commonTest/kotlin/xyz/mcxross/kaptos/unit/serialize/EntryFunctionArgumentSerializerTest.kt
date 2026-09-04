package xyz.mcxross.kaptos.unit.serialize

import kotlin.test.Test
import kotlin.test.assertContentEquals
import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.model.EntryFunctionArgument
import xyz.mcxross.kaptos.model.MoveVector
import xyz.mcxross.kaptos.model.U32
import xyz.mcxross.kaptos.model.U8

class EntryFunctionArgumentSerializerTest {
  @Test
  fun `serializes U32 entry function argument as length-prefixed bytes`() {
    val encoded = Bcs.encodeToByteArray<EntryFunctionArgument>(U32(16u))
    assertContentEquals(byteArrayOf(4, 16, 0, 0, 0), encoded)
  }

  @Test
  fun `serializes MoveVector U8 argument as length-prefixed vector bcs bytes`() {
    val encoded =
      Bcs.encodeToByteArray<EntryFunctionArgument>(MoveVector(listOf(U8(1), U8(2), U8(3))))

    // MoveVector<U8>(1,2,3) BCS bytes are: 03 01 02 03.
    // Entry function argument wraps these bytes as vector<u8>: 04 03 01 02 03.
    assertContentEquals(byteArrayOf(4, 3, 1, 2, 3), encoded)
  }
}
