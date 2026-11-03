@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
@_implementationOnly import SharedBridge_XyzMcxrossKaptosKaptosEncryptedTransactions
import XyzMcxrossKaptosKaptos

extension ExportedKotlinPackages.xyz.mcxross.kaptos.encrypted {
    /// Optional authentication-key overrides used to avoid an on-chain lookup after key rotation.
    public final class EncryptedTransactionOptions: KotlinRuntime.KotlinBase {
        public var claimedEntryFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction? {
            get {
                return { switch xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions_claimedEntryFunction_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var feePayerAuthenticationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32? {
            get {
                return { switch xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions_feePayerAuthenticationKey_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var secondarySignerAuthenticationKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32?]? {
            get {
                return xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions_secondarySignerAuthenticationKeys_get(self.__externalRCRef()).map { it in it as! Swift.Array<Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32>> }
            }
        }
        public var senderAuthenticationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32? {
            get {
                return { switch xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions_senderAuthenticationKey_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            senderAuthenticationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32?,
            secondarySignerAuthenticationKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32?]?,
            feePayerAuthenticationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32?,
            claimedEntryFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction?
        ) {
            let __kt = xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32__Swift_Optional_Swift_Array_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32____Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ClaimedEntryFunction___(__kt, senderAuthenticationKey.map { it in it.__externalRCRef() } ?? nil, secondarySignerAuthenticationKeys.map { it in it.map { it in it as! NSObject? ?? NSNull() } } ?? nil, feePayerAuthenticationKey.map { it in it.__externalRCRef() } ?? nil, claimedEntryFunction.map { it in it.__externalRCRef() } ?? nil); return () }()
        }
        public func copy(
            senderAuthenticationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32?,
            secondarySignerAuthenticationKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32?]?,
            feePayerAuthenticationKey: ExportedKotlinPackages.xyz.mcxross.kaptos.model.FixedBytes32?,
            claimedEntryFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.ClaimedEntryFunction?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.encrypted.EncryptedTransactionOptions {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.encrypted.EncryptedTransactionOptions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions_copy__TypesOfArguments__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32__Swift_Optional_Swift_Array_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32____Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_FixedBytes32__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_ClaimedEntryFunction___(self.__externalRCRef(), senderAuthenticationKey.map { it in it.__externalRCRef() } ?? nil, secondarySignerAuthenticationKeys.map { it in it.map { it in it as! NSObject? ?? NSNull() } } ?? nil, feePayerAuthenticationKey.map { it in it.__externalRCRef() } ?? nil, claimedEntryFunction.map { it in it.__externalRCRef() } ?? nil))
        }
    }
}
