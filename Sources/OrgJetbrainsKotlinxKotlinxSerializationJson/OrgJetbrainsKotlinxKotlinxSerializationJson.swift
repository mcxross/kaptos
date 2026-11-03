@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
import KotlinStdlib
import OrgJetbrainsKotlinxKotlinxSerializationCore
@_implementationOnly import SharedBridge_OrgJetbrainsKotlinxKotlinxSerializationJson

extension ExportedKotlinPackages.kotlinx.serialization.json {
    public enum JsonElement_SealedType: KotlinRuntimeSupport.SealedType {
        case jsonArray(ExportedKotlinPackages.kotlinx.serialization.json.JsonArray_SealedType)
        case jsonObject(ExportedKotlinPackages.kotlinx.serialization.json.JsonObject_SealedType)
        case jsonPrimitive(ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive_SealedType)
        public var value: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement {
            get {
                switch self {
                case let .jsonArray(type): type.value
                case let .jsonObject(type): type.value
                case let .jsonPrimitive(type): type.value
                }
            }
        }
    }
    public enum JsonPrimitive_SealedType: KotlinRuntimeSupport.SealedType {
        case jsonNull(ExportedKotlinPackages.kotlinx.serialization.json.JsonNull_SealedType)
        case unknown(ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive_SealedType.Unknown)
        public struct Unknown: KotlinRuntimeSupport.SealedType {
            public let value: ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive
            init(
                _ value: ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive
            ) {
                self.value = value
            }
        }
        public var value: ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive {
            get {
                switch self {
                case let .jsonNull(type): type.value
                case let .unknown(type): type.value
                }
            }
        }
    }
    /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
    /// 
    /// Since this class also implements [List] interface, you can use
    /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
    public final class JsonArray: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement {
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.kotlinx.serialization.json.JsonArray.Companion {
                get {
                    return ExportedKotlinPackages.kotlinx.serialization.json.JsonArray.Companion.__createClassWrapper(externalRCRef: kotlinx_serialization_json_JsonArray_Companion_get())
                }
            }
            private override init() {
                fatalError()
            }
            package override init(
                __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
                options: KotlinRuntime.KotlinBaseConstructionOptions
            ) {
                super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
            }
            /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
            /// 
            /// Since this class also implements [List] interface, you can use
            /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public var size: Swift.Int32 {
            get {
                return kotlinx_serialization_json_JsonArray_size_get(self.__externalRCRef())
            }
        }
        public init(
            content: [ExportedKotlinPackages.kotlinx.serialization.json.JsonElement]
        ) {
            let __kt = kotlinx_serialization_json_JsonArray_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlinx_serialization_json_JsonArray_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_kotlinx_serialization_json_JsonElement___(__kt, content); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func _get(
            index: Swift.Int32
        ) -> ExportedKotlinPackages.kotlinx.serialization.json.JsonElement {
            return ExportedKotlinPackages.kotlinx.serialization.json.JsonElement.__createClassWrapper(externalRCRef: kotlinx_serialization_json_JsonArray_get__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), index))
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func contains(
            element: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement
        ) -> Swift.Bool {
            return kotlinx_serialization_json_JsonArray_contains__TypesOfArguments__ExportedKotlinPackages_kotlinx_serialization_json_JsonElement__(self.__externalRCRef(), element.__externalRCRef())
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func indexOf(
            element: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement
        ) -> Swift.Int32 {
            return kotlinx_serialization_json_JsonArray_indexOf__TypesOfArguments__ExportedKotlinPackages_kotlinx_serialization_json_JsonElement__(self.__externalRCRef(), element.__externalRCRef())
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func isEmpty() -> Swift.Bool {
            return kotlinx_serialization_json_JsonArray_isEmpty(self.__externalRCRef())
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func iterator() -> any ExportedKotlinPackages.kotlin.collections.Iterator {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlinx_serialization_json_JsonArray_iterator(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.kotlin.collections.Iterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Iterator
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func lastIndexOf(
            element: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement
        ) -> Swift.Int32 {
            return kotlinx_serialization_json_JsonArray_lastIndexOf__TypesOfArguments__ExportedKotlinPackages_kotlinx_serialization_json_JsonElement__(self.__externalRCRef(), element.__externalRCRef())
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func listIterator() -> any ExportedKotlinPackages.kotlin.collections.ListIterator {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlinx_serialization_json_JsonArray_listIterator(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.kotlin.collections.ListIterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.ListIterator
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func listIterator(
            index: Swift.Int32
        ) -> any ExportedKotlinPackages.kotlin.collections.ListIterator {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlinx_serialization_json_JsonArray_listIterator__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), index), conformsTo: ExportedKotlinPackages.kotlin.collections.ListIterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.ListIterator
        }
        public override func sealedType() -> ExportedKotlinPackages.kotlinx.serialization.json.JsonElement_SealedType {
            .jsonArray(.init(self))
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public func subList(
            fromIndex: Swift.Int32,
            toIndex: Swift.Int32
        ) -> [ExportedKotlinPackages.kotlinx.serialization.json.JsonElement] {
            return kotlinx_serialization_json_JsonArray_subList__TypesOfArguments__Swift_Int32_Swift_Int32__(self.__externalRCRef(), fromIndex, toIndex) as! Swift.Array<ExportedKotlinPackages.kotlinx.serialization.json.JsonElement>
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public static func ~=(
            this: ExportedKotlinPackages.kotlinx.serialization.json.JsonArray,
            element: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement
        ) -> Swift.Bool {
            this.contains(element: element)
        }
        /// Class representing JSON array, consisting of indexed values, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [List] interface, you can use
        /// traditional methods like [List.get] or [List.getOrNull] to obtain Json elements.
        public subscript(
            index: Swift.Int32
        ) -> ExportedKotlinPackages.kotlinx.serialization.json.JsonElement {
            get {
                _get(index: index)
            }
        }
    }
    /// Class representing single JSON element.
    /// Can be [JsonPrimitive], [JsonArray] or [JsonObject].
    /// 
    /// [JsonElement.toString] properly prints JSON tree as valid JSON, taking into account quoted values and primitives.
    /// Whole hierarchy is serializable, but only when used with [Json] as [JsonElement] is purely JSON-specific structure
    /// which has a meaningful schemaless semantics only for JSON.
    /// 
    /// The whole hierarchy is [serializable][Serializable] only by [Json] format.
    open class JsonElement: KotlinRuntime.KotlinBase {
        /// Class representing single JSON element.
        /// Can be [JsonPrimitive], [JsonArray] or [JsonObject].
        /// 
        /// [JsonElement.toString] properly prints JSON tree as valid JSON, taking into account quoted values and primitives.
        /// Whole hierarchy is serializable, but only when used with [Json] as [JsonElement] is purely JSON-specific structure
        /// which has a meaningful schemaless semantics only for JSON.
        /// 
        /// The whole hierarchy is [serializable][Serializable] only by [Json] format.
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement.Companion {
                get {
                    return ExportedKotlinPackages.kotlinx.serialization.json.JsonElement.Companion.__createClassWrapper(externalRCRef: kotlinx_serialization_json_JsonElement_Companion_get())
                }
            }
            private override init() {
                fatalError()
            }
            package override init(
                __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
                options: KotlinRuntime.KotlinBaseConstructionOptions
            ) {
                super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
            }
            /// Class representing single JSON element.
            /// Can be [JsonPrimitive], [JsonArray] or [JsonObject].
            /// 
            /// [JsonElement.toString] properly prints JSON tree as valid JSON, taking into account quoted values and primitives.
            /// Whole hierarchy is serializable, but only when used with [Json] as [JsonElement] is purely JSON-specific structure
            /// which has a meaningful schemaless semantics only for JSON.
            /// 
            /// The whole hierarchy is [serializable][Serializable] only by [Json] format.
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        open func sealedType() -> ExportedKotlinPackages.kotlinx.serialization.json.JsonElement_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.kotlinx.serialization.json.JsonArray: .jsonArray(.init(value))
            case let value as ExportedKotlinPackages.kotlinx.serialization.json.JsonObject: .jsonObject(.init(value))
            case let value as ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive: .jsonPrimitive(value.sealedType())
            default: fatalError("missing sealedType for \(self)")
            }
        }
    }
    /// Class representing JSON `null` value
    public final class JsonNull: ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive {
        /// Content of given element without quotes. For [JsonNull], this method returns a "null" string.
        /// [JsonPrimitive.contentOrNull] should be used for [JsonNull] to get a `null`.
        public override var content: Swift.String {
            get {
                return kotlinx_serialization_json_JsonNull_content_get(self.__externalRCRef())
            }
        }
        /// Indicates whether the primitive was explicitly constructed from [String] and
        /// whether it should be serialized as one. E.g. `JsonPrimitive("42")` is represented
        /// by a string, while `JsonPrimitive(42)` is not.
        /// These primitives will be serialized as `"42"` and `42` respectively.
        public override var isString: Swift.Bool {
            get {
                return kotlinx_serialization_json_JsonNull_isString_get(self.__externalRCRef())
            }
        }
        public static var shared: ExportedKotlinPackages.kotlinx.serialization.json.JsonNull {
            get {
                return ExportedKotlinPackages.kotlinx.serialization.json.JsonNull.__createClassWrapper(externalRCRef: kotlinx_serialization_json_JsonNull_get())
            }
        }
        private init() {
            fatalError()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override func sealedType() -> ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive_SealedType {
            .jsonNull(.init(self))
        }
        /// Class representing JSON `null` value
        public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
            fatalError()
        }
        /// Class representing JSON `null` value
        public func serializer(
            typeParamsSerializers: any ExportedKotlinPackages.kotlinx.serialization.KSerializer...
        ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
            fatalError()
        }
    }
    /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
    /// 
    /// Since this class also implements [Map] interface, you can use
    /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
    public final class JsonObject: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement {
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.kotlinx.serialization.json.JsonObject.Companion {
                get {
                    return ExportedKotlinPackages.kotlinx.serialization.json.JsonObject.Companion.__createClassWrapper(externalRCRef: kotlinx_serialization_json_JsonObject_Companion_get())
                }
            }
            private override init() {
                fatalError()
            }
            package override init(
                __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
                options: KotlinRuntime.KotlinBaseConstructionOptions
            ) {
                super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
            }
            /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
            /// 
            /// Since this class also implements [Map] interface, you can use
            /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        @available(*, unavailable, message: "Declaration uses unsupported types")
        public var entries: Swift.Never {
            get {
                fatalError()
            }
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public var keys: Swift.Set<Swift.String> {
            get {
                return kotlinx_serialization_json_JsonObject_keys_get(self.__externalRCRef()) as! Swift.Set<Swift.String>
            }
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public var size: Swift.Int32 {
            get {
                return kotlinx_serialization_json_JsonObject_size_get(self.__externalRCRef())
            }
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public var values: any ExportedKotlinPackages.kotlin.collections.Collection {
            get {
                return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlinx_serialization_json_JsonObject_values_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.kotlin.collections.Collection.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Collection
            }
        }
        public init(
            content: [Swift.String: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement]
        ) {
            let __kt = kotlinx_serialization_json_JsonObject_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlinx_serialization_json_JsonObject_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Dictionary_Swift_String_ExportedKotlinPackages_kotlinx_serialization_json_JsonElement___(__kt, content); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public func _get(
            key: Swift.String
        ) -> ExportedKotlinPackages.kotlinx.serialization.json.JsonElement? {
            return { switch kotlinx_serialization_json_JsonObject_get__TypesOfArguments__Swift_String__(self.__externalRCRef(), key) { case nil: .none; case let res?: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement.__createClassWrapper(externalRCRef: res); } }()
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public func containsKey(
            key: Swift.String
        ) -> Swift.Bool {
            return kotlinx_serialization_json_JsonObject_containsKey__TypesOfArguments__Swift_String__(self.__externalRCRef(), key)
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public func containsValue(
            value: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement
        ) -> Swift.Bool {
            return kotlinx_serialization_json_JsonObject_containsValue__TypesOfArguments__ExportedKotlinPackages_kotlinx_serialization_json_JsonElement__(self.__externalRCRef(), value.__externalRCRef())
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public func isEmpty() -> Swift.Bool {
            return kotlinx_serialization_json_JsonObject_isEmpty(self.__externalRCRef())
        }
        public override func sealedType() -> ExportedKotlinPackages.kotlinx.serialization.json.JsonElement_SealedType {
            .jsonObject(.init(self))
        }
        /// Class representing JSON object, consisting of name-value pairs, where value is arbitrary [JsonElement]
        /// 
        /// Since this class also implements [Map] interface, you can use
        /// traditional methods like [Map.get] or [Map.getValue] to obtain Json elements.
        public subscript(
            key: Swift.String
        ) -> ExportedKotlinPackages.kotlinx.serialization.json.JsonElement? {
            get {
                _get(key: key)
            }
        }
    }
    /// Class representing JSON primitive value.
    /// JSON primitives include numbers, strings, booleans and special null value [JsonNull].
    open class JsonPrimitive: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement {
        /// Class representing JSON primitive value.
        /// JSON primitives include numbers, strings, booleans and special null value [JsonNull].
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive.Companion {
                get {
                    return ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive.Companion.__createClassWrapper(externalRCRef: kotlinx_serialization_json_JsonPrimitive_Companion_get())
                }
            }
            private override init() {
                fatalError()
            }
            package override init(
                __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
                options: KotlinRuntime.KotlinBaseConstructionOptions
            ) {
                super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
            }
            /// Class representing JSON primitive value.
            /// JSON primitives include numbers, strings, booleans and special null value [JsonNull].
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        /// Content of given element without quotes. For [JsonNull], this method returns a "null" string.
        /// [JsonPrimitive.contentOrNull] should be used for [JsonNull] to get a `null`.
        open var content: Swift.String {
            get {
                if Self.self == ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive.self {
                    return kotlinx_serialization_json_JsonPrimitive_content_get(self.__externalRCRef())
                } else {
                    fatalError("Cannot invoke the inherited implementation of abstract property 'ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive.content': a Swift subclass must override it and must not call super.")
                }
            }
        }
        /// Indicates whether the primitive was explicitly constructed from [String] and
        /// whether it should be serialized as one. E.g. `JsonPrimitive("42")` is represented
        /// by a string, while `JsonPrimitive(42)` is not.
        /// These primitives will be serialized as `"42"` and `42` respectively.
        open var isString: Swift.Bool {
            get {
                if Self.self == ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive.self {
                    return kotlinx_serialization_json_JsonPrimitive_isString_get(self.__externalRCRef())
                } else {
                    fatalError("Cannot invoke the inherited implementation of abstract property 'ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive.isString': a Swift subclass must override it and must not call super.")
                }
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        open func sealedType() -> ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.kotlinx.serialization.json.JsonNull: .jsonNull(.init(value))
            default: .unknown(.init(self))
            }
        }
        @_disfavoredOverload
        public final override func sealedType() -> ExportedKotlinPackages.kotlinx.serialization.json.JsonElement_SealedType {
            .jsonPrimitive(sealedType())
        }
    }
    public struct JsonArray_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.kotlinx.serialization.json.JsonArray
        init(
            _ value: ExportedKotlinPackages.kotlinx.serialization.json.JsonArray
        ) {
            self.value = value
        }
    }
    public struct JsonNull_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.kotlinx.serialization.json.JsonNull
        init(
            _ value: ExportedKotlinPackages.kotlinx.serialization.json.JsonNull
        ) {
            self.value = value
        }
    }
    public struct JsonObject_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.kotlinx.serialization.json.JsonObject
        init(
            _ value: ExportedKotlinPackages.kotlinx.serialization.json.JsonObject
        ) {
            self.value = value
        }
    }
}
@_cdecl("kotlinx_serialization_json_JsonPrimitive_content_get__reverse_swift")
package func kotlinx_serialization_json_JsonPrimitive_content_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.String {
    let _self = ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.String = _self.content
    return _result
}

@_cdecl("kotlinx_serialization_json_JsonPrimitive_isString_get__reverse_swift")
package func kotlinx_serialization_json_JsonPrimitive_isString_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = ExportedKotlinPackages.kotlinx.serialization.json.JsonPrimitive.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Bool = _self.isString
    return _result
}

