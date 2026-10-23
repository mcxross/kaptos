/* Copyright 2026 McXross. Licensed under the Apache License, Version 2.0. */
package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import xyz.mcxross.kaptos.swift.AptosException
import xyz.mcxross.kaptos.swift.OptionValues

class OptionValuesTest {
  @Test
  fun preservesOptionalUnsignedValues() {
    assertNull(OptionValues.transaction().gasUnitPrice)
    assertEquals(100uL, OptionValues.transaction(gasUnitPriceDecimal = "100").gasUnitPrice)
    assertEquals(
      ULong.MAX_VALUE,
      OptionValues.transaction(gasUnitPriceDecimal = ULong.MAX_VALUE.toString()).gasUnitPrice,
    )
    assertFailsWith<AptosException> {
      OptionValues.transaction(gasUnitPriceDecimal = "18446744073709551616")
    }
  }
}
