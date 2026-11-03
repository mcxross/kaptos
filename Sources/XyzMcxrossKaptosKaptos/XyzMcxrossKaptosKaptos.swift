@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
import KotlinStdlib
import OrgJetbrainsKotlinxKotlinxSerializationCore
import OrgJetbrainsKotlinxKotlinxSerializationJson
@_implementationOnly import SharedBridge_XyzMcxrossKaptosKaptos

public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset, ExportedKotlinPackages.xyz.mcxross.kaptos.account.__AccountAsset {
    public var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAsset_Coin_type_get(self.__externalRCRef()))
        }
    }
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_account_AccountAsset_Coin_value_get(self.__externalRCRef())
        }
    }
    public init(
        type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag
    ) {
        let __kt = xyz_mcxross_kaptos_account_AccountAsset_Coin_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_account_AccountAsset_Coin_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag__(__kt, type.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAsset_Coin_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag__(self.__externalRCRef(), type.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset_SealedType {
        .coin(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Companion: KotlinRuntime.KotlinBase {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Companion {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAsset_Companion_get())
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
    public func coin(
        type: Swift.String
    ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAsset_Companion_coin__TypesOfArguments__Swift_String__(self.__externalRCRef(), type), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset
    }
    public func fungibleAsset(
        metadataAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
    ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAsset_Companion_fungibleAsset__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), metadataAddress.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset
    }
    public func fungibleAsset(
        metadataAddress: Swift.String
    ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAsset_Companion_fungibleAsset__TypesOfArguments__Swift_String__(self.__externalRCRef(), metadataAddress), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset, ExportedKotlinPackages.xyz.mcxross.kaptos.account.__AccountAsset {
    public var metadataAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_metadataAddress_get(self.__externalRCRef()))
        }
    }
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_value_get(self.__externalRCRef())
        }
    }
    public init(
        metadataAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
    ) {
        let __kt = xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__(__kt, metadataAddress.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        metadataAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__(self.__externalRCRef(), metadataAddress.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset_SealedType {
        .fungibleAsset(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address_value_get(self.__externalRCRef()))
        }
    }
    public init(
        value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__(__kt, value.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__(self.__externalRCRef(), value.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .address(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: Swift.Bool {
        get {
            return xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.Bool
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.Bool
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool_copy__TypesOfArguments__Swift_Bool__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .bool(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: ExportedKotlinPackages.kotlin.ByteArray {
        get {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes_value_get(self.__externalRCRef()))
        }
    }
    public init(
        value: ExportedKotlinPackages.kotlin.ByteArray
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, value.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .bytes(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.String
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.String
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .text(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.String
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.String
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .u128(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: Swift.UInt16 {
        get {
            return xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt16
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt16__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt16
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16_copy__TypesOfArguments__Swift_UInt16__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .u16(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.String
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.String
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .u256(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: Swift.UInt32 {
        get {
            return xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt32
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt32__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt32
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32_copy__TypesOfArguments__Swift_UInt32__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .u32(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: Swift.UInt64 {
        get {
            return xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt64
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt64
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64_copy__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .u64(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public var value: Swift.UInt8 {
        get {
            return xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt8
    ) {
        let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt8__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt8
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8_copy__TypesOfArguments__Swift_UInt8__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        .u8(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_Api_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var errorCode: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Api_errorCode_get(self.__externalRCRef())
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Api_message_get(self.__externalRCRef())
        }
    }
    public var vmErrorCode: Swift.Int64? {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Api_vmErrorCode_get(self.__externalRCRef()).map { it in it.int64Value }
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        errorCode: Swift.String,
        vmErrorCode: Swift.Int64?,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_Api_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_Api_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_String_Swift_Optional_Swift_Int64__Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message, errorCode, vmErrorCode.map { it in NSNumber(value: it) } ?? nil, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        errorCode: Swift.String,
        vmErrorCode: Swift.Int64?,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_Api_copy__TypesOfArguments__Swift_String_Swift_String_Swift_Optional_Swift_Int64__Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(self.__externalRCRef(), message, errorCode, vmErrorCode.map { it in NSNumber(value: it) } ?? nil, cause.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .api(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_Cancelled_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Cancelled_message_get(self.__externalRCRef())
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_Cancelled_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_Cancelled_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_Cancelled_copy__TypesOfArguments__Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(self.__externalRCRef(), message, cause.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .cancelled(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_Crypto_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Crypto_message_get(self.__externalRCRef())
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_Crypto_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_Crypto_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_Crypto_copy__TypesOfArguments__Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(self.__externalRCRef(), message, cause.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .crypto(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_Indexer_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Indexer_message_get(self.__externalRCRef())
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_Indexer_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_Indexer_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_Indexer_copy__TypesOfArguments__Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(self.__externalRCRef(), message, cause.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .indexer(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_Serialization_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Serialization_message_get(self.__externalRCRef())
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_Serialization_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_Serialization_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_Serialization_copy__TypesOfArguments__Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(self.__externalRCRef(), message, cause.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .serialization(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_Timeout_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Timeout_message_get(self.__externalRCRef())
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_Timeout_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_Timeout_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_Timeout_copy__TypesOfArguments__Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(self.__externalRCRef(), message, cause.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .timeout(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_Transport_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Transport_message_get(self.__externalRCRef())
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_Transport_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_Transport_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_Transport_copy__TypesOfArguments__Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(self.__externalRCRef(), message, cause.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .transport(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var errorCode: Swift.String? {
        get {
            return xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_errorCode_get(self.__externalRCRef())
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_message_get(self.__externalRCRef())
        }
    }
    public var vmErrorCode: Swift.Int64? {
        get {
            return xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_vmErrorCode_get(self.__externalRCRef()).map { it in it.int64Value }
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?,
        errorCode: Swift.String?,
        vmErrorCode: Swift.Int64?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable__Swift_Optional_Swift_String__Swift_Optional_Swift_Int64___(__kt, message, cause.map { it in it.__externalRCRef() } ?? nil, errorCode ?? nil, vmErrorCode.map { it in NSNumber(value: it) } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?,
        errorCode: Swift.String?,
        vmErrorCode: Swift.Int64?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_copy__TypesOfArguments__Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable__Swift_Optional_Swift_String__Swift_Optional_Swift_Int64___(self.__externalRCRef(), message, cause.map { it in it.__externalRCRef() } ?? nil, errorCode ?? nil, vmErrorCode.map { it in NSNumber(value: it) } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .unsupportedFeature(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_Validation_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_Validation_message_get(self.__externalRCRef())
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosError_Validation_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosError_Validation_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(__kt, message, cause.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        message: Swift.String,
        cause: ExportedKotlinPackages.kotlin.Throwable?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosError_Validation_copy__TypesOfArguments__Swift_String_Swift_Optional_ExportedKotlinPackages_kotlin_Throwable___(self.__externalRCRef(), message, cause.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        .validation(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult_Failure: KotlinRuntime.KotlinBase {
    public var error: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError {
        get {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosResult_Failure_error_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError
        }
    }
    public init(
        error: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosResult_Failure_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosResult_Failure_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError__(__kt, error.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        error: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult_Failure {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult_Failure.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosResult_Failure_copy__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError__(self.__externalRCRef(), error.__externalRCRef()))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult_Success: KotlinRuntime.KotlinBase {
    public var value: (any KotlinRuntimeSupport._KotlinBridgeable)? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosResult_Success_value_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
    }
    public init(
        value: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) {
        let __kt = xyz_mcxross_kaptos_model_AptosResult_Success_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_AptosResult_Success_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(__kt, value.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult_Success {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult_Success.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosResult_Success_copy__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__MultisigPayload {
    public var call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction_call_get(self.__externalRCRef()))
        }
    }
    public init(
        call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall
    ) {
        let __kt = xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionCall__(__kt, call.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionCall__(self.__externalRCRef(), call.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload_SealedType {
        .entryFunction(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__MultisigPayload {
    public var script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MultisigPayload_Script_script_get(self.__externalRCRef()))
        }
    }
    public init(
        script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload
    ) {
        let __kt = xyz_mcxross_kaptos_model_MultisigPayload_Script_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_MultisigPayload_Script_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ScriptPayload__(__kt, script.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MultisigPayload_Script_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_ScriptPayload__(self.__externalRCRef(), script.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload_SealedType {
        .script(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__ReplayProtection {
    public var value: Swift.UInt64 {
        get {
            return xyz_mcxross_kaptos_model_ReplayProtection_Nonce_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt64
    ) {
        let __kt = xyz_mcxross_kaptos_model_ReplayProtection_Nonce_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_ReplayProtection_Nonce_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt64
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ReplayProtection_Nonce_copy__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection_SealedType {
        .nonce(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__ReplayProtection {
    public var value: Swift.UInt64 {
        get {
            return xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt64
    ) {
        let __kt = xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt64
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber_copy__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection_SealedType {
        .sequenceNumber(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Companion: KotlinRuntime.KotlinBase {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Companion {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_Companion_get())
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
    public func fromBcs(
        bytes: ExportedKotlinPackages.kotlin.ByteArray
    ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_Companion_fromBcs__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExecutable {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_Empty_get())
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
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable_SealedType {
        .empty(.init(self))
    }
}
/// Server-side sentinel placed in a payload after decryption.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExecutable {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted_get())
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
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable_SealedType {
        .encrypted(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExecutable {
    public var call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction_call_get(self.__externalRCRef()))
        }
    }
    public init(
        call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall
    ) {
        let __kt = xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionCall__(__kt, call.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionCall__(self.__externalRCRef(), call.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable_SealedType {
        .entryFunction(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExecutable {
    public var script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_Script_script_get(self.__externalRCRef()))
        }
    }
    public init(
        script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload
    ) {
        let __kt = xyz_mcxross_kaptos_model_TransactionExecutable_Script_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_TransactionExecutable_Script_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ScriptPayload__(__kt, script.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_Script_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_ScriptPayload__(self.__externalRCRef(), script.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable_SealedType {
        .script(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_Companion: KotlinRuntime.KotlinBase {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_Companion {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExtraConfig_Companion_get())
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
    public func fromBcs(
        bytes: ExportedKotlinPackages.kotlin.ByteArray
    ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExtraConfig_Companion_fromBcs__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExtraConfig {
    public var multisigAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress? {
        get {
            return { switch xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_multisigAddress_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var replayProtectionNonce: Swift.UInt64? {
        get {
            return xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_replayProtectionNonce_get(self.__externalRCRef()).map { it in it.uint64Value }
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        multisigAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
        replayProtectionNonce: Swift.UInt64?
    ) {
        let __kt = xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_Swift_UInt64___(__kt, multisigAddress.map { it in it.__externalRCRef() } ?? nil, replayProtectionNonce.map { it in NSNumber(value: it) } ?? nil); return () }()
    }
    public func copy(
        multisigAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
        replayProtectionNonce: Swift.UInt64?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_copy__TypesOfArguments__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_Swift_UInt64___(self.__externalRCRef(), multisigAddress.map { it in it.__externalRCRef() } ?? nil, replayProtectionNonce.map { it in NSNumber(value: it) } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig_SealedType {
        .v1(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Companion: KotlinRuntime.KotlinBase {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Companion {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Companion_get())
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
    /// Concise factory for the most common payload form.
    public func entryFunction(
        function: Swift.String,
        typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag],
        arguments: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Companion_entryFunction__TypesOfArguments__Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), function, typeArguments, arguments))
    }
    /// Builds an entry-function payload using a type-safe DSL block.
    public func entryFunction(
        function: Swift.String,
        typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag],
        builder: @escaping (ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgumentBuilder) -> Swift.Void
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Companion_entryFunction__TypesOfArguments__Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__U28ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilderU29202D_U20Swift_Void__(self.__externalRCRef(), function, typeArguments, Unmanaged.passRetained((builder as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgumentBuilder) -> Swift.Void) as AnyObject).toOpaque()))
    }
    /// Builds an entry-function payload with vararg arguments inferred as [MoveArgument]s.
    public func entryFunctionOf(
        function: Swift.String,
        arguments: (any KotlinRuntimeSupport._KotlinBridgeable)?...
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Companion_entryFunctionOf__TypesOfArguments__Swift_String_Swift_Array_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___Vararg___(self.__externalRCRef(), function, arguments.map { it in it as! NSObject? ?? NSNull() }))
    }
    /// Builds an entry-function payload with explicit [typeArguments] and vararg arguments inferred
    /// as [MoveArgument]s.
    public func entryFunctionOf(
        function: Swift.String,
        typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag],
        arguments: (any KotlinRuntimeSupport._KotlinBridgeable)?...
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Companion_entryFunctionOf__TypesOfArguments__Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__Swift_Array_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___Vararg___(self.__externalRCRef(), function, typeArguments, arguments.map { it in it as! NSObject? ?? NSNull() }))
    }
    /// Builds an entry-function payload with string [typeArguments] and vararg arguments inferred as
    /// [MoveArgument]s.
    public func entryFunctionOf(
        function: Swift.String,
        typeArguments: [Swift.String],
        arguments: (any KotlinRuntimeSupport._KotlinBridgeable)?...
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Companion_entryFunctionOf__TypesOfArguments__Swift_String_Swift_Array_Swift_String__Swift_Array_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___Vararg___(self.__externalRCRef(), function, typeArguments, arguments.map { it in it as! NSObject? ?? NSNull() }))
    }
    public func fromBcs(
        bytes: ExportedKotlinPackages.kotlin.ByteArray
    ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Companion_fromBcs__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionPayload {
    public var payload: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedTransactionPayload {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedTransactionPayload.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Encrypted_payload_get(self.__externalRCRef()))
        }
    }
    public init(
        payload: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedTransactionPayload
    ) {
        let __kt = xyz_mcxross_kaptos_model_TransactionPayload_Encrypted_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_TransactionPayload_Encrypted_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EncryptedTransactionPayload__(__kt, payload.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        payload: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedTransactionPayload
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Encrypted_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_EncryptedTransactionPayload__(self.__externalRCRef(), payload.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload_SealedType {
        .encrypted(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionPayload {
    public var call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction_call_get(self.__externalRCRef()))
        }
    }
    public init(
        call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall
    ) {
        let __kt = xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionCall__(__kt, call.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        call: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionCall__(self.__externalRCRef(), call.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload_SealedType {
        .entryFunction(.init(self))
    }
}
/// V1 inner payload used by orderless transactions and future transaction extensions.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionPayload {
    public var executable: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable {
        get {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_executable_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable
        }
    }
    public var extraConfig: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig {
        get {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_extraConfig_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        executable: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable,
        extraConfig: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig
    ) {
        let __kt = xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig__(__kt, executable.__externalRCRef(), extraConfig.__externalRCRef()); return () }()
    }
    public func copy(
        executable: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable,
        extraConfig: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_copy__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig__(self.__externalRCRef(), executable.__externalRCRef(), extraConfig.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload_SealedType {
        .innerV1(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionPayload {
    public var multisigAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Multisig_multisigAddress_get(self.__externalRCRef()))
        }
    }
    public var payload: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload)? {
        get {
            return { switch xyz_mcxross_kaptos_model_TransactionPayload_Multisig_payload_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload; } }()
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        multisigAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
        payload: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload)?
    ) {
        let __kt = xyz_mcxross_kaptos_model_TransactionPayload_Multisig_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_TransactionPayload_Multisig_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload___(__kt, multisigAddress.__externalRCRef(), payload.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        multisigAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
        payload: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload)?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Multisig_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload___(self.__externalRCRef(), multisigAddress.__externalRCRef(), payload.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload_SealedType {
        .multisig(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionPayload {
    public var script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Script_script_get(self.__externalRCRef()))
        }
    }
    public init(
        script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload
    ) {
        let __kt = xyz_mcxross_kaptos_model_TransactionPayload_Script_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_TransactionPayload_Script_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ScriptPayload__(__kt, script.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        script: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptPayload
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_Script_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_ScriptPayload__(self.__externalRCRef(), script.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload_SealedType {
        .script(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Companion: KotlinRuntime.KotlinBase {
    /// Used while an external sponsor is expected to provide its address and authenticator later.
    public var EXTERNAL_FEE_PAYER_PLACEHOLDER: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_Companion_EXTERNAL_FEE_PAYER_PLACEHOLDER_get(self.__externalRCRef()))
        }
    }
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Companion {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_Companion_get())
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
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__UnsignedTransaction {
    public var feePayerAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_feePayerAddress_get(self.__externalRCRef()))
        }
    }
    public var rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_rawTransaction_get(self.__externalRCRef()))
        }
    }
    public var secondarySignerAddresses: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress] {
        get {
            return xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_secondarySignerAddresses_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress>
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction,
        secondarySignerAddresses: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress],
        feePayerAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
    ) {
        let __kt = xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_RawTransaction_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__(__kt, rawTransaction.__externalRCRef(), secondarySignerAddresses, feePayerAddress.__externalRCRef()); return () }()
    }
    public func copy(
        rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction,
        secondarySignerAddresses: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress],
        feePayerAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_RawTransaction_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__(self.__externalRCRef(), rawTransaction.__externalRCRef(), secondarySignerAddresses, feePayerAddress.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction_SealedType {
        .feePayer(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__UnsignedTransaction {
    public var rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_rawTransaction_get(self.__externalRCRef()))
        }
    }
    public var secondarySignerAddresses: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress] {
        get {
            return xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_secondarySignerAddresses_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress>
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction,
        secondarySignerAddresses: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress]
    ) {
        let __kt = xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_RawTransaction_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress___(__kt, rawTransaction.__externalRCRef(), secondarySignerAddresses); return () }()
    }
    public func copy(
        rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction,
        secondarySignerAddresses: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress]
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_RawTransaction_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress___(self.__externalRCRef(), rawTransaction.__externalRCRef(), secondarySignerAddresses))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction_SealedType {
        .multiAgent(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__UnsignedTransaction {
    public var rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_Simple_rawTransaction_get(self.__externalRCRef()))
        }
    }
    public init(
        rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction
    ) {
        let __kt = xyz_mcxross_kaptos_model_UnsignedTransaction_Simple_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_model_UnsignedTransaction_Simple_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_RawTransaction__(__kt, rawTransaction.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_Simple_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_RawTransaction__(self.__externalRCRef(), rawTransaction.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction_SealedType {
        .simple(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Address_value_get(self.__externalRCRef()))
        }
    }
    public init(
        value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_Address_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_Address_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__(__kt, value.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Address_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__(self.__externalRCRef(), value.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .address(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.Bool {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_Bool_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.Bool
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_Bool_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_Bool_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.Bool
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Bool_copy__TypesOfArguments__Swift_Bool__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .bool(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: ExportedKotlinPackages.kotlin.ByteArray {
        get {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Bytes_value_get(self.__externalRCRef()))
        }
    }
    public init(
        value: ExportedKotlinPackages.kotlin.ByteArray
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_Bytes_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_Bytes_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, value.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .bytes(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Companion: KotlinRuntime.KotlinBase {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Companion {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Companion_get())
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
    /// Converts a supported Kotlin value into its corresponding [MoveArgument].
    /// 
    /// Mapping rules:
    /// - [MoveArgument] -> returned as-is
    /// - [AccountAddress] -> [MoveArgument.Address]
    /// - [AccountAddressInput] -> [MoveArgument.Address]
    /// - [Boolean] -> [MoveArgument.Bool]
    /// - [UByte] -> [MoveArgument.U8]
    /// - [UShort] -> [MoveArgument.U16]
    /// - [UInt] -> [MoveArgument.U32]
    /// - [ULong] -> [MoveArgument.U64]
    /// - [Byte] -> [MoveArgument.I8]
    /// - [Short] -> [MoveArgument.I16]
    /// - [Int] -> [MoveArgument.I32]
    /// - [Long] -> [MoveArgument.I64]
    /// - [ByteArray] -> [MoveArgument.Bytes]
    /// - [String] -> [MoveArgument.StringValue]
    /// - [Iterable] / [Array] -> [MoveArgument.Vector] (elements recursively mapped)
    /// - [Map] -> [MoveArgument.Struct] (field values recursively mapped)
    /// - `null` -> [MoveArgument.Option] with `null`
    public func from(
        value: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Companion_from__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
    }
    /// Converts an iterable of Kotlin values into a list of [MoveArgument]s.
    public func fromAll(
        values: any ExportedKotlinPackages.kotlin.collections.Iterable
    ) -> [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument] {
        return xyz_mcxross_kaptos_move_MoveArgument_Companion_fromAll__TypesOfArguments__anyU20ExportedKotlinPackages_kotlin_collections_Iterable__(self.__externalRCRef(), values.__externalRCRef()) as! Swift.Array<any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument>
    }
    /// Converts vararg Kotlin values into a list of [MoveArgument]s.
    public func fromAll(
        values: (any KotlinRuntimeSupport._KotlinBridgeable)?...
    ) -> [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument] {
        return xyz_mcxross_kaptos_move_MoveArgument_Companion_fromAll__TypesOfArguments__Swift_Array_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___Vararg___(self.__externalRCRef(), values.map { it in it as! NSObject? ?? NSNull() }) as! Swift.Array<any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument>
    }
}
/// A Move enum expressed by variant and field name; ABI resolution determines indices/order.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var fields: [Swift.String: any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument] {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_Enum_fields_get(self.__externalRCRef()) as! Swift.Dictionary<Swift.String,any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument>
        }
    }
    public var variant: Swift.String {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_Enum_variant_get(self.__externalRCRef())
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        variant: Swift.String,
        fields: [Swift.String: any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_Enum_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_Enum_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Dictionary_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(__kt, variant, fields); return () }()
    }
    public func copy(
        variant: Swift.String,
        fields: [Swift.String: any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Enum_copy__TypesOfArguments__Swift_String_Swift_Dictionary_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), variant, fields))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .`enum`(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_I128_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.String
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_I128_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_I128_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.String
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_I128_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .i128(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.Int16 {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_I16_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.Int16
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_I16_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_I16_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int16__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.Int16
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_I16_copy__TypesOfArguments__Swift_Int16__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .i16(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_I256_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.String
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_I256_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_I256_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.String
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_I256_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .i256(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.Int32 {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_I32_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.Int32
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_I32_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_I32_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.Int32
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_I32_copy__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .i32(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.Int64 {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_I64_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.Int64
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_I64_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_I64_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int64__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.Int64
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_I64_copy__TypesOfArguments__Swift_Int64__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .i64(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.Int8 {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_I8_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.Int8
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_I8_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_I8_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int8__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.Int8
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_I8_copy__TypesOfArguments__Swift_Int8__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .i8(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: (any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument)? {
        get {
            return { switch xyz_mcxross_kaptos_move_MoveArgument_Option_value_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument; } }()
        }
    }
    public init(
        value: (any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument)?
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_Option_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_Option_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(__kt, value.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: (any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument)?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Option_copy__TypesOfArguments__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .option(.init(self))
    }
}
/// Escape hatch for a value that has already been serialized according to its Move ABI type.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: ExportedKotlinPackages.kotlin.ByteArray {
        get {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_PreSerialized_value_get(self.__externalRCRef()))
        }
    }
    public init(
        value: ExportedKotlinPackages.kotlin.ByteArray
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_PreSerialized_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_PreSerialized_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, value.__externalRCRef()); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .preSerialized(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_StringValue_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.String
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_StringValue_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_StringValue_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.String
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_StringValue_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .stringValue(.init(self))
    }
}
/// A Move struct expressed by field name; ABI resolution determines declaration order.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var fields: [Swift.String: any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument] {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_Struct_fields_get(self.__externalRCRef()) as! Swift.Dictionary<Swift.String,any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument>
        }
    }
    public init(
        fields: [Swift.String: any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_Struct_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_Struct_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Dictionary_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(__kt, fields); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        fields: [Swift.String: any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Struct_copy__TypesOfArguments__Swift_Dictionary_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), fields))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .`struct`(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_U128_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.String
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_U128_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_U128_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.String
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_U128_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .u128(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.UInt16 {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_U16_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt16
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_U16_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_U16_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt16__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt16
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_U16_copy__TypesOfArguments__Swift_UInt16__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .u16(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_U256_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.String
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_U256_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_U256_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.String
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_U256_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .u256(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.UInt32 {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_U32_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt32
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_U32_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_U32_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt32__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt32
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_U32_copy__TypesOfArguments__Swift_UInt32__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .u32(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.UInt64 {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_U64_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt64
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_U64_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_U64_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt64
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_U64_copy__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .u64(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var value: Swift.UInt8 {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_U8_value_get(self.__externalRCRef())
        }
    }
    public init(
        value: Swift.UInt8
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_U8_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_U8_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt8__(__kt, value); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        value: Swift.UInt8
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_U8_copy__TypesOfArguments__Swift_UInt8__(self.__externalRCRef(), value))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .u8(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public var values: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument] {
        get {
            return xyz_mcxross_kaptos_move_MoveArgument_Vector_values_get(self.__externalRCRef()) as! Swift.Array<any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument>
        }
    }
    public init(
        values: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
    ) {
        let __kt = xyz_mcxross_kaptos_move_MoveArgument_Vector_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_move_MoveArgument_Vector_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(__kt, values); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        values: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_Vector_copy__TypesOfArguments__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), values))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        .vector(.init(self))
    }
}
/// Register a top-level domain for the currently supported one-year duration.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy, ExportedKotlinPackages.xyz.mcxross.kaptos.names.__NameExpirationPolicy {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain_get())
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
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy_SealedType {
        .domain(.init(self))
    }
}
/// Make a subdomain expire with its parent domain.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy, ExportedKotlinPackages.xyz.mcxross.kaptos.names.__NameExpirationPolicy {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain_get())
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
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy_SealedType {
        .followDomain(.init(self))
    }
}
/// Give a subdomain its own expiry, expressed as Unix seconds.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy, ExportedKotlinPackages.xyz.mcxross.kaptos.names.__NameExpirationPolicy {
    public var expirationTimestampSecs: Swift.UInt64 {
        get {
            return xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent_expirationTimestampSecs_get(self.__externalRCRef())
        }
    }
    public init(
        expirationTimestampSecs: Swift.UInt64
    ) {
        let __kt = xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__(__kt, expirationTimestampSecs); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public func copy(
        expirationTimestampSecs: Swift.UInt64
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent_copy__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), expirationTimestampSecs))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy_SealedType {
        .independent(.init(self))
    }
}
/// Authenticator produced by an on-chain account-abstraction function.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.__AccountAuthenticator {
    public var accountIdentity: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString? {
        get {
            return { switch xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_accountIdentity_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var function: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_function_get(self.__externalRCRef()))
        }
    }
    public var signature: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_signature_get(self.__externalRCRef()))
        }
    }
    public var signingMessageDigest: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_signingMessageDigest_get(self.__externalRCRef()))
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        function: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction,
        signingMessageDigest: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
        accountIdentity: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString?
    ) {
        let __kt = xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString___(__kt, function.__externalRCRef(), signingMessageDigest.__externalRCRef(), signature.__externalRCRef(), accountIdentity.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    public func copy(
        function: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction,
        signingMessageDigest: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
        accountIdentity: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString?
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString___(self.__externalRCRef(), function.__externalRCRef(), signingMessageDigest.__externalRCRef(), signature.__externalRCRef(), accountIdentity.map { it in it.__externalRCRef() } ?? nil))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator_SealedType {
        .abstraction(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Companion: KotlinRuntime.KotlinBase {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Companion {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Companion_get())
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
    public func fromBcs(
        bytes: ExportedKotlinPackages.kotlin.ByteArray
    ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator {
        return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Companion_fromBcs__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.__AccountAuthenticator {
    public var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_publicKey_get(self.__externalRCRef()))
        }
    }
    public var signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_signature_get(self.__externalRCRef()))
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature
    ) {
        let __kt = xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Ed25519Signature__(__kt, publicKey.__externalRCRef(), signature.__externalRCRef()); return () }()
    }
    public func copy(
        publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Ed25519Signature__(self.__externalRCRef(), publicKey.__externalRCRef(), signature.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator_SealedType {
        .ed25519(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.__AccountAuthenticator {
    public var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_publicKey_get(self.__externalRCRef()))
        }
    }
    public var signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_signature_get(self.__externalRCRef()))
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature
    ) {
        let __kt = xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature__(__kt, publicKey.__externalRCRef(), signature.__externalRCRef()); return () }()
    }
    public func copy(
        publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519 {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature__(self.__externalRCRef(), publicKey.__externalRCRef(), signature.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator_SealedType {
        .multiEd25519(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.__AccountAuthenticator {
    public var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKey {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_publicKey_get(self.__externalRCRef()))
        }
    }
    public var signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_signature_get(self.__externalRCRef()))
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKey,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature
    ) {
        let __kt = xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature__(__kt, publicKey.__externalRCRef(), signature.__externalRCRef()); return () }()
    }
    public func copy(
        publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKey,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature__(self.__externalRCRef(), publicKey.__externalRCRef(), signature.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator_SealedType {
        .multiKey(.init(self))
    }
}
/// Explicitly skips account authentication during simulation.
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.__AccountAuthenticator {
    public static var shared: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount {
        get {
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount_get())
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
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator_SealedType {
        .noAccount(.init(self))
    }
}
public final class _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.__AccountAuthenticator {
    public var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnyPublicKey {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnyPublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_publicKey_get(self.__externalRCRef()))
        }
    }
    public var signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_signature_get(self.__externalRCRef()))
        }
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public init(
        publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnyPublicKey,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature
    ) {
        let __kt = xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AnyPublicKey_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AnySignature__(__kt, publicKey.__externalRCRef(), signature.__externalRCRef()); return () }()
    }
    public func copy(
        publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnyPublicKey,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature
    ) -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey {
        return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AnyPublicKey_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AnySignature__(self.__externalRCRef(), publicKey.__externalRCRef(), signature.__externalRCRef()))
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator_SealedType {
        .singleKey(.init(self))
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey
    init(
        _ value: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey
    ) {
        self.value = value
    }
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AccountAddressInput {
    /// The value of the account address.
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AccountAddressInput_value_get(self.__externalRCRef())
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.account.__AccountAsset {
    public var value: Swift.String {
        get {
            return xyz_mcxross_kaptos_account_AccountAsset_value_get(self.__externalRCRef())
        }
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin: .coin(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset: .fungibleAsset(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset {
    public typealias Coin = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin
    public typealias Companion = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Companion
    public typealias FungibleAsset = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_SealedType
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.__AccountAuthenticator {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction: .abstraction(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519: .ed25519(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519: .multiEd25519(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey: .multiKey(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount: .noAccount(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey: .singleKey(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_toBcs(self.__externalRCRef()))
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator {
    public typealias Abstraction = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction
    public typealias Companion = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Companion
    public typealias Ed25519 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519
    public typealias MultiEd25519 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519
    public typealias MultiKey = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey
    public typealias NoAccount = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount
    public typealias SingleKey = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_SealedType
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_toBcs_direct(self.__externalRCRef()))
    }
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError {
    public var cause: ExportedKotlinPackages.kotlin.Throwable? {
        get {
            return { switch xyz_mcxross_kaptos_model_AptosError_cause_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.Throwable.__createClassWrapper(externalRCRef: res); } }()
        }
    }
    public var message: Swift.String {
        get {
            return xyz_mcxross_kaptos_model_AptosError_message_get(self.__externalRCRef())
        }
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api: .api(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled: .cancelled(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto: .crypto(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer: .indexer(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization: .serialization(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout: .timeout(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport: .transport(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature: .unsupportedFeature(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation: .validation(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError {
    public typealias Api = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api
    public typealias Cancelled = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled
    public typealias Crypto = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto
    public typealias Indexer = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer
    public typealias Serialization = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization
    public typealias Timeout = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout
    public typealias Transport = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport
    public typealias UnsupportedFeature = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature
    public typealias Validation = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation_SealedType
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosResult {
    public var isFailure: Swift.Bool {
        get {
            return xyz_mcxross_kaptos_model_AptosResult_isFailure_get(self.__externalRCRef())
        }
    }
    public var isSuccess: Swift.Bool {
        get {
            return xyz_mcxross_kaptos_model_AptosResult_isSuccess_get(self.__externalRCRef())
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
    public typealias Failure = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult_Failure
    public typealias Success = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult_Success
    public var isFailure: Swift.Bool {
        get {
            return xyz_mcxross_kaptos_model_AptosResult_isFailure_get_direct(self.__externalRCRef())
        }
    }
    public var isSuccess: Swift.Bool {
        get {
            return xyz_mcxross_kaptos_model_AptosResult_isSuccess_get_direct(self.__externalRCRef())
        }
    }
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address: .address(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool: .bool(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes: .bytes(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text: .text(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128: .u128(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16: .u16(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256: .u256(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32: .u32(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64: .u64(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8: .u8(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue {
    public typealias Address = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address
    public typealias Bool = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool
    public typealias Bytes = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes
    public typealias Text = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text
    public typealias U128 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128
    public typealias U16 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16
    public typealias U256 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256
    public typealias U32 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32
    public typealias U64 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64
    public typealias U8 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8_SealedType
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address: .address(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool: .bool(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes: .bytes(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum: .`enum`(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128: .i128(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16: .i16(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256: .i256(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32: .i32(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64: .i64(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8: .i8(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option: .option(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized: .preSerialized(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue: .stringValue(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct: .`struct`(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128: .u128(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16: .u16(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256: .u256(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32: .u32(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64: .u64(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8: .u8(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector: .vector(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
    /// Serializes ABI-independent primitive/container arguments to canonical BCS.
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_toBcs(self.__externalRCRef()))
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
    public typealias Address = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address
    public typealias Bool = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool
    public typealias Bytes = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes
    public typealias Companion = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Companion
    public typealias Enum = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum
    public typealias I128 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128
    public typealias I16 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16
    public typealias I256 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256
    public typealias I32 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32
    public typealias I64 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64
    public typealias I8 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8
    public typealias Option = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option
    public typealias PreSerialized = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized
    public typealias StringValue = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue
    public typealias Struct = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct
    public typealias U128 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128
    public typealias U16 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16
    public typealias U256 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256
    public typealias U32 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32
    public typealias U64 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64
    public typealias U8 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8
    public typealias Vector = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector_SealedType
    /// Serializes ABI-independent primitive/container arguments to canonical BCS.
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_move_MoveArgument_toBcs_direct(self.__externalRCRef()))
    }
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__MultisigPayload {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction: .entryFunction(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script: .script(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload {
    public typealias EntryFunction = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction
    public typealias Script = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script_SealedType
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.names.__NameExpirationPolicy {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain: .domain(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain: .followDomain(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent: .independent(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy {
    public typealias Domain = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain
    public typealias FollowDomain = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain
    public typealias Independent = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent_SealedType
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.__PrivateKey {
    /// Whether the secret material has been explicitly cleared.
    public var isCleared: Swift.Bool {
        get {
            return xyz_mcxross_kaptos_core_crypto_PrivateKey_isCleared_get(self.__externalRCRef())
        }
    }
    /// Clear secret material. Subsequent private-key operations must fail.
    public func clear() -> Swift.Void {
        return { xyz_mcxross_kaptos_core_crypto_PrivateKey_clear(self.__externalRCRef()); return () }()
    }
    /// Derive the public key associated with the private key
    public func publicKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey {
        return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_PrivateKey_publicKey(self.__externalRCRef()))
    }
    /// Sign the given message with the private key.
    /// 
    /// - Parameters:
    ///   - message: in [HexInput] format
    public func sign(
        message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
    ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
        return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_PrivateKey_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()))
    }
    /// Export this private key using the algorithm-prefixed AIP-80 format.
    public func toAip80() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey {
        return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_PrivateKey_toAip80(self.__externalRCRef()))
    }
    /// Get the private key in bytes.
    public func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_PrivateKey_toByteArray(self.__externalRCRef()))
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey {
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__ReplayProtection {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce: .nonce(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber: .sequenceNumber(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection {
    public typealias Nonce = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce
    public typealias SequenceNumber = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber_SealedType
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExecutable {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty: .empty(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted: .encrypted(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction: .entryFunction(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script: .script(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
    /// Exact Aptos BCS bytes used inside an encrypted plaintext.
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_toBcs(self.__externalRCRef()))
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable {
    public typealias Companion = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Companion
    public typealias Empty = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty
    public typealias Encrypted = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted
    public typealias EntryFunction = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction
    public typealias Script = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script_SealedType
    /// Exact Aptos BCS bytes used inside an encrypted plaintext.
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExecutable_toBcs_direct(self.__externalRCRef()))
    }
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExtraConfig {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1: .v1(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExtraConfig_toBcs(self.__externalRCRef()))
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig {
    public typealias Companion = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_Companion
    public typealias V1 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_SealedType
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionExtraConfig_toBcs_direct(self.__externalRCRef()))
    }
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionPayload {
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted: .encrypted(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction: .entryFunction(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1: .innerV1(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig: .multisig(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script: .script(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_toBcs(self.__externalRCRef()))
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload {
    public typealias Companion = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Companion
    public typealias Encrypted = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted
    public typealias EntryFunction = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction
    public typealias InnerV1 = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1
    public typealias Multisig = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig
    public typealias Script = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script_SealedType
    public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionPayload_toBcs_direct(self.__externalRCRef()))
    }
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.account.__TransactionSigner {
    public var accountAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_TransactionSigner_accountAddress_get(self.__externalRCRef()))
        }
    }
    /// Allows optional protocol modules to reject unsupported signer families before signing.
    public var kind: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSignerKind {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSignerKind(__externalRCRefUnsafe: xyz_mcxross_kaptos_account_TransactionSigner_kind_get(self.__externalRCRef()), options: .asBestFittingWrapper)
        }
    }
    public var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_TransactionSigner_publicKey_get(self.__externalRCRef()))
        }
    }
    /// Signs arbitrary bytes without imposing a display encoding.
    public func signBytes(
        message: ExportedKotlinPackages.kotlin.ByteArray
    ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
        try await withKotlinContinuation { continuation, exception, cancellation in
            let _: Bool = xyz_mcxross_kaptos_account_TransactionSigner_signBytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), message.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
        }
    }
    /// Signs UTF-8 text. Wallet implementations may present this text to the user.
    public func signText(
        message: Swift.String
    ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
        try await withKotlinContinuation { continuation, exception, cancellation in
            let _: Bool = xyz_mcxross_kaptos_account_TransactionSigner_signText__TypesOfArguments__Swift_String__(self.__externalRCRef(), message, Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
        }
    }
    /// Signs the correct domain-separated message for [transaction].
    public func signTransaction(
        transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
    ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
        try await withKotlinContinuation { continuation, exception, cancellation in
            let _: Bool = xyz_mcxross_kaptos_account_TransactionSigner_signTransaction__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(self.__externalRCRef(), transaction.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
        }
    }
    /// Verifies [signature] against arbitrary bytes using this signer's public key.
    public func verifySignature(
        message: ExportedKotlinPackages.kotlin.ByteArray,
        signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
    ) -> Swift.Bool {
        return xyz_mcxross_kaptos_account_TransactionSigner_verifySignature__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner {
    /// Allows optional protocol modules to reject unsupported signer families before signing.
    public var kind: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSignerKind {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSignerKind(__externalRCRefUnsafe: xyz_mcxross_kaptos_account_TransactionSigner_kind_get_direct(self.__externalRCRef()), options: .asBestFittingWrapper)
        }
    }
}
@_documentation(visibility: internal)
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction where Self : ExportedKotlinPackages.xyz.mcxross.kaptos.model.__UnsignedTransaction {
    public var rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction {
        get {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_rawTransaction_get(self.__externalRCRef()))
        }
    }
    public func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction_SealedType {
        switch self {
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer: .feePayer(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent: .multiAgent(.init(value))
        case let value as XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple: .simple(.init(value))
        default: fatalError("missing sealedType for \(self)")
        }
    }
    /// Exact bytes covered by the transaction signature, before domain separation.
    public func signingBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_signingBcs(self.__externalRCRef()))
    }
    public func signingMessage() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_signingMessage(self.__externalRCRef()))
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
    public typealias Companion = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Companion
    public typealias FeePayer = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer
    public typealias MultiAgent = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent
    public typealias Simple = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_SealedType
    public typealias _ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple_SealedType = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple_SealedType
    /// Exact bytes covered by the transaction signature, before domain separation.
    public func signingBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_signingBcs_direct(self.__externalRCRef()))
    }
    public func signingMessage() -> ExportedKotlinPackages.kotlin.ByteArray {
        return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UnsignedTransaction_signingMessage_direct(self.__externalRCRef()))
    }
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy, ExportedKotlinPackages.xyz.mcxross.kaptos.names.__NameExpirationPolicy where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.names._NameExpirationPolicy {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.__AccountAuthenticator where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator._AccountAuthenticator {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset, ExportedKotlinPackages.xyz.mcxross.kaptos.account.__AccountAsset where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.account._AccountAsset {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner, ExportedKotlinPackages.xyz.mcxross.kaptos.account.__TransactionSigner where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.account._TransactionSigner {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AccountAddressInput where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._AccountAddressInput {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosResult where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._AptosResult {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionPayload where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionPayload {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__UnsignedTransaction where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._UnsignedTransaction {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.move.__MoveArgument where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.move._MoveArgument {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey, ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.__PrivateKey where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto._PrivateKey {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.__DigitalAssetPropertyValue where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset._DigitalAssetPropertyValue {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AptosError where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._AptosError {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__MultisigPayload where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._MultisigPayload {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExecutable where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionExecutable {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__TransactionExtraConfig where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionExtraConfig {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__ReplayProtection where Wrapped : ExportedKotlinPackages.xyz.mcxross.kaptos.model._ReplayProtection {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.names._NameExpirationPolicy {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator._AccountAuthenticator {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.account._AccountAsset {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.account._TransactionSigner {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._AccountAddressInput {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._AptosResult {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionPayload {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._UnsignedTransaction {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.move._MoveArgument {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto._PrivateKey {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset._DigitalAssetPropertyValue {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._AptosError {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._MultisigPayload {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionExecutable {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionExtraConfig {
}
@_documentation(visibility: internal)
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.xyz.mcxross.kaptos.model._ReplayProtection {
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.account {
    public enum AccountAsset_SealedType: KotlinRuntimeSupport.SealedType {
        case coin(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Coin_SealedType)
        case fungibleAsset(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_FungibleAsset_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset {
            get {
                switch self {
                case let .coin(type): type.value
                case let .fungibleAsset(type): type.value
                }
            }
        }
    }
    /// Signer family used for feature compatibility checks before sensitive operations.
    public enum TransactionSignerKind: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Standard
        case Keyless
        case FederatedKeyless
        public var description: Swift.String {
            get {
                switch self {
                case .Standard: "Standard"
                case .Keyless: "Keyless"
                case .FederatedKeyless: "FederatedKeyless"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Standard: 0
                case .Keyless: 1
                case .FederatedKeyless: 2
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Standard": self = .Standard
            case "Keyless": self = .Keyless
            case "FederatedKeyless": self = .FederatedKeyless
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<3 ~= rawValue else { return nil }
            self = TransactionSignerKind.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_account_TransactionSignerKind_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Standard
            case 1: self = .Keyless
            case 2: self = .FederatedKeyless
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Standard: xyz_mcxross_kaptos_account_TransactionSignerKind_Standard()
            case .Keyless: xyz_mcxross_kaptos_account_TransactionSignerKind_Keyless()
            case .FederatedKeyless: xyz_mcxross_kaptos_account_TransactionSignerKind_FederatedKeyless()
            default: fatalError()
            }
        }
    }
    /// A coin type or fungible-asset metadata object accepted by the unified balance endpoint.
    public protocol AccountAsset: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.account._AccountAsset {
        var value: Swift.String {
            get
        }
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset_SealedType
    }
    /// Suspend-capable signer contract for local keys, wallets, passkeys, and remote custody systems.
    public protocol TransactionSigner: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.account._TransactionSigner {
        var accountAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get
        }
        /// Allows optional protocol modules to reject unsupported signer families before signing.
        var kind: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSignerKind {
            get
        }
        var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey {
            get
        }
        /// Signs arbitrary bytes without imposing a display encoding.
        func signBytes(
            message: ExportedKotlinPackages.kotlin.ByteArray
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult
        /// Signs UTF-8 text. Wallet implementations may present this text to the user.
        func signText(
            message: Swift.String
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult
        /// Signs the correct domain-separated message for [transaction].
        func signTransaction(
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult
        /// Verifies [signature] against arbitrary bytes using this signer's public key.
        func verifySignature(
            message: ExportedKotlinPackages.kotlin.ByteArray,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset)
    public protocol _AccountAsset {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner)
    public protocol _TransactionSigner {
    }
    public protocol __AccountAsset: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __TransactionSigner: KotlinRuntimeSupport._KotlinBridgeable {
    }
    open class Account: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner, ExportedKotlinPackages.xyz.mcxross.kaptos.account.__TransactionSigner, ExportedKotlinPackages.kotlin.AutoCloseable, ExportedKotlinPackages.kotlin.__AutoCloseable {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_Companion_get())
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
            /// Create an account from a private key, inferring the correct account authentication scheme.
            /// Ed25519 uses the legacy authenticator; other locally signable keys use SingleKey.
            public func fromPrivateKey(
                privateKey: any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey,
                address: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_Companion_fromPrivateKey__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PrivateKey_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput___(self.__externalRCRef(), privateKey.__externalRCRef(), address.map { it in it.__externalRCRef() } ?? nil))
            }
            /// Explicitly wrap Ed25519 or Secp256k1 key material in a SingleKey account.
            public func fromSingleKey(
                privateKey: any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey,
                address: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_Companion_fromSingleKey__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PrivateKey_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput___(self.__externalRCRef(), privateKey.__externalRCRef(), address.map { it in it.__externalRCRef() } ?? nil))
            }
            /// Generate the conventional legacy Ed25519 account used by most Aptos applications.
            public func generate() -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.Ed25519Account {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.account.Ed25519Account.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_Companion_generate(self.__externalRCRef()))
            }
        }
        /// Account address associated with the account
        open var accountAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_accountAddress_get(self.__externalRCRef()))
                } else {
                    fatalError("Cannot invoke the inherited implementation of abstract property 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.accountAddress': a Swift subclass must override it and must not call super.")
                }
            }
        }
        /// Whether all private key material owned by this account has been cleared.
        open var isPrivateKeyCleared: Swift.Bool {
            get {
                if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                    return xyz_mcxross_kaptos_account_Account_isPrivateKeyCleared_get(self.__externalRCRef())
                } else {
                    fatalError("Cannot invoke the inherited implementation of abstract property 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.isPrivateKeyCleared': a Swift subclass must override it and must not call super.")
                }
            }
        }
        /// Public key associated with the account
        open var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey {
            get {
                if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_publicKey_get(self.__externalRCRef()))
                } else {
                    fatalError("Cannot invoke the inherited implementation of abstract property 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.publicKey': a Swift subclass must override it and must not call super.")
                }
            }
        }
        /// Signing scheme used to sign transactions
        open var signingScheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme {
            get {
                if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme(__externalRCRefUnsafe: xyz_mcxross_kaptos_account_Account_signingScheme_get(self.__externalRCRef()), options: .asBestFittingWrapper)
                } else {
                    fatalError("Cannot invoke the inherited implementation of abstract property 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.signingScheme': a Swift subclass must override it and must not call super.")
                }
            }
        }
        public override init() {
            precondition(Self.self != ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self, "ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account is an abstract class and cannot be instantiated directly")
            let __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_account_Account_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Explicitly clear private key material owned by this account.
        open func clearPrivateKey() -> Swift.Void {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                return { xyz_mcxross_kaptos_account_Account_clearPrivateKey(self.__externalRCRef()); return () }()
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.clearPrivateKey': a Swift subclass must override it and must not call super.")
            }
        }
        /// Clears private key material when the account is owned by a managed SDK scope.
        public final func close() -> Swift.Void {
            return { xyz_mcxross_kaptos_account_Account_close(self.__externalRCRef()); return () }()
        }
        /// Sign the given message with the private key.
        /// 
        /// - Parameters:
        ///   - message: in HexInput format
        /// 
        /// - returns: AccountSignature
        open func sign(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()))
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.sign': a Swift subclass must override it and must not call super.")
            }
        }
        /// Signs arbitrary bytes without imposing a display encoding.
        public final func signBytes(
            message: ExportedKotlinPackages.kotlin.ByteArray
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_account_Account_signBytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), message.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        /// Signs UTF-8 text. Wallet implementations may present this text to the user.
        public final func signText(
            message: Swift.String
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_account_Account_signText__TypesOfArguments__Swift_String__(self.__externalRCRef(), message, Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        /// Signs the correct domain-separated message for [transaction].
        open func signTransaction(
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_account_Account_signTransaction__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(self.__externalRCRef(), transaction.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
            } else {
                try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_account_Account_signTransaction__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction___direct(self.__externalRCRef(), transaction.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
            }
        }
        open func signTransactionSignature(
            tx: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_signTransactionSignature__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(self.__externalRCRef(), tx.__externalRCRef()))
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.signTransactionSignature': a Swift subclass must override it and must not call super.")
            }
        }
        /// Sign a message using the available signing capabilities.
        /// 
        /// - Parameters:
        ///   - message: the signing message, as binary input
        /// 
        /// - Returns: the [AccountAuthenticator] containing the signature, together with the account's public
        ///   key
        open func signWithAuthenticator(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_account_Account_signWithAuthenticator__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.signWithAuthenticator': a Swift subclass must override it and must not call super.")
            }
        }
        /// Verifies [signature] against arbitrary bytes using this signer's public key.
        public final func verifySignature(
            message: ExportedKotlinPackages.kotlin.ByteArray,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_account_Account_verifySignature__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
        open func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.self {
                return xyz_mcxross_kaptos_account_Account_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.verifySignature': a Swift subclass must override it and must not call super.")
            }
        }
    }
    /// Current on-chain account-abstraction configuration for an account.
    public final class AccountAbstractionStatus: KotlinRuntime.KotlinBase {
        public var authenticationFunctions: [ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction] {
            get {
                return xyz_mcxross_kaptos_account_AccountAbstractionStatus_authenticationFunctions_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction>
            }
        }
        public var isEnabled: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_account_AccountAbstractionStatus_isEnabled_get(self.__externalRCRef())
            }
        }
        public init(
            authenticationFunctions: [ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction]
        ) {
            let __kt = xyz_mcxross_kaptos_account_AccountAbstractionStatus_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_account_AccountAbstractionStatus_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction___(__kt, authenticationFunctions); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            authenticationFunctions: [ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction]
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAbstractionStatus {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAbstractionStatus.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountAbstractionStatus_copy__TypesOfArguments__Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction___(self.__externalRCRef(), authenticationFunctions))
        }
    }
    /// An on-chain account associated with a public key.
    public final class AccountInfo: KotlinRuntime.KotlinBase {
        public var address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountInfo_address_get(self.__externalRCRef()))
            }
        }
        public var lastTransactionVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_account_AccountInfo_lastTransactionVersion_get(self.__externalRCRef())
            }
        }
        public var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountInfo_publicKey_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey,
            lastTransactionVersion: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_account_AccountInfo_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_account_AccountInfo_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AccountPublicKey_Swift_UInt64__(__kt, address.__externalRCRef(), publicKey.__externalRCRef(), lastTransactionVersion); return () }()
        }
        public func copy(
            address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey,
            lastTransactionVersion: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountInfo {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountInfo.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountInfo_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AccountPublicKey_Swift_UInt64__(self.__externalRCRef(), address.__externalRCRef(), publicKey.__externalRCRef(), lastTransactionVersion))
        }
    }
    /// Controls public-key restoration queries without exposing GraphQL filter types.
    public final class AccountLookupOptions: KotlinRuntime.KotlinBase {
        public var includeMultiKey: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_account_AccountLookupOptions_includeMultiKey_get(self.__externalRCRef())
            }
        }
        public var includeUnverified: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_account_AccountLookupOptions_includeUnverified_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            includeUnverified: Swift.Bool,
            includeMultiKey: Swift.Bool
        ) {
            let __kt = xyz_mcxross_kaptos_account_AccountLookupOptions_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_account_AccountLookupOptions_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool_Swift_Bool__(__kt, includeUnverified, includeMultiKey); return () }()
        }
        public func copy(
            includeUnverified: Swift.Bool,
            includeMultiKey: Swift.Bool
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountLookupOptions {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountLookupOptions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_AccountLookupOptions_copy__TypesOfArguments__Swift_Bool_Swift_Bool__(self.__externalRCRef(), includeUnverified, includeMultiKey))
        }
    }
    /// Signer implementation for the Ed25519 authentication scheme. This extends an [Ed25519Account] by
    /// adding signing capabilities through an [Ed25519PrivateKey].
    /// 
    /// Note: Generating a signer instance does not create the account on-chain.
    public final class Ed25519Account: ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.account.Ed25519Account.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.account.Ed25519Account.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_Companion_get())
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
            /// Derive a legacy Ed25519 account from an Aptos BIP-44 path.
            public func fromMnemonic(
                mnemonic: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase,
                path: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519,
                passphrase: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.Ed25519Account {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.account.Ed25519Account.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_Companion_fromMnemonic__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_Swift_String__(self.__externalRCRef(), mnemonic.__externalRCRef(), path.__externalRCRef(), passphrase))
            }
            public func generate() -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.Ed25519Account {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.account.Ed25519Account.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_Companion_generate(self.__externalRCRef()))
            }
        }
        /// Account address associated with the account
        public override var accountAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_accountAddress_get(self.__externalRCRef()))
            }
        }
        public var address: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)? {
            get {
                return { switch xyz_mcxross_kaptos_account_Ed25519Account_address_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput; } }()
            }
        }
        /// Whether all private key material owned by this account has been cleared.
        public override var isPrivateKeyCleared: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_account_Ed25519Account_isPrivateKeyCleared_get(self.__externalRCRef())
            }
        }
        public var privateKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_privateKey_get(self.__externalRCRef()))
            }
        }
        /// Public key associated with the account
        public override var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_publicKey_get(self.__externalRCRef()))
            }
        }
        /// Signing scheme used to sign transactions
        public override var signingScheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme(__externalRCRefUnsafe: xyz_mcxross_kaptos_account_Ed25519Account_signingScheme_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            privateKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey,
            address: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?
        ) {
            let __kt = xyz_mcxross_kaptos_account_Ed25519Account_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_account_Ed25519Account_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput___(__kt, privateKey.__externalRCRef(), address.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        /// Explicitly clear private key material owned by this account.
        public override func clearPrivateKey() -> Swift.Void {
            return { xyz_mcxross_kaptos_account_Ed25519Account_clearPrivateKey(self.__externalRCRef()); return () }()
        }
        /// Sign the given message with the private key.
        /// 
        /// - Parameters:
        ///   - message: in HexInput format
        /// 
        /// - returns: AccountSignature
        public override func sign(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()))
        }
        public override func signTransactionSignature(
            tx: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_signTransactionSignature__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(self.__externalRCRef(), tx.__externalRCRef()))
        }
        /// Sign a message using the available signing capabilities.
        /// 
        /// - Parameters:
        ///   - message: the signing message, as binary input
        /// 
        /// - Returns: the [AccountAuthenticator] containing the signature, together with the account's public
        ///   key
        public override func signWithAuthenticator(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_account_Ed25519Account_signWithAuthenticator__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
        }
        public override func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_account_Ed25519Account_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
    }
    /// Signer implementation for the SingleKey authentication scheme. This extends a SingleKeyAccount by
    /// adding signing capabilities through a valid private key. Currently, the only supported signature
    /// schemes are Ed25519 and Secp256k1. P-256 transaction signing uses [PasskeyAccount], because
    /// WebAuthn credential acquisition is application-owned.
    /// 
    /// Note: Generating a signer instance does not create the account on-chain.
    public final class SingleKeyAccount: ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_Companion_get())
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
            /// Derive an Ed25519 SingleKey account from an Aptos hardened BIP-44 path.
            public func fromMnemonic(
                mnemonic: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase,
                path: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519,
                passphrase: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_Companion_fromMnemonic__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_Swift_String__(self.__externalRCRef(), mnemonic.__externalRCRef(), path.__externalRCRef(), passphrase))
            }
            /// Derive a Secp256k1 SingleKey account from an Aptos BIP-44 path.
            public func fromSecp256k1Mnemonic(
                mnemonic: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase,
                path: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa,
                passphrase: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_Companion_fromSecp256k1Mnemonic__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_Swift_String__(self.__externalRCRef(), mnemonic.__externalRCRef(), path.__externalRCRef(), passphrase))
            }
            /// Derives an account from a randomly generated private key. Default generation is using an
            /// Ed25519 key
            /// 
            /// - returns: Account with the given signature scheme
            public func generate(
                scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningSchemeInput
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.account.SingleKeyAccount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_Companion_generate__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_SigningSchemeInput__(self.__externalRCRef(), scheme.__externalRCRef()))
            }
        }
        /// Account address associated with the account
        public override var accountAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_accountAddress_get(self.__externalRCRef()))
            }
        }
        public var address: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)? {
            get {
                return { switch xyz_mcxross_kaptos_account_SingleKeyAccount_address_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput; } }()
            }
        }
        /// Whether all private key material owned by this account has been cleared.
        public override var isPrivateKeyCleared: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_account_SingleKeyAccount_isPrivateKeyCleared_get(self.__externalRCRef())
            }
        }
        public var privateKey: any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey {
            get {
                return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_privateKey_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey
            }
        }
        /// Public key associated with the account
        public override var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnyPublicKey {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnyPublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_publicKey_get(self.__externalRCRef()))
            }
        }
        /// Signing scheme used to sign transactions
        public override var signingScheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme(__externalRCRefUnsafe: xyz_mcxross_kaptos_account_SingleKeyAccount_signingScheme_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            privateKey: any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey,
            address: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?
        ) {
            let __kt = xyz_mcxross_kaptos_account_SingleKeyAccount_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_account_SingleKeyAccount_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PrivateKey_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput___(__kt, privateKey.__externalRCRef(), address.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        /// Explicitly clear private key material owned by this account.
        public override func clearPrivateKey() -> Swift.Void {
            return { xyz_mcxross_kaptos_account_SingleKeyAccount_clearPrivateKey(self.__externalRCRef()); return () }()
        }
        /// Sign the given message with the private key.
        /// 
        /// - Parameters:
        ///   - message: in HexInput format
        /// 
        /// - returns: AccountSignature
        public override func sign(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()))
        }
        public override func signTransactionSignature(
            tx: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_signTransactionSignature__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(self.__externalRCRef(), tx.__externalRCRef()))
        }
        /// Sign a message using the available signing capabilities.
        /// 
        /// - Parameters:
        ///   - message: the signing message, as binary input
        /// 
        /// - Returns: the [AccountAuthenticator] containing the signature, together with the account's public
        ///   key
        public override func signWithAuthenticator(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_account_SingleKeyAccount_signWithAuthenticator__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
        }
        public override func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_account_SingleKeyAccount_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator {
    public enum AccountAuthenticator_SealedType: KotlinRuntimeSupport.SealedType {
        case abstraction(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Abstraction_SealedType)
        case ed25519(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Ed25519_SealedType)
        case multiEd25519(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiEd25519_SealedType)
        case multiKey(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_MultiKey_SealedType)
        case noAccount(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_NoAccount_SealedType)
        case singleKey(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_SingleKey_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator {
            get {
                switch self {
                case let .abstraction(type): type.value
                case let .ed25519(type): type.value
                case let .multiEd25519(type): type.value
                case let .multiKey(type): type.value
                case let .noAccount(type): type.value
                case let .singleKey(type): type.value
                }
            }
        }
    }
    /// Account-level authentication data attached to a transaction signer.
    public protocol AccountAuthenticator: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator._AccountAuthenticator {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator_SealedType
        func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator)
    public protocol _AccountAuthenticator {
    }
    public protocol __AccountAuthenticator: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public final class AuthenticationFunction: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_Companion_get())
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
            public func parse(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_Companion_parse__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
        }
        public var function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_function_get(self.__externalRCRef()))
            }
        }
        public var module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_module_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId,
            function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier
        ) {
            let __kt = xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ModuleId_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Identifier__(__kt, module.__externalRCRef(), function.__externalRCRef()); return () }()
        }
        public func copy(
            module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId,
            function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_ModuleId_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Identifier__(self.__externalRCRef(), module.__externalRCRef(), function.__externalRCRef()))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.core {
    /// Each account stores an authentication key. Authentication key enables account owners to rotate
    /// their private key(s) associated with the account without changing the address that hosts their
    /// account.
    /// 
    /// - See: [https://aptos.dev/concepts/accounts | Account Basics]
    /// 
    ///   Account addresses can be derived from AuthenticationKey
    public final class AuthenticationKey: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            /// An authentication key is always a SHA3-256 hash of data, and is always 32 bytes.
            /// 
            /// The data to hash depends on the underlying public key type and the derivation scheme.
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_AuthenticationKey_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_AuthenticationKey_Companion_get())
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
            public func fromSchemeAndBytes(
                scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme,
                input: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_AuthenticationKey_Companion_fromSchemeAndBytes__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AuthenticationKeyScheme_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), scheme.__externalRCRef(), input.__externalRCRef()))
            }
        }
        public init(
            data: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            let __kt = xyz_mcxross_kaptos_core_AuthenticationKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_AuthenticationKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(__kt, data.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Derives an account address from an AuthenticationKey. Since an AccountAddress is also 32 bytes,
        /// the AuthenticationKey bytes are directly translated to an AccountAddress.
        /// 
        /// - Returns: [AccountAddress]
        public func deriveAddress() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_AuthenticationKey_deriveAddress(self.__externalRCRef()))
        }
        public func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_AuthenticationKey_toByteArray(self.__externalRCRef()))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto {
    public enum AptosDerivationPath_SealedType: KotlinRuntimeSupport.SealedType {
        case ecdsa(ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa_SealedType)
        case ed25519(ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath {
            get {
                switch self {
                case let .ecdsa(type): type.value
                case let .ed25519(type): type.value
                }
            }
        }
    }
    /// Supported BIP-39 mnemonic sizes.
    public enum MnemonicWordCount: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Words12
        case Words15
        case Words18
        case Words21
        case Words24
        public var count: Swift.UInt32 {
            get {
                return xyz_mcxross_kaptos_core_crypto_MnemonicWordCount_count_get(self.__externalRCRef())
            }
        }
        public var description: Swift.String {
            get {
                switch self {
                case .Words12: "Words12"
                case .Words15: "Words15"
                case .Words18: "Words18"
                case .Words21: "Words21"
                case .Words24: "Words24"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Words12: 0
                case .Words15: 1
                case .Words18: 2
                case .Words21: 3
                case .Words24: 4
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Words12": self = .Words12
            case "Words15": self = .Words15
            case "Words18": self = .Words18
            case "Words21": self = .Words21
            case "Words24": self = .Words24
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<5 ~= rawValue else { return nil }
            self = MnemonicWordCount.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_core_crypto_MnemonicWordCount_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Words12
            case 1: self = .Words15
            case 2: self = .Words18
            case 3: self = .Words21
            case 4: self = .Words24
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Words12: xyz_mcxross_kaptos_core_crypto_MnemonicWordCount_Words12()
            case .Words15: xyz_mcxross_kaptos_core_crypto_MnemonicWordCount_Words15()
            case .Words18: xyz_mcxross_kaptos_core_crypto_MnemonicWordCount_Words18()
            case .Words21: xyz_mcxross_kaptos_core_crypto_MnemonicWordCount_Words21()
            case .Words24: xyz_mcxross_kaptos_core_crypto_MnemonicWordCount_Words24()
            default: fatalError()
            }
        }
    }
    /// The private-key algorithms with an AIP-80 textual representation.
    public enum PrivateKeyType: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Ed25519
        case Secp256k1
        case Secp256r1
        public var description: Swift.String {
            get {
                switch self {
                case .Ed25519: "Ed25519"
                case .Secp256k1: "Secp256k1"
                case .Secp256r1: "Secp256r1"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Ed25519: 0
                case .Secp256k1: 1
                case .Secp256r1: 2
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Ed25519": self = .Ed25519
            case "Secp256k1": self = .Secp256k1
            case "Secp256r1": self = .Secp256r1
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<3 ~= rawValue else { return nil }
            self = PrivateKeyType.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_core_crypto_PrivateKeyType_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Ed25519
            case 1: self = .Secp256k1
            case 2: self = .Secp256r1
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Ed25519: xyz_mcxross_kaptos_core_crypto_PrivateKeyType_Ed25519()
            case .Secp256k1: xyz_mcxross_kaptos_core_crypto_PrivateKeyType_Secp256k1()
            case .Secp256r1: xyz_mcxross_kaptos_core_crypto_PrivateKeyType_Secp256r1()
            default: fatalError()
            }
        }
    }
    /// An interface of a private key. It is associated to a signature scheme and provides signing
    /// capabilities.
    public protocol PrivateKey: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto._PrivateKey {
        /// Whether the secret material has been explicitly cleared.
        var isCleared: Swift.Bool {
            get
        }
        /// Clear secret material. Subsequent private-key operations must fail.
        func clear() -> Swift.Void
        /// Derive the public key associated with the private key
        func publicKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey
        /// Sign the given message with the private key.
        /// 
        /// - Parameters:
        ///   - message: in [HexInput] format
        func sign(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        /// Export this private key using the algorithm-prefixed AIP-80 format.
        func toAip80() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey
        /// Get the private key in bytes.
        func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PrivateKey)
    public protocol _PrivateKey {
    }
    public protocol __PrivateKey: KotlinRuntimeSupport._KotlinBridgeable {
    }
    /// An abstract representation of an account public key.
    /// 
    /// Provides a common interface for deriving an authentication key.
    open class AccountPublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey {
        /// An abstract representation of an account public key.
        /// 
        /// Provides a common interface for deriving an authentication key.
        public override init() {
            precondition(Self.self != ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey.self, "ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey is an abstract class and cannot be instantiated directly")
            let __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_AccountPublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Get the authentication key associated with this public key
        open func authKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey.self {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AccountPublicKey_authKey(self.__externalRCRef()))
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey.authKey': a Swift subclass must override it and must not call super.")
            }
        }
    }
    /// A validated AIP-80 private-key string.
    /// 
    /// Keeping this distinct from an ordinary [String] makes secret-bearing APIs explicit and prevents
    /// an unprefixed legacy hex value from being accepted accidentally.
    public final class Aip80PrivateKey: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Aip80PrivateKey_Companion_get())
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
            /// Parse and validate a canonical AIP-80 private-key string.
            public func parse(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Aip80PrivateKey_Companion_parse__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
        }
        public var type: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKeyType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKeyType(__externalRCRefUnsafe: xyz_mcxross_kaptos_core_crypto_Aip80PrivateKey_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_core_crypto_Aip80PrivateKey_value_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
    }
    /// Represents any public key supported by Aptos.
    /// 
    /// Since [AIP-55](https://github.com/aptos-foundation/AIPs/pull/263) Aptos supports `Legacy` and
    /// `Unified` authentication keys.
    /// 
    /// Any unified authentication key is represented in the SDK as `AnyPublicKey`.
    public final class AnyPublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey {
        public var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AnyPublicKey_publicKey_get(self.__externalRCRef()))
            }
        }
        public var variant: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AnyPublicKeyVariant {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AnyPublicKeyVariant(__externalRCRefUnsafe: xyz_mcxross_kaptos_core_crypto_AnyPublicKey_variant_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public init(
            publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_AnyPublicKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_AnyPublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey__(__kt, publicKey.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Get the authentication key associated with this public key
        public override func authKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AnyPublicKey_authKey(self.__externalRCRef()))
        }
        /// Get the BCS bytes
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AnyPublicKey_toBcs(self.__externalRCRef()))
        }
        /// Get the raw public key bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AnyPublicKey_toByteArray(self.__externalRCRef()))
        }
        /// Verifies that the private key associated with this public key signed the message with the given
        /// signature.
        /// 
        /// - Parameters:
        ///   - message: The message that was signed
        ///   - signature: The signature to verify
        public override func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_core_crypto_AnyPublicKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
    }
    /// Instance of signature that uses the SingleKey authentication scheme. This signature can only be
    /// generated by a `SingleKeySigner`, since it uses the same authentication scheme.
    public final class AnySignature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
        public var signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AnySignature_signature_get(self.__externalRCRef()))
            }
        }
        public var variant: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AnySignatureVariant {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AnySignatureVariant(__externalRCRefUnsafe: xyz_mcxross_kaptos_core_crypto_AnySignature_variant_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public init(
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_AnySignature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_AnySignature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(__kt, signature.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AnySignature_toBcs(self.__externalRCRef()))
        }
        /// Get the raw signature bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AnySignature_toByteArray(self.__externalRCRef()))
        }
    }
    /// An Aptos BIP-44 derivation path.
    /// 
    /// The type makes the Ed25519 hardened-path rule impossible to mix up with the ECDSA path rule.
    open class AptosDerivationPath: KotlinRuntime.KotlinBase {
        public final class Ecdsa: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath {
            public var accountIndex: Swift.UInt32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_accountIndex_get(self.__externalRCRef())
                }
            }
            public var addressIndex: Swift.UInt32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_addressIndex_get(self.__externalRCRef())
                }
            }
            public var changeIndex: Swift.UInt32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_changeIndex_get(self.__externalRCRef())
                }
            }
            package override init(
                __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
                options: KotlinRuntime.KotlinBaseConstructionOptions
            ) {
                super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
            }
            public init(
                accountIndex: Swift.UInt32,
                changeIndex: Swift.UInt32,
                addressIndex: Swift.UInt32
            ) {
                let __kt = xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_init_allocate()
                super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
                { xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt32_Swift_UInt32_Swift_UInt32__(__kt, accountIndex, changeIndex, addressIndex); return () }()
            }
            public func copy(
                accountIndex: Swift.UInt32,
                changeIndex: Swift.UInt32,
                addressIndex: Swift.UInt32
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_copy__TypesOfArguments__Swift_UInt32_Swift_UInt32_Swift_UInt32__(self.__externalRCRef(), accountIndex, changeIndex, addressIndex))
            }
            public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath_SealedType {
                .ecdsa(.init(self))
            }
        }
        public final class Ed25519: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath {
            public var accountIndex: Swift.UInt32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_accountIndex_get(self.__externalRCRef())
                }
            }
            public var addressIndex: Swift.UInt32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_addressIndex_get(self.__externalRCRef())
                }
            }
            public var changeIndex: Swift.UInt32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_changeIndex_get(self.__externalRCRef())
                }
            }
            package override init(
                __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
                options: KotlinRuntime.KotlinBaseConstructionOptions
            ) {
                super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
            }
            public init(
                accountIndex: Swift.UInt32,
                changeIndex: Swift.UInt32,
                addressIndex: Swift.UInt32
            ) {
                let __kt = xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_init_allocate()
                super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
                { xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt32_Swift_UInt32_Swift_UInt32__(__kt, accountIndex, changeIndex, addressIndex); return () }()
            }
            public func copy(
                accountIndex: Swift.UInt32,
                changeIndex: Swift.UInt32,
                addressIndex: Swift.UInt32
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519 {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_copy__TypesOfArguments__Swift_UInt32_Swift_UInt32_Swift_UInt32__(self.__externalRCRef(), accountIndex, changeIndex, addressIndex))
            }
            public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath_SealedType {
                .ed25519(.init(self))
            }
        }
        public struct Ecdsa_SealedType: KotlinRuntimeSupport.SealedType {
            public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa
            init(
                _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa
            ) {
                self.value = value
            }
        }
        public struct Ed25519_SealedType: KotlinRuntimeSupport.SealedType {
            public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519
            init(
                _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519
            ) {
                self.value = value
            }
        }
        public final var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_value_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        open func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa: .ecdsa(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519: .ed25519(.init(value))
            default: fatalError("missing sealedType for \(self)")
            }
        }
    }
    /// Represents the private key of an Ed25519 key pair.
    public final class Ed25519PrivateKey: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey, ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.__PrivateKey {
        public final class Companion: KotlinRuntime.KotlinBase {
            /// Length of an Ed25519 private key
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_Companion_get())
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
            public func fromAip80(
                value: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_Companion_fromAip80__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Aip80PrivateKey__(self.__externalRCRef(), value.__externalRCRef()))
            }
            /// Import an Ed25519 private key from the safe, algorithm-prefixed AIP-80 format.
            public func fromAip80(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_Companion_fromAip80__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
            /// Explicit opt-in for applications migrating an unprefixed private-key hex value.
            public func fromLegacyHex(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_Companion_fromLegacyHex__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
            /// Generate a new Ed25519 key pair
            public func generate() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_Companion_generate(self.__externalRCRef()))
            }
        }
        /// Whether the secret material has been explicitly cleared.
        public var isCleared: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_isCleared_get(self.__externalRCRef())
            }
        }
        public init(
            data: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, data.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Clear secret material. Subsequent private-key operations must fail.
        public func clear() -> Swift.Void {
            return { xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_clear(self.__externalRCRef()); return () }()
        }
        /// Derive the Ed25519PublicKey for this private key.
        /// 
        /// - Returns: Ed25519PublicKey
        public func publicKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_publicKey(self.__externalRCRef()))
        }
        /// Sign the given message with the private key.
        /// 
        /// - Parameters:
        ///   - message: in HexInput format
        /// 
        /// - Returns: [Signature]
        public func sign(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()))
        }
        /// Export this private key using the algorithm-prefixed AIP-80 format.
        public func toAip80() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_toAip80(self.__externalRCRef()))
        }
        /// Get the private key in bytes (ByteArray).
        /// 
        /// - Returns: [ByteArray] representation of the private key
        public func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PrivateKey_toByteArray(self.__externalRCRef()))
        }
    }
    /// Represents the public key of an Ed25519 key pair.
    /// 
    /// Since [AIP-55](https://github.com/aptos-foundation/AIPs/pull/263) Aptos supports `Legacy` and
    /// `Unified` authentication keys.
    /// 
    /// Ed25519 scheme is represented in the SDK as `Legacy authentication key` and also as
    /// `AnyPublicKey` that represents any `Unified authentication key`
    public final class Ed25519PublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey {
        public final class Companion: KotlinRuntime.KotlinBase {
            /// Length of an Ed25519 public key
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var data: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_data_get(self.__externalRCRef()))
            }
        }
        public init(
            data: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, data.__externalRCRef()); return () }()
        }
        public init(
            data: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(__kt, data.__externalRCRef()); return () }()
        }
        public init(
            pk: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, pk); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Get the authentication key associated with this public key
        public override func authKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_authKey(self.__externalRCRef()))
        }
        /// Get the public key in BCS bytes (ByteArray).
        /// 
        /// - Returns: [ByteArray] representation of the public key
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_toBcs(self.__externalRCRef()))
        }
        /// Get the public key in bytes (ByteArray).
        /// 
        /// - Returns: [ByteArray] representation of the public key
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_toByteArray(self.__externalRCRef()))
        }
        /// Verifies that the private key associated with this public key signed the message with the given
        /// signature.
        /// 
        /// - Parameters:
        ///   - message: The message that was signed
        ///   - signature: The signature to verify
        public override func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
    }
    /// A signature of a message signed using an Ed25519 private key
    public final class Ed25519Signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
        public final class Companion: KotlinRuntime.KotlinBase {
            /// Length of an Ed25519 signature
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Ed25519Signature_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519Signature_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public init(
            hexInput: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Ed25519Signature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Ed25519Signature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, hexInput.__externalRCRef()); return () }()
        }
        public init(
            hexInput: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Ed25519Signature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Ed25519Signature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(__kt, hexInput.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519Signature_toBcs(self.__externalRCRef()))
        }
        /// Get the raw signature bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Ed25519Signature_toByteArray(self.__externalRCRef()))
        }
    }
    /// A validated BIP-39 phrase whose textual form is redacted by default.
    public final class MnemonicPhrase: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_Companion_get())
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
            /// Generate a new English BIP-39 phrase using FastKrypto's secure randomness.
            public func generate(
                wordCount: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicWordCount
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_Companion_generate__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_MnemonicWordCount__(self.__externalRCRef(), wordCount.__externalRCRef()))
            }
            /// Parse and validate an English BIP-39 phrase.
            public func parse(
                phrase: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MnemonicPhrase.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_Companion_parse__TypesOfArguments__Swift_String__(self.__externalRCRef(), phrase))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func derivePrivateKey(
            path: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ed25519,
            passphrase: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_derivePrivateKey__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ed25519_Swift_String__(self.__externalRCRef(), path.__externalRCRef(), passphrase))
        }
        public func deriveSecp256k1PrivateKey(
            path: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa,
            passphrase: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_deriveSecp256k1PrivateKey__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_Swift_String__(self.__externalRCRef(), path.__externalRCRef(), passphrase))
        }
        public func deriveSecp256r1PrivateKey(
            path: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AptosDerivationPath.Ecdsa,
            passphrase: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_deriveSecp256r1PrivateKey__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AptosDerivationPath_Ecdsa_Swift_String__(self.__externalRCRef(), path.__externalRCRef(), passphrase))
        }
        /// Explicitly reveal the mnemonic for secure application-controlled storage.
        public func reveal() -> Swift.String {
            return xyz_mcxross_kaptos_core_crypto_MnemonicPhrase_reveal(self.__externalRCRef())
        }
    }
    /// A legacy Aptos K-of-N Ed25519 public key.
    public final class MultiEd25519PublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var MAX_KEYS: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_Companion_MAX_KEYS_get(self.__externalRCRef())
                }
            }
            public var MIN_KEYS: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_Companion_MIN_KEYS_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519PublicKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_Companion_get())
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
        }
        public var publicKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey] {
            get {
                return xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_publicKeys_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey>
            }
        }
        public var threshold: Swift.UInt8 {
            get {
                return xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_threshold_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            publicKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey],
            threshold: Swift.UInt8
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Ed25519PublicKey__Swift_UInt8__(__kt, publicKeys, threshold); return () }()
        }
        /// Get the authentication key associated with this public key
        public override func authKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_authKey(self.__externalRCRef()))
        }
        /// Get the BCS bytes
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_toBcs(self.__externalRCRef()))
        }
        /// Get the raw public key bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_toByteArray(self.__externalRCRef()))
        }
        /// Verifies that the private key associated with this public key signed the message with the given
        /// signature.
        /// 
        /// - Parameters:
        ///   - message: The message that was signed
        ///   - signature: The signature to verify
        public override func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_core_crypto_MultiEd25519PublicKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
    }
    /// Ed25519 signatures and their canonical 32-bit Aptos signer bitmap.
    public final class MultiEd25519Signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var BITMAP_LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_Companion_BITMAP_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.MultiEd25519Signature.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_Companion_get())
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
        }
        public var bitmap: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_bitmap_get(self.__externalRCRef()))
            }
        }
        public var signatures: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature] {
            get {
                return xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_signatures_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature>
            }
        }
        public var signerIndices: [Swift.Int32] {
            get {
                return xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_signerIndices_get(self.__externalRCRef()) as! Swift.Array<Swift.Int32>
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            signatures: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Ed25519Signature],
            bitmap: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Ed25519Signature__ExportedKotlinPackages_kotlin_ByteArray__(__kt, signatures, bitmap.__externalRCRef()); return () }()
        }
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_toBcs(self.__externalRCRef()))
        }
        /// Get the raw signature bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_MultiEd25519Signature_toByteArray(self.__externalRCRef()))
        }
    }
    /// An abstract representation of a public key.
    /// 
    /// Provides a common interface for verifying any signature.
    open class PublicKey: KotlinRuntime.KotlinBase {
        /// An abstract representation of a public key.
        /// 
        /// Provides a common interface for verifying any signature.
        public override init() {
            precondition(Self.self != ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.self, "ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey is an abstract class and cannot be instantiated directly")
            let __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_PublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Get the BCS bytes
        open func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.self {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_PublicKey_toBcs(self.__externalRCRef()))
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.toBcs': a Swift subclass must override it and must not call super.")
            }
        }
        /// Get the raw public key bytes
        open func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.self {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_PublicKey_toByteArray(self.__externalRCRef()))
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.toByteArray': a Swift subclass must override it and must not call super.")
            }
        }
        /// Verifies that the private key associated with this public key signed the message with the given
        /// signature.
        /// 
        /// - Parameters:
        ///   - message: The message that was signed
        ///   - signature: The signature to verify
        open func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.self {
                return xyz_mcxross_kaptos_core_crypto_PublicKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.verifySignature': a Swift subclass must override it and must not call super.")
            }
        }
    }
    /// A Secp256k1 ecdsa private key
    public final class Secp256k1PrivateKey: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey, ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.__PrivateKey {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_Companion_get())
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
            public func fromAip80(
                value: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_Companion_fromAip80__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Aip80PrivateKey__(self.__externalRCRef(), value.__externalRCRef()))
            }
            /// Import a Secp256k1 private key from the safe, algorithm-prefixed AIP-80 format.
            public func fromAip80(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_Companion_fromAip80__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
            /// Explicit opt-in for applications migrating an unprefixed private-key hex value.
            public func fromLegacyHex(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_Companion_fromLegacyHex__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
            public func generate() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_Companion_generate(self.__externalRCRef()))
            }
        }
        /// Whether the secret material has been explicitly cleared.
        public var isCleared: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_isCleared_get(self.__externalRCRef())
            }
        }
        public init(
            key: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, key.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Clear secret material. Subsequent private-key operations must fail.
        public func clear() -> Swift.Void {
            return { xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_clear(self.__externalRCRef()); return () }()
        }
        /// Derive the public key associated with the private key
        public func publicKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PublicKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_publicKey(self.__externalRCRef()))
        }
        /// Sign the given message with the private key.
        /// 
        /// - Parameters:
        ///   - message: in [HexInput] format
        public func sign(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1Signature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()))
        }
        /// Export this private key using the algorithm-prefixed AIP-80 format.
        public func toAip80() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_toAip80(self.__externalRCRef()))
        }
        /// Get the private key in bytes.
        public func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PrivateKey_toByteArray(self.__externalRCRef()))
        }
    }
    /// Represents the Secp256k1 ecdsa public key
    /// 
    /// Secp256k1 authentication key is represented in the SDK as `AnyPublicKey`.
    public final class Secp256k1PublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PublicKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1PublicKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_Companion_get())
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
        }
        public var hexInput: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_hexInput_get(self.__externalRCRef()))
            }
        }
        public init(
            hexInput: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(__kt, hexInput.__externalRCRef()); return () }()
        }
        public init(
            pk: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, pk); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Get the BCS bytes
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_toBcs(self.__externalRCRef()))
        }
        /// Get the raw public key bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_toByteArray(self.__externalRCRef()))
        }
        /// Verifies that the private key associated with this public key signed the message with the given
        /// signature.
        /// 
        /// - Parameters:
        ///   - message: The message that was signed
        ///   - signature: The signature to verify
        public override func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_core_crypto_Secp256k1PublicKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
    }
    public final class Secp256k1Signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
        public final class Companion: KotlinRuntime.KotlinBase {
            /// Secp256k1 ecdsa signatures are 256-bit.
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Secp256k1Signature_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1Signature.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256k1Signature.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1Signature_Companion_get())
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
        }
        public init(
            hexInput: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256k1Signature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256k1Signature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(__kt, hexInput.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1Signature_toBcs(self.__externalRCRef()))
        }
        /// Get the raw signature bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256k1Signature_toByteArray(self.__externalRCRef()))
        }
    }
    /// A NIST P-256 private key using Aptos' SHA3-256 signing convention.
    public final class Secp256r1PrivateKey: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey, ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.__PrivateKey {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_Companion_get())
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
            public func fromAip80(
                value: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_Companion_fromAip80__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Aip80PrivateKey__(self.__externalRCRef(), value.__externalRCRef()))
            }
            /// Import a P-256 private key from the safe, algorithm-prefixed AIP-80 format.
            public func fromAip80(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_Companion_fromAip80__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
            /// Explicit opt-in for applications migrating an unprefixed private-key hex value.
            public func fromLegacyHex(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_Companion_fromLegacyHex__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
            public func generate() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_Companion_generate(self.__externalRCRef()))
            }
        }
        /// Whether the secret material has been explicitly cleared.
        public var isCleared: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_isCleared_get(self.__externalRCRef())
            }
        }
        public init(
            key: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, key.__externalRCRef()); return () }()
        }
        public init(
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(__kt, key.__externalRCRef()); return () }()
        }
        public init(
            key: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, key); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Clear secret material. Subsequent private-key operations must fail.
        public func clear() -> Swift.Void {
            return { xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_clear(self.__externalRCRef()); return () }()
        }
        /// Derive the public key associated with the private key
        public func publicKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PublicKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_publicKey(self.__externalRCRef()))
        }
        /// Sign the given message with the private key.
        /// 
        /// - Parameters:
        ///   - message: in [HexInput] format
        public func sign(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), message.__externalRCRef()))
        }
        public func signBytes(
            message: ExportedKotlinPackages.kotlin.ByteArray
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_signBytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), message.__externalRCRef()))
        }
        public func signText(
            message: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_signText__TypesOfArguments__Swift_String__(self.__externalRCRef(), message))
        }
        /// Export this private key using the algorithm-prefixed AIP-80 format.
        public func toAip80() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_toAip80(self.__externalRCRef()))
        }
        /// Get the private key in bytes.
        public func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PrivateKey_toByteArray(self.__externalRCRef()))
        }
    }
    /// A NIST P-256 (Secp256r1) public key in canonical uncompressed SEC1 form.
    public final class Secp256r1PublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var COMPRESSED_LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_Companion_COMPRESSED_LENGTH_get(self.__externalRCRef())
                }
            }
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PublicKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1PublicKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_Companion_get())
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
        }
        public init(
            key: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, key.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Get the BCS bytes
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_toBcs(self.__externalRCRef()))
        }
        /// Get the raw public key bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_toByteArray(self.__externalRCRef()))
        }
        public func verifyBytes(
            message: ExportedKotlinPackages.kotlin.ByteArray,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_verifyBytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Secp256r1Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
        /// Verifies that the private key associated with this public key signed the message with the given
        /// signature.
        /// 
        /// - Parameters:
        ///   - message: The message that was signed
        ///   - signature: The signature to verify
        public override func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
        public func verifyText(
            message: Swift.String,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_core_crypto_Secp256r1PublicKey_verifyText__TypesOfArguments__Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Secp256r1Signature__(self.__externalRCRef(), message, signature.__externalRCRef())
        }
    }
    /// A canonical 64-byte `(r, s)` P-256 signature.
    public final class Secp256r1Signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Secp256r1Signature.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_Companion_get())
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
        }
        public init(
            signature: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, signature.__externalRCRef()); return () }()
        }
        public init(
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(__kt, signature.__externalRCRef()); return () }()
        }
        public init(
            signature: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, signature); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_toBcs(self.__externalRCRef()))
        }
        /// Get the raw signature bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Secp256r1Signature_toByteArray(self.__externalRCRef()))
        }
    }
    /// An abstract representation of a crypto signature, associated to a specific signature scheme e.g.
    /// Ed25519 or Secp256k1
    /// 
    /// This is the product of signing a message directly from a PrivateKey and can be verified against a
    /// CryptoPublicKey.
    open class Signature: KotlinRuntime.KotlinBase {
        /// An abstract representation of a crypto signature, associated to a specific signature scheme e.g.
        /// Ed25519 or Secp256k1
        /// 
        /// This is the product of signing a message directly from a PrivateKey and can be verified against a
        /// CryptoPublicKey.
        public override init() {
            precondition(Self.self != ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.self, "ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature is an abstract class and cannot be instantiated directly")
            let __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_Signature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        open func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.self {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Signature_toBcs(self.__externalRCRef()))
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.toBcs': a Swift subclass must override it and must not call super.")
            }
        }
        /// Get the raw signature bytes
        open func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.self {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_Signature_toByteArray(self.__externalRCRef()))
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.toByteArray': a Swift subclass must override it and must not call super.")
            }
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset {
    public enum DigitalAssetPropertyValue_SealedType: KotlinRuntimeSupport.SealedType {
        case address(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Address_SealedType)
        case bool(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bool_SealedType)
        case bytes(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Bytes_SealedType)
        case text(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_Text_SealedType)
        case u128(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U128_SealedType)
        case u16(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U16_SealedType)
        case u256(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U256_SealedType)
        case u32(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U32_SealedType)
        case u64(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U64_SealedType)
        case u8(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue_U8_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue {
            get {
                switch self {
                case let .address(type): type.value
                case let .bool(type): type.value
                case let .bytes(type): type.value
                case let .text(type): type.value
                case let .u128(type): type.value
                case let .u16(type): type.value
                case let .u256(type): type.value
                case let .u32(type): type.value
                case let .u64(type): type.value
                case let .u8(type): type.value
                }
            }
        }
    }
    /// Move-compatible value accepted by typed digital-asset property mutations.
    public protocol DigitalAssetPropertyValue: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset._DigitalAssetPropertyValue {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue_SealedType
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue)
    public protocol _DigitalAssetPropertyValue {
    }
    public protocol __DigitalAssetPropertyValue: KotlinRuntimeSupport._KotlinBridgeable {
    }
    /// Public collection representation independent of generated GraphQL models.
    public final class CollectionRecord: KotlinRuntime.KotlinBase {
        public var creator: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_CollectionRecord_creator_get(self.__externalRCRef()))
            }
        }
        public var currentSupply: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_currentSupply_get(self.__externalRCRef())
            }
        }
        public var id: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_CollectionRecord_id_get(self.__externalRCRef()))
            }
        }
        public var lastTransactionTimestamp: ExportedKotlinPackages.kotlin.time.Instant {
            get {
                return ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_CollectionRecord_lastTransactionTimestamp_get(self.__externalRCRef()))
            }
        }
        public var lastTransactionVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_lastTransactionVersion_get(self.__externalRCRef())
            }
        }
        public var maxSupply: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_maxSupply_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var mutableDescription: Swift.Bool? {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_mutableDescription_get(self.__externalRCRef()).map { it in it.boolValue }
            }
        }
        public var mutableUri: Swift.Bool? {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_mutableUri_get(self.__externalRCRef()).map { it in it.boolValue }
            }
        }
        public var name: Swift.String {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_name_get(self.__externalRCRef())
            }
        }
        public var tokenStandard: Swift.String {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_tokenStandard_get(self.__externalRCRef())
            }
        }
        public var totalMinted: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_totalMinted_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var uri: Swift.String {
            get {
                return xyz_mcxross_kaptos_digitalasset_CollectionRecord_uri_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            id: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            creator: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            name: Swift.String,
            description: Swift.String,
            uri: Swift.String,
            tokenStandard: Swift.String,
            currentSupply: Swift.UInt64,
            maxSupply: Swift.UInt64?,
            totalMinted: Swift.UInt64?,
            mutableDescription: Swift.Bool?,
            mutableUri: Swift.Bool?,
            lastTransactionVersion: Swift.UInt64,
            lastTransactionTimestamp: ExportedKotlinPackages.kotlin.time.Instant
        ) {
            let __kt = xyz_mcxross_kaptos_digitalasset_CollectionRecord_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_digitalasset_CollectionRecord_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_String_Swift_String_Swift_String_Swift_String_Swift_UInt64_Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_Bool__Swift_Optional_Swift_Bool__Swift_UInt64_ExportedKotlinPackages_kotlin_time_Instant__(__kt, id.__externalRCRef(), creator.__externalRCRef(), name, description, uri, tokenStandard, currentSupply, maxSupply.map { it in NSNumber(value: it) } ?? nil, totalMinted.map { it in NSNumber(value: it) } ?? nil, mutableDescription.map { it in NSNumber(value: it) } ?? nil, mutableUri.map { it in NSNumber(value: it) } ?? nil, lastTransactionVersion, lastTransactionTimestamp.__externalRCRef()); return () }()
        }
        public func copy(
            id: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            creator: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            name: Swift.String,
            description: Swift.String,
            uri: Swift.String,
            tokenStandard: Swift.String,
            currentSupply: Swift.UInt64,
            maxSupply: Swift.UInt64?,
            totalMinted: Swift.UInt64?,
            mutableDescription: Swift.Bool?,
            mutableUri: Swift.Bool?,
            lastTransactionVersion: Swift.UInt64,
            lastTransactionTimestamp: ExportedKotlinPackages.kotlin.time.Instant
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.CollectionRecord {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.CollectionRecord.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_CollectionRecord_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_String_Swift_String_Swift_String_Swift_String_Swift_UInt64_Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_Bool__Swift_Optional_Swift_Bool__Swift_UInt64_ExportedKotlinPackages_kotlin_time_Instant__(self.__externalRCRef(), id.__externalRCRef(), creator.__externalRCRef(), name, description, uri, tokenStandard, currentSupply, maxSupply.map { it in NSNumber(value: it) } ?? nil, totalMinted.map { it in NSNumber(value: it) } ?? nil, mutableDescription.map { it in NSNumber(value: it) } ?? nil, mutableUri.map { it in NSNumber(value: it) } ?? nil, lastTransactionVersion, lastTransactionTimestamp.__externalRCRef()))
        }
    }
    /// Collection behavior used by [DigitalAssetService.buildCreateCollection].
    public final class DigitalAssetCollectionOptions: KotlinRuntime.KotlinBase {
        public var assetsBurnableByCreator: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_assetsBurnableByCreator_get(self.__externalRCRef())
            }
        }
        public var assetsFreezableByCreator: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_assetsFreezableByCreator_get(self.__externalRCRef())
            }
        }
        public var maxSupply: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_maxSupply_get(self.__externalRCRef())
            }
        }
        public var mutableAssetDescription: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_mutableAssetDescription_get(self.__externalRCRef())
            }
        }
        public var mutableAssetName: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_mutableAssetName_get(self.__externalRCRef())
            }
        }
        public var mutableAssetProperties: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_mutableAssetProperties_get(self.__externalRCRef())
            }
        }
        public var mutableAssetUri: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_mutableAssetUri_get(self.__externalRCRef())
            }
        }
        public var mutableDescription: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_mutableDescription_get(self.__externalRCRef())
            }
        }
        public var mutableRoyalty: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_mutableRoyalty_get(self.__externalRCRef())
            }
        }
        public var mutableUri: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_mutableUri_get(self.__externalRCRef())
            }
        }
        public var royaltyDenominator: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_royaltyDenominator_get(self.__externalRCRef())
            }
        }
        public var royaltyNumerator: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_royaltyNumerator_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            maxSupply: Swift.UInt64,
            mutableDescription: Swift.Bool,
            mutableRoyalty: Swift.Bool,
            mutableUri: Swift.Bool,
            mutableAssetDescription: Swift.Bool,
            mutableAssetName: Swift.Bool,
            mutableAssetProperties: Swift.Bool,
            mutableAssetUri: Swift.Bool,
            assetsBurnableByCreator: Swift.Bool,
            assetsFreezableByCreator: Swift.Bool,
            royaltyNumerator: Swift.UInt64,
            royaltyDenominator: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_UInt64_Swift_UInt64__(__kt, maxSupply, mutableDescription, mutableRoyalty, mutableUri, mutableAssetDescription, mutableAssetName, mutableAssetProperties, mutableAssetUri, assetsBurnableByCreator, assetsFreezableByCreator, royaltyNumerator, royaltyDenominator); return () }()
        }
        public func copy(
            maxSupply: Swift.UInt64,
            mutableDescription: Swift.Bool,
            mutableRoyalty: Swift.Bool,
            mutableUri: Swift.Bool,
            mutableAssetDescription: Swift.Bool,
            mutableAssetName: Swift.Bool,
            mutableAssetProperties: Swift.Bool,
            mutableAssetUri: Swift.Bool,
            assetsBurnableByCreator: Swift.Bool,
            assetsFreezableByCreator: Swift.Bool,
            royaltyNumerator: Swift.UInt64,
            royaltyDenominator: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetCollectionOptions {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetCollectionOptions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_copy__TypesOfArguments__Swift_UInt64_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_Bool_Swift_UInt64_Swift_UInt64__(self.__externalRCRef(), maxSupply, mutableDescription, mutableRoyalty, mutableUri, mutableAssetDescription, mutableAssetName, mutableAssetProperties, mutableAssetUri, assetsBurnableByCreator, assetsFreezableByCreator, royaltyNumerator, royaltyDenominator))
        }
    }
    /// One typed property mutation; the value determines its on-chain property type.
    public final class DigitalAssetProperty: KotlinRuntime.KotlinBase {
        public var key: Swift.String {
            get {
                return xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty_key_get(self.__externalRCRef())
            }
        }
        public var value: any ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue {
            get {
                return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty_value_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            key: Swift.String,
            value: any ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue
        ) {
            let __kt = xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue__(__kt, key, value.__externalRCRef()); return () }()
        }
        public func copy(
            key: Swift.String,
            value: any ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetPropertyValue
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetProperty {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetProperty.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty_copy__TypesOfArguments__Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetPropertyValue__(self.__externalRCRef(), key, value.__externalRCRef()))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances {
    public final class ChainId: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_ChainId_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var chainId: Swift.UInt8 {
            get {
                return xyz_mcxross_kaptos_transaction_instances_ChainId_chainId_get(self.__externalRCRef())
            }
        }
        public init(
            chainId: Swift.UInt8
        ) {
            let __kt = xyz_mcxross_kaptos_transaction_instances_ChainId_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_transaction_instances_ChainId_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt8__(__kt, chainId); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            chainId: Swift.UInt8
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_ChainId_copy__TypesOfArguments__Swift_UInt8__(self.__externalRCRef(), chainId))
        }
    }
    public final class RawTransaction: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_RawTransaction_Companion_get())
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
            public func fromBcs(
                bytes: ExportedKotlinPackages.kotlin.ByteArray
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_RawTransaction_Companion_fromBcs__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef()))
            }
        }
        public var chainId: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_RawTransaction_chainId_get(self.__externalRCRef()))
            }
        }
        public var expirationTimestampSecs: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_transaction_instances_RawTransaction_expirationTimestampSecs_get(self.__externalRCRef())
            }
        }
        public var gasUnitPrice: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_transaction_instances_RawTransaction_gasUnitPrice_get(self.__externalRCRef())
            }
        }
        public var maxGasAmount: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_transaction_instances_RawTransaction_maxGasAmount_get(self.__externalRCRef())
            }
        }
        public var payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload {
            get {
                return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_RawTransaction_payload_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload
            }
        }
        public var sender: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_RawTransaction_sender_get(self.__externalRCRef()))
            }
        }
        public var sequenceNumber: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_transaction_instances_RawTransaction_sequenceNumber_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            sender: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            sequenceNumber: Swift.UInt64,
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            maxGasAmount: Swift.UInt64,
            gasUnitPrice: Swift.UInt64,
            expirationTimestampSecs: Swift.UInt64,
            chainId: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId
        ) {
            let __kt = xyz_mcxross_kaptos_transaction_instances_RawTransaction_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_transaction_instances_RawTransaction_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_UInt64_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_UInt64_Swift_UInt64_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_ChainId__(__kt, sender.__externalRCRef(), sequenceNumber, payload.__externalRCRef(), maxGasAmount, gasUnitPrice, expirationTimestampSecs, chainId.__externalRCRef()); return () }()
        }
        public func copy(
            sender: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            sequenceNumber: Swift.UInt64,
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            maxGasAmount: Swift.UInt64,
            gasUnitPrice: Swift.UInt64,
            expirationTimestampSecs: Swift.UInt64,
            chainId: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_RawTransaction_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_UInt64_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_UInt64_Swift_UInt64_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_ChainId__(self.__externalRCRef(), sender.__externalRCRef(), sequenceNumber, payload.__externalRCRef(), maxGasAmount, gasUnitPrice, expirationTimestampSecs, chainId.__externalRCRef()))
        }
        public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_transaction_instances_RawTransaction_toBcs(self.__externalRCRef()))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.ledger {
    /// Block identity and inclusive transaction-version range returned by a fullnode.
    public final class LedgerBlock: KotlinRuntime.KotlinBase {
        public var firstVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerBlock_firstVersion_get(self.__externalRCRef())
            }
        }
        public var height: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerBlock_height_get(self.__externalRCRef())
            }
        }
        public var lastVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerBlock_lastVersion_get(self.__externalRCRef())
            }
        }
        public var timestampMicros: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerBlock_timestampMicros_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            height: Swift.UInt64,
            hash: Swift.String,
            timestampMicros: Swift.UInt64,
            firstVersion: Swift.UInt64,
            lastVersion: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_ledger_LedgerBlock_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_ledger_LedgerBlock_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64_Swift_String_Swift_UInt64_Swift_UInt64_Swift_UInt64__(__kt, height, hash, timestampMicros, firstVersion, lastVersion); return () }()
        }
        public func copy(
            height: Swift.UInt64,
            hash: Swift.String,
            timestampMicros: Swift.UInt64,
            firstVersion: Swift.UInt64,
            lastVersion: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_ledger_LedgerBlock_copy__TypesOfArguments__Swift_UInt64_Swift_String_Swift_UInt64_Swift_UInt64_Swift_UInt64__(self.__externalRCRef(), height, hash, timestampMicros, firstVersion, lastVersion))
        }
    }
    /// Current fullnode ledger state with every Aptos `u64` represented losslessly.
    public final class LedgerState: KotlinRuntime.KotlinBase {
        public var blockHeight: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerState_blockHeight_get(self.__externalRCRef())
            }
        }
        public var chainId: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_ledger_LedgerState_chainId_get(self.__externalRCRef()))
            }
        }
        /// Raw BCS encryption-key bytes when encrypted transaction submission is available.
        public var encryptionKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString? {
            get {
                return { switch xyz_mcxross_kaptos_ledger_LedgerState_encryptionKey_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var epoch: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerState_epoch_get(self.__externalRCRef())
            }
        }
        public var gitHash: Swift.String? {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerState_gitHash_get(self.__externalRCRef())
            }
        }
        public var nodeRole: Swift.String {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerState_nodeRole_get(self.__externalRCRef())
            }
        }
        public var oldestBlockHeight: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerState_oldestBlockHeight_get(self.__externalRCRef())
            }
        }
        public var oldestVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerState_oldestVersion_get(self.__externalRCRef())
            }
        }
        public var timestampMicros: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerState_timestampMicros_get(self.__externalRCRef())
            }
        }
        public var version: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_ledger_LedgerState_version_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            chainId: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId,
            epoch: Swift.UInt64,
            version: Swift.UInt64,
            oldestVersion: Swift.UInt64,
            timestampMicros: Swift.UInt64,
            nodeRole: Swift.String,
            oldestBlockHeight: Swift.UInt64,
            blockHeight: Swift.UInt64,
            gitHash: Swift.String?,
            encryptionKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString?
        ) {
            let __kt = xyz_mcxross_kaptos_ledger_LedgerState_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_ledger_LedgerState_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_ChainId_Swift_UInt64_Swift_UInt64_Swift_UInt64_Swift_UInt64_Swift_String_Swift_UInt64_Swift_UInt64_Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString___(__kt, chainId.__externalRCRef(), epoch, version, oldestVersion, timestampMicros, nodeRole, oldestBlockHeight, blockHeight, gitHash ?? nil, encryptionKey.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func copy(
            chainId: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.ChainId,
            epoch: Swift.UInt64,
            version: Swift.UInt64,
            oldestVersion: Swift.UInt64,
            timestampMicros: Swift.UInt64,
            nodeRole: Swift.String,
            oldestBlockHeight: Swift.UInt64,
            blockHeight: Swift.UInt64,
            gitHash: Swift.String?,
            encryptionKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerState {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerState.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_ledger_LedgerState_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_instances_ChainId_Swift_UInt64_Swift_UInt64_Swift_UInt64_Swift_UInt64_Swift_String_Swift_UInt64_Swift_UInt64_Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString___(self.__externalRCRef(), chainId.__externalRCRef(), epoch, version, oldestVersion, timestampMicros, nodeRole, oldestBlockHeight, blockHeight, gitHash ?? nil, encryptionKey.map { it in it.__externalRCRef() } ?? nil))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.model {
    public enum AnyPublicKeyVariant: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Ed25519
        case Secp256k1
        case Secp256r1
        case Keyless
        case FederatedKeyless
        case SlhDsaSha2_128s
        public var description: Swift.String {
            get {
                switch self {
                case .Ed25519: "Ed25519"
                case .Secp256k1: "Secp256k1"
                case .Secp256r1: "Secp256r1"
                case .Keyless: "Keyless"
                case .FederatedKeyless: "FederatedKeyless"
                case .SlhDsaSha2_128s: "SlhDsaSha2_128s"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Ed25519: 0
                case .Secp256k1: 1
                case .Secp256r1: 2
                case .Keyless: 3
                case .FederatedKeyless: 4
                case .SlhDsaSha2_128s: 5
                default: fatalError()
                }
            }
        }
        public var value: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_AnyPublicKeyVariant_value_get(self.__externalRCRef())
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Ed25519": self = .Ed25519
            case "Secp256k1": self = .Secp256k1
            case "Secp256r1": self = .Secp256r1
            case "Keyless": self = .Keyless
            case "FederatedKeyless": self = .FederatedKeyless
            case "SlhDsaSha2_128s": self = .SlhDsaSha2_128s
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<6 ~= rawValue else { return nil }
            self = AnyPublicKeyVariant.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_model_AnyPublicKeyVariant_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Ed25519
            case 1: self = .Secp256k1
            case 2: self = .Secp256r1
            case 3: self = .Keyless
            case 4: self = .FederatedKeyless
            case 5: self = .SlhDsaSha2_128s
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Ed25519: xyz_mcxross_kaptos_model_AnyPublicKeyVariant_Ed25519()
            case .Secp256k1: xyz_mcxross_kaptos_model_AnyPublicKeyVariant_Secp256k1()
            case .Secp256r1: xyz_mcxross_kaptos_model_AnyPublicKeyVariant_Secp256r1()
            case .Keyless: xyz_mcxross_kaptos_model_AnyPublicKeyVariant_Keyless()
            case .FederatedKeyless: xyz_mcxross_kaptos_model_AnyPublicKeyVariant_FederatedKeyless()
            case .SlhDsaSha2_128s: xyz_mcxross_kaptos_model_AnyPublicKeyVariant_SlhDsaSha2_128s()
            default: fatalError()
            }
        }
    }
    public enum AnySignatureVariant: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Ed25519
        case Secp256k1
        case WebAuthn
        case Keyless
        case SlhDsaSha2_128s
        public var description: Swift.String {
            get {
                switch self {
                case .Ed25519: "Ed25519"
                case .Secp256k1: "Secp256k1"
                case .WebAuthn: "WebAuthn"
                case .Keyless: "Keyless"
                case .SlhDsaSha2_128s: "SlhDsaSha2_128s"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Ed25519: 0
                case .Secp256k1: 1
                case .WebAuthn: 2
                case .Keyless: 3
                case .SlhDsaSha2_128s: 4
                default: fatalError()
                }
            }
        }
        public var value: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_AnySignatureVariant_value_get(self.__externalRCRef())
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Ed25519": self = .Ed25519
            case "Secp256k1": self = .Secp256k1
            case "WebAuthn": self = .WebAuthn
            case "Keyless": self = .Keyless
            case "SlhDsaSha2_128s": self = .SlhDsaSha2_128s
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<5 ~= rawValue else { return nil }
            self = AnySignatureVariant.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_model_AnySignatureVariant_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Ed25519
            case 1: self = .Secp256k1
            case 2: self = .WebAuthn
            case 3: self = .Keyless
            case 4: self = .SlhDsaSha2_128s
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Ed25519: xyz_mcxross_kaptos_model_AnySignatureVariant_Ed25519()
            case .Secp256k1: xyz_mcxross_kaptos_model_AnySignatureVariant_Secp256k1()
            case .WebAuthn: xyz_mcxross_kaptos_model_AnySignatureVariant_WebAuthn()
            case .Keyless: xyz_mcxross_kaptos_model_AnySignatureVariant_Keyless()
            case .SlhDsaSha2_128s: xyz_mcxross_kaptos_model_AnySignatureVariant_SlhDsaSha2_128s()
            default: fatalError()
            }
        }
    }
    public enum AptosError_SealedType: KotlinRuntimeSupport.SealedType {
        case api(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Api_SealedType)
        case cancelled(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Cancelled_SealedType)
        case crypto(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Crypto_SealedType)
        case indexer(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Indexer_SealedType)
        case serialization(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Serialization_SealedType)
        case timeout(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Timeout_SealedType)
        case transport(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Transport_SealedType)
        case unsupportedFeature(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_UnsupportedFeature_SealedType)
        case validation(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError_Validation_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError {
            get {
                switch self {
                case let .api(type): type.value
                case let .cancelled(type): type.value
                case let .crypto(type): type.value
                case let .indexer(type): type.value
                case let .serialization(type): type.value
                case let .timeout(type): type.value
                case let .transport(type): type.value
                case let .unsupportedFeature(type): type.value
                case let .validation(type): type.value
                }
            }
        }
    }
    public enum AuthenticationKeyScheme_SealedType: KotlinRuntimeSupport.SealedType {
        case derive(ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Derive_SealedType)
        case signing(ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Signing_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme {
            get {
                switch self {
                case let .derive(type): type.value
                case let .signing(type): type.value
                }
            }
        }
    }
    public enum DeriveScheme: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case DeriveAuid
        case DeriveObjectAddressFromObject
        case DeriveObjectAddressFromGuid
        case DeriveObjectAddressFromSeed
        case DeriveResourceAccountAddress
        public var description: Swift.String {
            get {
                switch self {
                case .DeriveAuid: "DeriveAuid"
                case .DeriveObjectAddressFromObject: "DeriveObjectAddressFromObject"
                case .DeriveObjectAddressFromGuid: "DeriveObjectAddressFromGuid"
                case .DeriveObjectAddressFromSeed: "DeriveObjectAddressFromSeed"
                case .DeriveResourceAccountAddress: "DeriveResourceAccountAddress"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .DeriveAuid: 0
                case .DeriveObjectAddressFromObject: 1
                case .DeriveObjectAddressFromGuid: 2
                case .DeriveObjectAddressFromSeed: 3
                case .DeriveResourceAccountAddress: 4
                default: fatalError()
                }
            }
        }
        public var value: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_DeriveScheme_value_get(self.__externalRCRef())
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "DeriveAuid": self = .DeriveAuid
            case "DeriveObjectAddressFromObject": self = .DeriveObjectAddressFromObject
            case "DeriveObjectAddressFromGuid": self = .DeriveObjectAddressFromGuid
            case "DeriveObjectAddressFromSeed": self = .DeriveObjectAddressFromSeed
            case "DeriveResourceAccountAddress": self = .DeriveResourceAccountAddress
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<5 ~= rawValue else { return nil }
            self = DeriveScheme.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_model_DeriveScheme_ordinal(__externalRCRefUnsafe) {
            case 0: self = .DeriveAuid
            case 1: self = .DeriveObjectAddressFromObject
            case 2: self = .DeriveObjectAddressFromGuid
            case 3: self = .DeriveObjectAddressFromSeed
            case 4: self = .DeriveResourceAccountAddress
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .DeriveAuid: xyz_mcxross_kaptos_model_DeriveScheme_DeriveAuid()
            case .DeriveObjectAddressFromObject: xyz_mcxross_kaptos_model_DeriveScheme_DeriveObjectAddressFromObject()
            case .DeriveObjectAddressFromGuid: xyz_mcxross_kaptos_model_DeriveScheme_DeriveObjectAddressFromGuid()
            case .DeriveObjectAddressFromSeed: xyz_mcxross_kaptos_model_DeriveScheme_DeriveObjectAddressFromSeed()
            case .DeriveResourceAccountAddress: xyz_mcxross_kaptos_model_DeriveScheme_DeriveResourceAccountAddress()
            default: fatalError()
            }
        }
    }
    public enum EntryFunctionArgument_SealedType: KotlinRuntimeSupport.SealedType {
        case transactionArgument(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument {
            get {
                switch self {
                case let .transactionArgument(type): type.value
                }
            }
        }
    }
    public enum MultisigPayload_SealedType: KotlinRuntimeSupport.SealedType {
        case entryFunction(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_EntryFunction_SealedType)
        case script(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload_Script_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload {
            get {
                switch self {
                case let .entryFunction(type): type.value
                case let .script(type): type.value
                }
            }
        }
    }
    /// Enum class representing the network to connect to.
    public enum Network: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case MAINNET
        case TESTNET
        case DEVNET
        case SHELBYNET
        case NETNA
        case LOCAL
        case CUSTOM
        public var description: Swift.String {
            get {
                switch self {
                case .MAINNET: "MAINNET"
                case .TESTNET: "TESTNET"
                case .DEVNET: "DEVNET"
                case .SHELBYNET: "SHELBYNET"
                case .NETNA: "NETNA"
                case .LOCAL: "LOCAL"
                case .CUSTOM: "CUSTOM"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .MAINNET: 0
                case .TESTNET: 1
                case .DEVNET: 2
                case .SHELBYNET: 3
                case .NETNA: 4
                case .LOCAL: 5
                case .CUSTOM: 6
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "MAINNET": self = .MAINNET
            case "TESTNET": self = .TESTNET
            case "DEVNET": self = .DEVNET
            case "SHELBYNET": self = .SHELBYNET
            case "NETNA": self = .NETNA
            case "LOCAL": self = .LOCAL
            case "CUSTOM": self = .CUSTOM
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<7 ~= rawValue else { return nil }
            self = Network.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_model_Network_ordinal(__externalRCRefUnsafe) {
            case 0: self = .MAINNET
            case 1: self = .TESTNET
            case 2: self = .DEVNET
            case 3: self = .SHELBYNET
            case 4: self = .NETNA
            case 5: self = .LOCAL
            case 6: self = .CUSTOM
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .MAINNET: xyz_mcxross_kaptos_model_Network_MAINNET()
            case .TESTNET: xyz_mcxross_kaptos_model_Network_TESTNET()
            case .DEVNET: xyz_mcxross_kaptos_model_Network_DEVNET()
            case .SHELBYNET: xyz_mcxross_kaptos_model_Network_SHELBYNET()
            case .NETNA: xyz_mcxross_kaptos_model_Network_NETNA()
            case .LOCAL: xyz_mcxross_kaptos_model_Network_LOCAL()
            case .CUSTOM: xyz_mcxross_kaptos_model_Network_CUSTOM()
            default: fatalError()
            }
        }
    }
    public enum ReplayProtection_SealedType: KotlinRuntimeSupport.SealedType {
        case nonce(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_Nonce_SealedType)
        case sequenceNumber(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection_SequenceNumber_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection {
            get {
                switch self {
                case let .nonce(type): type.value
                case let .sequenceNumber(type): type.value
                }
            }
        }
    }
    public enum ScriptFunctionArgument_SealedType: KotlinRuntimeSupport.SealedType {
        case entryFunctionArgument(ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptFunctionArgument {
            get {
                switch self {
                case let .entryFunctionArgument(type): type.value
                }
            }
        }
    }
    public enum SigningScheme: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Ed25519
        case MultiEd25519
        case SingleKey
        case MultiKey
        public var description: Swift.String {
            get {
                switch self {
                case .Ed25519: "Ed25519"
                case .MultiEd25519: "MultiEd25519"
                case .SingleKey: "SingleKey"
                case .MultiKey: "MultiKey"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Ed25519: 0
                case .MultiEd25519: 1
                case .SingleKey: 2
                case .MultiKey: 3
                default: fatalError()
                }
            }
        }
        public var value: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_SigningScheme_value_get(self.__externalRCRef())
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Ed25519": self = .Ed25519
            case "MultiEd25519": self = .MultiEd25519
            case "SingleKey": self = .SingleKey
            case "MultiKey": self = .MultiKey
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<4 ~= rawValue else { return nil }
            self = SigningScheme.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_model_SigningScheme_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Ed25519
            case 1: self = .MultiEd25519
            case 2: self = .SingleKey
            case 3: self = .MultiKey
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Ed25519: xyz_mcxross_kaptos_model_SigningScheme_Ed25519()
            case .MultiEd25519: xyz_mcxross_kaptos_model_SigningScheme_MultiEd25519()
            case .SingleKey: xyz_mcxross_kaptos_model_SigningScheme_SingleKey()
            case .MultiKey: xyz_mcxross_kaptos_model_SigningScheme_MultiKey()
            default: fatalError()
            }
        }
    }
    public enum SigningSchemeInput: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Ed25519
        case Secp256k1
        case Secp256r1
        public var description: Swift.String {
            get {
                switch self {
                case .Ed25519: "Ed25519"
                case .Secp256k1: "Secp256k1"
                case .Secp256r1: "Secp256r1"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Ed25519: 0
                case .Secp256k1: 1
                case .Secp256r1: 2
                default: fatalError()
                }
            }
        }
        public var value: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_SigningSchemeInput_value_get(self.__externalRCRef())
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Ed25519": self = .Ed25519
            case "Secp256k1": self = .Secp256k1
            case "Secp256r1": self = .Secp256r1
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<3 ~= rawValue else { return nil }
            self = SigningSchemeInput.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_model_SigningSchemeInput_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Ed25519
            case 1: self = .Secp256k1
            case 2: self = .Secp256r1
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Ed25519: xyz_mcxross_kaptos_model_SigningSchemeInput_Ed25519()
            case .Secp256k1: xyz_mcxross_kaptos_model_SigningSchemeInput_Secp256k1()
            case .Secp256r1: xyz_mcxross_kaptos_model_SigningSchemeInput_Secp256r1()
            default: fatalError()
            }
        }
    }
    public enum TransactionArgument_SealedType: KotlinRuntimeSupport.SealedType {
        case accountAddress(ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress_SealedType)
        case bool(ExportedKotlinPackages.xyz.mcxross.kaptos.model.Bool_SealedType)
        case fixedBytes(ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes_SealedType)
        case hexInput(ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput_SealedType)
        case moveOption(ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveOption_SealedType)
        case moveString(ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveString_SealedType)
        case moveVector(ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector_SealedType)
        case u128(ExportedKotlinPackages.xyz.mcxross.kaptos.model.U128_SealedType)
        case u16(ExportedKotlinPackages.xyz.mcxross.kaptos.model.U16_SealedType)
        case u256(ExportedKotlinPackages.xyz.mcxross.kaptos.model.U256_SealedType)
        case u32(ExportedKotlinPackages.xyz.mcxross.kaptos.model.U32_SealedType)
        case u64(ExportedKotlinPackages.xyz.mcxross.kaptos.model.U64_SealedType)
        case u8(ExportedKotlinPackages.xyz.mcxross.kaptos.model.U8_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
            get {
                switch self {
                case let .accountAddress(type): type.value
                case let .bool(type): type.value
                case let .fixedBytes(type): type.value
                case let .hexInput(type): type.value
                case let .moveOption(type): type.value
                case let .moveString(type): type.value
                case let .moveVector(type): type.value
                case let .u128(type): type.value
                case let .u16(type): type.value
                case let .u256(type): type.value
                case let .u32(type): type.value
                case let .u64(type): type.value
                case let .u8(type): type.value
                }
            }
        }
    }
    public enum TransactionExecutable_SealedType: KotlinRuntimeSupport.SealedType {
        case empty(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Empty_SealedType)
        case encrypted(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Encrypted_SealedType)
        case entryFunction(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_EntryFunction_SealedType)
        case script(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable_Script_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable {
            get {
                switch self {
                case let .empty(type): type.value
                case let .encrypted(type): type.value
                case let .entryFunction(type): type.value
                case let .script(type): type.value
                }
            }
        }
    }
    public enum TransactionExtraConfig_SealedType: KotlinRuntimeSupport.SealedType {
        case v1(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_V1_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig {
            get {
                switch self {
                case let .v1(type): type.value
                }
            }
        }
    }
    public enum TransactionPayload_SealedType: KotlinRuntimeSupport.SealedType {
        case encrypted(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Encrypted_SealedType)
        case entryFunction(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction_SealedType)
        case innerV1(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_InnerV1_SealedType)
        case multisig(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Multisig_SealedType)
        case script(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Script_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload {
            get {
                switch self {
                case let .encrypted(type): type.value
                case let .entryFunction(type): type.value
                case let .innerV1(type): type.value
                case let .multisig(type): type.value
                case let .script(type): type.value
                }
            }
        }
    }
    public enum TransactionResponseType: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case PENDING
        case USER
        case GENESIS
        case BLOCK_METADATA
        case STATE_CHECKPOINT
        case VALIDATOR
        case BLOCK_EPILOGUE
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionResponseType_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
            public func serializer(
                typeParamsSerializers: any ExportedKotlinPackages.kotlinx.serialization.KSerializer...
            ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var description: Swift.String {
            get {
                switch self {
                case .PENDING: "PENDING"
                case .USER: "USER"
                case .GENESIS: "GENESIS"
                case .BLOCK_METADATA: "BLOCK_METADATA"
                case .STATE_CHECKPOINT: "STATE_CHECKPOINT"
                case .VALIDATOR: "VALIDATOR"
                case .BLOCK_EPILOGUE: "BLOCK_EPILOGUE"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .PENDING: 0
                case .USER: 1
                case .GENESIS: 2
                case .BLOCK_METADATA: 3
                case .STATE_CHECKPOINT: 4
                case .VALIDATOR: 5
                case .BLOCK_EPILOGUE: 6
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "PENDING": self = .PENDING
            case "USER": self = .USER
            case "GENESIS": self = .GENESIS
            case "BLOCK_METADATA": self = .BLOCK_METADATA
            case "STATE_CHECKPOINT": self = .STATE_CHECKPOINT
            case "VALIDATOR": self = .VALIDATOR
            case "BLOCK_EPILOGUE": self = .BLOCK_EPILOGUE
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<7 ~= rawValue else { return nil }
            self = TransactionResponseType.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_model_TransactionResponseType_ordinal(__externalRCRefUnsafe) {
            case 0: self = .PENDING
            case 1: self = .USER
            case 2: self = .GENESIS
            case 3: self = .BLOCK_METADATA
            case 4: self = .STATE_CHECKPOINT
            case 5: self = .VALIDATOR
            case 6: self = .BLOCK_EPILOGUE
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .PENDING: xyz_mcxross_kaptos_model_TransactionResponseType_PENDING()
            case .USER: xyz_mcxross_kaptos_model_TransactionResponseType_USER()
            case .GENESIS: xyz_mcxross_kaptos_model_TransactionResponseType_GENESIS()
            case .BLOCK_METADATA: xyz_mcxross_kaptos_model_TransactionResponseType_BLOCK_METADATA()
            case .STATE_CHECKPOINT: xyz_mcxross_kaptos_model_TransactionResponseType_STATE_CHECKPOINT()
            case .VALIDATOR: xyz_mcxross_kaptos_model_TransactionResponseType_VALIDATOR()
            case .BLOCK_EPILOGUE: xyz_mcxross_kaptos_model_TransactionResponseType_BLOCK_EPILOGUE()
            default: fatalError()
            }
        }
    }
    public enum TransactionResponse_SealedType: KotlinRuntimeSupport.SealedType {
        case blockEpilogueTransactionResponse(ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse_SealedType)
        case blockMetadataTransactionResponse(ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse_SealedType)
        case genesisTransactionResponse(ExportedKotlinPackages.xyz.mcxross.kaptos.model.GenesisTransactionResponse_SealedType)
        case pendingTransactionResponse(ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse_SealedType)
        case stateCheckpointTransactionResponse(ExportedKotlinPackages.xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse_SealedType)
        case userTransactionResponse(ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse_SealedType)
        case validatorTransactionResponse(ExportedKotlinPackages.xyz.mcxross.kaptos.model.ValidatorTransactionResponse_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            get {
                switch self {
                case let .blockEpilogueTransactionResponse(type): type.value
                case let .blockMetadataTransactionResponse(type): type.value
                case let .genesisTransactionResponse(type): type.value
                case let .pendingTransactionResponse(type): type.value
                case let .stateCheckpointTransactionResponse(type): type.value
                case let .userTransactionResponse(type): type.value
                case let .validatorTransactionResponse(type): type.value
                }
            }
        }
    }
    public enum TypeTag_SealedType: KotlinRuntimeSupport.SealedType {
        case typeTagAddress(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagAddress_SealedType)
        case typeTagBool(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagBool_SealedType)
        case typeTagGeneric(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagGeneric_SealedType)
        case typeTagI128(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI128_SealedType)
        case typeTagI16(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI16_SealedType)
        case typeTagI256(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI256_SealedType)
        case typeTagI32(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI32_SealedType)
        case typeTagI64(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI64_SealedType)
        case typeTagI8(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI8_SealedType)
        case typeTagReference(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagReference_SealedType)
        case typeTagSigner(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagSigner_SealedType)
        case typeTagStruct(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct_SealedType)
        case typeTagU128(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU128_SealedType)
        case typeTagU16(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU16_SealedType)
        case typeTagU256(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU256_SealedType)
        case typeTagU32(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU32_SealedType)
        case typeTagU64(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU64_SealedType)
        case typeTagU8(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU8_SealedType)
        case typeTagVector(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
            get {
                switch self {
                case let .typeTagAddress(type): type.value
                case let .typeTagBool(type): type.value
                case let .typeTagGeneric(type): type.value
                case let .typeTagI128(type): type.value
                case let .typeTagI16(type): type.value
                case let .typeTagI256(type): type.value
                case let .typeTagI32(type): type.value
                case let .typeTagI64(type): type.value
                case let .typeTagI8(type): type.value
                case let .typeTagReference(type): type.value
                case let .typeTagSigner(type): type.value
                case let .typeTagStruct(type): type.value
                case let .typeTagU128(type): type.value
                case let .typeTagU16(type): type.value
                case let .typeTagU256(type): type.value
                case let .typeTagU32(type): type.value
                case let .typeTagU64(type): type.value
                case let .typeTagU8(type): type.value
                case let .typeTagVector(type): type.value
                }
            }
        }
    }
    public enum UnsignedTransaction_SealedType: KotlinRuntimeSupport.SealedType {
        case feePayer(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_SealedType)
        case multiAgent(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent_SealedType)
        case simple(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            get {
                switch self {
                case let .feePayer(type): type.value
                case let .multiAgent(type): type.value
                case let .simple(type): type.value
                }
            }
        }
    }
    public typealias MoveModuleId = Swift.String
    /// This interface is used to define the input for an account address.
    public protocol AccountAddressInput: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._AccountAddressInput {
        /// The value of the account address.
        var value: Swift.String {
            get
        }
    }
    /// Stable error categories exposed by the redesigned SDK API.
    public protocol AptosError: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._AptosError {
        var cause: ExportedKotlinPackages.kotlin.Throwable? {
            get
        }
        var message: Swift.String {
            get
        }
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError_SealedType
    }
    /// The public, typed outcome of a Kaptos operation.
    public protocol AptosResult: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._AptosResult {
        var isFailure: Swift.Bool {
            get
        }
        var isSuccess: Swift.Bool {
            get
        }
    }
    public protocol MultisigPayload: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._MultisigPayload {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MultisigPayload_SealedType
    }
    /// Selects conventional sequence-number or AIP-122 nonce replay protection.
    public protocol ReplayProtection: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._ReplayProtection {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection_SealedType
    }
    public protocol TransactionExecutable: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionExecutable {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable_SealedType
        /// Exact Aptos BCS bytes used inside an encrypted plaintext.
        func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray
    }
    public protocol TransactionExtraConfig: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionExtraConfig {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig_SealedType
        func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray
    }
    /// Exact wire model for Aptos transaction payload variants.
    public protocol TransactionPayload: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._TransactionPayload {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload_SealedType
        func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray
    }
    /// A transaction before account authenticators are attached.
    public protocol UnsignedTransaction: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.model._UnsignedTransaction {
        var rawTransaction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction {
            get
        }
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction_SealedType
        /// Exact bytes covered by the transaction signature, before domain separation.
        func signingBcs() -> ExportedKotlinPackages.kotlin.ByteArray
        func signingMessage() -> ExportedKotlinPackages.kotlin.ByteArray
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput)
    public protocol _AccountAddressInput {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosError)
    public protocol _AptosError {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult)
    public protocol _AptosResult {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_MultisigPayload)
    public protocol _MultisigPayload {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection)
    public protocol _ReplayProtection {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExecutable)
    public protocol _TransactionExecutable {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig)
    public protocol _TransactionExtraConfig {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload)
    public protocol _TransactionPayload {
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction)
    public protocol _UnsignedTransaction {
    }
    public protocol __AccountAddressInput: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __AptosError: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __AptosResult: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __MultisigPayload: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __ReplayProtection: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __TransactionExecutable: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __TransactionExtraConfig: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __TransactionPayload: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __UnsignedTransaction: KotlinRuntimeSupport._KotlinBridgeable {
    }
    /// This class is used to represent an account address.
    /// 
    /// It is used for working with account addresses. Account addresses, when represented as a string,
    /// generally look like these examples:
    /// - 0x1
    /// - 0xaa86fe99004361f747f91342ca13c426ca0cccb0c1217677180c9493bad6ef0c
    public final class AccountAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AccountAddressInput {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var FOUR: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_FOUR_get(self.__externalRCRef()))
                }
            }
            public var LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_model_AccountAddress_Companion_LENGTH_get(self.__externalRCRef())
                }
            }
            public var LONG_STRING_LENGTH: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_model_AccountAddress_Companion_LONG_STRING_LENGTH_get(self.__externalRCRef())
                }
            }
            public var ONE: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_ONE_get(self.__externalRCRef()))
                }
            }
            public var THREE: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_THREE_get(self.__externalRCRef()))
                }
            }
            public var TWO: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_TWO_get(self.__externalRCRef()))
                }
            }
            public var ZERO: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_ZERO_get(self.__externalRCRef()))
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_get())
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
            /// Convenience method for creating an AccountAddress from all known inputs.
            /// 
            /// This handles, HexInput, and AccountAddress itself
            /// 
            /// - Parameters:
            ///   - input:
            public func from(
                input: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_from__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), input.__externalRCRef()))
            }
            /// Parses relaxed address formats. Use [fromStringStrict] when input must satisfy canonical
            /// AIP-40 formatting.
            /// 
            /// Creates an instance of [AccountAddress] from a hex string.
            /// 
            /// This function allows all formats defined by AIP-40. In short this means the following formats
            /// are accepted:
            /// - LONG, with or without leading 0x
            /// - SHORT, with or without leading 0x
            /// 
            /// Where:
            /// - LONG is 64 hex characters.
            /// - SHORT is 1 to 63 hex characters inclusive.
            /// - Padding zeroes are allowed, e.g. 0x0123 is valid.
            /// 
            /// Learn more about the different address formats by reading AIP-40:
            /// https://github.com/aptos-foundation/AIPs/blob/main/aips/aip-40.md.
            /// 
            /// - Parameters:
            ///   - input: A hex string representing an account address.
            /// 
            /// - Returns: An instance of [AccountAddress].
            public func fromString(
                input: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_fromString__TypesOfArguments__Swift_String__(self.__externalRCRef(), input))
            }
            /// NOTE: This function has strict parsing behavior. For relaxed behavior, please use the
            /// `[fromString]` function.
            /// 
            /// Creates an instance of AccountAddress from a hex string.
            /// 
            /// This function allows only the strictest formats defined by AIP-40. In short this means only
            /// the following formats are accepted:
            /// - LONG
            /// - SHORT for special addresses
            /// 
            /// Where:
            /// - LONG is defined as 0x + 64 hex characters.
            /// - SHORT for special addresses is 0x0 to 0xf inclusive without padding zeroes.
            /// 
            /// This means the following are not accepted:
            /// - SHORT for non-special addresses.
            /// - Any address without a leading 0x.
            /// 
            /// Learn more about the different address formats by reading AIP-40:
            /// https://github.com/aptos-foundation/AIPs/blob/main/aips/aip-40.md.
            /// 
            /// - Parameters:
            ///   - input: A hex string representing an account address.
            /// 
            /// - Returns: An instance of [AccountAddress].
            public func fromStringStrict(
                input: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_Companion_fromStringStrict__TypesOfArguments__Swift_String__(self.__externalRCRef(), input))
            }
            /// Check if the string is a valid [AccountAddress].
            /// 
            /// - Parameters:
            ///   - input: A hex string representing an account address.
            ///   - strict: If true, use strict parsing behavior. If false, use relaxed parsing behavior.
            ///     Default is false.
            /// 
            /// - Returns: valid = true if the string is valid, valid = false if not. If the string is not
            ///   valid, invalidReason will be set explaining why it is invalid.
            public func isValid(
                input: Swift.String,
                strict: Swift.Bool
            ) -> Swift.Bool {
                return xyz_mcxross_kaptos_model_AccountAddress_Companion_isValid__TypesOfArguments__Swift_String_Swift_Bool__(self.__externalRCRef(), input, strict)
            }
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        /// The data of the account address.
        public var data: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_data_get(self.__externalRCRef()))
            }
        }
        public var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_AccountAddress_value_get(self.__externalRCRef())
            }
        }
        /// Creates an account address from a hex string.
        /// 
        /// - Parameters:
        ///   - data: The data of the account address.
        public init(
            data: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_model_AccountAddress_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_AccountAddress_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, data.__externalRCRef()); return () }()
        }
        /// This constructor is used to create an account address from a hex string.
        /// 
        /// It is made to be as sensitive as possible to invalid hex characters. There are instances where
        /// a character less is considered valid, but we are not allowing that here. It must fail at both
        /// the char level and the byte level.
        /// 
        /// - Parameters:
        ///   - hex: The hex string to create the account address from.
        public init(
            hex: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_AccountAddress_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_AccountAddress_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, hex); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            data: ExportedKotlinPackages.kotlin.ByteArray
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_copy__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), data.__externalRCRef()))
        }
        public func isSpecial() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_AccountAddress_isSpecial(self.__externalRCRef())
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .accountAddress(.init(self))
        }
        public func toLongAddress() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountAddress_toLongAddress(self.__externalRCRef()))
        }
        public func toStringLong() -> Swift.String {
            return xyz_mcxross_kaptos_model_AccountAddress_toStringLong(self.__externalRCRef())
        }
        public func toStringLongWithoutPrefix() -> Swift.String {
            return xyz_mcxross_kaptos_model_AccountAddress_toStringLongWithoutPrefix(self.__externalRCRef())
        }
        public func toStringWithoutPrefix() -> Swift.String {
            return xyz_mcxross_kaptos_model_AccountAddress_toStringWithoutPrefix(self.__externalRCRef())
        }
    }
    /// Represents core account metadata on the Aptos blockchain.
    /// 
    /// This data structure corresponds to account information returned by the Aptos Fullnode API. It
    /// provides essential details used for tracking account state and verifying ownership.
    public final class AccountData: KotlinRuntime.KotlinBase {
        /// Represents core account metadata on the Aptos blockchain.
        /// 
        /// This data structure corresponds to account information returned by the Aptos Fullnode API. It
        /// provides essential details used for tracking account state and verifying ownership.
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountData_Companion_get())
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
            /// Represents core account metadata on the Aptos blockchain.
            /// 
            /// This data structure corresponds to account information returned by the Aptos Fullnode API. It
            /// provides essential details used for tracking account state and verifying ownership.
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        /// Authentication key for the account, represented as a hex-encoded string.
        /// - Always prefixed with "0x".
        /// - Each byte is encoded as exactly two hex digits.
        /// - Unlike the `Address` type, leading zeros are preserved and never trimmed.
        /// 
        /// This key defines the set of public keys that are allowed to sign transactions for the account.
        /// 
        /// A 32-byte authentication key in hex encoding (prefixed with "0x").
        /// Determines who is authorized to sign transactions for this account. Unlike `Address`, leading
        /// zeros are preserved.
        public var authenticationKey: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_AccountData_authenticationKey_get(self.__externalRCRef())
            }
        }
        /// Sequence number for the account, represented as a string-encoded 64-bit unsigned integer (u64).
        /// 
        /// The sequence number increases with each transaction submitted from this account. Returned as a
        /// string to avoid precision loss in languages like JavaScript, which do not natively support
        /// 64-bit integers in JSON.
        /// 
        /// Monotonically increasing sequence number of transactions sent from this
        /// account. Used for preventing replay attacks and ensuring transaction ordering. Represented as a
        /// string-encoded u64 for cross-language compatibility.
        public var sequenceNumber: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_model_AccountData_sequenceNumber_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// - Parameters:
        ///   - sequenceNumber: Monotonically increasing sequence number of transactions sent from this
        ///     account. Used for preventing replay attacks and ensuring transaction ordering. Represented as a
        ///     string-encoded u64 for cross-language compatibility.
        ///   - authenticationKey: A 32-byte authentication key in hex encoding (prefixed with "0x").
        ///     Determines who is authorized to sign transactions for this account. Unlike `Address`, leading
        ///     zeros are preserved.
        public init(
            sequenceNumber: Swift.UInt64,
            authenticationKey: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_AccountData_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_AccountData_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64_Swift_String__(__kt, sequenceNumber, authenticationKey); return () }()
        }
        public func copy(
            sequenceNumber: Swift.UInt64,
            authenticationKey: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AccountData_copy__TypesOfArguments__Swift_UInt64_Swift_String__(self.__externalRCRef(), sequenceNumber, authenticationKey))
        }
    }
    /// A page of domain models with a stable aggregate count.
    public final class AptosPage: KotlinRuntime.KotlinBase {
        public var hasNextPage: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_AptosPage_hasNextPage_get(self.__externalRCRef())
            }
        }
        public var items: [(any KotlinRuntimeSupport._KotlinBridgeable)?] {
            get {
                return xyz_mcxross_kaptos_model_AptosPage_items_get(self.__externalRCRef()) as! Swift.Array<Swift.Optional<any KotlinRuntimeSupport._KotlinBridgeable>>
            }
        }
        public var request: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosPage_request_get(self.__externalRCRef()))
            }
        }
        public var totalCount: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_AptosPage_totalCount_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            items: [(any KotlinRuntimeSupport._KotlinBridgeable)?],
            totalCount: Swift.Int32,
            request: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) {
            let __kt = xyz_mcxross_kaptos_model_AptosPage_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_AptosPage_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___Swift_Int32_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(__kt, items.map { it in it as! NSObject? ?? NSNull() }, totalCount, request.__externalRCRef()); return () }()
        }
        public func copy(
            items: [(any KotlinRuntimeSupport._KotlinBridgeable)?],
            totalCount: Swift.Int32,
            request: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AptosPage_copy__TypesOfArguments__Swift_Array_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___Swift_Int32_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), items.map { it in it as! NSObject? ?? NSNull() }, totalCount, request.__externalRCRef()))
        }
    }
    open class AuthenticationKeyScheme: KotlinRuntime.KotlinBase {
        public final class Derive: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme {
            public var scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.DeriveScheme {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.DeriveScheme(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_AuthenticationKeyScheme_Derive_scheme_get(self.__externalRCRef()), options: .asBestFittingWrapper)
                }
            }
            public init(
                scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.DeriveScheme
            ) {
                let __kt = xyz_mcxross_kaptos_model_AuthenticationKeyScheme_Derive_init_allocate()
                super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
                { xyz_mcxross_kaptos_model_AuthenticationKeyScheme_Derive_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_DeriveScheme__(__kt, scheme.__externalRCRef()); return () }()
            }
            package override init(
                __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
                options: KotlinRuntime.KotlinBaseConstructionOptions
            ) {
                super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
            }
            public func copy(
                scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.DeriveScheme
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Derive {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Derive.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AuthenticationKeyScheme_Derive_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_DeriveScheme__(self.__externalRCRef(), scheme.__externalRCRef()))
            }
            public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme_SealedType {
                .derive(.init(self))
            }
        }
        public final class Signing: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme {
            public var scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_AuthenticationKeyScheme_Signing_scheme_get(self.__externalRCRef()), options: .asBestFittingWrapper)
                }
            }
            public init(
                scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme
            ) {
                let __kt = xyz_mcxross_kaptos_model_AuthenticationKeyScheme_Signing_init_allocate()
                super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
                { xyz_mcxross_kaptos_model_AuthenticationKeyScheme_Signing_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_SigningScheme__(__kt, scheme.__externalRCRef()); return () }()
            }
            package override init(
                __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
                options: KotlinRuntime.KotlinBaseConstructionOptions
            ) {
                super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
            }
            public func copy(
                scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Signing {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Signing.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_AuthenticationKeyScheme_Signing_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_SigningScheme__(self.__externalRCRef(), scheme.__externalRCRef()))
            }
            public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme_SealedType {
                .signing(.init(self))
            }
        }
        public struct Derive_SealedType: KotlinRuntimeSupport.SealedType {
            public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Derive
            init(
                _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Derive
            ) {
                self.value = value
            }
        }
        public struct Signing_SealedType: KotlinRuntimeSupport.SealedType {
            public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Signing
            init(
                _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Signing
            ) {
                self.value = value
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        open func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Derive: .derive(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.AuthenticationKeyScheme.Signing: .signing(.init(value))
            default: fatalError("missing sealedType for \(self)")
            }
        }
    }
    public final class BibeCiphertext: KotlinRuntime.KotlinBase {
        public var body: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BibeCiphertext_body_get(self.__externalRCRef()))
            }
        }
        public var id: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BibeCiphertext_id_get(self.__externalRCRef()))
            }
        }
        public var nonce: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes12 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes12.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BibeCiphertext_nonce_get(self.__externalRCRef()))
            }
        }
        public var paddedKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes16 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes16.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BibeCiphertext_paddedKey_get(self.__externalRCRef()))
            }
        }
        public var threeG2Points: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BibeCiphertext_threeG2Points_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            id: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
            threeG2Points: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
            paddedKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes16,
            nonce: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes12,
            body: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString
        ) {
            let __kt = xyz_mcxross_kaptos_model_BibeCiphertext_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_BibeCiphertext_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes16_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes12_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString__(__kt, id.__externalRCRef(), threeG2Points.__externalRCRef(), paddedKey.__externalRCRef(), nonce.__externalRCRef(), body.__externalRCRef()); return () }()
        }
        public func copy(
            id: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
            threeG2Points: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
            paddedKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes16,
            nonce: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes12,
            body: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.BibeCiphertext {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.BibeCiphertext.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BibeCiphertext_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes16_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes12_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString__(self.__externalRCRef(), id.__externalRCRef(), threeG2Points.__externalRCRef(), paddedKey.__externalRCRef(), nonce.__externalRCRef(), body.__externalRCRef()))
        }
    }
    public final class BlockEpilogueTransactionResponse: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var accumulatorRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_accumulatorRootHash_get(self.__externalRCRef())
            }
        }
        public var eventRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_eventRootHash_get(self.__externalRCRef())
            }
        }
        public var gasUsed: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_gasUsed_get(self.__externalRCRef())
            }
        }
        public var stateChangeHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_stateChangeHash_get(self.__externalRCRef())
            }
        }
        public var stateCheckpointHash: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_stateCheckpointHash_get(self.__externalRCRef())
            }
        }
        public var success: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_success_get(self.__externalRCRef())
            }
        }
        public var timestamp: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_timestamp_get(self.__externalRCRef())
            }
        }
        public override var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var version: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_version_get(self.__externalRCRef())
            }
        }
        public var vmStatus: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_vmStatus_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            timestamp: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_String__(__kt, type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, timestamp); return () }()
        }
        public func copy(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            timestamp: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BlockEpilogueTransactionResponse_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_String__(self.__externalRCRef(), type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, timestamp))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse_SealedType {
            .blockEpilogueTransactionResponse(.init(self))
        }
    }
    public final class BlockMetadataTransactionResponse: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var accumulatorRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_accumulatorRootHash_get(self.__externalRCRef())
            }
        }
        public var epoch: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_epoch_get(self.__externalRCRef())
            }
        }
        public var eventRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_eventRootHash_get(self.__externalRCRef())
            }
        }
        public var events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event] {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_events_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event>
            }
        }
        public var failedProposerIndices: [Swift.Int64] {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_failedProposerIndices_get(self.__externalRCRef()) as! Swift.Array<Swift.Int64>
            }
        }
        public var gasUsed: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_gasUsed_get(self.__externalRCRef())
            }
        }
        public var id: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_id_get(self.__externalRCRef())
            }
        }
        public var previousBlockVotesBitvec: [Swift.Int64] {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_previousBlockVotesBitvec_get(self.__externalRCRef()) as! Swift.Array<Swift.Int64>
            }
        }
        public var proposer: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_proposer_get(self.__externalRCRef())
            }
        }
        public var round: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_round_get(self.__externalRCRef())
            }
        }
        public var stateChangeHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_stateChangeHash_get(self.__externalRCRef())
            }
        }
        public var stateCheckpointHash: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_stateCheckpointHash_get(self.__externalRCRef())
            }
        }
        public var success: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_success_get(self.__externalRCRef())
            }
        }
        public var timestamp: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_timestamp_get(self.__externalRCRef())
            }
        }
        public override var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var version: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_version_get(self.__externalRCRef())
            }
        }
        public var vmStatus: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_vmStatus_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            id: Swift.String,
            epoch: Swift.String,
            round: Swift.String,
            events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event],
            previousBlockVotesBitvec: [Swift.Int64],
            proposer: Swift.String,
            failedProposerIndices: [Swift.Int64],
            timestamp: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Event__Swift_Array_Swift_Int64__Swift_String_Swift_Array_Swift_Int64__Swift_String__(__kt, type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, id, epoch, round, events, previousBlockVotesBitvec.map { it in NSNumber(value: it) }, proposer, failedProposerIndices.map { it in NSNumber(value: it) }, timestamp); return () }()
        }
        public func copy(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            id: Swift.String,
            epoch: Swift.String,
            round: Swift.String,
            events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event],
            previousBlockVotesBitvec: [Swift.Int64],
            proposer: Swift.String,
            failedProposerIndices: [Swift.Int64],
            timestamp: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_BlockMetadataTransactionResponse_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Event__Swift_Array_Swift_Int64__Swift_String_Swift_Array_Swift_Int64__Swift_String__(self.__externalRCRef(), type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, id, epoch, round, events, previousBlockVotesBitvec.map { it in NSNumber(value: it) }, proposer, failedProposerIndices.map { it in NSNumber(value: it) }, timestamp))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse_SealedType {
            .blockMetadataTransactionResponse(.init(self))
        }
    }
    public final class Bool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Bool.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Bool.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Bool_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_Bool_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.Bool
        ) {
            let __kt = xyz_mcxross_kaptos_model_Bool_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_Bool_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.Bool
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.Bool {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Bool.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Bool_copy__TypesOfArguments__Swift_Bool__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .bool(.init(self))
        }
    }
    /// Immutable byte sequence with content equality.
    open class ByteString: KotlinRuntime.KotlinBase {
        public final var size: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_ByteString_size_get(self.__externalRCRef())
            }
        }
        public init(
            value: ExportedKotlinPackages.kotlin.ByteArray
        ) {
             let __kt: Swift.UnsafeMutableRawPointer!
             if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.self {
                 __kt = xyz_mcxross_kaptos_model_ByteString_init_allocate()
             } else {
                 __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
             }
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_ByteString_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, value.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public final func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ByteString_toByteArray(self.__externalRCRef()))
        }
    }
    public final class ClaimedEntryFunction: KotlinRuntime.KotlinBase {
        public var function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier? {
            get {
                return { switch xyz_mcxross_kaptos_model_ClaimedEntryFunction_function_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ClaimedEntryFunction_module_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId,
            function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier?
        ) {
            let __kt = xyz_mcxross_kaptos_model_ClaimedEntryFunction_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_ClaimedEntryFunction_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ModuleId_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Identifier___(__kt, module.__externalRCRef(), function.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func copy(
            module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId,
            function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ClaimedEntryFunction_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_ModuleId_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Identifier___(self.__externalRCRef(), module.__externalRCRef(), function.map { it in it.__externalRCRef() } ?? nil))
        }
    }
    public final class EncryptedCiphertext: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedCiphertext_Companion_get())
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
            public func fromBcs(
                bytes: ExportedKotlinPackages.kotlin.ByteArray
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedCiphertext_Companion_fromBcs__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef()))
            }
        }
        public var associatedData: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedCiphertext_associatedData_get(self.__externalRCRef()))
            }
        }
        public var bibeCiphertext: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BibeCiphertext {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.BibeCiphertext.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedCiphertext_bibeCiphertext_get(self.__externalRCRef()))
            }
        }
        public var signature: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes64 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes64.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedCiphertext_signature_get(self.__externalRCRef()))
            }
        }
        public var verificationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedCiphertext_verificationKey_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            verificationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32,
            bibeCiphertext: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BibeCiphertext,
            associatedData: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes64
        ) {
            let __kt = xyz_mcxross_kaptos_model_EncryptedCiphertext_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_EncryptedCiphertext_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32_ExportedKotlinPackages_xyz_mcxross_kaptos_model_BibeCiphertext_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes64__(__kt, verificationKey.__externalRCRef(), bibeCiphertext.__externalRCRef(), associatedData.__externalRCRef(), signature.__externalRCRef()); return () }()
        }
        public func copy(
            verificationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32,
            bibeCiphertext: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BibeCiphertext,
            associatedData: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedCiphertext_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32_ExportedKotlinPackages_xyz_mcxross_kaptos_model_BibeCiphertext_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ByteString_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes64__(self.__externalRCRef(), verificationKey.__externalRCRef(), bibeCiphertext.__externalRCRef(), associatedData.__externalRCRef(), signature.__externalRCRef()))
        }
        public func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedCiphertext_toBcs(self.__externalRCRef()))
        }
    }
    /// Batch-encryption wire record. Cryptographic construction lives in kaptos-encrypted-transactions.
    public final class EncryptedTransactionPayload: KotlinRuntime.KotlinBase {
        public var ciphertext: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedTransactionPayload_ciphertext_get(self.__externalRCRef()))
            }
        }
        public var claimedEntryFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction? {
            get {
                return { switch xyz_mcxross_kaptos_model_EncryptedTransactionPayload_claimedEntryFunction_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var encryptionEpoch: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_model_EncryptedTransactionPayload_encryptionEpoch_get(self.__externalRCRef())
            }
        }
        public var extraConfig: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig {
            get {
                return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedTransactionPayload_extraConfig_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig
            }
        }
        public var payloadHash: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedTransactionPayload_payloadHash_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            ciphertext: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext,
            extraConfig: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig,
            payloadHash: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32,
            encryptionEpoch: Swift.UInt64,
            claimedEntryFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction?
        ) {
            let __kt = xyz_mcxross_kaptos_model_EncryptedTransactionPayload_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_EncryptedTransactionPayload_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EncryptedCiphertext_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32_Swift_UInt64_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ClaimedEntryFunction___(__kt, ciphertext.__externalRCRef(), extraConfig.__externalRCRef(), payloadHash.__externalRCRef(), encryptionEpoch, claimedEntryFunction.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func copy(
            ciphertext: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedCiphertext,
            extraConfig: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig,
            payloadHash: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32,
            encryptionEpoch: Swift.UInt64,
            claimedEntryFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedTransactionPayload {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EncryptedTransactionPayload.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EncryptedTransactionPayload_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_EncryptedCiphertext_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionExtraConfig_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32_Swift_UInt64_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ClaimedEntryFunction___(self.__externalRCRef(), ciphertext.__externalRCRef(), extraConfig.__externalRCRef(), payloadHash.__externalRCRef(), encryptionEpoch, claimedEntryFunction.map { it in it.__externalRCRef() } ?? nil))
        }
    }
    open class EntryFunctionArgument: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptFunctionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EntryFunctionArgument_Companion_get())
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
        open func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument: .transactionArgument(value.sealedType())
            default: fatalError("missing sealedType for \(self)")
            }
        }
        @_disfavoredOverload
        public final override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptFunctionArgument_SealedType {
            .entryFunctionArgument(sealedType())
        }
    }
    /// Builder for constructing entry-function arguments with inferred [MoveArgument] conversions.
    public final class EntryFunctionArgumentBuilder: KotlinRuntime.KotlinBase {
        /// Builder for constructing entry-function arguments with inferred [MoveArgument] conversions.
        public override init() {
            let __kt = xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func arg(
            value: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> Swift.Void {
            return { xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder_arg__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func args(
            values: any ExportedKotlinPackages.kotlin.collections.Iterable
        ) -> Swift.Void {
            return { xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder_args__TypesOfArguments__anyU20ExportedKotlinPackages_kotlin_collections_Iterable__(self.__externalRCRef(), values.__externalRCRef()); return () }()
        }
        public func args(
            values: (any KotlinRuntimeSupport._KotlinBridgeable)?...
        ) -> Swift.Void {
            return { xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder_args__TypesOfArguments__Swift_Array_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___Vararg___(self.__externalRCRef(), values.map { it in it as! NSObject? ?? NSNull() }); return () }()
        }
        public func build() -> [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument] {
            return xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder_build(self.__externalRCRef()) as! Swift.Array<any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument>
        }
        public func unaryPlus(
            _ receiver: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> Swift.Void {
            return { xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder_unaryPlus__TypesOfArgumentsE__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), receiver.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
    }
    public final class EntryFunctionCall: KotlinRuntime.KotlinBase {
        public var arguments: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument] {
            get {
                return xyz_mcxross_kaptos_model_EntryFunctionCall_arguments_get(self.__externalRCRef()) as! Swift.Array<any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument>
            }
        }
        public var function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EntryFunctionCall_function_get(self.__externalRCRef()))
            }
        }
        public var module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EntryFunctionCall_module_get(self.__externalRCRef()))
            }
        }
        public var typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag] {
            get {
                return xyz_mcxross_kaptos_model_EntryFunctionCall_typeArguments_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag>
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId,
            function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier,
            typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag],
            arguments: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
        ) {
            let __kt = xyz_mcxross_kaptos_model_EntryFunctionCall_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_EntryFunctionCall_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ModuleId_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Identifier_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(__kt, module.__externalRCRef(), function.__externalRCRef(), typeArguments, arguments); return () }()
        }
        public func copy(
            module: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId,
            function: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier,
            typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag],
            arguments: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionCall.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_EntryFunctionCall_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_ModuleId_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Identifier_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), module.__externalRCRef(), function.__externalRCRef(), typeArguments, arguments))
        }
    }
    public final class Event: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Event_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var data: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement? {
            get {
                return { switch xyz_mcxross_kaptos_model_Event_data_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var guid: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Guid {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Guid.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Event_guid_get(self.__externalRCRef()))
            }
        }
        public var sequenceNumber: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_Event_sequenceNumber_get(self.__externalRCRef())
            }
        }
        public var type: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_Event_type_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            guid: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Guid,
            sequenceNumber: Swift.String?,
            type: Swift.String?,
            data: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement?
        ) {
            let __kt = xyz_mcxross_kaptos_model_Event_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_Event_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Guid_Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_kotlinx_serialization_json_JsonElement___(__kt, guid.__externalRCRef(), sequenceNumber ?? nil, type ?? nil, data.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func copy(
            guid: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Guid,
            sequenceNumber: Swift.String?,
            type: Swift.String?,
            data: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Event_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_Guid_Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_kotlinx_serialization_json_JsonElement___(self.__externalRCRef(), guid.__externalRCRef(), sequenceNumber ?? nil, type ?? nil, data.map { it in it.__externalRCRef() } ?? nil))
        }
    }
    /// Opaque BCS values accepted by an external Aptos Gas Station.
    /// 
    /// [transactionBytes] is the `RawTransactionWithData::FeePayer` value signed by the sender, not a
    /// partially assembled `SignedTransaction`. The external sponsor supplies its address and
    /// authenticator before submitting the final transaction.
    public final class ExternalFeePayerRequest: KotlinRuntime.KotlinBase {
        public var additionalSignersAuthenticatorBytes: [ExportedKotlinPackages.kotlin.ByteArray] {
            get {
                return xyz_mcxross_kaptos_model_ExternalFeePayerRequest_additionalSignersAuthenticatorBytes_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.kotlin.ByteArray>
            }
        }
        /// Stable client-side correlation value. This is not an on-chain transaction hash.
        public var fingerprint: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ExternalFeePayerRequest_fingerprint_get(self.__externalRCRef())
            }
        }
        public var senderAuthenticatorBytes: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ExternalFeePayerRequest_senderAuthenticatorBytes_get(self.__externalRCRef()))
            }
        }
        public var transactionBytes: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ExternalFeePayerRequest_transactionBytes_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            transactionBytes: ExportedKotlinPackages.kotlin.ByteArray,
            senderAuthenticatorBytes: ExportedKotlinPackages.kotlin.ByteArray,
            additionalSignersAuthenticatorBytes: [ExportedKotlinPackages.kotlin.ByteArray],
            fingerprint: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_ExternalFeePayerRequest_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_ExternalFeePayerRequest_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_kotlin_ByteArray_Swift_Array_ExportedKotlinPackages_kotlin_ByteArray__Swift_String__(__kt, transactionBytes.__externalRCRef(), senderAuthenticatorBytes.__externalRCRef(), additionalSignersAuthenticatorBytes, fingerprint); return () }()
        }
        public func copy(
            transactionBytes: ExportedKotlinPackages.kotlin.ByteArray,
            senderAuthenticatorBytes: ExportedKotlinPackages.kotlin.ByteArray,
            additionalSignersAuthenticatorBytes: [ExportedKotlinPackages.kotlin.ByteArray],
            fingerprint: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ExternalFeePayerRequest {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ExternalFeePayerRequest.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ExternalFeePayerRequest_copy__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_kotlin_ByteArray_Swift_Array_ExportedKotlinPackages_kotlin_ByteArray__Swift_String__(self.__externalRCRef(), transactionBytes.__externalRCRef(), senderAuthenticatorBytes.__externalRCRef(), additionalSignersAuthenticatorBytes, fingerprint))
        }
    }
    public final class FixedBytes: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_FixedBytes_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_FixedBytes_value_get(self.__externalRCRef()))
            }
        }
        public init(
            value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            let __kt = xyz_mcxross_kaptos_model_FixedBytes_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_FixedBytes_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(__kt, value.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_FixedBytes_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), value.__externalRCRef()))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .fixedBytes(.init(self))
        }
    }
    public final class FixedBytes12: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
        public override init(
            value: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_model_FixedBytes12_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_FixedBytes12_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, value.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
    }
    public final class FixedBytes16: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
        public override init(
            value: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_model_FixedBytes16_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_FixedBytes16_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, value.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
    }
    public final class FixedBytes32: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
        public override init(
            value: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_model_FixedBytes32_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_FixedBytes32_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, value.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
    }
    public final class FixedBytes64: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ByteString {
        public override init(
            value: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_model_FixedBytes64_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_FixedBytes64_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, value.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
    }
    public final class GenesisTransactionResponse: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.GenesisTransactionResponse.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.GenesisTransactionResponse.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_GenesisTransactionResponse_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var accumulatorRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_accumulatorRootHash_get(self.__externalRCRef())
            }
        }
        public var eventRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_eventRootHash_get(self.__externalRCRef())
            }
        }
        public var events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event] {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_events_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event>
            }
        }
        public var gasUsed: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_gasUsed_get(self.__externalRCRef())
            }
        }
        public var payload: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement {
            get {
                return ExportedKotlinPackages.kotlinx.serialization.json.JsonElement.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_GenesisTransactionResponse_payload_get(self.__externalRCRef()))
            }
        }
        public var stateChangeHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_stateChangeHash_get(self.__externalRCRef())
            }
        }
        public var stateCheckpointHash: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_stateCheckpointHash_get(self.__externalRCRef())
            }
        }
        public var success: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_success_get(self.__externalRCRef())
            }
        }
        public override var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_GenesisTransactionResponse_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var version: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_version_get(self.__externalRCRef())
            }
        }
        public var vmStatus: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_GenesisTransactionResponse_vmStatus_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            payload: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement,
            events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event]
        ) {
            let __kt = xyz_mcxross_kaptos_model_GenesisTransactionResponse_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_GenesisTransactionResponse_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_ExportedKotlinPackages_kotlinx_serialization_json_JsonElement_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Event___(__kt, type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, payload.__externalRCRef(), events); return () }()
        }
        public func copy(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            payload: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement,
            events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event]
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.GenesisTransactionResponse {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.GenesisTransactionResponse.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_GenesisTransactionResponse_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_ExportedKotlinPackages_kotlinx_serialization_json_JsonElement_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Event___(self.__externalRCRef(), type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, payload.__externalRCRef(), events))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse_SealedType {
            .genesisTransactionResponse(.init(self))
        }
    }
    public final class Guid: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Guid.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Guid.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Guid_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var accountAddress: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_Guid_accountAddress_get(self.__externalRCRef())
            }
        }
        public var creationNumber: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_Guid_creationNumber_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            creationNumber: Swift.String?,
            accountAddress: Swift.String?
        ) {
            let __kt = xyz_mcxross_kaptos_model_Guid_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_Guid_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String__Swift_Optional_Swift_String___(__kt, creationNumber ?? nil, accountAddress ?? nil); return () }()
        }
        public func copy(
            creationNumber: Swift.String?,
            accountAddress: Swift.String?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.Guid {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Guid.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Guid_copy__TypesOfArguments__Swift_Optional_Swift_String__Swift_Optional_Swift_String___(self.__externalRCRef(), creationNumber ?? nil, accountAddress ?? nil))
        }
    }
    public final class HexInput: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument, ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput, ExportedKotlinPackages.xyz.mcxross.kaptos.model.__AccountAddressInput {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_HexInput_Companion_get())
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
            public func fromByteArray(
                byteArray: ExportedKotlinPackages.kotlin.ByteArray
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_HexInput_Companion_fromByteArray__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), byteArray.__externalRCRef()))
            }
            public func fromString(
                string: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_HexInput_Companion_fromString__TypesOfArguments__Swift_String__(self.__externalRCRef(), string))
            }
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_HexInput_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_HexInput_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_HexInput_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_HexInput_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .hexInput(.init(self))
        }
        public func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_HexInput_toByteArray(self.__externalRCRef()))
        }
    }
    /// Representation of an Identifier that can serialized and deserialized. We use Identifier to
    /// represent the module "name" in "ModuleId" and the "function name" in "EntryFunction"
    public final class Identifier: KotlinRuntime.KotlinBase {
        /// Representation of an Identifier that can serialized and deserialized. We use Identifier to
        /// represent the module "name" in "ModuleId" and the "function name" in "EntryFunction"
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Identifier_Companion_get())
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
            /// Representation of an Identifier that can serialized and deserialized. We use Identifier to
            /// represent the module "name" in "ModuleId" and the "function name" in "EntryFunction"
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public init(
            value: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_Identifier_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_Identifier_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_Identifier_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
        }
    }
    /// Representation of a ModuleId that can serialized and deserialized ModuleId means the module
    /// address (e.g "0x1") and the module name (e.g "coin")
    public final class ModuleId: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ModuleId_Companion_get())
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
            public func fromString(
                moduleId: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveModuleId
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ModuleId_Companion_fromString__TypesOfArguments__Swift_String__(self.__externalRCRef(), moduleId))
            }
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ModuleId_address_get(self.__externalRCRef()))
            }
        }
        public var name: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ModuleId_name_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// - Parameters:
        ///   - address: The account address. e.g "0x1"
        ///   - name: The module name under the "address". e.g "coin"
        public init(
            address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            name: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier
        ) {
            let __kt = xyz_mcxross_kaptos_model_ModuleId_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_ModuleId_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Identifier__(__kt, address.__externalRCRef(), name.__externalRCRef()); return () }()
        }
        public func copy(
            address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            name: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Identifier
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ModuleId.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ModuleId_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Identifier__(self.__externalRCRef(), address.__externalRCRef(), name.__externalRCRef()))
        }
    }
    public final class MoveOption: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveOption.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveOption.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveOption_Companion_get())
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
            public func serializer(
                typeParamsSerializers: any ExportedKotlinPackages.kotlinx.serialization.KSerializer...
            ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
            public func serializer(
                typeSerial0: any ExportedKotlinPackages.kotlinx.serialization.KSerializer
            ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument? {
            get {
                return { switch xyz_mcxross_kaptos_model_MoveOption_value_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public init(
            value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument?
        ) {
            let __kt = xyz_mcxross_kaptos_model_MoveOption_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_MoveOption_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionArgument___(__kt, value.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveOption {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveOption.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveOption_copy__TypesOfArguments__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionArgument___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .moveOption(.init(self))
        }
        public func unwrap() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveOption_unwrap(self.__externalRCRef()))
        }
    }
    public final class MoveString: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveString.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveString.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveString_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_MoveString_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_MoveString_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_MoveString_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func bcsBytes() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveString_bcsBytes(self.__externalRCRef()))
        }
        public func copy(
            value: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveString {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveString.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveString_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .moveString(.init(self))
        }
    }
    /// This class is the Kotlin representation of a Move `vector<T>`, where `T` is a type that
    /// represents either a primitive type (`bool`, `u8`, `u64`, ...) or a BCS-serializable struct
    /// itself.
    /// 
    /// The `MoveVector` class is a BCS-serializable subclass of `TransactionArgument`, which is a
    /// superclass for all types that can be used as arguments in a transaction.
    /// 
    /// The purpose of this class is to facilitate easy construction of BCS-serializable Move `vector<T>`
    /// types.
    /// 
    /// ```kotlin
    /// val shorts = MoveVector.u16(listOf(1u, 2u, 3u))
    /// 
    /// val bools = MoveVector.bool(listOf(true, false, true))
    /// 
    /// val strings = MoveVector.string(listOf("hello", "world"))
    /// ```
    public final class MoveVector: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_Companion_get())
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
            /// Factory method to generate a MoveVector of boolean from an array of booleans.
            /// 
            /// ```kotlin
            /// val vector = MoveVector.bool(listOf(true, false, true))
            /// ```
            /// 
            /// - Parameters:
            ///   - value: booleans to convert to Move `bool` values
            /// 
            /// - Returns: a `MoveVector<Bool>`
            public func bool(
                value: [Swift.Bool]
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_Companion_bool__TypesOfArguments__Swift_Array_Swift_Bool___(self.__externalRCRef(), value.map { it in NSNumber(value: it) }))
            }
            public func serializer(
                typeParamsSerializers: any ExportedKotlinPackages.kotlinx.serialization.KSerializer...
            ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
            public func serializer(
                typeSerial0: any ExportedKotlinPackages.kotlinx.serialization.KSerializer
            ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
            public func string(
                value: [Swift.String]
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_Companion_string__TypesOfArguments__Swift_Array_Swift_String___(self.__externalRCRef(), value))
            }
            /// Factory method to generate a MoveVector of U16s from an array of numbers.
            /// 
            /// ```kotlin
            /// val vector = MoveVector.u16(listOf(1u, 2u, 3u))
            /// ```
            /// 
            /// - Parameters:
            ///   - value: numbers to convert to U16 values
            /// 
            /// - Returns: a `MoveVector<U16>`
            public func u16(
                value: [Swift.UInt16]
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_Companion_u16__TypesOfArguments__Swift_Array_Swift_UInt16___(self.__externalRCRef(), value.map { it in NSNumber(value: it) }))
            }
            /// Factory method to generate a MoveVector of U32s from an array of numbers.
            /// 
            /// ```kotlin
            /// val vector = MoveVector.u32(listOf(1u, 2u, 3u))
            /// ```
            /// 
            /// - Parameters:
            ///   - value: numbers to convert to U32 values
            /// 
            /// - Returns: a `MoveVector<U32>`
            public func u32(
                value: [Swift.UInt32]
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_Companion_u32__TypesOfArguments__Swift_Array_Swift_UInt32___(self.__externalRCRef(), value.map { it in NSNumber(value: it) }))
            }
            /// Factory method to generate a MoveVector of U64s from an array of numbers.
            /// 
            /// ```kotlin
            /// val vector = MoveVector.u64(listOf(1u, 2u, 3u))
            /// ```
            /// 
            /// - Parameters:
            ///   - value: numbers to convert to U64 values
            /// 
            /// - Returns: a `MoveVector<U64>`
            public func u64(
                value: [Swift.UInt64]
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_Companion_u64__TypesOfArguments__Swift_Array_Swift_UInt64___(self.__externalRCRef(), value.map { it in NSNumber(value: it) }))
            }
            public func u8(
                value: ExportedKotlinPackages.kotlin.ByteArray
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_Companion_u8__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), value.__externalRCRef()))
            }
            /// Factory method to generate a MoveVector of U8s from an array of numbers.
            /// 
            /// - returns: a `MoveVector<U8>`
            public func u8(
                value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_Companion_u8__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput__(self.__externalRCRef(), value.__externalRCRef()))
            }
        }
        /// : a list of `T` values that the `MoveVector` will contain
        public var values: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument] {
            get {
                return xyz_mcxross_kaptos_model_MoveVector_values_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument>
            }
            set {
                return { xyz_mcxross_kaptos_model_MoveVector_values_set__TypesOfArguments__Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionArgument___(self.__externalRCRef(), newValue); return () }()
            }
        }
        /// - Parameters:
        ///   - values: : a list of `T` values that the `MoveVector` will contain
        public init(
            values: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument]
        ) {
            let __kt = xyz_mcxross_kaptos_model_MoveVector_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_MoveVector_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionArgument___(__kt, values); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            values: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument]
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_copy__TypesOfArguments__Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionArgument___(self.__externalRCRef(), values))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .moveVector(.init(self))
        }
        public func serialize() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_MoveVector_serialize(self.__externalRCRef()))
        }
    }
    /// Kotlin-native offset pagination shared by indexer-backed services.
    public final class PageRequest: KotlinRuntime.KotlinBase {
        public var limit: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_PageRequest_limit_get(self.__externalRCRef())
            }
        }
        public var offset: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_PageRequest_offset_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            offset: Swift.Int32,
            limit: Swift.Int32
        ) {
            let __kt = xyz_mcxross_kaptos_model_PageRequest_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_PageRequest_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32_Swift_Int32__(__kt, offset, limit); return () }()
        }
        public func copy(
            offset: Swift.Int32,
            limit: Swift.Int32
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_PageRequest_copy__TypesOfArguments__Swift_Int32_Swift_Int32__(self.__externalRCRef(), offset, limit))
        }
    }
    public final class PendingTransactionResponse: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_PendingTransactionResponse_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var expirationTimestampSecs: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_PendingTransactionResponse_expirationTimestampSecs_get(self.__externalRCRef())
            }
        }
        public var gasUnitPrice: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_PendingTransactionResponse_gasUnitPrice_get(self.__externalRCRef())
            }
        }
        public var maxGasAmount: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_PendingTransactionResponse_maxGasAmount_get(self.__externalRCRef())
            }
        }
        public var sender: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_PendingTransactionResponse_sender_get(self.__externalRCRef())
            }
        }
        public var sequenceNumber: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_PendingTransactionResponse_sequenceNumber_get(self.__externalRCRef())
            }
        }
        public override var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_PendingTransactionResponse_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            hash: Swift.String,
            sender: Swift.String,
            sequenceNumber: Swift.String,
            maxGasAmount: Swift.String,
            gasUnitPrice: Swift.String,
            expirationTimestampSecs: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_PendingTransactionResponse_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_PendingTransactionResponse_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String__(__kt, type.__externalRCRef(), hash, sender, sequenceNumber, maxGasAmount, gasUnitPrice, expirationTimestampSecs); return () }()
        }
        public func copy(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            hash: Swift.String,
            sender: Swift.String,
            sequenceNumber: Swift.String,
            maxGasAmount: Swift.String,
            gasUnitPrice: Swift.String,
            expirationTimestampSecs: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_PendingTransactionResponse_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String__(self.__externalRCRef(), type.__externalRCRef(), hash, sender, sequenceNumber, maxGasAmount, gasUnitPrice, expirationTimestampSecs))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse_SealedType {
            .pendingTransactionResponse(.init(self))
        }
    }
    open class ScriptFunctionArgument: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptFunctionArgument.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptFunctionArgument.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ScriptFunctionArgument_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
            public func serializer(
                typeParamsSerializers: any ExportedKotlinPackages.kotlinx.serialization.KSerializer...
            ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            seen0: Swift.Int32,
            serializationConstructorMarker: ExportedKotlinPackages.kotlinx.serialization.`internal`.SerializationConstructorMarker?
        ) {
            fatalError()
        }
        open func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ScriptFunctionArgument_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument: .entryFunctionArgument(value.sealedType())
            default: fatalError("missing sealedType for \(self)")
            }
        }
    }
    public final class ScriptPayload: KotlinRuntime.KotlinBase {
        public var arguments: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument] {
            get {
                return xyz_mcxross_kaptos_model_ScriptPayload_arguments_get(self.__externalRCRef()) as! Swift.Array<any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument>
            }
        }
        public var bytecode: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ScriptPayload_bytecode_get(self.__externalRCRef()))
            }
        }
        public var typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag] {
            get {
                return xyz_mcxross_kaptos_model_ScriptPayload_typeArguments_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag>
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            bytecode: ExportedKotlinPackages.kotlin.ByteArray,
            typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag],
            arguments: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
        ) {
            let __kt = xyz_mcxross_kaptos_model_ScriptPayload_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_ScriptPayload_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(__kt, bytecode.__externalRCRef(), typeArguments, arguments); return () }()
        }
    }
    /// Controls the estimates returned by transaction simulation.
    public final class SimulationOptions: KotlinRuntime.KotlinBase {
        public var estimateGasUnitPrice: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_SimulationOptions_estimateGasUnitPrice_get(self.__externalRCRef())
            }
        }
        public var estimateMaxGasAmount: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_SimulationOptions_estimateMaxGasAmount_get(self.__externalRCRef())
            }
        }
        public var estimatePrioritizedGasUnitPrice: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_SimulationOptions_estimatePrioritizedGasUnitPrice_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            estimateGasUnitPrice: Swift.Bool,
            estimateMaxGasAmount: Swift.Bool,
            estimatePrioritizedGasUnitPrice: Swift.Bool
        ) {
            let __kt = xyz_mcxross_kaptos_model_SimulationOptions_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_SimulationOptions_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool_Swift_Bool_Swift_Bool__(__kt, estimateGasUnitPrice, estimateMaxGasAmount, estimatePrioritizedGasUnitPrice); return () }()
        }
        public func copy(
            estimateGasUnitPrice: Swift.Bool,
            estimateMaxGasAmount: Swift.Bool,
            estimatePrioritizedGasUnitPrice: Swift.Bool
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.SimulationOptions {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.SimulationOptions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_SimulationOptions_copy__TypesOfArguments__Swift_Bool_Swift_Bool_Swift_Bool__(self.__externalRCRef(), estimateGasUnitPrice, estimateMaxGasAmount, estimatePrioritizedGasUnitPrice))
        }
    }
    public final class StateCheckpointTransactionResponse: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var accumulatorRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_accumulatorRootHash_get(self.__externalRCRef())
            }
        }
        public var eventRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_eventRootHash_get(self.__externalRCRef())
            }
        }
        public var gasUsed: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_gasUsed_get(self.__externalRCRef())
            }
        }
        public var stateChangeHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_stateChangeHash_get(self.__externalRCRef())
            }
        }
        public var stateCheckpointHash: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_stateCheckpointHash_get(self.__externalRCRef())
            }
        }
        public var success: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_success_get(self.__externalRCRef())
            }
        }
        public var timestamp: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_timestamp_get(self.__externalRCRef())
            }
        }
        public override var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var version: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_version_get(self.__externalRCRef())
            }
        }
        public var vmStatus: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_vmStatus_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            timestamp: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_String__(__kt, type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, timestamp); return () }()
        }
        public func copy(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            timestamp: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_StateCheckpointTransactionResponse_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_String__(self.__externalRCRef(), type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, timestamp))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse_SealedType {
            .stateCheckpointTransactionResponse(.init(self))
        }
    }
    public final class StructTag: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_StructTag_Companion_get())
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
            public func fromString(
                string: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_StructTag_Companion_fromString__TypesOfArguments__Swift_String__(self.__externalRCRef(), string))
            }
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_StructTag_address_get(self.__externalRCRef()))
            }
        }
        public var moduleName: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StructTag_moduleName_get(self.__externalRCRef())
            }
        }
        public var name: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_StructTag_name_get(self.__externalRCRef())
            }
        }
        public var typeArgs: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag] {
            get {
                return xyz_mcxross_kaptos_model_StructTag_typeArgs_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag>
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            moduleName: Swift.String,
            name: Swift.String,
            typeArgs: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag]
        ) {
            let __kt = xyz_mcxross_kaptos_model_StructTag_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_StructTag_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag___(__kt, address.__externalRCRef(), moduleName, name, typeArgs); return () }()
        }
    }
    open class TransactionArgument: ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionArgument_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
            public func serializer(
                typeParamsSerializers: any ExportedKotlinPackages.kotlinx.serialization.KSerializer...
            ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            seen0: Swift.Int32,
            serializationConstructorMarker: ExportedKotlinPackages.kotlinx.serialization.`internal`.SerializationConstructorMarker?
        ) {
            fatalError()
        }
        open func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress: .accountAddress(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.Bool: .bool(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes: .fixedBytes(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput: .hexInput(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveOption: .moveOption(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveString: .moveString(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector: .moveVector(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.U128: .u128(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.U16: .u16(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.U256: .u256(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.U32: .u32(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.U64: .u64(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.U8: .u8(.init(value))
            default: fatalError("missing sealedType for \(self)")
            }
        }
        @_disfavoredOverload
        public final override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgument_SealedType {
            .transactionArgument(sealedType())
        }
    }
    /// Immutable options used when building a transaction.
    public final class TransactionOptions: KotlinRuntime.KotlinBase {
        public var expirationSecondsFromNow: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_model_TransactionOptions_expirationSecondsFromNow_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var expirationTimestampSecs: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_model_TransactionOptions_expirationTimestampSecs_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var gasUnitPrice: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_model_TransactionOptions_gasUnitPrice_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        /// Null inherits the value configured on [xyz.mcxross.kaptos.Aptos].
        public var maxGasAmount: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_model_TransactionOptions_maxGasAmount_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var replayProtection: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection)? {
            get {
                return { switch xyz_mcxross_kaptos_model_TransactionOptions_replayProtection_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection; } }()
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            maxGasAmount: Swift.UInt64?,
            gasUnitPrice: Swift.UInt64?,
            expirationTimestampSecs: Swift.UInt64?,
            expirationSecondsFromNow: Swift.UInt64?,
            replayProtection: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection)?
        ) {
            let __kt = xyz_mcxross_kaptos_model_TransactionOptions_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_TransactionOptions_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection___(__kt, maxGasAmount.map { it in NSNumber(value: it) } ?? nil, gasUnitPrice.map { it in NSNumber(value: it) } ?? nil, expirationTimestampSecs.map { it in NSNumber(value: it) } ?? nil, expirationSecondsFromNow.map { it in NSNumber(value: it) } ?? nil, replayProtection.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func copy(
            maxGasAmount: Swift.UInt64?,
            gasUnitPrice: Swift.UInt64?,
            expirationTimestampSecs: Swift.UInt64?,
            expirationSecondsFromNow: Swift.UInt64?,
            replayProtection: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.ReplayProtection)?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionOptions_copy__TypesOfArguments__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_ReplayProtection___(self.__externalRCRef(), maxGasAmount.map { it in NSNumber(value: it) } ?? nil, gasUnitPrice.map { it in NSNumber(value: it) } ?? nil, expirationTimestampSecs.map { it in NSNumber(value: it) } ?? nil, expirationSecondsFromNow.map { it in NSNumber(value: it) } ?? nil, replayProtection.map { it in it.__externalRCRef() } ?? nil))
        }
    }
    open class TransactionResponse: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TransactionResponse_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
            public func serializer(
                typeParamsSerializers: any ExportedKotlinPackages.kotlinx.serialization.KSerializer...
            ) -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        open var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType {
            get {
                if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse.self {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_TransactionResponse_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
                } else {
                    fatalError("Cannot invoke the inherited implementation of abstract property 'ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse.type': a Swift subclass must override it and must not call super.")
                }
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            seen0: Swift.Int32,
            serializationConstructorMarker: ExportedKotlinPackages.kotlinx.serialization.`internal`.SerializationConstructorMarker?
        ) {
            fatalError()
        }
        open func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse: .blockEpilogueTransactionResponse(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse: .blockMetadataTransactionResponse(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.GenesisTransactionResponse: .genesisTransactionResponse(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse: .pendingTransactionResponse(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse: .stateCheckpointTransactionResponse(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse: .userTransactionResponse(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.ValidatorTransactionResponse: .validatorTransactionResponse(.init(value))
            default: fatalError("missing sealedType for \(self)")
            }
        }
    }
    open class TypeTag: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public var APT: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_APT_get(self.__externalRCRef()))
                }
            }
            public var Address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_Address_get(self.__externalRCRef()))
                }
            }
            public var AptosCoin: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_AptosCoin_get(self.__externalRCRef()))
                }
            }
            public var Bool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_Bool_get(self.__externalRCRef()))
                }
            }
            public var I128: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_I128_get(self.__externalRCRef()))
                }
            }
            public var I16: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_I16_get(self.__externalRCRef()))
                }
            }
            public var I256: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_I256_get(self.__externalRCRef()))
                }
            }
            public var I32: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_I32_get(self.__externalRCRef()))
                }
            }
            public var I64: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_I64_get(self.__externalRCRef()))
                }
            }
            public var I8: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_I8_get(self.__externalRCRef()))
                }
            }
            public var Signer: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_Signer_get(self.__externalRCRef()))
                }
            }
            public var String: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_String_get(self.__externalRCRef()))
                }
            }
            public var U128: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_U128_get(self.__externalRCRef()))
                }
            }
            public var U16: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_U16_get(self.__externalRCRef()))
                }
            }
            public var U256: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_U256_get(self.__externalRCRef()))
                }
            }
            public var U32: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_U32_get(self.__externalRCRef()))
                }
            }
            public var U64: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_U64_get(self.__externalRCRef()))
                }
            }
            public var U8: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_U8_get(self.__externalRCRef()))
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_get())
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
            /// Parses [string] into a [TypeTag], allowing invoke syntax: `TypeTag("0x1::coin::Coin")`.
            public func callAsFunction(
                string: Swift.String,
                allowGenerics: Swift.Bool
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_invoke__TypesOfArguments__Swift_String_Swift_Bool__(self.__externalRCRef(), string, allowGenerics))
            }
            /// Converts [value] to [TypeTag] from supported types ([TypeTag], [StructTag], [String],
            /// [MoveType]).
            public func from(
                value: (any KotlinRuntimeSupport._KotlinBridgeable)?
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_from__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil))
            }
            /// Converts each value in [values] to a [TypeTag].
            public func fromAll(
                values: any ExportedKotlinPackages.kotlin.collections.Iterable
            ) -> [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag] {
                return xyz_mcxross_kaptos_model_TypeTag_Companion_fromAll__TypesOfArguments__anyU20ExportedKotlinPackages_kotlin_collections_Iterable__(self.__externalRCRef(), values.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag>
            }
            /// Converts each value in [values] to a [TypeTag].
            public func fromAll(
                values: (any KotlinRuntimeSupport._KotlinBridgeable)?...
            ) -> [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag] {
                return xyz_mcxross_kaptos_model_TypeTag_Companion_fromAll__TypesOfArguments__Swift_Array_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___Vararg___(self.__externalRCRef(), values.map { it in it as! NSObject? ?? NSNull() }) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag>
            }
            public func fromString(
                string: Swift.String,
                allowGenerics: Swift.Bool
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_fromString__TypesOfArguments__Swift_String_Swift_Bool__(self.__externalRCRef(), string, allowGenerics))
            }
            public func objectTag(
                elementType: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_objectTag__TypesOfArguments__Swift_String__(self.__externalRCRef(), elementType))
            }
            public func objectTag(
                elementType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_objectTag__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__(self.__externalRCRef(), elementType.__externalRCRef()))
            }
            public func option(
                elementType: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_option__TypesOfArguments__Swift_String__(self.__externalRCRef(), elementType))
            }
            public func option(
                elementType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_option__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__(self.__externalRCRef(), elementType.__externalRCRef()))
            }
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
            public func `struct`(
                type: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_struct__TypesOfArguments__Swift_String__(self.__externalRCRef(), type))
            }
            public func `struct`(
                type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_struct__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag__(self.__externalRCRef(), type.__externalRCRef()))
            }
            public func valueOf(
                string: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_valueOf__TypesOfArguments__Swift_String__(self.__externalRCRef(), string))
            }
            public func vector(
                elementType: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_vector__TypesOfArguments__Swift_String__(self.__externalRCRef(), elementType))
            }
            public func vector(
                elementType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTag_Companion_vector__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__(self.__externalRCRef(), elementType.__externalRCRef()))
            }
        }
        open var value: Swift.String {
            get {
                if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.self {
                    return xyz_mcxross_kaptos_model_TypeTag_value_get(self.__externalRCRef())
                } else {
                    fatalError("Cannot invoke the inherited implementation of abstract property 'ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.value': a Swift subclass must override it and must not call super.")
                }
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public final func isAddress() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isAddress(self.__externalRCRef())
        }
        public final func isBool() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isBool(self.__externalRCRef())
        }
        public final func isGeneric() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isGeneric(self.__externalRCRef())
        }
        public final func isReference() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isReference(self.__externalRCRef())
        }
        public final func isSignedInteger() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isSignedInteger(self.__externalRCRef())
        }
        public final func isSigner() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isSigner(self.__externalRCRef())
        }
        public final func isStruct() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isStruct(self.__externalRCRef())
        }
        public final func isU128() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isU128(self.__externalRCRef())
        }
        public final func isU16() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isU16(self.__externalRCRef())
        }
        public final func isU256() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isU256(self.__externalRCRef())
        }
        public final func isU32() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isU32(self.__externalRCRef())
        }
        public final func isU64() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isU64(self.__externalRCRef())
        }
        public final func isU8() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isU8(self.__externalRCRef())
        }
        public final func isVector() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTag_isVector(self.__externalRCRef())
        }
        open func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            switch self {
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagAddress: .typeTagAddress(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagBool: .typeTagBool(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagGeneric: .typeTagGeneric(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI128: .typeTagI128(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI16: .typeTagI16(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI256: .typeTagI256(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI32: .typeTagI32(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI64: .typeTagI64(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI8: .typeTagI8(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagReference: .typeTagReference(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagSigner: .typeTagSigner(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct: .typeTagStruct(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU128: .typeTagU128(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU16: .typeTagU16(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU256: .typeTagU256(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU32: .typeTagU32(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU64: .typeTagU64(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU8: .typeTagU8(.init(value))
            case let value as ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector: .typeTagVector(.init(value))
            default: fatalError("missing sealedType for \(self)")
            }
        }
    }
    public final class TypeTagAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagAddress_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagAddress_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagAddress(.init(self))
        }
    }
    public final class TypeTagBool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagBool {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagBool.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagBool_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagBool_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagBool(.init(self))
        }
    }
    public final class TypeTagGeneric: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public var id: Swift.UInt16 {
            get {
                return xyz_mcxross_kaptos_model_TypeTagGeneric_id_get(self.__externalRCRef())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagGeneric_value_get(self.__externalRCRef())
            }
        }
        public init(
            id: Swift.UInt16
        ) {
            let __kt = xyz_mcxross_kaptos_model_TypeTagGeneric_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_TypeTagGeneric_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt16__(__kt, id); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagGeneric(.init(self))
        }
    }
    public final class TypeTagI128: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI128 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI128.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagI128_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagI128_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagI128(.init(self))
        }
    }
    public final class TypeTagI16: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI16 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI16.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagI16_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagI16_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagI16(.init(self))
        }
    }
    public final class TypeTagI256: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI256 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI256.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagI256_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagI256_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagI256(.init(self))
        }
    }
    public final class TypeTagI32: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI32 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI32.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagI32_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagI32_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagI32(.init(self))
        }
    }
    public final class TypeTagI64: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI64 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI64.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagI64_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagI64_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagI64(.init(self))
        }
    }
    public final class TypeTagI8: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI8 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI8.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagI8_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagI8_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagI8(.init(self))
        }
    }
    public final class TypeTagReference: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public var ref: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagReference_ref_get(self.__externalRCRef()))
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagReference_value_get(self.__externalRCRef())
            }
        }
        public init(
            ref: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag
        ) {
            let __kt = xyz_mcxross_kaptos_model_TypeTagReference_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_TypeTagReference_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__(__kt, ref.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagReference(.init(self))
        }
    }
    public final class TypeTagSigner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagSigner {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagSigner.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagSigner_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagSigner_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagSigner(.init(self))
        }
    }
    public final class TypeTagStruct: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagStruct_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagStruct_type_get(self.__externalRCRef()))
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagStruct_value_get(self.__externalRCRef())
            }
        }
        public init(
            type: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_TypeTagStruct_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_TypeTagStruct_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, type); return () }()
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag
        ) {
            let __kt = xyz_mcxross_kaptos_model_TypeTagStruct_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_TypeTagStruct_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag__(__kt, type.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func isString() -> Swift.Bool {
            return xyz_mcxross_kaptos_model_TypeTagStruct_isString(self.__externalRCRef())
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagStruct(.init(self))
        }
    }
    public final class TypeTagU128: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU128 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU128.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagU128_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagU128_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagU128(.init(self))
        }
    }
    public final class TypeTagU16: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU16 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU16.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagU16_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagU16_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagU16(.init(self))
        }
    }
    public final class TypeTagU256: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU256 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU256.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagU256_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagU256_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagU256(.init(self))
        }
    }
    public final class TypeTagU32: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU32 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU32.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagU32_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagU32_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagU32(.init(self))
        }
    }
    public final class TypeTagU64: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU64 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU64.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagU64_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagU64_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagU64(.init(self))
        }
    }
    public final class TypeTagU8: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU8 {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU8.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagU8_get())
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagU8_value_get(self.__externalRCRef())
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
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagU8(.init(self))
        }
    }
    public final class TypeTagVector: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagVector_Companion_get())
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
            public func u8() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagVector_Companion_u8(self.__externalRCRef()))
            }
        }
        public var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_TypeTagVector_type_get(self.__externalRCRef()))
            }
        }
        public override var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_TypeTagVector_value_get(self.__externalRCRef())
            }
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag
        ) {
            let __kt = xyz_mcxross_kaptos_model_TypeTagVector_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_TypeTagVector_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__(__kt, type.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag_SealedType {
            .typeTagVector(.init(self))
        }
    }
    public final class U128: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U128.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U128.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U128_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_U128_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_U128_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_U128_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.U128 {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U128.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U128_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .u128(.init(self))
        }
    }
    public final class U16: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U16.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U16.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U16_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.UInt16 {
            get {
                return xyz_mcxross_kaptos_model_U16_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.UInt16
        ) {
            let __kt = xyz_mcxross_kaptos_model_U16_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_U16_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt16__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.UInt16
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.U16 {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U16.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U16_copy__TypesOfArguments__Swift_UInt16__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .u16(.init(self))
        }
    }
    public final class U256: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U256.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U256.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U256_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_U256_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_U256_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_U256_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.U256 {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U256.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U256_copy__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .u256(.init(self))
        }
    }
    public final class U32: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U32.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U32.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U32_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.UInt32 {
            get {
                return xyz_mcxross_kaptos_model_U32_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.UInt32
        ) {
            let __kt = xyz_mcxross_kaptos_model_U32_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_U32_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt32__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.UInt32
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.U32 {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U32.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U32_copy__TypesOfArguments__Swift_UInt32__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .u32(.init(self))
        }
    }
    public final class U64: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U64.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U64.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U64_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_model_U64_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_model_U64_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_U64_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.U64 {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U64.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U64_copy__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .u64(.init(self))
        }
    }
    public final class U8: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U8.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U8.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U8_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var value: Swift.Int8 {
            get {
                return xyz_mcxross_kaptos_model_U8_value_get(self.__externalRCRef())
            }
        }
        public init(
            value: Swift.Int8
        ) {
            let __kt = xyz_mcxross_kaptos_model_U8_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_U8_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int8__(__kt, value); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            value: Swift.Int8
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.U8 {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.U8.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_U8_copy__TypesOfArguments__Swift_Int8__(self.__externalRCRef(), value))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionArgument_SealedType {
            .u8(.init(self))
        }
    }
    public final class UserTransactionResponse: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UserTransactionResponse_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var accumulatorRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_accumulatorRootHash_get(self.__externalRCRef())
            }
        }
        public var eventRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_eventRootHash_get(self.__externalRCRef())
            }
        }
        public var events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event] {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_events_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event>
            }
        }
        public var expirationTimestampSecs: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_expirationTimestampSecs_get(self.__externalRCRef())
            }
        }
        public var gasUnitPrice: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_gasUnitPrice_get(self.__externalRCRef())
            }
        }
        public var gasUsed: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_gasUsed_get(self.__externalRCRef())
            }
        }
        public var maxGasAmount: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_maxGasAmount_get(self.__externalRCRef())
            }
        }
        public var sender: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_sender_get(self.__externalRCRef())
            }
        }
        public var sequenceNumber: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_sequenceNumber_get(self.__externalRCRef())
            }
        }
        public var stateChangeHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_stateChangeHash_get(self.__externalRCRef())
            }
        }
        public var statecCheckpointHash: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_statecCheckpointHash_get(self.__externalRCRef())
            }
        }
        public var success: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_success_get(self.__externalRCRef())
            }
        }
        public var timestamp: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_timestamp_get(self.__externalRCRef())
            }
        }
        public override var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_UserTransactionResponse_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var version: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_version_get(self.__externalRCRef())
            }
        }
        public var vmStatus: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_UserTransactionResponse_vmStatus_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            statecCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            sender: Swift.String,
            sequenceNumber: Swift.String,
            maxGasAmount: Swift.String,
            gasUnitPrice: Swift.String,
            expirationTimestampSecs: Swift.String,
            events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event],
            timestamp: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_model_UserTransactionResponse_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_UserTransactionResponse_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Event__Swift_String__(__kt, type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, statecCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, sender, sequenceNumber, maxGasAmount, gasUnitPrice, expirationTimestampSecs, events, timestamp); return () }()
        }
        public func copy(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            statecCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            sender: Swift.String,
            sequenceNumber: Swift.String,
            maxGasAmount: Swift.String,
            gasUnitPrice: Swift.String,
            expirationTimestampSecs: Swift.String,
            events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event],
            timestamp: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_UserTransactionResponse_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Event__Swift_String__(self.__externalRCRef(), type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, statecCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, sender, sequenceNumber, maxGasAmount, gasUnitPrice, expirationTimestampSecs, events, timestamp))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse_SealedType {
            .userTransactionResponse(.init(self))
        }
    }
    public final class ValidatorTransactionResponse: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ValidatorTransactionResponse.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ValidatorTransactionResponse.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ValidatorTransactionResponse_Companion_get())
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
            public func serializer() -> any ExportedKotlinPackages.kotlinx.serialization.KSerializer {
                fatalError()
            }
        }
        public var accumulatorRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_accumulatorRootHash_get(self.__externalRCRef())
            }
        }
        public var dkgTranscript: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement? {
            get {
                return { switch xyz_mcxross_kaptos_model_ValidatorTransactionResponse_dkgTranscript_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var eventRootHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_eventRootHash_get(self.__externalRCRef())
            }
        }
        public var events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event] {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_events_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event>
            }
        }
        public var gasUsed: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_gasUsed_get(self.__externalRCRef())
            }
        }
        public var stateChangeHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_stateChangeHash_get(self.__externalRCRef())
            }
        }
        public var stateCheckpointHash: Swift.String? {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_stateCheckpointHash_get(self.__externalRCRef())
            }
        }
        public var success: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_success_get(self.__externalRCRef())
            }
        }
        public var timestamp: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_timestamp_get(self.__externalRCRef())
            }
        }
        public override var type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType(__externalRCRefUnsafe: xyz_mcxross_kaptos_model_ValidatorTransactionResponse_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var validatorTransactionType: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_validatorTransactionType_get(self.__externalRCRef())
            }
        }
        public var version: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_version_get(self.__externalRCRef())
            }
        }
        public var vmStatus: Swift.String {
            get {
                return xyz_mcxross_kaptos_model_ValidatorTransactionResponse_vmStatus_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event],
            timestamp: Swift.String,
            validatorTransactionType: Swift.String,
            dkgTranscript: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement?
        ) {
            let __kt = xyz_mcxross_kaptos_model_ValidatorTransactionResponse_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_ValidatorTransactionResponse_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Event__Swift_String_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlinx_serialization_json_JsonElement___(__kt, type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, events, timestamp, validatorTransactionType, dkgTranscript.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func copy(
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType,
            version: Swift.String,
            hash: Swift.String,
            stateChangeHash: Swift.String,
            eventRootHash: Swift.String,
            stateCheckpointHash: Swift.String?,
            gasUsed: Swift.String,
            success: Swift.Bool,
            vmStatus: Swift.String,
            accumulatorRootHash: Swift.String,
            events: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.Event],
            timestamp: Swift.String,
            validatorTransactionType: Swift.String,
            dkgTranscript: ExportedKotlinPackages.kotlinx.serialization.json.JsonElement?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ValidatorTransactionResponse {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ValidatorTransactionResponse.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_ValidatorTransactionResponse_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponseType_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_Bool_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Event__Swift_String_Swift_String_Swift_Optional_ExportedKotlinPackages_kotlinx_serialization_json_JsonElement___(self.__externalRCRef(), type.__externalRCRef(), version, hash, stateChangeHash, eventRootHash, stateCheckpointHash ?? nil, gasUsed, success, vmStatus, accumulatorRootHash, events, timestamp, validatorTransactionType, dkgTranscript.map { it in it.__externalRCRef() } ?? nil))
        }
        public override func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse_SealedType {
            .validatorTransactionResponse(.init(self))
        }
    }
    /// Options for waiting for a transaction to be finalized.
    public final class WaitForTransactionOptions: KotlinRuntime.KotlinBase {
        public var checkSuccess: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_model_WaitForTransactionOptions_checkSuccess_get(self.__externalRCRef())
            }
        }
        public var timeoutSecs: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_model_WaitForTransactionOptions_timeoutSecs_get(self.__externalRCRef())
            }
        }
        public var waitForIndexer: Swift.Bool? {
            get {
                return xyz_mcxross_kaptos_model_WaitForTransactionOptions_waitForIndexer_get(self.__externalRCRef()).map { it in it.boolValue }
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// - Parameters:
        ///   - timeoutSecs: The maximum number of seconds to wait for the transaction to be finalized.
        ///   - checkSuccess: If true, the method will return an error if the transaction is not
        ///     successful.
        ///   - waitForIndexer: If true, the method will wait for the transaction to be indexed by the
        ///     indexer.
        public init(
            timeoutSecs: Swift.Int32,
            checkSuccess: Swift.Bool,
            waitForIndexer: Swift.Bool?
        ) {
            let __kt = xyz_mcxross_kaptos_model_WaitForTransactionOptions_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_model_WaitForTransactionOptions_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32_Swift_Bool_Swift_Optional_Swift_Bool___(__kt, timeoutSecs, checkSuccess, waitForIndexer.map { it in NSNumber(value: it) } ?? nil); return () }()
        }
        public func copy(
            timeoutSecs: Swift.Int32,
            checkSuccess: Swift.Bool,
            waitForIndexer: Swift.Bool?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_model_WaitForTransactionOptions_copy__TypesOfArguments__Swift_Int32_Swift_Bool_Swift_Optional_Swift_Bool___(self.__externalRCRef(), timeoutSecs, checkSuccess, waitForIndexer.map { it in NSNumber(value: it) } ?? nil))
        }
    }
    public struct AccountAddress_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
        ) {
            self.value = value
        }
    }
    public struct BlockEpilogueTransactionResponse_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockEpilogueTransactionResponse
        ) {
            self.value = value
        }
    }
    public struct BlockMetadataTransactionResponse_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.BlockMetadataTransactionResponse
        ) {
            self.value = value
        }
    }
    public struct Bool_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Bool
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Bool
        ) {
            self.value = value
        }
    }
    public struct FixedBytes_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes
        ) {
            self.value = value
        }
    }
    public struct GenesisTransactionResponse_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.GenesisTransactionResponse
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.GenesisTransactionResponse
        ) {
            self.value = value
        }
    }
    public struct HexInput_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput
        ) {
            self.value = value
        }
    }
    public struct MoveOption_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveOption
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveOption
        ) {
            self.value = value
        }
    }
    public struct MoveString_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveString
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveString
        ) {
            self.value = value
        }
    }
    public struct MoveVector_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.MoveVector
        ) {
            self.value = value
        }
    }
    public struct PendingTransactionResponse_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse
        ) {
            self.value = value
        }
    }
    public struct StateCheckpointTransactionResponse_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StateCheckpointTransactionResponse
        ) {
            self.value = value
        }
    }
    public struct TypeTagAddress_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagAddress
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagAddress
        ) {
            self.value = value
        }
    }
    public struct TypeTagBool_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagBool
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagBool
        ) {
            self.value = value
        }
    }
    public struct TypeTagGeneric_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagGeneric
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagGeneric
        ) {
            self.value = value
        }
    }
    public struct TypeTagI128_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI128
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI128
        ) {
            self.value = value
        }
    }
    public struct TypeTagI16_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI16
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI16
        ) {
            self.value = value
        }
    }
    public struct TypeTagI256_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI256
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI256
        ) {
            self.value = value
        }
    }
    public struct TypeTagI32_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI32
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI32
        ) {
            self.value = value
        }
    }
    public struct TypeTagI64_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI64
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI64
        ) {
            self.value = value
        }
    }
    public struct TypeTagI8_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI8
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagI8
        ) {
            self.value = value
        }
    }
    public struct TypeTagReference_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagReference
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagReference
        ) {
            self.value = value
        }
    }
    public struct TypeTagSigner_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagSigner
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagSigner
        ) {
            self.value = value
        }
    }
    public struct TypeTagStruct_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagStruct
        ) {
            self.value = value
        }
    }
    public struct TypeTagU128_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU128
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU128
        ) {
            self.value = value
        }
    }
    public struct TypeTagU16_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU16
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU16
        ) {
            self.value = value
        }
    }
    public struct TypeTagU256_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU256
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU256
        ) {
            self.value = value
        }
    }
    public struct TypeTagU32_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU32
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU32
        ) {
            self.value = value
        }
    }
    public struct TypeTagU64_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU64
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU64
        ) {
            self.value = value
        }
    }
    public struct TypeTagU8_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU8
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagU8
        ) {
            self.value = value
        }
    }
    public struct TypeTagVector_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTagVector
        ) {
            self.value = value
        }
    }
    public struct U128_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U128
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U128
        ) {
            self.value = value
        }
    }
    public struct U16_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U16
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U16
        ) {
            self.value = value
        }
    }
    public struct U256_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U256
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U256
        ) {
            self.value = value
        }
    }
    public struct U32_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U32
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U32
        ) {
            self.value = value
        }
    }
    public struct U64_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U64
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U64
        ) {
            self.value = value
        }
    }
    public struct U8_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U8
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.U8
        ) {
            self.value = value
        }
    }
    public struct UserTransactionResponse_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse
        ) {
            self.value = value
        }
    }
    public struct ValidatorTransactionResponse_SealedType: KotlinRuntimeSupport.SealedType {
        public let value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ValidatorTransactionResponse
        init(
            _ value: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ValidatorTransactionResponse
        ) {
            self.value = value
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.move {
    public enum MoveArgument_SealedType: KotlinRuntimeSupport.SealedType {
        case address(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Address_SealedType)
        case bool(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bool_SealedType)
        case bytes(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Bytes_SealedType)
        case `enum`(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Enum_SealedType)
        case i128(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I128_SealedType)
        case i16(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I16_SealedType)
        case i256(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I256_SealedType)
        case i32(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I32_SealedType)
        case i64(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I64_SealedType)
        case i8(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_I8_SealedType)
        case option(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Option_SealedType)
        case preSerialized(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_PreSerialized_SealedType)
        case stringValue(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_StringValue_SealedType)
        case `struct`(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Struct_SealedType)
        case u128(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U128_SealedType)
        case u16(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U16_SealedType)
        case u256(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U256_SealedType)
        case u32(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U32_SealedType)
        case u64(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U64_SealedType)
        case u8(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_U8_SealedType)
        case vector(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument_Vector_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            get {
                switch self {
                case let .address(type): type.value
                case let .bool(type): type.value
                case let .bytes(type): type.value
                case let .`enum`(type): type.value
                case let .i128(type): type.value
                case let .i16(type): type.value
                case let .i256(type): type.value
                case let .i32(type): type.value
                case let .i64(type): type.value
                case let .i8(type): type.value
                case let .option(type): type.value
                case let .preSerialized(type): type.value
                case let .stringValue(type): type.value
                case let .`struct`(type): type.value
                case let .u128(type): type.value
                case let .u16(type): type.value
                case let .u256(type): type.value
                case let .u32(type): type.value
                case let .u64(type): type.value
                case let .u8(type): type.value
                case let .vector(type): type.value
                }
            }
        }
    }
    /// Kotlin-native representation of values accepted by Move transaction and view functions.
    public protocol MoveArgument: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.move._MoveArgument {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument_SealedType
        /// Serializes ABI-independent primitive/container arguments to canonical BCS.
        func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument)
    public protocol _MoveArgument {
    }
    public protocol __MoveArgument: KotlinRuntimeSupport._KotlinBridgeable {
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey {
    open class AbstractMultiKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey {
        open var publicKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey] {
            get {
                if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey.self {
                    return xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_publicKeys_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey>
                } else {
                    return xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_publicKeys_get_direct(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey>
                }
            }
        }
        public init(
            publicKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey]
        ) {
            precondition(Self.self != ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey.self, "ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey is an abstract class and cannot be instantiated directly")
            let __kt = _kotlinAllocInstanceForSwiftSubclass(Self.self)
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey___(__kt, publicKeys); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// Creates a bitmap that holds the mapping from the original public keys to the signatures passed
        /// in.
        /// 
        /// - Parameters:
        ///   - bits: A list of indices (0-31) mapping to the matching public keys that have provided
        ///     signatures.
        /// 
        /// - Returns: A 4-byte ByteArray representing the bitmap.
        /// 
        /// - Throws:
        ///   - `IllegalArgumentException`: if validation fails (e.g., too many signatures, duplicate
        ///     bits, invalid bit index).
        public final func createBitmap(
            bits: [Swift.Int32]
        ) -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_createBitmap__TypesOfArguments__Swift_Array_Swift_Int32___(self.__externalRCRef(), bits.map { it in NSNumber(value: it) }))
        }
        /// Gets the number of signatures required for this multi-key set.
        /// 
        /// - Returns: The number of required signatures.
        open func getSigsRequired() -> Swift.Int32 {
            if Self.self == ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey.self {
                return xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_getSigsRequired(self.__externalRCRef())
            } else {
                fatalError("Cannot invoke the inherited implementation of abstract member 'ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey.getSigsRequired': a Swift subclass must override it and must not call super.")
            }
        }
        /// Gets the index of the provided public key.
        /// 
        /// This function retrieves the index of a specified public key within the MultiKey. If the public
        /// key does not exist, it throws an error.
        /// 
        /// - Parameters:
        ///   - publicKey: The public key to find the index for. Can be any type whose `toString()`
        ///     representation matches one in the `publicKeys` list.
        /// 
        /// - Returns: The corresponding index of the public key, if it exists.
        /// 
        /// - Throws:
        ///   - `IllegalArgumentException`: If the public key is not found in the MultiKey.
        public final func index(
            publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey
        ) -> Swift.Int32 {
            return xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_index__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey__(self.__externalRCRef(), publicKey.__externalRCRef())
        }
    }
    public final class MultiKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_Companion_get())
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
            public func isInstance(
                publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey
            ) -> Swift.Bool {
                return xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_Companion_isInstance__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey__(self.__externalRCRef(), publicKey.__externalRCRef())
            }
        }
        public var pks: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey] {
            get {
                return xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_pks_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey>
            }
        }
        @_nonoverride
        public var publicKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnyPublicKey] {
            get {
                return xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_publicKeys_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnyPublicKey>
            }
        }
        public var signaturesRequired: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_signaturesRequired_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            pks: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey],
            signaturesRequired: Swift.Int32
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey__Swift_Int32__(__kt, pks, signaturesRequired); return () }()
        }
        /// Get the authentication key associated with this public key
        public override func authKey() -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_authKey(self.__externalRCRef()))
        }
        /// Gets the number of signatures required for this multi-key set.
        /// 
        /// - Returns: The number of required signatures.
        public override func getSigsRequired() -> Swift.Int32 {
            return xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_getSigsRequired(self.__externalRCRef())
        }
        /// Get the BCS bytes
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_toBcs(self.__externalRCRef()))
        }
        /// Get the raw public key bytes
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_toByteArray(self.__externalRCRef()))
        }
        /// Verifies that the private key associated with this public key signed the message with the given
        /// signature.
        /// 
        /// - Parameters:
        ///   - message: The message that was signed
        ///   - signature: The signature to verify
        public override func verifySignature(
            message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_core_crypto_multikey_MultiKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
    }
    /// Represents a multi-signature transaction using Ed25519 signatures. This class allows for the
    /// creation and management of a K-of-N multi-signature scheme, where a specified number of
    /// signatures are required to authorize a transaction.
    /// 
    /// This implementation is compatible with Kotlin Multiplatform (Common, JVM, JS, Native).
    public final class MultiKeySignature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature {
        public final class Companion: KotlinRuntime.KotlinBase {
            /// Number of bytes in the bitmap (32-bits).
            public var BITMAP_LEN: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_Companion_BITMAP_LEN_get(self.__externalRCRef())
                }
            }
            /// Maximum number of signatures supported.
            public var MAX_SIGNATURES_SUPPORTED: Swift.Int32 {
                get {
                    return xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_Companion_MAX_SIGNATURES_SUPPORTED_get(self.__externalRCRef())
                }
            }
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.MultiKeySignature.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_Companion_get())
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
            /// Helper method to create a bitmap from a list of specified bit positions.
            /// 
            /// - Parameters:
            ///   - bits: The list of bit positions (0-31) that should be set to '1'.
            /// 
            /// - Returns: A 4-byte array representing the bitmap.
            public func createBitmap(
                bits: [Swift.Int32]
            ) -> ExportedKotlinPackages.kotlin.ByteArray {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_Companion_createBitmap__TypesOfArguments__Swift_Array_Swift_Int32___(self.__externalRCRef(), bits.map { it in NSNumber(value: it) }))
            }
        }
        /// 4-byte (32-bit) array representing which of the N possible signers have signed.
        public var bitmap: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_bitmap_get(self.__externalRCRef()))
            }
        }
        /// The list of underlying signatures.
        public var signatures: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature] {
            get {
                return xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_signatures_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature>
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        /// - Parameters:
        ///   - signatures: The list of underlying signatures.
        ///   - bitmap: 4-byte (32-bit) array representing which of the N possible signers have signed.
        public init(
            signatures: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AnySignature],
            bitmap: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AnySignature__ExportedKotlinPackages_kotlin_ByteArray__(__kt, signatures, bitmap.__externalRCRef()); return () }()
        }
        /// Converts the bitmap to a list of signer indices.
        /// 
        /// - Returns: A list of integer indices for each signer who provided a signature.
        public func bitMapToSignerIndices() -> [Swift.Int32] {
            return xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_bitMapToSignerIndices(self.__externalRCRef()) as! Swift.Array<Swift.Int32>
        }
        /// Serializes the MultiKeySignature into its raw byte representation in a KMP-compatible way. The
        /// format is the concatenation of all individual signatures followed by the 4-byte bitmap.
        /// 
        /// - Returns: The serialized byte array.
        public override func toBcs() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_toBcs(self.__externalRCRef()))
        }
        /// Returns the raw byte representation of the signature. This is an alias for `toBcs()`.
        public override func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_core_crypto_multikey_MultiKeySignature_toByteArray(self.__externalRCRef()))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.names {
    public enum NameExpirationPolicy_SealedType: KotlinRuntimeSupport.SealedType {
        case domain(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Domain_SealedType)
        case followDomain(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_FollowDomain_SealedType)
        case independent(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Independent_SealedType)
        public var value: ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy {
            get {
                switch self {
                case let .domain(type): type.value
                case let .followDomain(type): type.value
                case let .independent(type): type.value
                }
            }
        }
    }
    /// Expiration behavior for ANS registration.
    public protocol NameExpirationPolicy: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.names._NameExpirationPolicy {
        func sealedType() -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy_SealedType
    }
    @objc(_ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy)
    public protocol _NameExpirationPolicy {
    }
    public protocol __NameExpirationPolicy: KotlinRuntimeSupport._KotlinBridgeable {
    }
    /// Validated Aptos Name Service name, without the optional `.apt` suffix.
    public final class AptosName: KotlinRuntime.KotlinBase {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_names_AptosName_Companion_get())
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
            public func isValidSegment(
                value: Swift.String
            ) -> Swift.Bool {
                return xyz_mcxross_kaptos_names_AptosName_Companion_isValidSegment__TypesOfArguments__Swift_String__(self.__externalRCRef(), value)
            }
            public func parse(
                value: Swift.String
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_names_AptosName_Companion_parse__TypesOfArguments__Swift_String__(self.__externalRCRef(), value))
            }
        }
        public var domain: Swift.String {
            get {
                return xyz_mcxross_kaptos_names_AptosName_domain_get(self.__externalRCRef())
            }
        }
        public var subdomain: Swift.String? {
            get {
                return xyz_mcxross_kaptos_names_AptosName_subdomain_get(self.__externalRCRef())
            }
        }
        public var value: Swift.String {
            get {
                return xyz_mcxross_kaptos_names_AptosName_value_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            domain: Swift.String,
            subdomain: Swift.String?
        ) {
            let __kt = xyz_mcxross_kaptos_names_AptosName_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_names_AptosName_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_Swift_String___(__kt, domain, subdomain ?? nil); return () }()
        }
        public func copy(
            domain: Swift.String,
            subdomain: Swift.String?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_names_AptosName_copy__TypesOfArguments__Swift_String_Swift_Optional_Swift_String___(self.__externalRCRef(), domain, subdomain ?? nil))
        }
    }
    /// Stable public ANS row; Apollo-generated response classes stay internal.
    public final class NameRecord: KotlinRuntime.KotlinBase {
        public var domainExpiration: ExportedKotlinPackages.kotlin.time.Instant? {
            get {
                return { switch xyz_mcxross_kaptos_names_NameRecord_domainExpiration_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var expiration: ExportedKotlinPackages.kotlin.time.Instant? {
            get {
                return { switch xyz_mcxross_kaptos_names_NameRecord_expiration_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.kotlin.time.Instant.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var isActive: Swift.Bool? {
            get {
                return xyz_mcxross_kaptos_names_NameRecord_isActive_get(self.__externalRCRef()).map { it in it.boolValue }
            }
        }
        public var isPrimary: Swift.Bool? {
            get {
                return xyz_mcxross_kaptos_names_NameRecord_isPrimary_get(self.__externalRCRef()).map { it in it.boolValue }
            }
        }
        public var lastTransactionVersion: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_names_NameRecord_lastTransactionVersion_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var name: ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_names_NameRecord_name_get(self.__externalRCRef()))
            }
        }
        public var owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress? {
            get {
                return { switch xyz_mcxross_kaptos_names_NameRecord_owner_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var registeredAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress? {
            get {
                return { switch xyz_mcxross_kaptos_names_NameRecord_registeredAddress_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var subdomainExpirationPolicy: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_names_NameRecord_subdomainExpirationPolicy_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var tokenName: Swift.String? {
            get {
                return xyz_mcxross_kaptos_names_NameRecord_tokenName_get(self.__externalRCRef())
            }
        }
        public var tokenStandard: Swift.String? {
            get {
                return xyz_mcxross_kaptos_names_NameRecord_tokenStandard_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            name: ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName,
            expiration: ExportedKotlinPackages.kotlin.time.Instant?,
            domainExpiration: ExportedKotlinPackages.kotlin.time.Instant?,
            isActive: Swift.Bool?,
            isPrimary: Swift.Bool?,
            lastTransactionVersion: Swift.UInt64?,
            owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
            registeredAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
            subdomainExpirationPolicy: Swift.UInt64?,
            tokenName: Swift.String?,
            tokenStandard: Swift.String?
        ) {
            let __kt = xyz_mcxross_kaptos_names_NameRecord_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_names_NameRecord_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_names_AptosName_Swift_Optional_ExportedKotlinPackages_kotlin_time_Instant__Swift_Optional_ExportedKotlinPackages_kotlin_time_Instant__Swift_Optional_Swift_Bool__Swift_Optional_Swift_Bool__Swift_Optional_Swift_UInt64__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_String__Swift_Optional_Swift_String___(__kt, name.__externalRCRef(), expiration.map { it in it.__externalRCRef() } ?? nil, domainExpiration.map { it in it.__externalRCRef() } ?? nil, isActive.map { it in NSNumber(value: it) } ?? nil, isPrimary.map { it in NSNumber(value: it) } ?? nil, lastTransactionVersion.map { it in NSNumber(value: it) } ?? nil, owner.map { it in it.__externalRCRef() } ?? nil, registeredAddress.map { it in it.__externalRCRef() } ?? nil, subdomainExpirationPolicy.map { it in NSNumber(value: it) } ?? nil, tokenName ?? nil, tokenStandard ?? nil); return () }()
        }
        public func copy(
            name: ExportedKotlinPackages.xyz.mcxross.kaptos.names.AptosName,
            expiration: ExportedKotlinPackages.kotlin.time.Instant?,
            domainExpiration: ExportedKotlinPackages.kotlin.time.Instant?,
            isActive: Swift.Bool?,
            isPrimary: Swift.Bool?,
            lastTransactionVersion: Swift.UInt64?,
            owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
            registeredAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
            subdomainExpirationPolicy: Swift.UInt64?,
            tokenName: Swift.String?,
            tokenStandard: Swift.String?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameRecord {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameRecord.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_names_NameRecord_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_names_AptosName_Swift_Optional_ExportedKotlinPackages_kotlin_time_Instant__Swift_Optional_ExportedKotlinPackages_kotlin_time_Instant__Swift_Optional_Swift_Bool__Swift_Optional_Swift_Bool__Swift_Optional_Swift_UInt64__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_Swift_UInt64__Swift_Optional_Swift_String__Swift_Optional_Swift_String___(self.__externalRCRef(), name.__externalRCRef(), expiration.map { it in it.__externalRCRef() } ?? nil, domainExpiration.map { it in it.__externalRCRef() } ?? nil, isActive.map { it in NSNumber(value: it) } ?? nil, isPrimary.map { it in NSNumber(value: it) } ?? nil, lastTransactionVersion.map { it in NSNumber(value: it) } ?? nil, owner.map { it in it.__externalRCRef() } ?? nil, registeredAddress.map { it in it.__externalRCRef() } ?? nil, subdomainExpirationPolicy.map { it in NSNumber(value: it) } ?? nil, tokenName ?? nil, tokenStandard ?? nil))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.objects {
    /// Current indexed ownership and transfer state for an Aptos object.
    public final class ObjectRecord: KotlinRuntime.KotlinBase {
        public var address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_objects_ObjectRecord_address_get(self.__externalRCRef()))
            }
        }
        public var allowUngatedTransfer: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_objects_ObjectRecord_allowUngatedTransfer_get(self.__externalRCRef())
            }
        }
        public var isDeleted: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_objects_ObjectRecord_isDeleted_get(self.__externalRCRef())
            }
        }
        public var lastGuidCreationNumber: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_objects_ObjectRecord_lastGuidCreationNumber_get(self.__externalRCRef())
            }
        }
        public var lastTransactionVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_objects_ObjectRecord_lastTransactionVersion_get(self.__externalRCRef())
            }
        }
        public var owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_objects_ObjectRecord_owner_get(self.__externalRCRef()))
            }
        }
        public var stateKeyHash: Swift.String {
            get {
                return xyz_mcxross_kaptos_objects_ObjectRecord_stateKeyHash_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            stateKeyHash: Swift.String,
            allowUngatedTransfer: Swift.Bool,
            lastTransactionVersion: Swift.UInt64,
            lastGuidCreationNumber: Swift.UInt64,
            isDeleted: Swift.Bool
        ) {
            let __kt = xyz_mcxross_kaptos_objects_ObjectRecord_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_objects_ObjectRecord_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_String_Swift_Bool_Swift_UInt64_Swift_UInt64_Swift_Bool__(__kt, address.__externalRCRef(), owner.__externalRCRef(), stateKeyHash, allowUngatedTransfer, lastTransactionVersion, lastGuidCreationNumber, isDeleted); return () }()
        }
        public func copy(
            address: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            stateKeyHash: Swift.String,
            allowUngatedTransfer: Swift.Bool,
            lastTransactionVersion: Swift.UInt64,
            lastGuidCreationNumber: Swift.UInt64,
            isDeleted: Swift.Bool
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.objects.ObjectRecord {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.objects.ObjectRecord.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_objects_ObjectRecord_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_String_Swift_Bool_Swift_UInt64_Swift_UInt64_Swift_Bool__(self.__externalRCRef(), address.__externalRCRef(), owner.__externalRCRef(), stateKeyHash, allowUngatedTransfer, lastTransactionVersion, lastGuidCreationNumber, isDeleted))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.staking {
    /// One indexed delegated-staking balance change.
    public final class DelegatedStakingActivity: KotlinRuntime.KotlinBase {
        public var amount: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_staking_DelegatedStakingActivity_amount_get(self.__externalRCRef())
            }
        }
        public var delegator: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_staking_DelegatedStakingActivity_delegator_get(self.__externalRCRef()))
            }
        }
        public var eventIndex: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_staking_DelegatedStakingActivity_eventIndex_get(self.__externalRCRef())
            }
        }
        public var eventType: Swift.String {
            get {
                return xyz_mcxross_kaptos_staking_DelegatedStakingActivity_eventType_get(self.__externalRCRef())
            }
        }
        public var pool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_staking_DelegatedStakingActivity_pool_get(self.__externalRCRef()))
            }
        }
        public var transactionVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_staking_DelegatedStakingActivity_transactionVersion_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            amount: Swift.UInt64,
            delegator: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            eventIndex: Swift.UInt64,
            eventType: Swift.String,
            pool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            transactionVersion: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_staking_DelegatedStakingActivity_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_staking_DelegatedStakingActivity_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_UInt64_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_UInt64__(__kt, amount, delegator.__externalRCRef(), eventIndex, eventType, pool.__externalRCRef(), transactionVersion); return () }()
        }
        public func copy(
            amount: Swift.UInt64,
            delegator: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            eventIndex: Swift.UInt64,
            eventType: Swift.String,
            pool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            transactionVersion: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.staking.DelegatedStakingActivity {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.staking.DelegatedStakingActivity.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_staking_DelegatedStakingActivity_copy__TypesOfArguments__Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_UInt64_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_UInt64__(self.__externalRCRef(), amount, delegator.__externalRCRef(), eventIndex, eventType, pool.__externalRCRef(), transactionVersion))
        }
    }
    /// Active delegator count for one staking pool.
    public final class StakingPoolDelegatorCount: KotlinRuntime.KotlinBase {
        public var activeDelegators: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_staking_StakingPoolDelegatorCount_activeDelegators_get(self.__externalRCRef())
            }
        }
        public var pool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_staking_StakingPoolDelegatorCount_pool_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            pool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            activeDelegators: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_staking_StakingPoolDelegatorCount_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_staking_StakingPoolDelegatorCount_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_UInt64__(__kt, pool.__externalRCRef(), activeDelegators); return () }()
        }
        public func copy(
            pool: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            activeDelegators: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.staking.StakingPoolDelegatorCount {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.staking.StakingPoolDelegatorCount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_staking_StakingPoolDelegatorCount_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_UInt64__(self.__externalRCRef(), pool.__externalRCRef(), activeDelegators))
        }
    }
}
extension ExportedKotlinPackages.xyz.mcxross.kaptos.table {
    /// One historical table write returned by the indexer.
    public final class TableItem: KotlinRuntime.KotlinBase {
        public var decodedKey: Swift.String {
            get {
                return xyz_mcxross_kaptos_table_TableItem_decodedKey_get(self.__externalRCRef())
            }
        }
        public var decodedValue: Swift.String? {
            get {
                return xyz_mcxross_kaptos_table_TableItem_decodedValue_get(self.__externalRCRef())
            }
        }
        public var key: Swift.String {
            get {
                return xyz_mcxross_kaptos_table_TableItem_key_get(self.__externalRCRef())
            }
        }
        public var tableHandle: Swift.String {
            get {
                return xyz_mcxross_kaptos_table_TableItem_tableHandle_get(self.__externalRCRef())
            }
        }
        public var transactionVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_table_TableItem_transactionVersion_get(self.__externalRCRef())
            }
        }
        public var writeSetChangeIndex: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_table_TableItem_writeSetChangeIndex_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            decodedKey: Swift.String,
            decodedValue: Swift.String?,
            key: Swift.String,
            tableHandle: Swift.String,
            transactionVersion: Swift.UInt64,
            writeSetChangeIndex: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_table_TableItem_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_table_TableItem_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_String_Swift_UInt64_Swift_UInt64__(__kt, decodedKey, decodedValue ?? nil, key, tableHandle, transactionVersion, writeSetChangeIndex); return () }()
        }
        public func copy(
            decodedKey: Swift.String,
            decodedValue: Swift.String?,
            key: Swift.String,
            tableHandle: Swift.String,
            transactionVersion: Swift.UInt64,
            writeSetChangeIndex: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableItem {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableItem.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_table_TableItem_copy__TypesOfArguments__Swift_String_Swift_Optional_Swift_String__Swift_String_Swift_String_Swift_UInt64_Swift_UInt64__(self.__externalRCRef(), decodedKey, decodedValue ?? nil, key, tableHandle, transactionVersion, writeSetChangeIndex))
        }
    }
    /// Move key and value types registered for a table handle.
    public final class TableMetadata: KotlinRuntime.KotlinBase {
        public var handle: Swift.String {
            get {
                return xyz_mcxross_kaptos_table_TableMetadata_handle_get(self.__externalRCRef())
            }
        }
        public var keyType: Swift.String {
            get {
                return xyz_mcxross_kaptos_table_TableMetadata_keyType_get(self.__externalRCRef())
            }
        }
        public var valueType: Swift.String {
            get {
                return xyz_mcxross_kaptos_table_TableMetadata_valueType_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            handle: Swift.String,
            keyType: Swift.String,
            valueType: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_table_TableMetadata_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_table_TableMetadata_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String_Swift_String_Swift_String__(__kt, handle, keyType, valueType); return () }()
        }
        public func copy(
            handle: Swift.String,
            keyType: Swift.String,
            valueType: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableMetadata {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableMetadata.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_table_TableMetadata_copy__TypesOfArguments__Swift_String_Swift_String_Swift_String__(self.__externalRCRef(), handle, keyType, valueType))
        }
    }
}
@_cdecl("XyzMcxrossKaptosKaptos_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder__")
package func XyzMcxrossKaptosKaptos_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_EntryFunctionArgumentBuilder__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgumentBuilder) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.model.EntryFunctionArgumentBuilder.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("XyzMcxrossKaptosKaptos_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___")
package func XyzMcxrossKaptosKaptos_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<Swift.Error>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: KotlinRuntimeSupport.swiftError(fromKotlinThrowable: KotlinRuntime.KotlinBase.__createClassWrapper(externalRCRef: res)); } }())
    return { _result; return true }()
}

