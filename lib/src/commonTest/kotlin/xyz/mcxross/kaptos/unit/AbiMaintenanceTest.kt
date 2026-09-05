package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.move.*
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader

class AbiMaintenanceTest :
  StringSpec({
    "fetched ABIs expire and invalidation reloads them" {
      var loads = 0
      var now = 0L
      val codec =
        MoveArgumentCodec(
          MoveModuleLoader { _, _ ->
            loads++
            AptosResult.Success(abiModule())
          },
          AbiCachePolicy(10, 2),
        )
      codec.nowMillis = { now }
      suspend fun read() =
        codec
          .viewRequest("0x42::cache::read", emptyList(), emptyList())
          .shouldBeInstanceOf<AptosResult.Success<ByteArray>>()
      read()
      now = 9
      read()
      loads shouldBe 1
      now = 10
      read()
      loads shouldBe 2
      codec.invalidateModule(AccountAddress.fromString("0x42"), "cache")
      read()
      loads shouldBe 3
      codec.clearCache()
      read()
      loads shouldBe 4
    }

    "preloads stay pinned and failed preload batches do not partially apply" {
      var loads = 0
      val codec =
        MoveArgumentCodec(
          MoveModuleLoader { _, _ ->
            loads++
            AptosResult.Success(abiModule())
          },
          AbiCachePolicy(0, 1),
        )
      codec.preload(abiModule())
      shouldThrow<IllegalArgumentException> {
        codec.preload(abiModule("other"), MoveModuleBytecode("0x"))
      }
      repeat(2) { codec.viewRequest("0x42::cache::read", emptyList(), emptyList()) }
      loads shouldBe 0
      codec.clearCache()
      repeat(2) { codec.viewRequest("0x42::cache::read", emptyList(), emptyList()) }
      loads shouldBe 2
    }

    "capacity eviction bounds fetched modules" {
      val loads = mutableListOf<String>()
      val codec =
        MoveArgumentCodec(
          MoveModuleLoader { _, name ->
            loads += name
            AptosResult.Success(abiModule(name))
          },
          AbiCachePolicy(60_000, 1),
        )
      for (name in listOf("a", "b", "a")) codec.viewRequest(
        "0x42::$name::read",
        emptyList(),
        emptyList(),
      )
      loads shouldBe listOf("a", "b", "a")
    }

    "invalidation orders against in-flight loads" {
      coroutineScope {
        val started = CompletableDeferred<Unit>()
        val finish = CompletableDeferred<Unit>()
        var loads = 0
        val codec =
          MoveArgumentCodec(
            MoveModuleLoader { _, _ ->
              loads++
              started.complete(Unit)
              finish.await()
              AptosResult.Success(abiModule())
            }
          )
        val first = async { codec.viewRequest("0x42::cache::read", emptyList(), emptyList()) }
        started.await()
        val invalidate = async { codec.clearCache() }
        finish.complete(Unit)
        first.await()
        invalidate.await()
        codec.viewRequest("0x42::cache::read", emptyList(), emptyList())
        loads shouldBe 2
      }
    }

    "explicit enum ABI variants encode heterogeneous named fields in ABI order" {
      // REST ABI fixture: variants are separate from ordinary struct fields.
      val module = Json.decodeFromString<MoveModuleBytecode>(ENUM_ABI)
      val codec = MoveArgumentCodec(MoveModuleLoader { _, _ -> AptosResult.Success(module) })
      val args =
        listOf(
          MoveArgument.Enum(
            "Pair",
            linkedMapOf("enabled" to MoveArgument.Bool(true), "count" to MoveArgument.U64(9u)),
          )
        )
      val request =
        codec
          .viewRequest("0x42::cache::read", emptyList(), args)
          .shouldBeInstanceOf<AptosResult.Success<ByteArray>>()
          .value
      val reader = AptosBcsReader(request)
      reader.accountAddress()
      reader.string()
      reader.string()
      reader.vector { typeTag() }
      val payload = AptosBcsReader(reader.vector { bytes() }.single())
      payload.uleb128() shouldBe 1u
      payload.u64() shouldBe 9uL
      payload.bool() shouldBe true
      payload.ensureFinished()
      reader.ensureFinished()
      val entry =
        codec
          .entryFunctionPayload("0x42::cache::read", arguments = args)
          .shouldBeInstanceOf<AptosResult.Success<TransactionPayload.EntryFunction>>()
      entry.value.call.arguments.single().toBcs().toList() shouldBe
        listOf<Byte>(1, 9, 0, 0, 0, 0, 0, 0, 0, 1)
    }

    "enum layouts reject missing extra mistyped and unknown fields" {
      val module = Json.decodeFromString<MoveModuleBytecode>(ENUM_ABI)
      val codec = MoveArgumentCodec(MoveModuleLoader { _, _ -> AptosResult.Success(module) })
      for (arg in
        listOf(
          MoveArgument.Enum("Unknown"),
          MoveArgument.Enum("Pair"),
          MoveArgument.Enum("Empty", mapOf("x" to MoveArgument.U8(1u))),
          MoveArgument.Enum(
            "Pair",
            mapOf("count" to MoveArgument.U8(1u), "enabled" to MoveArgument.Bool(true)),
          ),
        )) codec
        .viewRequest("0x42::cache::read", emptyList(), listOf(arg))
        .shouldBeInstanceOf<AptosResult.Failure>()
      val incomplete =
        module.copy(
          abi =
            module.abi!!.copy(
              structs = module.abi!!.structs.map { it.copy(variants = emptyList()) }
            )
        )
      codec.preload(incomplete)
      codec
        .viewRequest("0x42::cache::read", emptyList(), listOf(MoveArgument.Enum("Empty")))
        .shouldBeInstanceOf<AptosResult.Failure>()
    }
  })

private fun abiModule(name: String = "cache") =
  MoveModuleBytecode(
    "0x",
    MoveModule(
      "0x42",
      name,
      emptyList(),
      listOf(
        MoveFunction(
          "read",
          MoveVisibility.PUBLIC,
          false,
          true,
          emptyList(),
          emptyList(),
          emptyList(),
        )
      ),
      emptyList(),
    ),
  )

private const val ENUM_ABI =
  """{"bytecode":"0x","abi":{"address":"0x42","name":"cache","friends":[],"exposed_functions":[{"name":"read","visibility":"public","is_entry":true,"is_view":true,"generic_type_params":[],"params":["0x42::cache::Choice"],"return":[]}],"structs":[{"name":"Choice","is_native":false,"is_enum":true,"abilities":["copy","drop"],"generic_type_params":[],"fields":[],"variants":[{"name":"Empty","fields":[]},{"name":"Pair","fields":[{"name":"count","type":"u64"},{"name":"enabled","type":"bool"}]}]}]}}"""
