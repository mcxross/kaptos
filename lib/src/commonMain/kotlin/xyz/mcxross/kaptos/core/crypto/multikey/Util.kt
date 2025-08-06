package xyz.mcxross.kaptos.core.crypto.multikey

fun bitCount(byte: Int): Int {
    var n = byte
    n -= (n ushr 1) and 0x55555555
    n = (n and 0x33333333) + ((n ushr 2) and 0x33333333)
    return ((n + (n ushr 4)) and 0x0F0F0F0F) * 0x01010101 ushr 24
}