@_cdecl("XyzMcxrossKaptosKaptos_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult__")
package func XyzMcxrossKaptosKaptos_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void)(KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: _1, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult)
    return { _result; return true }()
}

@_cdecl("xyz_mcxross_kaptos_account_AccountAsset_value_get__reverse_swift")
package func xyz_mcxross_kaptos_account_AccountAsset_value_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.String {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset
    let _result: Swift.String = _self.value
    return _result
}

@_cdecl("xyz_mcxross_kaptos_account_Account_accountAddress_get__reverse_swift")
package func xyz_mcxross_kaptos_account_Account_accountAddress_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress = _self.accountAddress
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_Account_clearPrivateKey__reverse_swift")
package func xyz_mcxross_kaptos_account_Account_clearPrivateKey__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Void = _self.clearPrivateKey()
    return { _result; return true }()
}

@_cdecl("xyz_mcxross_kaptos_account_Account_isPrivateKeyCleared_get__reverse_swift")
package func xyz_mcxross_kaptos_account_Account_isPrivateKeyCleared_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Bool = _self.isPrivateKeyCleared
    return _result
}

@_cdecl("xyz_mcxross_kaptos_account_Account_publicKey_get__reverse_swift")
package func xyz_mcxross_kaptos_account_Account_publicKey_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey = _self.publicKey
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_Account_signTransactionSignature__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction____reverse_swift")
package func xyz_mcxross_kaptos_account_Account_signTransactionSignature__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ tx: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature = _self.signTransactionSignature(tx: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: tx, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction)
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_Account_signTransaction__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction____reverse_swift")
package func xyz_mcxross_kaptos_account_Account_signTransaction__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ transaction: Swift.UnsafeMutableRawPointer, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let __continuation: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { XyzMcxrossKaptosKaptos_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult__(pointerToBlock.__externalRCRef(), _1.__externalRCRef()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { XyzMcxrossKaptosKaptos_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await _self.signTransaction(transaction: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: transaction, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction)
    }
    return true
}

@_cdecl("xyz_mcxross_kaptos_account_Account_signWithAuthenticator__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput____reverse_swift")
package func xyz_mcxross_kaptos_account_Account_signWithAuthenticator__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ message: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator = _self.signWithAuthenticator(message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: message))
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_Account_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput____reverse_swift")
package func xyz_mcxross_kaptos_account_Account_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ message: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature = _self.sign(message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: message))
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_Account_signingScheme_get__reverse_swift")
package func xyz_mcxross_kaptos_account_Account_signingScheme_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningScheme = _self.signingScheme
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_Account_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature____reverse_swift")
package func xyz_mcxross_kaptos_account_Account_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ message: Swift.UnsafeMutableRawPointer, _ signature: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Bool = _self.verifySignature(message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: message), signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: signature))
    return _result
}

