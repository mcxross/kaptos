package xyz.mcxross.kaptos.unit.serialize

import xyz.mcxross.bcs.Bcs
import kotlin.test.Test
import xyz.mcxross.kaptos.model.MoveVector
import kotlin.test.assertContentEquals

class MoveVectorSerializerTest {
    @Test()
    fun `can serialize a vector with less than 128 items`() {
        val base = listOf(1.toByte(), 2.toByte(), 3.toByte()).toByteArray()
        val vec = MoveVector.u8(base)

        val encoded = Bcs.encodeToByteArray(vec)
        val expected = listOf(3.toByte(), 1.toByte(), 2.toByte(), 3.toByte()).toByteArray()

        assertContentEquals(expected, encoded)
    }

    @Test()
    fun `can serialize an empty vector`() {
        val base = emptyList<Byte>().toByteArray()
        val vec = MoveVector.u8(base)

        val encoded = Bcs.encodeToByteArray(vec)
        val expected = listOf(0.toByte()).toByteArray()

        assertContentEquals(expected, encoded)
    }

    @Test()
    fun `can serialize a vector with 128 items`() {
        val base = ByteArray(128) { it.toByte() }
        val vec = MoveVector.u8(base)

        val encoded = Bcs.encodeToByteArray(vec)
        val expectedLengthTag = listOf(0x80.toByte(), 0x01.toByte()).toByteArray()
        val expected = expectedLengthTag + base

        assertContentEquals(expected, encoded)
    }
}