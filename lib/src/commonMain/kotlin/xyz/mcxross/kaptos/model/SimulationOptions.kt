/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package xyz.mcxross.kaptos.model

/** Controls the estimates returned by transaction simulation. */
data class SimulationOptions(
  val estimateGasUnitPrice: Boolean = false,
  val estimateMaxGasAmount: Boolean = false,
  val estimatePrioritizedGasUnitPrice: Boolean = false,
)
