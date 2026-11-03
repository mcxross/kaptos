@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
import KotlinStdlib
import OrgJetbrainsKotlinxKotlinxSerializationJson
@_implementationOnly import SharedBridge_XyzMcxrossKaptosKaptosConfidentialAssets
import XyzMcxrossKaptosKaptos

extension ExportedKotlinPackages.xyz.mcxross.kaptos.confidential {
    /// Confidential-asset event categories emitted by the Aptos indexer.
    public enum ConfidentialActivityType: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Registered
        case Deposited
        case Withdrawn
        case Transferred
        case Normalized
        case RolledOver
        case KeyRotated
        case IncomingTransfersPauseChanged
        case AllowListingChanged
        case ConfidentialityForAssetTypeChanged
        case GlobalAuditorChanged
        case AssetSpecificAuditorChanged
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_Companion_get())
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
        public var description: Swift.String {
            get {
                switch self {
                case .Registered: "Registered"
                case .Deposited: "Deposited"
                case .Withdrawn: "Withdrawn"
                case .Transferred: "Transferred"
                case .Normalized: "Normalized"
                case .RolledOver: "RolledOver"
                case .KeyRotated: "KeyRotated"
                case .IncomingTransfersPauseChanged: "IncomingTransfersPauseChanged"
                case .AllowListingChanged: "AllowListingChanged"
                case .ConfidentialityForAssetTypeChanged: "ConfidentialityForAssetTypeChanged"
                case .GlobalAuditorChanged: "GlobalAuditorChanged"
                case .AssetSpecificAuditorChanged: "AssetSpecificAuditorChanged"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Registered: 0
                case .Deposited: 1
                case .Withdrawn: 2
                case .Transferred: 3
                case .Normalized: 4
                case .RolledOver: 5
                case .KeyRotated: 6
                case .IncomingTransfersPauseChanged: 7
                case .AllowListingChanged: 8
                case .ConfidentialityForAssetTypeChanged: 9
                case .GlobalAuditorChanged: 10
                case .AssetSpecificAuditorChanged: 11
                default: fatalError()
                }
            }
        }
        public var wireName: Swift.String {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialActivityType_wireName_get(self.__externalRCRef())
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Registered": self = .Registered
            case "Deposited": self = .Deposited
            case "Withdrawn": self = .Withdrawn
            case "Transferred": self = .Transferred
            case "Normalized": self = .Normalized
            case "RolledOver": self = .RolledOver
            case "KeyRotated": self = .KeyRotated
            case "IncomingTransfersPauseChanged": self = .IncomingTransfersPauseChanged
            case "AllowListingChanged": self = .AllowListingChanged
            case "ConfidentialityForAssetTypeChanged": self = .ConfidentialityForAssetTypeChanged
            case "GlobalAuditorChanged": self = .GlobalAuditorChanged
            case "AssetSpecificAuditorChanged": self = .AssetSpecificAuditorChanged
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<12 ~= rawValue else { return nil }
            self = ConfidentialActivityType.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_confidential_ConfidentialActivityType_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Registered
            case 1: self = .Deposited
            case 2: self = .Withdrawn
            case 3: self = .Transferred
            case 4: self = .Normalized
            case 5: self = .RolledOver
            case 6: self = .KeyRotated
            case 7: self = .IncomingTransfersPauseChanged
            case 8: self = .AllowListingChanged
            case 9: self = .ConfidentialityForAssetTypeChanged
            case 10: self = .GlobalAuditorChanged
            case 11: self = .AssetSpecificAuditorChanged
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Registered: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_Registered()
            case .Deposited: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_Deposited()
            case .Withdrawn: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_Withdrawn()
            case .Transferred: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_Transferred()
            case .Normalized: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_Normalized()
            case .RolledOver: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_RolledOver()
            case .KeyRotated: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_KeyRotated()
            case .IncomingTransfersPauseChanged: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_IncomingTransfersPauseChanged()
            case .AllowListingChanged: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_AllowListingChanged()
            case .ConfidentialityForAssetTypeChanged: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_ConfidentialityForAssetTypeChanged()
            case .GlobalAuditorChanged: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_GlobalAuditorChanged()
            case .AssetSpecificAuditorChanged: xyz_mcxross_kaptos_confidential_ConfidentialActivityType_AssetSpecificAuditorChanged()
            default: fatalError()
            }
        }
    }
    /// Filters and pagination for confidential-asset activity lookup.
    public final class ConfidentialActivityQuery: KotlinRuntime.KotlinBase {
        public var asset: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)? {
            get {
                return { switch xyz_mcxross_kaptos_confidential_ConfidentialActivityQuery_asset_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput; } }()
            }
        }
        public var owner: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)? {
            get {
                return { switch xyz_mcxross_kaptos_confidential_ConfidentialActivityQuery_owner_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput; } }()
            }
        }
        public var page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialActivityQuery_page_get(self.__externalRCRef()))
            }
        }
        public var types: Swift.Set<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType> {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialActivityQuery_types_get(self.__externalRCRef()) as! Swift.Set<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType>
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            owner: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?,
            asset: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?,
            types: Swift.Set<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType>,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_ConfidentialActivityQuery_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_ConfidentialActivityQuery_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__Swift_Set_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialActivityType__ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(__kt, owner.map { it in it.__externalRCRef() } ?? nil, asset.map { it in it.__externalRCRef() } ?? nil, types, page.__externalRCRef()); return () }()
        }
        public func copy(
            owner: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?,
            asset: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?,
            types: Swift.Set<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType>,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityQuery {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityQuery.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialActivityQuery_copy__TypesOfArguments__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__Swift_Set_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialActivityType__ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), owner.map { it in it.__externalRCRef() } ?? nil, asset.map { it in it.__externalRCRef() } ?? nil, types, page.__externalRCRef()))
        }
    }
    /// A fixed-width confidential amount, least-significant base-2^16 limb first.
    public final class ConfidentialAmount: KotlinRuntime.KotlinBase {
        public var chunks: [Swift.UInt32] {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAmount_chunks_get(self.__externalRCRef()) as! Swift.Array<Swift.UInt32>
            }
        }
        /// Lossless base-10 representation, including values wider than Kotlin's [ULong].
        public var decimal: Swift.String {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAmount_decimal_get(self.__externalRCRef())
            }
        }
        public var normalizedChunks: [Swift.UInt32] {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAmount_normalizedChunks_get(self.__externalRCRef()) as! Swift.Array<Swift.UInt32>
            }
        }
        public init(
            chunks: [Swift.UInt32]
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_ConfidentialAmount_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_ConfidentialAmount_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_Swift_UInt32___(__kt, chunks.map { it in NSNumber(value: it) }); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            chunks: [Swift.UInt32]
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialAmount_copy__TypesOfArguments__Swift_Array_Swift_UInt32___(self.__externalRCRef(), chunks.map { it in NSNumber(value: it) }))
        }
        public func fitsInULong() -> Swift.Bool {
            return xyz_mcxross_kaptos_confidential_ConfidentialAmount_fitsInULong(self.__externalRCRef())
        }
        public func toULong() -> Swift.UInt64 {
            return xyz_mcxross_kaptos_confidential_ConfidentialAmount_toULong(self.__externalRCRef())
        }
    }
    /// Stable Kotlin model for one confidential-asset indexer row.
    public final class ConfidentialAssetActivity: KotlinRuntime.KotlinBase {
        public var amount: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_amount_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var asset: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress? {
            get {
                return { switch xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_asset_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var blockHeight: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_blockHeight_get(self.__externalRCRef())
            }
        }
        public var counterparty: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress? {
            get {
                return { switch xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_counterparty_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var counterpartyPrimaryName: Swift.String? {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_counterpartyPrimaryName_get(self.__externalRCRef())
            }
        }
        public var entryFunction: Swift.String? {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_entryFunction_get(self.__externalRCRef())
            }
        }
        public var eventData: ExportedKotlinPackages.kotlinx.serialization.json.JsonObject {
            get {
                return ExportedKotlinPackages.kotlinx.serialization.json.JsonObject.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_eventData_get(self.__externalRCRef()))
            }
        }
        public var eventDataVersion: Swift.String {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_eventDataVersion_get(self.__externalRCRef())
            }
        }
        public var eventIndex: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_eventIndex_get(self.__externalRCRef())
            }
        }
        public var owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_owner_get(self.__externalRCRef()))
            }
        }
        public var ownerPrimaryName: Swift.String? {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_ownerPrimaryName_get(self.__externalRCRef())
            }
        }
        public var timestamp: Swift.String {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_timestamp_get(self.__externalRCRef())
            }
        }
        public var transactionSucceeded: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_transactionSucceeded_get(self.__externalRCRef())
            }
        }
        public var transactionVersion: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_transactionVersion_get(self.__externalRCRef())
            }
        }
        public var type: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType(__externalRCRefUnsafe: xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_type_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            transactionVersion: Swift.UInt64,
            eventIndex: Swift.UInt64,
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType,
            owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            ownerPrimaryName: Swift.String?,
            asset: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
            counterparty: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
            counterpartyPrimaryName: Swift.String?,
            amount: Swift.UInt64?,
            eventData: ExportedKotlinPackages.kotlinx.serialization.json.JsonObject,
            eventDataVersion: Swift.String,
            blockHeight: Swift.UInt64,
            transactionSucceeded: Swift.Bool,
            entryFunction: Swift.String?,
            timestamp: Swift.String
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialActivityType_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_Swift_String__Swift_Optional_Swift_UInt64__ExportedKotlinPackages_kotlinx_serialization_json_JsonObject_Swift_String_Swift_UInt64_Swift_Bool_Swift_Optional_Swift_String__Swift_String__(__kt, transactionVersion, eventIndex, type.__externalRCRef(), owner.__externalRCRef(), ownerPrimaryName ?? nil, asset.map { it in it.__externalRCRef() } ?? nil, counterparty.map { it in it.__externalRCRef() } ?? nil, counterpartyPrimaryName ?? nil, amount.map { it in NSNumber(value: it) } ?? nil, eventData.__externalRCRef(), eventDataVersion, blockHeight, transactionSucceeded, entryFunction ?? nil, timestamp); return () }()
        }
        public func copy(
            transactionVersion: Swift.UInt64,
            eventIndex: Swift.UInt64,
            type: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityType,
            owner: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            ownerPrimaryName: Swift.String?,
            asset: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
            counterparty: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress?,
            counterpartyPrimaryName: Swift.String?,
            amount: Swift.UInt64?,
            eventData: ExportedKotlinPackages.kotlinx.serialization.json.JsonObject,
            eventDataVersion: Swift.String,
            blockHeight: Swift.UInt64,
            transactionSucceeded: Swift.Bool,
            entryFunction: Swift.String?,
            timestamp: Swift.String
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetActivity {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetActivity.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity_copy__TypesOfArguments__Swift_UInt64_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialActivityType_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_Swift_Optional_Swift_String__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress__Swift_Optional_Swift_String__Swift_Optional_Swift_UInt64__ExportedKotlinPackages_kotlinx_serialization_json_JsonObject_Swift_String_Swift_UInt64_Swift_Bool_Swift_Optional_Swift_String__Swift_String__(self.__externalRCRef(), transactionVersion, eventIndex, type.__externalRCRef(), owner.__externalRCRef(), ownerPrimaryName ?? nil, asset.map { it in it.__externalRCRef() } ?? nil, counterparty.map { it in it.__externalRCRef() } ?? nil, counterpartyPrimaryName ?? nil, amount.map { it in NSNumber(value: it) } ?? nil, eventData.__externalRCRef(), eventDataVersion, blockHeight, transactionSucceeded, entryFunction ?? nil, timestamp))
        }
    }
    /// Current registration, normalization, and incoming-transfer state.
    public final class ConfidentialAssetStatus: KotlinRuntime.KotlinBase {
        public var incomingTransfersPaused: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetStatus_incomingTransfersPaused_get(self.__externalRCRef())
            }
        }
        public var normalized: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetStatus_normalized_get(self.__externalRCRef())
            }
        }
        public var registered: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialAssetStatus_registered_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            registered: Swift.Bool,
            normalized: Swift.Bool,
            incomingTransfersPaused: Swift.Bool
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_ConfidentialAssetStatus_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_ConfidentialAssetStatus_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool_Swift_Bool_Swift_Bool__(__kt, registered, normalized, incomingTransfersPaused); return () }()
        }
        public func copy(
            registered: Swift.Bool,
            normalized: Swift.Bool,
            incomingTransfersPaused: Swift.Bool
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetStatus {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetStatus.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialAssetStatus_copy__TypesOfArguments__Swift_Bool_Swift_Bool_Swift_Bool__(self.__externalRCRef(), registered, normalized, incomingTransfersPaused))
        }
    }
    /// Available and pending ciphertexts plus their locally decrypted values.
    public final class ConfidentialBalance: KotlinRuntime.KotlinBase {
        public var available: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialCiphertext] {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialBalance_available_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialCiphertext>
            }
        }
        public var availableAmount: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialBalance_availableAmount_get(self.__externalRCRef()))
            }
        }
        public var pending: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialCiphertext] {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialBalance_pending_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialCiphertext>
            }
        }
        public var pendingAmount: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialBalance_pendingAmount_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            available: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialCiphertext],
            pending: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialCiphertext],
            availableAmount: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount,
            pendingAmount: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_ConfidentialBalance_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_ConfidentialBalance_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialCiphertext__Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialCiphertext__ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialAmount_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialAmount__(__kt, available, pending, availableAmount.__externalRCRef(), pendingAmount.__externalRCRef()); return () }()
        }
        public func copy(
            available: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialCiphertext],
            pending: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialCiphertext],
            availableAmount: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount,
            pendingAmount: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAmount
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialBalance {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialBalance.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialBalance_copy__TypesOfArguments__Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialCiphertext__Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialCiphertext__ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialAmount_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialAmount__(self.__externalRCRef(), available, pending, availableAmount.__externalRCRef(), pendingAmount.__externalRCRef()))
        }
    }
    /// One Aptos twisted-ElGamal ciphertext `(C, D)`.
    public final class ConfidentialCiphertext: KotlinRuntime.KotlinBase {
        public var commitment: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialCiphertext_commitment_get(self.__externalRCRef()))
            }
        }
        public var handle: ExportedKotlinPackages.kotlin.ByteArray {
            get {
                return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialCiphertext_handle_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            commitment: ExportedKotlinPackages.kotlin.ByteArray,
            handle: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_ConfidentialCiphertext_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_ConfidentialCiphertext_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_kotlin_ByteArray__(__kt, commitment.__externalRCRef(), handle.__externalRCRef()); return () }()
        }
    }
    /// Clearable Aptos twisted-Ed25519 decryption key.
    public final class ConfidentialDecryptionKey: KotlinRuntime.KotlinBase, ExportedKotlinPackages.kotlin.AutoCloseable, ExportedKotlinPackages.kotlin.__AutoCloseable {
        public final class Companion: KotlinRuntime.KotlinBase {
            public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey.Companion {
                get {
                    return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey.Companion.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Companion_get())
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
            /// Import one canonical little-endian scalar. Callers remain responsible for key custody.
            public func fromBytes(
                bytes: ExportedKotlinPackages.kotlin.ByteArray
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Companion_fromBytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef()))
            }
            /// Derive the key from the 64-byte account signature required by the Aptos protocol.
            public func fromSignature(
                signature: ExportedKotlinPackages.kotlin.ByteArray
            ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Companion_fromSignature__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), signature.__externalRCRef()))
            }
            public func generate() -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Companion_generate(self.__externalRCRef()))
            }
        }
        public var encryptionKey: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_encryptionKey_get(self.__externalRCRef()))
            }
        }
        public var isCleared: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_isCleared_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func clear() -> Swift.Void {
            return { xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_clear(self.__externalRCRef()); return () }()
        }
        public func close() -> Swift.Void {
            return { xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_close(self.__externalRCRef()); return () }()
        }
    }
    /// Aptos twisted-Ed25519 encryption key, represented by one compressed Ristretto point.
    public final class ConfidentialEncryptionKey: KotlinRuntime.KotlinBase {
        public init(
            bytes: ExportedKotlinPackages.kotlin.ByteArray
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(__kt, bytes.__externalRCRef()); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func toByteArray() -> ExportedKotlinPackages.kotlin.ByteArray {
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey_toByteArray(self.__externalRCRef()))
        }
    }
    /// Fee-payer and gas settings shared by confidential transaction builders.
    public final class ConfidentialTransactionOptions: KotlinRuntime.KotlinBase {
        public var externalFeePayerAddress: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)? {
            get {
                return { switch xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions_externalFeePayerAddress_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput; } }()
            }
        }
        public var feePayer: (any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner)? {
            get {
                return { switch xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions_feePayer_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: res, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner; } }()
            }
        }
        public var transaction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions_transaction_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            transaction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions,
            feePayer: (any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner)?,
            externalFeePayerAddress: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput___(__kt, transaction.__externalRCRef(), feePayer.map { it in it.__externalRCRef() } ?? nil, externalFeePayerAddress.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func copy(
            transaction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions,
            feePayer: (any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner)?,
            externalFeePayerAddress: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput___(self.__externalRCRef(), transaction.__externalRCRef(), feePayer.map { it in it.__externalRCRef() } ?? nil, externalFeePayerAddress.map { it in it.__externalRCRef() } ?? nil))
        }
    }
    /// Identifies whether the effective auditor is global and the key epoch to use.
    public final class EffectiveAuditorHint: KotlinRuntime.KotlinBase {
        public var epoch: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_confidential_EffectiveAuditorHint_epoch_get(self.__externalRCRef())
            }
        }
        public var isGlobal: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_confidential_EffectiveAuditorHint_isGlobal_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            isGlobal: Swift.Bool,
            epoch: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_confidential_EffectiveAuditorHint_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_confidential_EffectiveAuditorHint_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool_Swift_UInt64__(__kt, isGlobal, epoch); return () }()
        }
        public func copy(
            isGlobal: Swift.Bool,
            epoch: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.EffectiveAuditorHint {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.EffectiveAuditorHint.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_confidential_EffectiveAuditorHint_copy__TypesOfArguments__Swift_Bool_Swift_UInt64__(self.__externalRCRef(), isGlobal, epoch))
        }
    }
}
