package xyz.mcxross.kaptos.unit.serialize

import xyz.mcxross.bcs.Bcs
import kotlin.test.Test
import xyz.mcxross.kaptos.model.MoveVector
import xyz.mcxross.kaptos.model.U8
import kotlin.test.Ignore
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

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

    @Test
    fun `can deserialize a vector with less than 128 items`() {
        val encoded = listOf(3.toByte(), 1.toByte(), 2.toByte(), 3.toByte()).toByteArray()

        val decoded: MoveVector<U8> = Bcs.decodeFromByteArray(encoded)
        val expected = MoveVector.u8(listOf(1.toByte(), 2.toByte(), 3.toByte()).toByteArray())

        assertContentEquals(expected.values, decoded.values)
    }

    @Test
    fun `can deserialize an empty vector`() {
        val encoded = listOf(0.toByte()).toByteArray()

        val decoded: MoveVector<U8> = Bcs.decodeFromByteArray(encoded)
        val expected = MoveVector.u8(emptyList<Byte>().toByteArray())

        assertContentEquals(expected.values, decoded.values)
    }

    @Test // Fails due to incorrect size check in bcs lib, will circle back after fixing bcs
    @Ignore
    fun `can deserialize a vector with 128 items`() {
        val base = ByteArray(128) { it.toByte() }
        val expectedLengthTag = listOf(0x80.toByte(), 0x01.toByte()).toByteArray()
        val encoded = expectedLengthTag + base

        val decoded: MoveVector<U8> = Bcs.decodeFromByteArray(encoded)
        val expected = MoveVector.u8(base)

        assertContentEquals(expected.values, decoded.values)
    }

    @Test
    fun `should throw error when deserializing empty byte array`() {
        val emptyInput = byteArrayOf()

        assertFailsWith<Exception> {
            Bcs.decodeFromByteArray<MoveVector<U8>>(emptyInput)
        }
    }
}
