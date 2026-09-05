package xyz.mcxross.kaptos.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.*
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.AptosConfig
import xyz.mcxross.kaptos.model.*
import xyz.mcxross.kaptos.move.*
import xyz.mcxross.kaptos.transaction.bcs.AptosBcsReader
import xyz.mcxross.kaptos.view.MoveViewResult

class TypedViewTest :
  StringSpec({
    "typed views post a BCS request and retain exact u64 values" {
      var posts = 0
      val http =
        HttpClient(
          MockEngine { request ->
            if (request.method == HttpMethod.Get) {
              respond(
                Json.encodeToString(MoveModuleBytecode.serializer(), viewModule()),
                headers = jsonHeaders,
              )
            } else {
              posts++
              request.body.contentType.toString() shouldBe "application/x.aptos.view_function+bcs"
              val reader =
                AptosBcsReader((request.body as OutgoingContent.ByteArrayContent).bytes())
              reader.accountAddress() shouldBe AccountAddress.fromString("0x42")
              reader.string() shouldBe "demo"
              reader.string() shouldBe "read"
              reader.vector { typeTag() }.size shouldBe 0
              val args = reader.vector { bytes() }
              args.size shouldBe 1
              args.single().toList() shouldBe List(8) { (-1).toByte() }
              reader.ensureFinished()
              respond("[\"18446744073709551615\"]", headers = jsonHeaders)
            }
          }
        ) {
          install(ContentNegotiation) { json() }
        }
      val aptos = Aptos(AptosConfig(network = Network.LOCAL, httpClient = http))
      try {
        val value =
          aptos.views
            .call("0x42::demo::read", arguments = listOf(MoveArgument.U64(ULong.MAX_VALUE)))
            .shouldBeInstanceOf<AptosResult.Success<MoveViewResult>>()
            .value
        value
          .decodeValue(0, ULong.serializer())
          .shouldBeInstanceOf<AptosResult.Success<ULong>>()
          .value shouldBe ULong.MAX_VALUE
        posts shouldBe 1
      } finally {
        aptos.close()
        http.close()
      }
    }

    "historical ABI lookup and execution use the same ledger version" {
      val versions = mutableListOf<String?>()
      val http =
        HttpClient(
          MockEngine { request ->
            versions += request.url.parameters["ledger_version"]
            if (request.method == HttpMethod.Get)
              respond(
                Json.encodeToString(MoveModuleBytecode.serializer(), viewModule()),
                headers = jsonHeaders,
              )
            else respond("[]", headers = jsonHeaders)
          }
        ) {
          install(ContentNegotiation) { json() }
        }
      val aptos = Aptos(AptosConfig(network = Network.LOCAL, httpClient = http))
      try {
        aptos.views
          .call("0x42::demo::read", arguments = listOf(MoveArgument.U64(1u)), ledgerVersion = 123u)
          .shouldBeInstanceOf<AptosResult.Success<MoveViewResult>>()
        versions shouldBe listOf("123", "123")
      } finally {
        aptos.close()
        http.close()
      }
    }

    "transaction ABI preload is shared with typed views" {
      var gets = 0
      val http =
        HttpClient(
          MockEngine { request ->
            if (request.method == HttpMethod.Get) gets++
            respond("[]", headers = jsonHeaders)
          }
        ) {
          install(ContentNegotiation) { json() }
        }
      val aptos = Aptos(AptosConfig(network = Network.LOCAL, httpClient = http))
      try {
        aptos.transactions.preloadModuleAbis(viewModule())
        aptos.views
          .call("0x42::demo::read", arguments = listOf(MoveArgument.U64(1u)))
          .shouldBeInstanceOf<AptosResult.Success<MoveViewResult>>()
        gets shouldBe 0
      } finally {
        aptos.close()
        http.close()
      }
    }

    "invalid typed arguments fail before POST" {
      var posts = 0
      val http =
        HttpClient(
          MockEngine { request ->
            if (request.method == HttpMethod.Post) posts++
            respond(
              Json.encodeToString(MoveModuleBytecode.serializer(), viewModule()),
              headers = jsonHeaders,
            )
          }
        ) {
          install(ContentNegotiation) { json() }
        }
      val aptos = Aptos(AptosConfig(network = Network.LOCAL, httpClient = http))
      try {
        aptos.views
          .call("0x42::demo::read", arguments = listOf(MoveArgument.U8(1u)))
          .shouldBeInstanceOf<AptosResult.Failure>()
          .error
          .shouldBeInstanceOf<AptosError.Validation>()
        posts shouldBe 0
      } finally {
        aptos.close()
        http.close()
      }
    }

    val invalid =
      listOf(
        "missing value" to emptyList(),
        "extra value" to listOf(MoveArgument.U64(1u), MoveArgument.U64(2u)),
        "wrong integer width" to listOf(MoveArgument.U8(1u)),
        "opaque BCS" to listOf(MoveArgument.PreSerialized(ByteArray(8))),
        "ambiguous enum" to listOf(MoveArgument.Enum("A")),
      )
    for ((name, args) in invalid) {
      "codec rejects $name" {
        val codec =
          MoveArgumentCodec(MoveModuleLoader { _, _ -> AptosResult.Success(viewModule()) })
        codec
          .viewRequest("0x42::demo::read", emptyList(), args)
          .shouldBeInstanceOf<AptosResult.Failure>()
          .error
          .shouldBeInstanceOf<AptosError.Validation>()
      }
    }

    "non-view functions and generic arity mismatches are rejected" {
      val codec =
        MoveArgumentCodec(
          MoveModuleLoader { _, _ -> AptosResult.Success(viewModule(view = false)) }
        )
      codec
        .viewRequest("0x42::demo::read", emptyList(), listOf(MoveArgument.U64(1u)))
        .shouldBeInstanceOf<AptosResult.Failure>()
      val valid = MoveArgumentCodec(MoveModuleLoader { _, _ -> AptosResult.Success(viewModule()) })
      valid
        .viewRequest("0x42::demo::read", listOf(TypeTagU64), listOf(MoveArgument.U64(1u)))
        .shouldBeInstanceOf<AptosResult.Failure>()
    }

    "nested opaque arguments and signer parameters are rejected" {
      for ((param, value) in
        listOf(
          "vector<u64>" to MoveArgument.Vector(listOf(MoveArgument.PreSerialized(ByteArray(8)))),
          "signer" to MoveArgument.Address(AccountAddress.ONE),
          "&u64" to MoveArgument.U64(1u),
        )) {
        val codec =
          MoveArgumentCodec(MoveModuleLoader { _, _ -> AptosResult.Success(viewModule(param)) })
        codec
          .viewRequest("0x42::demo::read", emptyList(), listOf(value))
          .shouldBeInstanceOf<AptosResult.Failure>()
      }
    }

    "typed values use canonical BCS for strings options bytes and signed integers" {
      val cases =
        listOf(
          Triple("0x1::string::String", MoveArgument.StringValue("hi"), listOf(2, 104, 105)),
          Triple("vector<u8>", MoveArgument.Bytes(byteArrayOf(1, 2)), listOf(2, 1, 2)),
          Triple("0x1::option::Option<u8>", MoveArgument.Option(null), listOf(0)),
          Triple("0x1::option::Option<u8>", MoveArgument.Option(MoveArgument.U8(9u)), listOf(1, 9)),
          Triple("i16", MoveArgument.I16(-2), listOf(254, 255)),
        )
      for ((type, argument, expected) in cases) {
        val codec =
          MoveArgumentCodec(MoveModuleLoader { _, _ -> AptosResult.Success(viewModule(type)) })
        val request =
          codec
            .viewRequest("0x42::demo::read", emptyList(), listOf(argument))
            .shouldBeInstanceOf<AptosResult.Success<ByteArray>>()
            .value
        val reader = AptosBcsReader(request)
        reader.accountAddress()
        reader.string()
        reader.string()
        reader.vector { typeTag() }
        reader.vector { bytes() }.single().map { it.toInt() and 255 } shouldBe expected
        reader.ensureFinished()
      }
    }

    "struct fields must exactly match the ABI" {
      val base = viewModule("0x42::demo::Record")
      val module =
        base.copy(
          abi =
            base.abi!!.copy(
              structs =
                listOf(
                  MoveStruct(
                    name = "Record",
                    isNative = false,
                    abilities = listOf(MoveAbility.COPY),
                    genericTypeParams = emptyList(),
                    fields = listOf(MoveStructField("count", "u64")),
                  )
                )
            )
        )
      val codec = MoveArgumentCodec(MoveModuleLoader { _, _ -> AptosResult.Success(module) })
      for (fields in
        listOf(
          emptyMap(),
          mapOf("extra" to MoveArgument.U64(1u)),
          mapOf("count" to MoveArgument.U8(1u)),
        )) {
        codec
          .viewRequest("0x42::demo::read", emptyList(), listOf(MoveArgument.Struct(fields)))
          .shouldBeInstanceOf<AptosResult.Failure>()
      }
      codec
        .viewRequest(
          "0x42::demo::read",
          emptyList(),
          listOf(MoveArgument.Struct(mapOf("count" to MoveArgument.U64(1u)))),
        )
        .shouldBeInstanceOf<AptosResult.Success<ByteArray>>()
    }

    "ABI loading preserves cancellation" {
      val codec =
        MoveArgumentCodec(MoveModuleLoader { _, _ -> throw CancellationException("cancelled") })
      shouldThrow<CancellationException> {
        codec.viewRequest("0x42::demo::read", emptyList(), listOf(MoveArgument.U64(1u)))
      }
    }

    "return decoding reports invalid indexes and incompatible serializers" {
      val value = MoveViewResult(listOf(JsonPrimitive("abc")))
      value
        .decodeValue(-1, String.serializer())
        .shouldBeInstanceOf<AptosResult.Failure>()
        .error
        .shouldBeInstanceOf<AptosError.Validation>()
      value
        .decodeValue(0, ULong.serializer())
        .shouldBeInstanceOf<AptosResult.Failure>()
        .error
        .shouldBeInstanceOf<AptosError.Serialization>()
    }
  })

private val jsonHeaders =
  headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())

private fun viewModule(param: String = "u64", view: Boolean = true) =
  MoveModuleBytecode(
    "0x",
    MoveModule(
      address = "0x42",
      name = "demo",
      friends = emptyList(),
      structs = emptyList(),
      exposedFunctions =
        listOf(
          MoveFunction(
            "read",
            MoveVisibility.PUBLIC,
            false,
            view,
            emptyList(),
            listOf(param),
            emptyList(),
          )
        ),
    ),
  )
