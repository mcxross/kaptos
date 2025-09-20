package xyz.mcxross.kaptos.core.crypto.multikey

import xyz.mcxross.kaptos.core.crypto.AnySignature
import xyz.mcxross.kaptos.core.crypto.Signature

class MultiKeySignature(val signatures: List<AnySignature>, val bitmap: ByteArray) : Signature() {

  init {
    if (signatures.size > MAX_SIGNATURES_SUPPORTED) {
      throw IllegalArgumentException(
        "`The number of signatures cannot be greater than ${MAX_SIGNATURES_SUPPORTED}`"
      )
    }

    if (bitmap.size != BITMAP_LEN) {
      throw IllegalArgumentException("bitmap length should be $BITMAP_LEN")
    }

    val nSignatures = bitmap.sumOf { bitCount(it.toInt() and 0xFF) }

    if (nSignatures != signatures.size) {
      throw IllegalArgumentException("Expecting $nSignatures signatures from the bitmap, but got ${signatures.size}")
    }
  }

  override fun toByteArray(): ByteArray {
    TODO("Not yet implemented")
  }

  override fun toBcs(): ByteArray {
    TODO("Not yet implemented")
  }

  companion object {
    const val BITMAP_LEN = 4
    const val MAX_SIGNATURES_SUPPORTED = BITMAP_LEN * 8
  }
}
