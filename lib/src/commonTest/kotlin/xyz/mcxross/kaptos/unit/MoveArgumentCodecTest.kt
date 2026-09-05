/*
 * Copyright 2026 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.CancellationException
import xyz.mcxross.kaptos.model.AptosError
import xyz.mcxross.kaptos.model.AptosResult
import xyz.mcxross.kaptos.model.MoveAbility
import xyz.mcxross.kaptos.model.MoveFunction
import xyz.mcxross.kaptos.model.MoveModule
import xyz.mcxross.kaptos.model.MoveModuleBytecode
import xyz.mcxross.kaptos.model.MoveStruct
import xyz.mcxross.kaptos.model.MoveStructField
import xyz.mcxross.kaptos.model.MoveVisibility
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.move.MoveArgumentCodec
import xyz.mcxross.kaptos.move.MoveModuleLoader

class MoveArgumentCodecTest :
  StringSpec({
    "recursive structs, enums, generics, and signed integers encode from preloaded ABIs" {
      val codec =
        MoveArgumentCodec(
          MoveModuleLoader { _, _ ->
            AptosResult.Failure(AptosError.Transport("offline loader must not be called"))
          }
        )
      codec.preload(SHAPES_MODULE, GEOMETRY_MODULE)

      val payload =
        codec
          .entryFunctionPayload(
            function = "0x42::geometry::draw",
            arguments =
              listOf(
                MoveArgument.Struct(
                  mapOf(
                    "start" to point(1uL, 2uL),
                    "end" to point(3uL, 4uL),
                  )
                ),
                MoveArgument.Struct(mapOf("value" to MoveArgument.U16(513u.toUShort()))),
                MoveArgument.Enum(
                  variant = "Number",
                  fields = mapOf("0" to MoveArgument.U64(42uL)),
                ),
                MoveArgument.I128("-1"),
              ),
          )
          .success()

      payload.call.arguments.shouldHaveSize(4)
      payload.call.arguments[0].bytes() shouldBe u64(1uL) + u64(2uL) + u64(3uL) + u64(4uL)
      payload.call.arguments[1].bytes() shouldBe byteArrayOf(0x01, 0x02)
      payload.call.arguments[2].bytes() shouldBe byteArrayOf(0x01) + u64(42uL)
      payload.call.arguments[3].bytes() shouldBe ByteArray(16) { 0xff.toByte() }
    }

    "module loader results are cached across recursive payload builds" {
      val loadCounts = mutableMapOf<String, Int>()
      val codec =
        MoveArgumentCodec(
          MoveModuleLoader { address, moduleName ->
            val key = "${address.toStringLong()}::$moduleName"
            loadCounts[key] = loadCounts.getOrElse(key) { 0 } + 1
            when (moduleName) {
              "geometry" -> AptosResult.Success(GEOMETRY_MODULE)
              "shapes" -> AptosResult.Success(SHAPES_MODULE)
              else -> AptosResult.Failure(AptosError.Validation("unknown module"))
            }
          }
        )
      val arguments =
        listOf(
          MoveArgument.Struct(
            mapOf(
              "start" to point(1uL, 2uL),
              "end" to point(3uL, 4uL),
            )
          ),
          MoveArgument.Struct(mapOf("value" to MoveArgument.U16(5u.toUShort()))),
          MoveArgument.Enum("None"),
          MoveArgument.I128("0"),
        )

      codec.entryFunctionPayload("0x42::geometry::draw", arguments = arguments).success()
      codec.entryFunctionPayload("0x42::geometry::draw", arguments = arguments).success()

      loadCounts.values.toList() shouldBe listOf(1, 1)
    }

    "ABI loading preserves coroutine cancellation" {
      val codec =
        MoveArgumentCodec(MoveModuleLoader { _, _ -> throw CancellationException("cancelled") })

      shouldThrow<CancellationException> {
        codec.entryFunctionPayload("0x42::geometry::draw")
      }
    }
  })

private fun point(x: ULong, y: ULong): MoveArgument.Struct =
  MoveArgument.Struct(mapOf("x" to MoveArgument.U64(x), "y" to MoveArgument.U64(y)))

private fun MoveArgument.bytes(): ByteArray = shouldBeInstanceOf<MoveArgument.PreSerialized>().value

private fun <T> AptosResult<T>.success(): T = shouldBeInstanceOf<AptosResult.Success<T>>().value

private fun u64(value: ULong): ByteArray =
  ByteArray(8) { index -> ((value shr (index * 8)) and 0xffu).toByte() }

private val SHAPES_MODULE =
  MoveModuleBytecode(
    bytecode = "0x",
    abi =
      MoveModule(
        address = "0x43",
        name = "shapes",
        friends = emptyList(),
        exposedFunctions = emptyList(),
        structs =
          listOf(
            MoveStruct(
              name = "Point",
              isNative = false,
              abilities = listOf(MoveAbility.COPY, MoveAbility.DROP),
              genericTypeParams = emptyList(),
              fields = listOf(MoveStructField("x", "u64"), MoveStructField("y", "u64")),
            )
          ),
      ),
  )

private val GEOMETRY_MODULE =
  MoveModuleBytecode(
    bytecode = "0x",
    abi =
      MoveModule(
        address = "0x42",
        name = "geometry",
        friends = emptyList(),
        exposedFunctions =
          listOf(
            MoveFunction(
              name = "draw",
              visibility = MoveVisibility.PUBLIC,
              isEntry = true,
              isView = false,
              genericTypeParams = emptyList(),
              params =
                listOf(
                  "&signer",
                  "0x42::geometry::Line",
                  "0x42::geometry::Box<u16>",
                  "0x42::geometry::Choice",
                  "i128",
                ),
              `return` = emptyList(),
            )
          ),
        structs =
          listOf(
            MoveStruct(
              name = "Line",
              isNative = false,
              abilities = listOf(MoveAbility.COPY, MoveAbility.DROP),
              genericTypeParams = emptyList(),
              fields =
                listOf(
                  MoveStructField("start", "0x43::shapes::Point"),
                  MoveStructField("end", "0x43::shapes::Point"),
                ),
            ),
            MoveStruct(
              name = "Box",
              isNative = false,
              abilities = listOf(MoveAbility.COPY, MoveAbility.DROP),
              genericTypeParams =
                listOf(xyz.mcxross.kaptos.model.MoveFunctionGenericTypeParam(emptyList())),
              fields = listOf(MoveStructField("value", "T0")),
            ),
            MoveStruct(
              name = "Choice",
              isNative = false,
              isEnum = true,
              abilities = listOf(MoveAbility.COPY, MoveAbility.DROP),
              genericTypeParams = emptyList(),
              fields = emptyList(),
              variants =
                listOf(
                  xyz.mcxross.kaptos.model.MoveStructVariant("None", emptyList()),
                  xyz.mcxross.kaptos.model.MoveStructVariant(
                    "Number",
                    listOf(MoveStructField("0", "u64")),
                  ),
                ),
            ),
          ),
      ),
  )
