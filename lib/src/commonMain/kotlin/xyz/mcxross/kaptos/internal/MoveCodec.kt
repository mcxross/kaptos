package xyz.mcxross.kaptos.internal

import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.move.MoveArgumentCodec
import xyz.mcxross.kaptos.move.MoveModuleLoader

internal fun moveCodec(config: TransportConfig, ledgerVersion: ULong? = null): MoveArgumentCodec =
  MoveArgumentCodec(
    MoveModuleLoader { address, name ->
      getModule(
          config,
          address,
          name,
          ledgerVersion?.let { mapOf("ledger_version" to it.toString()) },
        )
        .toAptosResult()
    }
  )
