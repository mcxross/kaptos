@_exported import ExportedKotlinPackages
@_implementationOnly import KotlinBridges_KotlinStdlib
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport

public protocol _ExportedKotlinPackages_kotlin_collections_Map_Entry: KotlinRuntime.KotlinBase, KotlinStdlib.__ExportedKotlinPackages_kotlin_collections_Map_Entry {
    var key: (any KotlinRuntimeSupport._KotlinBridgeable)? {
        get
    }
    var value: (any KotlinRuntimeSupport._KotlinBridgeable)? {
        get
    }
}
@objc(_KotlinStdlib__ExportedKotlinPackages_kotlin_collections_Map_Entry)
public protocol __ExportedKotlinPackages_kotlin_collections_Map_Entry {
}
public protocol ___ExportedKotlinPackages_kotlin_collections_Map_Entry: KotlinRuntimeSupport._KotlinBridgeable {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlin.AutoCloseable where Self : ExportedKotlinPackages.kotlin.__AutoCloseable {
    public func close() -> Swift.Void {
        return { kotlin_AutoCloseable_close(self.__externalRCRef()); return () }()
    }
}
extension ExportedKotlinPackages.kotlin.AutoCloseable {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlin.CharSequence where Self : ExportedKotlinPackages.kotlin.__CharSequence {
    public var length: Swift.Int32 {
        get {
            return kotlin_CharSequence_length_get(self.__externalRCRef())
        }
    }
    public func _get(
        index: Swift.Int32
    ) -> Swift.Unicode.UTF16.CodeUnit {
        return kotlin_CharSequence_get__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), index)
    }
    public func subSequence(
        startIndex: Swift.Int32,
        endIndex: Swift.Int32
    ) -> any ExportedKotlinPackages.kotlin.CharSequence {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlin_CharSequence_subSequence__TypesOfArguments__Swift_Int32_Swift_Int32__(self.__externalRCRef(), startIndex, endIndex), conformsTo: ExportedKotlinPackages.kotlin.CharSequence.Type.self) as! any ExportedKotlinPackages.kotlin.CharSequence
    }
    public subscript(
        index: Swift.Int32
    ) -> Swift.Unicode.UTF16.CodeUnit {
        get {
            _get(index: index)
        }
    }
}
extension ExportedKotlinPackages.kotlin.CharSequence {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlin.collections.Collection where Self : ExportedKotlinPackages.kotlin.collections.__Collection {
    public var size: Swift.Int32 {
        get {
            return kotlin_collections_Collection_size_get(self.__externalRCRef())
        }
    }
    public func contains(
        element: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        return kotlin_collections_Collection_contains__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), element.map { it in it.__externalRCRef() } ?? nil)
    }
    public func containsAll(
        elements: any ExportedKotlinPackages.kotlin.collections.Collection
    ) -> Swift.Bool {
        return kotlin_collections_Collection_containsAll__TypesOfArguments__anyU20ExportedKotlinPackages_kotlin_collections_Collection__(self.__externalRCRef(), elements.__externalRCRef())
    }
    public func isEmpty() -> Swift.Bool {
        return kotlin_collections_Collection_isEmpty(self.__externalRCRef())
    }
    public func iterator() -> any ExportedKotlinPackages.kotlin.collections.Iterator {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlin_collections_Collection_iterator(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.kotlin.collections.Iterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Iterator
    }
    public static func ~=(
        this: Self,
        element: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        this.contains(element: element)
    }
}
extension ExportedKotlinPackages.kotlin.collections.Collection {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlin.collections.Iterable where Self : ExportedKotlinPackages.kotlin.collections.__Iterable {
    public func iterator() -> any ExportedKotlinPackages.kotlin.collections.Iterator {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlin_collections_Iterable_iterator(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.kotlin.collections.Iterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Iterator
    }
}
extension ExportedKotlinPackages.kotlin.collections.Iterable {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlin.collections.Iterator where Self : ExportedKotlinPackages.kotlin.collections.__Iterator {
    public func hasNext() -> Swift.Bool {
        return kotlin_collections_Iterator_hasNext(self.__externalRCRef())
    }
    public func next() -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
        return { switch kotlin_collections_Iterator_next(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
    }
}
extension ExportedKotlinPackages.kotlin.collections.Iterator {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlin.collections.ListIterator where Self : ExportedKotlinPackages.kotlin.collections.__ListIterator {
    public func hasNext() -> Swift.Bool {
        return kotlin_collections_ListIterator_hasNext(self.__externalRCRef())
    }
    public func hasPrevious() -> Swift.Bool {
        return kotlin_collections_ListIterator_hasPrevious(self.__externalRCRef())
    }
    public func next() -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
        return { switch kotlin_collections_ListIterator_next(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
    }
    public func nextIndex() -> Swift.Int32 {
        return kotlin_collections_ListIterator_nextIndex(self.__externalRCRef())
    }
    public func previous() -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
        return { switch kotlin_collections_ListIterator_previous(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
    }
    public func previousIndex() -> Swift.Int32 {
        return kotlin_collections_ListIterator_previousIndex(self.__externalRCRef())
    }
}
extension ExportedKotlinPackages.kotlin.collections.ListIterator {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.kotlin.collections.Map where Self : ExportedKotlinPackages.kotlin.collections.__Map {
    public var keys: Swift.Set<Swift.Optional<Swift.AnyHashable>> {
        get {
            return kotlin_collections_Map_keys_get(self.__externalRCRef()) as! Swift.Set<Swift.Optional<Swift.AnyHashable>>
        }
    }
    public var size: Swift.Int32 {
        get {
            return kotlin_collections_Map_size_get(self.__externalRCRef())
        }
    }
    public var values: any ExportedKotlinPackages.kotlin.collections.Collection {
        get {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlin_collections_Map_values_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.kotlin.collections.Collection.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Collection
        }
    }
    public func _get(
        key: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
        return { switch kotlin_collections_Map_get__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), key.map { it in it.__externalRCRef() } ?? nil) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
    }
    public func containsKey(
        key: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        return kotlin_collections_Map_containsKey__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), key.map { it in it.__externalRCRef() } ?? nil)
    }
    public func containsValue(
        value: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        return kotlin_collections_Map_containsValue__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil)
    }
    public func isEmpty() -> Swift.Bool {
        return kotlin_collections_Map_isEmpty(self.__externalRCRef())
    }
    public subscript(
        key: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
        get {
            _get(key: key)
        }
    }
}
extension ExportedKotlinPackages.kotlin.collections.Map {
    public typealias Entry = KotlinStdlib._ExportedKotlinPackages_kotlin_collections_Map_Entry
}
@_documentation(visibility: internal)
extension KotlinStdlib._ExportedKotlinPackages_kotlin_collections_Map_Entry where Self : KotlinStdlib.___ExportedKotlinPackages_kotlin_collections_Map_Entry {
    public var key: (any KotlinRuntimeSupport._KotlinBridgeable)? {
        get {
            return { switch kotlin_collections_Map_Entry_key_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
    }
    public var value: (any KotlinRuntimeSupport._KotlinBridgeable)? {
        get {
            return { switch kotlin_collections_Map_Entry_value_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
    }
}
extension KotlinStdlib._ExportedKotlinPackages_kotlin_collections_Map_Entry {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlin.collections.Iterable, ExportedKotlinPackages.kotlin.collections.__Iterable where Wrapped : ExportedKotlinPackages.kotlin.collections._Iterable {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlin.AutoCloseable, ExportedKotlinPackages.kotlin.__AutoCloseable where Wrapped : ExportedKotlinPackages.kotlin._AutoCloseable {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlin.collections.Collection, ExportedKotlinPackages.kotlin.collections.__Collection where Wrapped : ExportedKotlinPackages.kotlin.collections._Collection {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlin.collections.Iterator, ExportedKotlinPackages.kotlin.collections.__Iterator where Wrapped : ExportedKotlinPackages.kotlin.collections._Iterator {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlin.collections.Map, ExportedKotlinPackages.kotlin.collections.__Map where Wrapped : ExportedKotlinPackages.kotlin.collections._Map {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlin.CharSequence, ExportedKotlinPackages.kotlin.__CharSequence where Wrapped : ExportedKotlinPackages.kotlin._CharSequence {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: KotlinStdlib._ExportedKotlinPackages_kotlin_collections_Map_Entry, KotlinStdlib.___ExportedKotlinPackages_kotlin_collections_Map_Entry where Wrapped : KotlinStdlib.__ExportedKotlinPackages_kotlin_collections_Map_Entry {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.kotlin.collections.ListIterator, ExportedKotlinPackages.kotlin.collections.__ListIterator where Wrapped : ExportedKotlinPackages.kotlin.collections._ListIterator {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlin.collections._Iterable {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlin._AutoCloseable {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlin.collections._Collection {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlin.collections._Iterator {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlin.collections._Map {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlin._CharSequence {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: KotlinStdlib.__ExportedKotlinPackages_kotlin_collections_Map_Entry {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.kotlin.collections._ListIterator {
}
extension ExportedKotlinPackages.kotlin.collections {
    public protocol Collection: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlin.collections.Iterable, ExportedKotlinPackages.kotlin.collections._Collection {
        var size: Swift.Int32 {
            get
        }
        func contains(
            element: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> Swift.Bool
        func containsAll(
            elements: any ExportedKotlinPackages.kotlin.collections.Collection
        ) -> Swift.Bool
        func isEmpty() -> Swift.Bool
        func iterator() -> any ExportedKotlinPackages.kotlin.collections.Iterator
    }
    public protocol Iterable: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlin.collections._Iterable {
        func iterator() -> any ExportedKotlinPackages.kotlin.collections.Iterator
    }
    public protocol Iterator: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlin.collections._Iterator {
        func hasNext() -> Swift.Bool
        func next() -> (any KotlinRuntimeSupport._KotlinBridgeable)?
    }
    public protocol ListIterator: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlin.collections.Iterator, ExportedKotlinPackages.kotlin.collections._ListIterator {
        func hasNext() -> Swift.Bool
        func hasPrevious() -> Swift.Bool
        func next() -> (any KotlinRuntimeSupport._KotlinBridgeable)?
        func nextIndex() -> Swift.Int32
        func previous() -> (any KotlinRuntimeSupport._KotlinBridgeable)?
        func previousIndex() -> Swift.Int32
    }
    public protocol Map: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlin.collections._Map {
        var keys: Swift.Set<Swift.Optional<Swift.AnyHashable>> {
            get
        }
        var size: Swift.Int32 {
            get
        }
        var values: any ExportedKotlinPackages.kotlin.collections.Collection {
            get
        }
        func _get(
            key: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> (any KotlinRuntimeSupport._KotlinBridgeable)?
        func containsKey(
            key: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> Swift.Bool
        func containsValue(
            value: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> Swift.Bool
        func isEmpty() -> Swift.Bool
    }
    @objc(_ExportedKotlinPackages_kotlin_collections_Collection)
    public protocol _Collection: ExportedKotlinPackages.kotlin.collections._Iterable {
    }
    @objc(_ExportedKotlinPackages_kotlin_collections_Iterable)
    public protocol _Iterable {
    }
    @objc(_ExportedKotlinPackages_kotlin_collections_Iterator)
    public protocol _Iterator {
    }
    @objc(_ExportedKotlinPackages_kotlin_collections_ListIterator)
    public protocol _ListIterator: ExportedKotlinPackages.kotlin.collections._Iterator {
    }
    @objc(_ExportedKotlinPackages_kotlin_collections_Map)
    public protocol _Map {
    }
    public protocol __Collection: KotlinRuntimeSupport._KotlinBridgeable, ExportedKotlinPackages.kotlin.collections.__Iterable {
    }
    public protocol __Iterable: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __Iterator: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __ListIterator: KotlinRuntimeSupport._KotlinBridgeable, ExportedKotlinPackages.kotlin.collections.__Iterator {
    }
    public protocol __Map: KotlinRuntimeSupport._KotlinBridgeable {
    }
    open class ByteIterator: KotlinRuntime.KotlinBase {
        public override init() {
            precondition(Self.self != ExportedKotlinPackages.kotlin.collections.ByteIterator.self, "ExportedKotlinPackages.kotlin.collections.ByteIterator is an abstract class and cannot be instantiated directly")
            let __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_collections_ByteIterator_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public final func next() -> Swift.Int8 {
            return kotlin_collections_ByteIterator_next(self.__externalRCRef())
        }
        open func nextByte() -> Swift.Int8 {
            if Self.self == ExportedKotlinPackages.kotlin.collections.ByteIterator.self {
                return kotlin_collections_ByteIterator_nextByte(self.__externalRCRef())
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.kotlin.collections.ByteIterator.nextByte': a Swift subclass must override it and must not call super.")
            }
        }
    }
}
extension ExportedKotlinPackages.kotlin {
    public protocol AutoCloseable: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlin._AutoCloseable {
        func close() -> Swift.Void
    }
    public protocol CharSequence: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlin._CharSequence {
        var length: Swift.Int32 {
            get
        }
        func _get(
            index: Swift.Int32
        ) -> Swift.Unicode.UTF16.CodeUnit
        func subSequence(
            startIndex: Swift.Int32,
            endIndex: Swift.Int32
        ) -> any ExportedKotlinPackages.kotlin.CharSequence
    }
    @objc(_ExportedKotlinPackages_kotlin_AutoCloseable)
    public protocol _AutoCloseable {
    }
    @objc(_ExportedKotlinPackages_kotlin_CharSequence)
    public protocol _CharSequence {
    }
    public protocol __AutoCloseable: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __CharSequence: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public final class Array: KotlinRuntime.KotlinBase {
        public var size: Swift.Int32 {
            get {
                return kotlin_Array_size_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            size: Swift.Int32,
            `init`: @escaping (Swift.Int32) -> (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) {
            fatalError()
        }
        public func _get(
            index: Swift.Int32
        ) -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
            return { switch kotlin_Array_get__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), index) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
        public func _set(
            index: Swift.Int32,
            value: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> Swift.Void {
            return { kotlin_Array_set__TypesOfArguments__Swift_Int32_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), index, value.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func iterator() -> any ExportedKotlinPackages.kotlin.collections.Iterator {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: kotlin_Array_iterator(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.kotlin.collections.Iterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Iterator
        }
        public subscript(
            index: Swift.Int32
        ) -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
            get {
                _get(index: index)
            }
            set(value) {
                _set(index: index, value: value)
            }
        }
    }
    public final class ByteArray: KotlinRuntime.KotlinBase {
        public var size: Swift.Int32 {
            get {
                return kotlin_ByteArray_size_get(self.__externalRCRef())
            }
        }
        public init(
            size: Swift.Int32
        ) {
            fatalError()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            size: Swift.Int32,
            `init`: @escaping (Swift.Int32) -> Swift.Int8
        ) {
            fatalError()
        }
        public func _get(
            index: Swift.Int32
        ) -> Swift.Int8 {
            return kotlin_ByteArray_get__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), index)
        }
        public func _set(
            index: Swift.Int32,
            value: Swift.Int8
        ) -> Swift.Void {
            return { kotlin_ByteArray_set__TypesOfArguments__Swift_Int32_Swift_Int8__(self.__externalRCRef(), index, value); return () }()
        }
        public func iterator() -> ExportedKotlinPackages.kotlin.collections.ByteIterator {
            return ExportedKotlinPackages.kotlin.collections.ByteIterator.__createClassWrapper(externalRCRef: kotlin_ByteArray_iterator(self.__externalRCRef()))
        }
        public subscript(
            index: Swift.Int32
        ) -> Swift.Int8 {
            get {
                _get(index: index)
            }
            set(value) {
                _set(index: index, value: value)
            }
        }
    }
    open class Exception: ExportedKotlinPackages.kotlin.Throwable {
        public override init() {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.Exception.self {
                 __kt = kotlin_Exception_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_Exception_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        public override init(
            cause: ExportedKotlinPackages.kotlin.Throwable?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.Exception.self {
                 __kt = kotlin_Exception_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_Exception_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public override init(
            message: Swift.String?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.Exception.self {
                 __kt = kotlin_Exception_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_Exception_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String___(__kt, message ?? nil); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override init(
            message: Swift.String?,
            cause: ExportedKotlinPackages.kotlin.Throwable?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.Exception.self {
                 __kt = kotlin_Exception_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_Exception_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message ?? nil, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
    }
    open class RuntimeException: ExportedKotlinPackages.kotlin.Exception {
        public override init() {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.RuntimeException.self {
                 __kt = kotlin_RuntimeException_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_RuntimeException_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        public override init(
            cause: ExportedKotlinPackages.kotlin.Throwable?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.RuntimeException.self {
                 __kt = kotlin_RuntimeException_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_RuntimeException_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public override init(
            message: Swift.String?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.RuntimeException.self {
                 __kt = kotlin_RuntimeException_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_RuntimeException_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String___(__kt, message ?? nil); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override init(
            message: Swift.String?,
            cause: ExportedKotlinPackages.kotlin.Throwable?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.RuntimeException.self {
                 __kt = kotlin_RuntimeException_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_RuntimeException_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message ?? nil, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
    }
    open class Throwable: KotlinRuntime.KotlinBase, Swift.Error {
        open var cause: ExportedKotlinPackages.kotlin.Throwable? {
            get {
                if Self.self == ExportedKotlinPackages.kotlin.Throwable.self {
                    return { switch kotlin_Throwable_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
                } else {
                    return { switch kotlin_Throwable_cause_get_direct(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
                }
            }
        }
        open var message: Swift.String? {
            get {
                if Self.self == ExportedKotlinPackages.kotlin.Throwable.self {
                    return kotlin_Throwable_message_get(self.__externalRCRef())
                } else {
                    return kotlin_Throwable_message_get_direct(self.__externalRCRef())
                }
            }
        }
        public override init() {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.Throwable.self {
                 __kt = kotlin_Throwable_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_Throwable_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        public init(
            cause: ExportedKotlinPackages.kotlin.Throwable?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.Throwable.self {
                 __kt = kotlin_Throwable_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_Throwable_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public init(
            message: Swift.String?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.Throwable.self {
                 __kt = kotlin_Throwable_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_Throwable_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String___(__kt, message ?? nil); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            message: Swift.String?,
            cause: ExportedKotlinPackages.kotlin.Throwable?
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.kotlin.Throwable.self {
                 __kt = kotlin_Throwable_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { kotlin_Throwable_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message ?? nil, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        @_spi(kotlin$experimental$ExperimentalNativeApi)
        public final func getStackTrace() -> ExportedKotlinPackages.kotlin.Array {
            return ExportedKotlinPackages.kotlin.Array.__createClassWrapper(externalRCRef: kotlin_Throwable_getStackTrace(self.__externalRCRef()))
        }
        public final func printStackTrace() -> Swift.Void {
            return { kotlin_Throwable_printStackTrace(self.__externalRCRef()); return () }()
        }
    }
}
extension ExportedKotlinPackages.kotlin.time {
    public enum DurationUnit: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case NANOSECONDS
        case MICROSECONDS
        case MILLISECONDS
        case SECONDS
        case MINUTES
        case HOURS
        case DAYS
        public var description: Swift.String {
            get {
                switch self {
                case .NANOSECONDS: "NANOSECONDS"
                case .MICROSECONDS: "MICROSECONDS"
                case .MILLISECONDS: "MILLISECONDS"
                case .SECONDS: "SECONDS"
                case .MINUTES: "MINUTES"
                case .HOURS: "HOURS"
                case .DAYS: "DAYS"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .NANOSECONDS: 0
                case .MICROSECONDS: 1
                case .MILLISECONDS: 2
                case .SECONDS: 3
                case .MINUTES: 4
                case .HOURS: 5
                case .DAYS: 6
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "NANOSECONDS": self = .NANOSECONDS
            case "MICROSECONDS": self = .MICROSECONDS
            case "MILLISECONDS": self = .MILLISECONDS
            case "SECONDS": self = .SECONDS
            case "MINUTES": self = .MINUTES
            case "HOURS": self = .HOURS
            case "DAYS": self = .DAYS
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<7 ~= rawValue else { return nil }
            self = DurationUnit.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch kotlin_time_DurationUnit_ordinal(__externalRCRefUnsafe) {
            case 0: self = .NANOSECONDS
            case 1: self = .MICROSECONDS
            case 2: self = .MILLISECONDS
            case 3: self = .SECONDS
            case 4: self = .MINUTES
            case 5: self = .HOURS
            case 6: self = .DAYS
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .NANOSECONDS: kotlin_time_DurationUnit_NANOSECONDS()
            case .MICROSECONDS: kotlin_time_DurationUnit_MICROSECONDS()
            case .MILLISECONDS: kotlin_time_DurationUnit_MILLISECONDS()
            case .SECONDS: kotlin_time_DurationUnit_SECONDS()
            case .MINUTES: kotlin_time_DurationUnit_MINUTES()
            case .HOURS: kotlin_time_DurationUnit_HOURS()
            case .DAYS: kotlin_time_DurationUnit_DAYS()
            default: fatalError()
            }
        }
    }
    public final class Duration: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var INFINITE: ExportedKotlinPackages.kotlin.time.Duration {
                get {
                    return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_INFINITE_get(self.__externalRCRef()))
                }
            }
            public var ZERO: ExportedKotlinPackages.kotlin.time.Duration {
                get {
                    return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_ZERO_get(self.__externalRCRef()))
                }
            }
            public static var shared: ExportedKotlinPackages.kotlin.time.Duration.Companion {
                get {
                    return ExportedKotlinPackages.kotlin.time.Duration.Companion.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_get())
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
            @_spi(kotlin$time$ExperimentalTime)
            public func convert(
                value: Swift.Double,
                sourceUnit: ExportedKotlinPackages.kotlin.time.DurationUnit,
                targetUnit: ExportedKotlinPackages.kotlin.time.DurationUnit
            ) -> Swift.Double {
                return kotlin_time_Duration_Companion_convert__TypesOfArguments__Swift_Double_ExportedKotlinPackages_kotlin_time_DurationUnit_ExportedKotlinPackages_kotlin_time_DurationUnit__(self.__externalRCRef(), value, sourceUnit.__externalRCRef(), targetUnit.__externalRCRef())
            }
            public func getDays(
                _ receiver: Swift.Int32
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_days_get__TypesOfArgumentsE__Swift_Int32__(self.__externalRCRef(), receiver))
            }
            public func getDays(
                _ receiver: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_days_get__TypesOfArgumentsE__Swift_Int64__(self.__externalRCRef(), receiver))
            }
            public func getDays(
                _ receiver: Swift.Double
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_days_get__TypesOfArgumentsE__Swift_Double__(self.__externalRCRef(), receiver))
            }
            public func getHours(
                _ receiver: Swift.Int32
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_hours_get__TypesOfArgumentsE__Swift_Int32__(self.__externalRCRef(), receiver))
            }
            public func getHours(
                _ receiver: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_hours_get__TypesOfArgumentsE__Swift_Int64__(self.__externalRCRef(), receiver))
            }
            public func getHours(
                _ receiver: Swift.Double
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_hours_get__TypesOfArgumentsE__Swift_Double__(self.__externalRCRef(), receiver))
            }
            public func getMicroseconds(
                _ receiver: Swift.Int32
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_microseconds_get__TypesOfArgumentsE__Swift_Int32__(self.__externalRCRef(), receiver))
            }
            public func getMicroseconds(
                _ receiver: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_microseconds_get__TypesOfArgumentsE__Swift_Int64__(self.__externalRCRef(), receiver))
            }
            public func getMicroseconds(
                _ receiver: Swift.Double
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_microseconds_get__TypesOfArgumentsE__Swift_Double__(self.__externalRCRef(), receiver))
            }
            public func getMilliseconds(
                _ receiver: Swift.Int32
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_milliseconds_get__TypesOfArgumentsE__Swift_Int32__(self.__externalRCRef(), receiver))
            }
            public func getMilliseconds(
                _ receiver: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_milliseconds_get__TypesOfArgumentsE__Swift_Int64__(self.__externalRCRef(), receiver))
            }
            public func getMilliseconds(
                _ receiver: Swift.Double
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_milliseconds_get__TypesOfArgumentsE__Swift_Double__(self.__externalRCRef(), receiver))
            }
            public func getMinutes(
                _ receiver: Swift.Int32
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_minutes_get__TypesOfArgumentsE__Swift_Int32__(self.__externalRCRef(), receiver))
            }
            public func getMinutes(
                _ receiver: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_minutes_get__TypesOfArgumentsE__Swift_Int64__(self.__externalRCRef(), receiver))
            }
            public func getMinutes(
                _ receiver: Swift.Double
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_minutes_get__TypesOfArgumentsE__Swift_Double__(self.__externalRCRef(), receiver))
            }
            public func getNanoseconds(
                _ receiver: Swift.Int32
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_nanoseconds_get__TypesOfArgumentsE__Swift_Int32__(self.__externalRCRef(), receiver))
            }
            public func getNanoseconds(
                _ receiver: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_nanoseconds_get__TypesOfArgumentsE__Swift_Int64__(self.__externalRCRef(), receiver))
            }
            public func getNanoseconds(
                _ receiver: Swift.Double
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_nanoseconds_get__TypesOfArgumentsE__Swift_Double__(self.__externalRCRef(), receiver))
            }
            public func getSeconds(
                _ receiver: Swift.Int32
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_seconds_get__TypesOfArgumentsE__Swift_Int32__(self.__externalRCRef(), receiver))
            }
            public func getSeconds(
                _ receiver: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_seconds_get__TypesOfArgumentsE__Swift_Int64__(self.__externalRCRef(), receiver))
            }
            public func getSeconds(
                _ receiver: Swift.Double
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_seconds_get__TypesOfArgumentsE__Swift_Double__(self.__externalRCRef(), receiver))
            }
            public func parse(
                value: Swift.String
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_parse__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
            public func parseIsoString(
                value: Swift.String
            ) -> ExportedKotlinPackages.kotlin.time.Duration {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_Companion_parseIsoString__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
            public func parseIsoStringOrNull(
                value: Swift.String
            ) -> ExportedKotlinPackages.kotlin.time.Duration? {
                return { switch kotlin_time_Duration_Companion_parseIsoStringOrNull__TypesOfArguments__Swift_String__(self.__externalRCRef(), value) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: res); } }()
            }
            public func parseOrNull(
                value: Swift.String
            ) -> ExportedKotlinPackages.kotlin.time.Duration? {
                return { switch kotlin_time_Duration_Companion_parseOrNull__TypesOfArguments__Swift_String__(self.__externalRCRef(), value) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var absoluteValue: ExportedKotlinPackages.kotlin.time.Duration {
            get {
                return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_absoluteValue_get(self.__externalRCRef()))
            }
        }
        public var inWholeDays: Swift.Int64 {
            get {
                return kotlin_time_Duration_inWholeDays_get(self.__externalRCRef())
            }
        }
        public var inWholeHours: Swift.Int64 {
            get {
                return kotlin_time_Duration_inWholeHours_get(self.__externalRCRef())
            }
        }
        public var inWholeMicroseconds: Swift.Int64 {
            get {
                return kotlin_time_Duration_inWholeMicroseconds_get(self.__externalRCRef())
            }
        }
        public var inWholeMilliseconds: Swift.Int64 {
            get {
                return kotlin_time_Duration_inWholeMilliseconds_get(self.__externalRCRef())
            }
        }
        public var inWholeMinutes: Swift.Int64 {
            get {
                return kotlin_time_Duration_inWholeMinutes_get(self.__externalRCRef())
            }
        }
        public var inWholeNanoseconds: Swift.Int64 {
            get {
                return kotlin_time_Duration_inWholeNanoseconds_get(self.__externalRCRef())
            }
        }
        public var inWholeSeconds: Swift.Int64 {
            get {
                return kotlin_time_Duration_inWholeSeconds_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public static func *(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            scale: Swift.Double
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            this._times(scale: scale)
        }
        public static func *(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            scale: Swift.Int32
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            this._times(scale: scale)
        }
        public static func +(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            this._plus(other: other)
        }
        public static prefix func -(
            this: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            this._unaryMinus()
        }
        public static func -(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            this._minus(other: other)
        }
        public static func /(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> Swift.Double {
            this._div(other: other)
        }
        public static func /(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            scale: Swift.Double
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            this._div(scale: scale)
        }
        public static func /(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            scale: Swift.Int32
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            this._div(scale: scale)
        }
        public static func <(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> Swift.Bool {
            this._compareTo(other: other) < 0
        }
        public static func <=(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> Swift.Bool {
            this._compareTo(other: other) <= 0
        }
        public static func >(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> Swift.Bool {
            this._compareTo(other: other) > 0
        }
        public static func >=(
            this: ExportedKotlinPackages.kotlin.time.Duration,
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> Swift.Bool {
            this._compareTo(other: other) >= 0
        }
        public func _compareTo(
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> Swift.Int32 {
            return kotlin_time_Duration_compareTo__TypesOfArguments__ExportedKotlinPackages_kotlin_time_Duration__(self.__externalRCRef(), other.__externalRCRef())
        }
        public func _div(
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> Swift.Double {
            return kotlin_time_Duration_div__TypesOfArguments__ExportedKotlinPackages_kotlin_time_Duration__(self.__externalRCRef(), other.__externalRCRef())
        }
        public func _div(
            scale: Swift.Double
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_div__TypesOfArguments__Swift_Double__(self.__externalRCRef(), scale))
        }
        public func _div(
            scale: Swift.Int32
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_div__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), scale))
        }
        public func _minus(
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_minus__TypesOfArguments__ExportedKotlinPackages_kotlin_time_Duration__(self.__externalRCRef(), other.__externalRCRef()))
        }
        public func _plus(
            other: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_plus__TypesOfArguments__ExportedKotlinPackages_kotlin_time_Duration__(self.__externalRCRef(), other.__externalRCRef()))
        }
        public func _times(
            scale: Swift.Double
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_times__TypesOfArguments__Swift_Double__(self.__externalRCRef(), scale))
        }
        public func _times(
            scale: Swift.Int32
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_times__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), scale))
        }
        public func _unaryMinus() -> ExportedKotlinPackages.kotlin.time.Duration {
            return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Duration_unaryMinus(self.__externalRCRef()))
        }
        public func isFinite() -> Swift.Bool {
            return kotlin_time_Duration_isFinite(self.__externalRCRef())
        }
        public func isInfinite() -> Swift.Bool {
            return kotlin_time_Duration_isInfinite(self.__externalRCRef())
        }
        public func isNegative() -> Swift.Bool {
            return kotlin_time_Duration_isNegative(self.__externalRCRef())
        }
        public func isPositive() -> Swift.Bool {
            return kotlin_time_Duration_isPositive(self.__externalRCRef())
        }
        public func toComponents(
            action: @escaping (Swift.Int64, Swift.Int32, Swift.Int32, Swift.Int32, Swift.Int32) -> (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
            return { switch kotlin_time_Duration_toComponents__TypesOfArguments__U28Swift_Int64_U20Swift_Int32_U20Swift_Int32_U20Swift_Int32_U20Swift_Int32U29202D_U20Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), Unmanaged.passRetained((action as (Swift.Int64, Swift.Int32, Swift.Int32, Swift.Int32, Swift.Int32) -> Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>) as AnyObject).toOpaque()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
        public func toComponents(
            action: @escaping (Swift.Int64, Swift.Int32, Swift.Int32, Swift.Int32) -> (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
            return { switch kotlin_time_Duration_toComponents__TypesOfArguments__U28Swift_Int64_U20Swift_Int32_U20Swift_Int32_U20Swift_Int32U29202D_U20Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), Unmanaged.passRetained((action as (Swift.Int64, Swift.Int32, Swift.Int32, Swift.Int32) -> Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>) as AnyObject).toOpaque()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
        public func toComponents(
            action: @escaping (Swift.Int64, Swift.Int32, Swift.Int32) -> (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
            return { switch kotlin_time_Duration_toComponents__TypesOfArguments__U28Swift_Int64_U20Swift_Int32_U20Swift_Int32U29202D_U20Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), Unmanaged.passRetained((action as (Swift.Int64, Swift.Int32, Swift.Int32) -> Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>) as AnyObject).toOpaque()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
        public func toComponents(
            action: @escaping (Swift.Int64, Swift.Int32) -> (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> (any KotlinRuntimeSupport._KotlinBridgeable)? {
            return { switch kotlin_time_Duration_toComponents__TypesOfArguments__U28Swift_Int64_U20Swift_Int32U29202D_U20Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), Unmanaged.passRetained((action as (Swift.Int64, Swift.Int32) -> Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>) as AnyObject).toOpaque()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
        public func toDouble(
            unit: ExportedKotlinPackages.kotlin.time.DurationUnit
        ) -> Swift.Double {
            return kotlin_time_Duration_toDouble__TypesOfArguments__ExportedKotlinPackages_kotlin_time_DurationUnit__(self.__externalRCRef(), unit.__externalRCRef())
        }
        public func toInt(
            unit: ExportedKotlinPackages.kotlin.time.DurationUnit
        ) -> Swift.Int32 {
            return kotlin_time_Duration_toInt__TypesOfArguments__ExportedKotlinPackages_kotlin_time_DurationUnit__(self.__externalRCRef(), unit.__externalRCRef())
        }
        public func toIsoString() -> Swift.String {
            return kotlin_time_Duration_toIsoString(self.__externalRCRef())
        }
        public func toLong(
            unit: ExportedKotlinPackages.kotlin.time.DurationUnit
        ) -> Swift.Int64 {
            return kotlin_time_Duration_toLong__TypesOfArguments__ExportedKotlinPackages_kotlin_time_DurationUnit__(self.__externalRCRef(), unit.__externalRCRef())
        }
        public func toString(
            unit: ExportedKotlinPackages.kotlin.time.DurationUnit,
            decimals: Swift.Int32
        ) -> Swift.String {
            return kotlin_time_Duration_toString__TypesOfArguments__ExportedKotlinPackages_kotlin_time_DurationUnit_Swift_Int32__(self.__externalRCRef(), unit.__externalRCRef(), decimals)
        }
    }
    public final class Instant: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var DISTANT_FUTURE: ExportedKotlinPackages.kotlin.time.Instant {
                get {
                    return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: kotlin_time_Instant_Companion_DISTANT_FUTURE_get(self.__externalRCRef()))
                }
            }
            public var DISTANT_PAST: ExportedKotlinPackages.kotlin.time.Instant {
                get {
                    return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: kotlin_time_Instant_Companion_DISTANT_PAST_get(self.__externalRCRef()))
                }
            }
            public static var shared: ExportedKotlinPackages.kotlin.time.Instant.Companion {
                get {
                    return ExportedKotlinPackages.kotlin.time.Instant.Companion.__createClassWrapper(externalRCRef: kotlin_time_Instant_Companion_get())
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
            public func fromEpochMilliseconds(
                epochMilliseconds: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Instant {
                return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: kotlin_time_Instant_Companion_fromEpochMilliseconds__TypesOfArguments__Swift_Int64__(self.__externalRCRef(), epochMilliseconds))
            }
            public func fromEpochSeconds(
                epochSeconds: Swift.Int64,
                nanosecondAdjustment: Swift.Int32
            ) -> ExportedKotlinPackages.kotlin.time.Instant {
                return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: kotlin_time_Instant_Companion_fromEpochSeconds__TypesOfArguments__Swift_Int64_Swift_Int32__(self.__externalRCRef(), epochSeconds, nanosecondAdjustment))
            }
            public func fromEpochSeconds(
                epochSeconds: Swift.Int64,
                nanosecondAdjustment: Swift.Int64
            ) -> ExportedKotlinPackages.kotlin.time.Instant {
                return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: kotlin_time_Instant_Companion_fromEpochSeconds__TypesOfArguments__Swift_Int64_Swift_Int64__(self.__externalRCRef(), epochSeconds, nanosecondAdjustment))
            }
            @available(*, unavailable, message: "Use Clock.System.now() instead. Replacement: Clock.System.now()")
            public func now() -> ExportedKotlinPackages.kotlin.time.Instant {
                fatalError()
            }
            public func parse(
                input: any ExportedKotlinPackages.kotlin.CharSequence
            ) -> ExportedKotlinPackages.kotlin.time.Instant {
                return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: kotlin_time_Instant_Companion_parse__TypesOfArguments__anyU20ExportedKotlinPackages_kotlin_CharSequence__(self.__externalRCRef(), input.__externalRCRef()))
            }
            public func parseOrNull(
                input: any ExportedKotlinPackages.kotlin.CharSequence
            ) -> ExportedKotlinPackages.kotlin.time.Instant? {
                return { switch kotlin_time_Instant_Companion_parseOrNull__TypesOfArguments__anyU20ExportedKotlinPackages_kotlin_CharSequence__(self.__externalRCRef(), input.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var epochSeconds: Swift.Int64 {
            get {
                return kotlin_time_Instant_epochSeconds_get(self.__externalRCRef())
            }
        }
        public var nanosecondsOfSecond: Swift.Int32 {
            get {
                return kotlin_time_Instant_nanosecondsOfSecond_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public static func +(
            this: ExportedKotlinPackages.kotlin.time.Instant,
            duration: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Instant {
            this._plus(duration: duration)
        }
        public static func -(
            this: ExportedKotlinPackages.kotlin.time.Instant,
            duration: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Instant {
            this._minus(duration: duration)
        }
        public static func -(
            this: ExportedKotlinPackages.kotlin.time.Instant,
            other: ExportedKotlinPackages.kotlin.time.Instant
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            this._minus(other: other)
        }
        public static func <(
            this: ExportedKotlinPackages.kotlin.time.Instant,
            other: ExportedKotlinPackages.kotlin.time.Instant
        ) -> Swift.Bool {
            this._compareTo(other: other) < 0
        }
        public static func <=(
            this: ExportedKotlinPackages.kotlin.time.Instant,
            other: ExportedKotlinPackages.kotlin.time.Instant
        ) -> Swift.Bool {
            this._compareTo(other: other) <= 0
        }
        public static func >(
            this: ExportedKotlinPackages.kotlin.time.Instant,
            other: ExportedKotlinPackages.kotlin.time.Instant
        ) -> Swift.Bool {
            this._compareTo(other: other) > 0
        }
        public static func >=(
            this: ExportedKotlinPackages.kotlin.time.Instant,
            other: ExportedKotlinPackages.kotlin.time.Instant
        ) -> Swift.Bool {
            this._compareTo(other: other) >= 0
        }
        public func _compareTo(
            other: ExportedKotlinPackages.kotlin.time.Instant
        ) -> Swift.Int32 {
            return kotlin_time_Instant_compareTo__TypesOfArguments__ExportedKotlinPackages_kotlin_time_Instant__(self.__externalRCRef(), other.__externalRCRef())
        }
        public func _minus(
            duration: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Instant {
            return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: kotlin_time_Instant_minus__TypesOfArguments__ExportedKotlinPackages_kotlin_time_Duration__(self.__externalRCRef(), duration.__externalRCRef()))
        }
        public func _minus(
            other: ExportedKotlinPackages.kotlin.time.Instant
        ) -> ExportedKotlinPackages.kotlin.time.Duration {
            return ExportedKotlinPackages.kotlin.time.Duration.__createClassWrapper(externalRCRef: kotlin_time_Instant_minus__TypesOfArguments__ExportedKotlinPackages_kotlin_time_Instant__(self.__externalRCRef(), other.__externalRCRef()))
        }
        public func _plus(
            duration: ExportedKotlinPackages.kotlin.time.Duration
        ) -> ExportedKotlinPackages.kotlin.time.Instant {
            return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: kotlin_time_Instant_plus__TypesOfArguments__ExportedKotlinPackages_kotlin_time_Duration__(self.__externalRCRef(), duration.__externalRCRef()))
        }
        public func toEpochMilliseconds() -> Swift.Int64 {
            return kotlin_time_Instant_toEpochMilliseconds(self.__externalRCRef())
        }
    }
}
@_cdecl("KotlinStdlib_internal_functional_type_callee_SwiftU2EOptionalU3CanyU20KotlinRuntimeSupportU2E_KotlinBridgeableU3E__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64_Swift_Int32_Swift_Int32_Swift_Int32_Swift_Int32__")
package func KotlinStdlib_internal_functional_type_callee_SwiftU2EOptionalU3CanyU20KotlinRuntimeSupportU2E_KotlinBridgeableU3E__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64_Swift_Int32_Swift_Int32_Swift_Int32_Swift_Int32__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.Int64, _ _2: Swift.Int32, _ _3: Swift.Int32, _ _4: Swift.Int32, _ _5: Swift.Int32) -> Swift.UnsafeMutableRawPointer? {
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Int64, Swift.Int32, Swift.Int32, Swift.Int32, Swift.Int32) -> Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>)(_1, _2, _3, _4, _5)
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("KotlinStdlib_internal_functional_type_callee_SwiftU2EOptionalU3CanyU20KotlinRuntimeSupportU2E_KotlinBridgeableU3E__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64_Swift_Int32_Swift_Int32_Swift_Int32__")
package func KotlinStdlib_internal_functional_type_callee_SwiftU2EOptionalU3CanyU20KotlinRuntimeSupportU2E_KotlinBridgeableU3E__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64_Swift_Int32_Swift_Int32_Swift_Int32__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.Int64, _ _2: Swift.Int32, _ _3: Swift.Int32, _ _4: Swift.Int32) -> Swift.UnsafeMutableRawPointer? {
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Int64, Swift.Int32, Swift.Int32, Swift.Int32) -> Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>)(_1, _2, _3, _4)
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("KotlinStdlib_internal_functional_type_callee_SwiftU2EOptionalU3CanyU20KotlinRuntimeSupportU2E_KotlinBridgeableU3E__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64_Swift_Int32_Swift_Int32__")
package func KotlinStdlib_internal_functional_type_callee_SwiftU2EOptionalU3CanyU20KotlinRuntimeSupportU2E_KotlinBridgeableU3E__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64_Swift_Int32_Swift_Int32__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.Int64, _ _2: Swift.Int32, _ _3: Swift.Int32) -> Swift.UnsafeMutableRawPointer? {
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Int64, Swift.Int32, Swift.Int32) -> Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>)(_1, _2, _3)
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("KotlinStdlib_internal_functional_type_callee_SwiftU2EOptionalU3CanyU20KotlinRuntimeSupportU2E_KotlinBridgeableU3E__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64_Swift_Int32__")
package func KotlinStdlib_internal_functional_type_callee_SwiftU2EOptionalU3CanyU20KotlinRuntimeSupportU2E_KotlinBridgeableU3E__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64_Swift_Int32__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.Int64, _ _2: Swift.Int32) -> Swift.UnsafeMutableRawPointer? {
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Int64, Swift.Int32) -> Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>)(_1, _2)
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("kotlin_AutoCloseable_close__reverse_swift")
package func kotlin_AutoCloseable_close__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.AutoCloseable.Type.self) as! any ExportedKotlinPackages.kotlin.AutoCloseable
    let _result: Swift.Void = _self.close()
    return { _result; return true }()
}

@_cdecl("kotlin_CharSequence_get__TypesOfArguments__Swift_Int32____reverse_swift")
package func kotlin_CharSequence_get__TypesOfArguments__Swift_Int32____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ index: Swift.Int32) -> Swift.UInt16 {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.CharSequence.Type.self) as! any ExportedKotlinPackages.kotlin.CharSequence
    let _result: Swift.Unicode.UTF16.CodeUnit = _self._get(index: index)
    return _result
}

@_cdecl("kotlin_CharSequence_length_get__reverse_swift")
package func kotlin_CharSequence_length_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Int32 {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.CharSequence.Type.self) as! any ExportedKotlinPackages.kotlin.CharSequence
    let _result: Swift.Int32 = _self.length
    return _result
}

@_cdecl("kotlin_CharSequence_subSequence__TypesOfArguments__Swift_Int32_Swift_Int32____reverse_swift")
package func kotlin_CharSequence_subSequence__TypesOfArguments__Swift_Int32_Swift_Int32____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ startIndex: Swift.Int32, _ endIndex: Swift.Int32) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.CharSequence.Type.self) as! any ExportedKotlinPackages.kotlin.CharSequence
    let _result: any ExportedKotlinPackages.kotlin.CharSequence = _self.subSequence(startIndex: startIndex, endIndex: endIndex)
    return _result.__externalRCRef()
}

@_cdecl("kotlin_Throwable_cause_get__reverse_swift")
package func kotlin_Throwable_cause_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer? {
    let _self = ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Optional<ExportedKotlinPackages.kotlin.Throwable> = _self.cause
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("kotlin_Throwable_message_get__reverse_swift")
package func kotlin_Throwable_message_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.String? {
    let _self = ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Optional<Swift.String> = _self.message
    return _result ?? nil
}

@_cdecl("kotlin_collections_ByteIterator_nextByte__reverse_swift")
package func kotlin_collections_ByteIterator_nextByte__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Int8 {
    let _self = ExportedKotlinPackages.kotlin.collections.ByteIterator.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Int8 = _self.nextByte()
    return _result
}

@_cdecl("kotlin_collections_Collection_containsAll__TypesOfArguments__anyU20ExportedKotlinPackages_kotlin_collections_Collection____reverse_swift")
package func kotlin_collections_Collection_containsAll__TypesOfArguments__anyU20ExportedKotlinPackages_kotlin_collections_Collection____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ elements: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Collection.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Collection
    let _result: Swift.Bool = _self.containsAll(elements: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: elements, conformsTo: ExportedKotlinPackages.kotlin.collections.Collection.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Collection)
    return _result
}

@_cdecl("kotlin_collections_Collection_contains__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift")
package func kotlin_collections_Collection_contains__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ element: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Collection.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Collection
    let _result: Swift.Bool = _self.contains(element: { switch element { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }())
    return _result
}

@_cdecl("kotlin_collections_Collection_isEmpty__reverse_swift")
package func kotlin_collections_Collection_isEmpty__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Collection.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Collection
    let _result: Swift.Bool = _self.isEmpty()
    return _result
}

@_cdecl("kotlin_collections_Collection_iterator__reverse_swift")
package func kotlin_collections_Collection_iterator__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Collection.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Collection
    let _result: any ExportedKotlinPackages.kotlin.collections.Iterator = _self.iterator()
    return _result.__externalRCRef()
}

@_cdecl("kotlin_collections_Collection_size_get__reverse_swift")
package func kotlin_collections_Collection_size_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Int32 {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Collection.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Collection
    let _result: Swift.Int32 = _self.size
    return _result
}

@_cdecl("kotlin_collections_Iterable_iterator__reverse_swift")
package func kotlin_collections_Iterable_iterator__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Iterable.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Iterable
    let _result: any ExportedKotlinPackages.kotlin.collections.Iterator = _self.iterator()
    return _result.__externalRCRef()
}

@_cdecl("kotlin_collections_Iterator_hasNext__reverse_swift")
package func kotlin_collections_Iterator_hasNext__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Iterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Iterator
    let _result: Swift.Bool = _self.hasNext()
    return _result
}

@_cdecl("kotlin_collections_Iterator_next__reverse_swift")
package func kotlin_collections_Iterator_next__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer? {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Iterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Iterator
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = _self.next()
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("kotlin_collections_ListIterator_hasNext__reverse_swift")
package func kotlin_collections_ListIterator_hasNext__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.ListIterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.ListIterator
    let _result: Swift.Bool = _self.hasNext()
    return _result
}

@_cdecl("kotlin_collections_ListIterator_hasPrevious__reverse_swift")
package func kotlin_collections_ListIterator_hasPrevious__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.ListIterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.ListIterator
    let _result: Swift.Bool = _self.hasPrevious()
    return _result
}

@_cdecl("kotlin_collections_ListIterator_nextIndex__reverse_swift")
package func kotlin_collections_ListIterator_nextIndex__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Int32 {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.ListIterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.ListIterator
    let _result: Swift.Int32 = _self.nextIndex()
    return _result
}

@_cdecl("kotlin_collections_ListIterator_next__reverse_swift")
package func kotlin_collections_ListIterator_next__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer? {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.ListIterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.ListIterator
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = _self.next()
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("kotlin_collections_ListIterator_previousIndex__reverse_swift")
package func kotlin_collections_ListIterator_previousIndex__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Int32 {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.ListIterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.ListIterator
    let _result: Swift.Int32 = _self.previousIndex()
    return _result
}

@_cdecl("kotlin_collections_ListIterator_previous__reverse_swift")
package func kotlin_collections_ListIterator_previous__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer? {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.ListIterator.Type.self) as! any ExportedKotlinPackages.kotlin.collections.ListIterator
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = _self.previous()
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("kotlin_collections_Map_Entry_key_get__reverse_swift")
package func kotlin_collections_Map_Entry_key_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer? {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: KotlinStdlib._ExportedKotlinPackages_kotlin_collections_Map_Entry.Type.self) as! any KotlinStdlib._ExportedKotlinPackages_kotlin_collections_Map_Entry
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = _self.key
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("kotlin_collections_Map_Entry_value_get__reverse_swift")
package func kotlin_collections_Map_Entry_value_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer? {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: KotlinStdlib._ExportedKotlinPackages_kotlin_collections_Map_Entry.Type.self) as! any KotlinStdlib._ExportedKotlinPackages_kotlin_collections_Map_Entry
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = _self.value
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("kotlin_collections_Map_containsKey__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift")
package func kotlin_collections_Map_containsKey__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ key: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Map.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Map
    let _result: Swift.Bool = _self.containsKey(key: { switch key { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }())
    return _result
}

@_cdecl("kotlin_collections_Map_containsValue__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift")
package func kotlin_collections_Map_containsValue__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ value: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Map.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Map
    let _result: Swift.Bool = _self.containsValue(value: { switch value { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }())
    return _result
}

@_cdecl("kotlin_collections_Map_get__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift")
package func kotlin_collections_Map_get__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable_____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ key: Swift.UnsafeMutableRawPointer?) -> Swift.UnsafeMutableRawPointer? {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Map.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Map
    let _result: Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable> = _self._get(key: { switch key { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }())
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("kotlin_collections_Map_isEmpty__reverse_swift")
package func kotlin_collections_Map_isEmpty__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Map.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Map
    let _result: Swift.Bool = _self.isEmpty()
    return _result
}

@_cdecl("kotlin_collections_Map_keys_get__reverse_swift")
package func kotlin_collections_Map_keys_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Any {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Map.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Map
    let _result: Swift.Set<Swift.Optional<Swift.AnyHashable>> = _self.keys
    return Set(_result.map { it in it as! NSObject? ?? NSNull() })
}

@_cdecl("kotlin_collections_Map_size_get__reverse_swift")
package func kotlin_collections_Map_size_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Int32 {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Map.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Map
    let _result: Swift.Int32 = _self.size
    return _result
}

@_cdecl("kotlin_collections_Map_values_get__reverse_swift")
package func kotlin_collections_Map_values_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.kotlin.collections.Map.Type.self) as! any ExportedKotlinPackages.kotlin.collections.Map
    let _result: any ExportedKotlinPackages.kotlin.collections.Collection = _self.values
    return _result.__externalRCRef()
}

