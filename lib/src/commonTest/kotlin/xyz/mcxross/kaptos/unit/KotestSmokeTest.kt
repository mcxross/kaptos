package xyz.mcxross.kaptos.unit

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import xyz.mcxross.kaptos.util.bytesToHex

class KotestSmokeTest :
  StringSpec({
    "bytesToHex should encode bytes as lowercase hex" {
      bytesToHex(byteArrayOf(0x0f, 0x10)) shouldBe "0f10"
    }
  })
