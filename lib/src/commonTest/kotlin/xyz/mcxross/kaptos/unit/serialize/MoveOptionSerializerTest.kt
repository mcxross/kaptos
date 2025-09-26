package xyz.mcxross.kaptos.unit.serialize

import xyz.mcxross.bcs.Bcs
import kotlin.test.Test
import xyz.mcxross.kaptos.model.MoveOption
import xyz.mcxross.kaptos.model.U8
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class MoveOptionSerializerTest {
    @Test
    fun `can deserialize a Some option (vector of length 1)`() {
        val encoded = listOf(1.toByte(), 42.toByte()).toByteArray()

        val decoded: MoveOption<U8> = Bcs.decodeFromByteArray(encoded)
        val expected = MoveOption(U8(42.toByte()))

        assertEquals(expected.value?.value, decoded.value?.value)
    }

    @Test
    fun `can deserialize a None option (vector of length 0)`() {
        val encoded = listOf(0.toByte()).toByteArray()

        val decoded: MoveOption<U8> = Bcs.decodeFromByteArray(encoded)
        val expected = MoveOption<U8>(null)

        assertNull(decoded.value)
        assertEquals(expected.value, decoded.value)
    }

    @Test
    fun `unwrap returns value when Some`() {
        val encoded = listOf(1.toByte(), 100.toByte()).toByteArray()

        val decoded: MoveOption<U8> = Bcs.decodeFromByteArray(encoded)

        assertEquals(100.toByte(), decoded.unwrap().value)
    }

    @Test
    fun `unwrap throws when None`() {
        val encoded = listOf(0.toByte()).toByteArray()

        val decoded: MoveOption<U8> = Bcs.decodeFromByteArray(encoded)

        assertFailsWith<IllegalArgumentException> {
            decoded.unwrap()
        }
    }

    @Test
    fun `should throw error when deserializing empty byte array`() {
        val emptyInput = byteArrayOf()

        assertFailsWith<Exception> {
            Bcs.decodeFromByteArray<MoveOption<U8>>(emptyInput)
        }
    }

    @Test
    fun `should throw error when deserializing vector with length greater than 1`() {
        val encoded = listOf(2.toByte(), 1.toByte(), 2.toByte()).toByteArray()

        assertFailsWith<IllegalArgumentException> {
            Bcs.decodeFromByteArray<MoveOption<U8>>(encoded)
        }
    }
}