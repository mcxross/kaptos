package xyz.mcxross.kaptos.util

import xyz.mcxross.kaptos.model.HexInput


fun String.toAccountAddress(): HexInput {
    return HexInput(this)
}