@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
@_implementationOnly import SharedBridge_OrgJetbrainsKotlinxKotlinxSerializationCore

extension ExportedKotlinPackages.kotlinx.serialization.`internal` {
    public final class SerializationConstructorMarker: KotlinRuntime.KotlinBase {
    }
}
extension ExportedKotlinPackages.kotlinx.serialization {
    /// KSerializer is responsible for the representation of a serial form of a type [T]
    /// in terms of [encoders][Encoder] and [decoders][Decoder] and for constructing and deconstructing [T]
    /// from/to a sequence of encoding primitives. For classes marked with [@Serializable][Serializable], can be
    /// obtained from generated companion extension `.serializer()` or from [serializer<T>()][serializer] function.
    /// 
    /// Serialization is decoupled from the encoding process to make it completely format-agnostic.
    /// Serialization represents a type as its serial form and is abstracted from the actual
    /// format (whether its JSON, ProtoBuf or a hashing) and unaware of the underlying storage
    /// (whether it is a string builder, byte array or a network socket), while
    /// encoding/decoding is abstracted from a particular type and its serial form and is responsible
    /// for transforming primitives ("here in an int property 'foo'" call from a serializer) into a particular
    /// format-specific representation ("for a given int, append a property name in quotation marks,
    /// then append a colon, then append an actual value" for JSON) and how to retrieve a primitive
    /// ("give me an int that is 'foo' property") from the underlying representation ("expect the next string to be 'foo',
    /// parse it, then parse colon, then parse a string until the next comma as an int and return it).
    /// 
    /// Serial form consists of a structural description, declared by the [descriptor] and
    /// actual serialization and deserialization processes, defined by the corresponding
    /// [serialize] and [deserialize] methods implementation.
    /// 
    /// Structural description specifies how the [T] is represented in the serial form:
    /// its [kind][SerialKind] (e.g. whether it is represented as a primitive, a list or a class),
    /// its [elements][SerialDescriptor.elementNames] and their [positional names][SerialDescriptor.getElementName].
    /// 
    /// Serialization process is defined as a sequence of calls to an [Encoder], and transforms a type [T]
    /// into a stream of format-agnostic primitives that represent [T], such as "here is an int, here is a double
    /// and here is another nested object". It can be demonstrated by the example:
    /// ```
    /// class MyData(int: Int, stringList: List<String>, alwaysZero: Long)
    /// 
    /// // .. serialize method of a corresponding serializer
    /// fun serialize(encoder: Encoder, value: MyData): Unit = encoder.encodeStructure(descriptor) {
    ///     // encodeStructure encodes beginning and end of the structure
    ///     // encode 'int' property as Int
    ///     encodeIntElement(descriptor, index = 0, value.int)
    ///     // encode 'stringList' property as List<String>
    ///     encodeSerializableElement(descriptor, index = 1, serializer<List<String>>, value.stringList)
    ///     // don't encode 'alwaysZero' property because we decided to do so
    /// } // end of the structure
    /// ```
    /// 
    /// Deserialization process is symmetric and uses [Decoder].
    /// 
    /// ### Exception types for `KSerializer` implementation
    /// 
    /// Implementations of [serialize] and [deserialize] methods are allowed to throw
    /// any subtype of [IllegalArgumentException] in order to indicate serialization
    /// and deserialization errors.
    /// 
    /// For serializer implementations, it is recommended to throw subclasses of [SerializationException] for
    /// any serialization-specific errors related to invalid or unsupported format of the data
    /// and [IllegalStateException] for errors during validation of the data.
    public protocol KSerializer: KotlinRuntime.KotlinBase {
    }
}