@_cdecl("xyz_mcxross_kaptos_account_TransactionSigner_accountAddress_get__reverse_swift")
package func xyz_mcxross_kaptos_account_TransactionSigner_accountAddress_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress = _self.accountAddress
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_TransactionSigner_kind_get__reverse_swift")
package func xyz_mcxross_kaptos_account_TransactionSigner_kind_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSignerKind = _self.kind
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_TransactionSigner_publicKey_get__reverse_swift")
package func xyz_mcxross_kaptos_account_TransactionSigner_publicKey_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey = _self.publicKey
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_account_TransactionSigner_signBytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray____reverse_swift")
package func xyz_mcxross_kaptos_account_TransactionSigner_signBytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ message: Swift.UnsafeMutableRawPointer, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner
    let __continuation: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { XyzMcxrossKaptosKaptos_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult__(pointerToBlock.__externalRCRef(), _1.__externalRCRef()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { XyzMcxrossKaptosKaptos_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await _self.signBytes(message: ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: message))
    }
    return true
}

@_cdecl("xyz_mcxross_kaptos_account_TransactionSigner_signText__TypesOfArguments__Swift_String____reverse_swift")
package func xyz_mcxross_kaptos_account_TransactionSigner_signText__TypesOfArguments__Swift_String____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ message: Swift.String, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner
    let __continuation: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { XyzMcxrossKaptosKaptos_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult__(pointerToBlock.__externalRCRef(), _1.__externalRCRef()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { XyzMcxrossKaptosKaptos_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await _self.signText(message: message)
    }
    return true
}

@_cdecl("xyz_mcxross_kaptos_account_TransactionSigner_signTransaction__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction____reverse_swift")
package func xyz_mcxross_kaptos_account_TransactionSigner_signTransaction__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ transaction: Swift.UnsafeMutableRawPointer, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner
    let __continuation: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { XyzMcxrossKaptosKaptos_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult__(pointerToBlock.__externalRCRef(), _1.__externalRCRef()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { XyzMcxrossKaptosKaptos_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await _self.signTransaction(transaction: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: transaction, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction)
    }
    return true
}

@_cdecl("xyz_mcxross_kaptos_account_TransactionSigner_verifySignature__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature____reverse_swift")
package func xyz_mcxross_kaptos_account_TransactionSigner_verifySignature__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ message: Swift.UnsafeMutableRawPointer, _ signature: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner
    let _result: Swift.Bool = _self.verifySignature(message: ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: message), signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: signature))
    return _result
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_AccountPublicKey_authKey__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_AccountPublicKey_authKey__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.core.AuthenticationKey = _self.authKey()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PrivateKey_clear__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PrivateKey_clear__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey
    let _result: Swift.Void = _self.clear()
    return { _result; return true }()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PrivateKey_isCleared_get__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PrivateKey_isCleared_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey
    let _result: Swift.Bool = _self.isCleared
    return _result
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PrivateKey_publicKey__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PrivateKey_publicKey__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey = _self.publicKey()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PrivateKey_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput____reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PrivateKey_sign__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ message: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature = _self.sign(message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: message))
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PrivateKey_toAip80__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PrivateKey_toAip80__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Aip80PrivateKey = _self.toAip80()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PrivateKey_toByteArray__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PrivateKey_toByteArray__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PrivateKey
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toByteArray()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PublicKey_toBcs__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PublicKey_toBcs__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toBcs()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PublicKey_toByteArray__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PublicKey_toByteArray__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toByteArray()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_PublicKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature____reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_PublicKey_verifySignature__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_HexInput_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature____reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer, _ message: Swift.UnsafeMutableRawPointer, _ signature: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Bool = _self.verifySignature(message: ExportedKotlinPackages.xyz.mcxross.kaptos.model.HexInput.__createClassWrapper(externalRCRef: message), signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: signature))
    return _result
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_Signature_toBcs__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_Signature_toBcs__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toBcs()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_Signature_toByteArray__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_Signature_toByteArray__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toByteArray()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_getSigsRequired__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_getSigsRequired__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Int32 {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Int32 = _self.getSigsRequired()
    return _result
}

