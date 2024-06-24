package xyz.mcxross.kaptos.sample

import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.protocol.view

suspend fun getModule(aptos: Aptos): Option<List<MoveValue.MoveUint64Type>> {
  return aptos.view<List<MoveValue.MoveUint64Type>>(
    InputViewFunctionData(
      function = "0x1::coin::balance",
      typeArguments =
        listOf(TypeTagStruct(type = StructTag.fromString("0x1::aptos_coin::AptosCoin"))),
      functionArguments =
        listOf(MoveString("0x7df36a50ed0af77f288c216b4db6e9feb71e4d1b6e5fbc4032d9daa2021fe94e")),
    )
  )
}