@_cdecl("xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_publicKeys_get__reverse_swift")
package func xyz_mcxross_kaptos_core_crypto_multikey_AbstractMultiKey_publicKeys_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Any {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.multikey.AbstractMultiKey.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey> = _self.publicKeys
    return _result
}

@_cdecl("xyz_mcxross_kaptos_model_AccountAddressInput_value_get__reverse_swift")
package func xyz_mcxross_kaptos_model_AccountAddressInput_value_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.String {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
    let _result: Swift.String = _self.value
    return _result
}

@_cdecl("xyz_mcxross_kaptos_model_AptosError_cause_get__reverse_swift")
package func xyz_mcxross_kaptos_model_AptosError_cause_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer? {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError
    let _result: Swift.Optional<ExportedKotlinPackages.kotlin.Throwable> = _self.cause
    return _result.map { it in it.__externalRCRef() } ?? nil
}

@_cdecl("xyz_mcxross_kaptos_model_AptosError_message_get__reverse_swift")
package func xyz_mcxross_kaptos_model_AptosError_message_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.String {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosError
    let _result: Swift.String = _self.message
    return _result
}

@_cdecl("xyz_mcxross_kaptos_model_AptosResult_isFailure_get__reverse_swift")
package func xyz_mcxross_kaptos_model_AptosResult_isFailure_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult
    let _result: Swift.Bool = _self.isFailure
    return _result
}

@_cdecl("xyz_mcxross_kaptos_model_AptosResult_isSuccess_get__reverse_swift")
package func xyz_mcxross_kaptos_model_AptosResult_isSuccess_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult
    let _result: Swift.Bool = _self.isSuccess
    return _result
}

@_cdecl("xyz_mcxross_kaptos_model_TransactionExecutable_toBcs__reverse_swift")
package func xyz_mcxross_kaptos_model_TransactionExecutable_toBcs__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExecutable
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toBcs()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_model_TransactionExtraConfig_toBcs__reverse_swift")
package func xyz_mcxross_kaptos_model_TransactionExtraConfig_toBcs__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionExtraConfig
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toBcs()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_model_TransactionPayload_toBcs__reverse_swift")
package func xyz_mcxross_kaptos_model_TransactionPayload_toBcs__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toBcs()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_model_TransactionResponse_type_get__reverse_swift")
package func xyz_mcxross_kaptos_model_TransactionResponse_type_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse.__createClassWrapper(externalRCRef: `self`)
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponseType = _self.type
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_model_TypeTag_value_get__reverse_swift")
package func xyz_mcxross_kaptos_model_TypeTag_value_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.String {
    let _self = ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag.__createClassWrapper(externalRCRef: `self`)
    let _result: Swift.String = _self.value
    return _result
}

@_cdecl("xyz_mcxross_kaptos_model_UnsignedTransaction_rawTransaction_get__reverse_swift")
package func xyz_mcxross_kaptos_model_UnsignedTransaction_rawTransaction_get__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
    let _result: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.instances.RawTransaction = _self.rawTransaction
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_model_UnsignedTransaction_signingBcs__reverse_swift")
package func xyz_mcxross_kaptos_model_UnsignedTransaction_signingBcs__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.signingBcs()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_model_UnsignedTransaction_signingMessage__reverse_swift")
package func xyz_mcxross_kaptos_model_UnsignedTransaction_signingMessage__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.signingMessage()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_move_MoveArgument_toBcs__reverse_swift")
package func xyz_mcxross_kaptos_move_MoveArgument_toBcs__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toBcs()
    return _result.__externalRCRef()
}

@_cdecl("xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_toBcs__reverse_swift")
package func xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_toBcs__reverse_swift(_ `self`: Swift.UnsafeMutableRawPointer) -> Swift.UnsafeMutableRawPointer {
    let _self = KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: `self`, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
    let _result: ExportedKotlinPackages.kotlin.ByteArray = _self.toBcs()
    return _result.__externalRCRef()
}

