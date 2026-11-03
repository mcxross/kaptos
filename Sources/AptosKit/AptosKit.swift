@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
import KotlinStdlib
import OrgJetbrainsKotlinxKotlinxCoroutinesCore
@_implementationOnly import SharedBridge_AptosKit
import XyzMcxrossKaptosKaptos
import XyzMcxrossKaptosKaptosConfidentialAssets
import XyzMcxrossKaptosKaptosEncryptedTransactions

/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Abstraction = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Abstraction
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Accounts = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Accounts
public typealias Addresses = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Addresses
/// A concrete exported exception so Swift can catch errors without inspecting erased generics.
public typealias AptosException = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.AptosException
/// Copying conversion avoids signed-byte truncation and unsupported ByteArray constructors.
public typealias ByteCodec = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ByteCodec
/// A wallet or custody signer owned by Swift. Kotlin retains these callbacks while this signer is
/// reachable. Swift export propagates coroutine cancellation in both directions. The transaction
/// callback must sign [UnsignedTransaction.signingMessage], including its domain.
public typealias CallbackSigner = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.CallbackSigner
/// Concrete throwing service boundary for Swift export. All requests, encoding, signing, and
/// lifecycle ownership delegate to [Aptos]. Confine a client and its accounts to one Swift actor.
public typealias Client = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Client
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Coins = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Coins
public typealias ConfidentialActivityPage = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialActivityPage
/// Concrete throwing boundary for the optional ConfidentialAssets module.
public typealias ConfidentialAssets = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialAssets
/// Swift configuration keeps transport and serializer implementation types out of call sites.
public typealias Configuration = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Configuration
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias DigitalAssets = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.DigitalAssets
/// Concrete throwing boundary for the optional EncryptedTransactions module.
public typealias EncryptedTransactions = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.EncryptedTransactions
/// Stable categories shared with the Kotlin result API.
public typealias ErrorCategory = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ErrorCategory
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Faucet = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Faucet
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias FungibleAssets = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.FungibleAssets
public typealias Indexer = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Indexer
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Ledger = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Ledger
/// Explicit Move values; large integers use validated decimal strings without floating point.
public typealias Move = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Move
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Names = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Names
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Objects = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Objects
/// Avoids Beta2's NSNumber-to-nullable-unsigned input bridge. Swift conveniences accept UInt64?.
public typealias OptionValues = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.OptionValues
/// Swift export omits the model's hash property because KotlinBase inherits NSObject.hash.
public typealias ResponseValues = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ResponseValues
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Staking = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Staking
/// Await cancellation stops observing a request; queued work keeps its own lifetime.
public typealias Submission = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Submission
/// Concrete table page avoids exporting the generic AptosSlice across the Swift boundary.
public typealias TablePage = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.TablePage
public typealias Tables = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Tables
/// Concrete throwing operations for Swift; models and business logic remain in the SDK.
public typealias Transactions = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Transactions
/// Raw JSON remains a UTF-8 string, preserving Aptos decimal strings for Swift Decodable.
public typealias Views = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Views
/// A client-owned pipeline. Call shutdown before closing its client or clearing its signer.
public typealias Worker = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Worker
/// Nullable fields are populated only for the corresponding event kind.
public typealias WorkerEvent = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEvent
public typealias WorkerEventKind = ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEventKind
extension ExportedKotlinPackages.xyz.mcxross.kaptos.swift {
    /// Stable categories shared with the Kotlin result API.
    public enum ErrorCategory: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Transport
        case Api
        case Indexer
        case Validation
        case Serialization
        case Crypto
        case Timeout
        case UnsupportedFeature
        public var description: Swift.String {
            get {
                switch self {
                case .Transport: "Transport"
                case .Api: "Api"
                case .Indexer: "Indexer"
                case .Validation: "Validation"
                case .Serialization: "Serialization"
                case .Crypto: "Crypto"
                case .Timeout: "Timeout"
                case .UnsupportedFeature: "UnsupportedFeature"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Transport: 0
                case .Api: 1
                case .Indexer: 2
                case .Validation: 3
                case .Serialization: 4
                case .Crypto: 5
                case .Timeout: 6
                case .UnsupportedFeature: 7
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Transport": self = .Transport
            case "Api": self = .Api
            case "Indexer": self = .Indexer
            case "Validation": self = .Validation
            case "Serialization": self = .Serialization
            case "Crypto": self = .Crypto
            case "Timeout": self = .Timeout
            case "UnsupportedFeature": self = .UnsupportedFeature
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<8 ~= rawValue else { return nil }
            self = ErrorCategory.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_swift_ErrorCategory_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Transport
            case 1: self = .Api
            case 2: self = .Indexer
            case 3: self = .Validation
            case 4: self = .Serialization
            case 5: self = .Crypto
            case 6: self = .Timeout
            case 7: self = .UnsupportedFeature
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Transport: xyz_mcxross_kaptos_swift_ErrorCategory_Transport()
            case .Api: xyz_mcxross_kaptos_swift_ErrorCategory_Api()
            case .Indexer: xyz_mcxross_kaptos_swift_ErrorCategory_Indexer()
            case .Validation: xyz_mcxross_kaptos_swift_ErrorCategory_Validation()
            case .Serialization: xyz_mcxross_kaptos_swift_ErrorCategory_Serialization()
            case .Crypto: xyz_mcxross_kaptos_swift_ErrorCategory_Crypto()
            case .Timeout: xyz_mcxross_kaptos_swift_ErrorCategory_Timeout()
            case .UnsupportedFeature: xyz_mcxross_kaptos_swift_ErrorCategory_UnsupportedFeature()
            default: fatalError()
            }
        }
    }
    public enum WorkerEventKind: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Queued
        case Building
        case Submitted
        case Failed
        case Cancelled
        case Drained
        public var description: Swift.String {
            get {
                switch self {
                case .Queued: "Queued"
                case .Building: "Building"
                case .Submitted: "Submitted"
                case .Failed: "Failed"
                case .Cancelled: "Cancelled"
                case .Drained: "Drained"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Queued: 0
                case .Building: 1
                case .Submitted: 2
                case .Failed: 3
                case .Cancelled: 4
                case .Drained: 5
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Queued": self = .Queued
            case "Building": self = .Building
            case "Submitted": self = .Submitted
            case "Failed": self = .Failed
            case "Cancelled": self = .Cancelled
            case "Drained": self = .Drained
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<6 ~= rawValue else { return nil }
            self = WorkerEventKind.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch xyz_mcxross_kaptos_swift_WorkerEventKind_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Queued
            case 1: self = .Building
            case 2: self = .Submitted
            case 3: self = .Failed
            case 4: self = .Cancelled
            case 5: self = .Drained
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer {
            return switch self {
            case .Queued: xyz_mcxross_kaptos_swift_WorkerEventKind_Queued()
            case .Building: xyz_mcxross_kaptos_swift_WorkerEventKind_Building()
            case .Submitted: xyz_mcxross_kaptos_swift_WorkerEventKind_Submitted()
            case .Failed: xyz_mcxross_kaptos_swift_WorkerEventKind_Failed()
            case .Cancelled: xyz_mcxross_kaptos_swift_WorkerEventKind_Cancelled()
            case .Drained: xyz_mcxross_kaptos_swift_WorkerEventKind_Drained()
            default: fatalError()
            }
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Abstraction: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func buildDisable(
            accountAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            authenticationFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction?,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Abstraction_buildDisable__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), accountAddress.__externalRCRef(), authenticationFunction.map { it in it.__externalRCRef() } ?? nil, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildEnable(
            accountAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            authenticationFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Abstraction_buildEnable__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), accountAddress.__externalRCRef(), authenticationFunction.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func isEnabled(
            accountAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            authenticationFunction: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AuthenticationFunction
        ) async throws -> Swift.Bool {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Abstraction_isEnabled__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AuthenticationFunction__(self.__externalRCRef(), accountAddress.__externalRCRef(), authenticationFunction.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Bool) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func status(
            accountAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAbstractionStatus {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Abstraction_status__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), accountAddress.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAbstractionStatus) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Accounts: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func buildUnverifiedRotation(
            currentAccount: ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account,
            newPublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Accounts_buildUnverifiedRotation__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_account_Account_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AccountPublicKey_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), currentAccount.__externalRCRef(), newPublicKey.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildVerifiedRotation(
            currentAccount: ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account,
            newAccount: ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Accounts_buildVerifiedRotation__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_account_Account_ExportedKotlinPackages_xyz_mcxross_kaptos_account_Account_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), currentAccount.__externalRCRef(), newAccount.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func deriveOwned(
            signer: ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountLookupOptions
        ) async throws -> [ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account] {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Accounts_deriveOwned__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_account_Account_ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountLookupOptions__(self.__externalRCRef(), signer.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func findByPublicKey(
            publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.AccountPublicKey,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountLookupOptions
        ) async throws -> [ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountInfo] {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Accounts_findByPublicKey__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_AccountPublicKey_ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountLookupOptions__(self.__externalRCRef(), publicKey.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountInfo>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func get(
            address: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Accounts_get__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), address.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getBalance(
            address: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            asset: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAsset
        ) async throws -> Swift.UInt64 {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Accounts_getBalance__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset__(self.__externalRCRef(), address.__externalRCRef(), asset.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.UInt64) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    public final class Addresses: KotlinRuntime.KotlinBase {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Addresses {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Addresses.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Addresses_get())
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
        ) throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Addresses_parse__TypesOfArguments__Swift_String__(self.__externalRCRef(), value, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: _result)
        }
    }
    /// A concrete exported exception so Swift can catch errors without inspecting erased generics.
    public final class AptosException: ExportedKotlinPackages.kotlin.RuntimeException {
        public var apiCode: Swift.String? {
            get {
                return xyz_mcxross_kaptos_swift_AptosException_apiCode_get(self.__externalRCRef())
            }
        }
        public var category: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ErrorCategory {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ErrorCategory(__externalRCRefUnsafe: xyz_mcxross_kaptos_swift_AptosException_category_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var vmCode: Swift.Int64? {
            get {
                return xyz_mcxross_kaptos_swift_AptosException_vmCode_get(self.__externalRCRef()).map { it in it.int64Value }
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
    }
    /// Copying conversion avoids signed-byte truncation and unsupported ByteArray constructors.
    public final class ByteCodec: KotlinRuntime.KotlinBase {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ByteCodec {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ByteCodec.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_ByteCodec_get())
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
        public func fromOctets(
            values: [Swift.Int32]
        ) throws -> ExportedKotlinPackages.kotlin.ByteArray {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_ByteCodec_fromOctets__TypesOfArguments__Swift_Array_Swift_Int32___(self.__externalRCRef(), values.map { it in NSNumber(value: it) }, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: _result)
        }
        public func toOctets(
            bytes: ExportedKotlinPackages.kotlin.ByteArray
        ) -> [Swift.Int32] {
            return xyz_mcxross_kaptos_swift_ByteCodec_toOctets__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef()) as! Swift.Array<Swift.Int32>
        }
    }
    /// A wallet or custody signer owned by Swift. Kotlin retains these callbacks while this signer is
    /// reachable. Swift export propagates coroutine cancellation in both directions. The transaction
    /// callback must sign [UnsignedTransaction.signingMessage], including its domain.
    public final class CallbackSigner: KotlinRuntime.KotlinBase, ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner, ExportedKotlinPackages.xyz.mcxross.kaptos.account.__TransactionSigner {
        public var accountAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_CallbackSigner_accountAddress_get(self.__externalRCRef()))
            }
        }
        public var publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_CallbackSigner_publicKey_get(self.__externalRCRef()))
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            accountAddress: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress,
            publicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey,
            bytes: @escaping (ExportedKotlinPackages.kotlin.ByteArray) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature,
            text: @escaping (Swift.String) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature,
            transaction: @escaping (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
        ) {
            let __kt = xyz_mcxross_kaptos_swift_CallbackSigner_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_swift_CallbackSigner_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey_U28ExportedKotlinPackages_kotlin_ByteArrayU2920asyncU20throwsU202D_U20ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature_U28Swift_StringU2920asyncU20throwsU202D_U20ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature_U28anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransactionU2920asyncU20throwsU202D_U20anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__(__kt, accountAddress.__externalRCRef(), publicKey.__externalRCRef(), Unmanaged.passRetained((bytes as (ExportedKotlinPackages.kotlin.ByteArray) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature) as AnyObject).toOpaque(), Unmanaged.passRetained((text as (Swift.String) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature) as AnyObject).toOpaque(), Unmanaged.passRetained((transaction as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator) as AnyObject).toOpaque()); return () }()
        }
        /// Signs arbitrary bytes without imposing a display encoding.
        public func signBytes(
            message: ExportedKotlinPackages.kotlin.ByteArray
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_CallbackSigner_signBytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), message.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        /// Signs UTF-8 text. Wallet implementations may present this text to the user.
        public func signText(
            message: Swift.String
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_CallbackSigner_signText__TypesOfArguments__Swift_String__(self.__externalRCRef(), message, Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        /// Signs the correct domain-separated message for [transaction].
        public func signTransaction(
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_CallbackSigner_signTransaction__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(self.__externalRCRef(), transaction.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        /// Verifies [signature] against arbitrary bytes using this signer's public key.
        public func verifySignature(
            message: ExportedKotlinPackages.kotlin.ByteArray,
            signature: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
        ) -> Swift.Bool {
            return xyz_mcxross_kaptos_swift_CallbackSigner_verifySignature__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(self.__externalRCRef(), message.__externalRCRef(), signature.__externalRCRef())
        }
    }
    /// Concrete throwing service boundary for Swift export. All requests, encoding, signing, and
    /// lifecycle ownership delegate to [Aptos]. Confine a client and its accounts to one Swift actor.
    public final class Client: KotlinRuntime.KotlinBase {
        public var abstraction: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Abstraction {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Abstraction.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_abstraction_get(self.__externalRCRef()))
            }
        }
        public var accounts: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Accounts {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Accounts.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_accounts_get(self.__externalRCRef()))
            }
        }
        public var coins: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Coins {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Coins.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_coins_get(self.__externalRCRef()))
            }
        }
        public var confidentialAssets: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialAssets {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialAssets.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_confidentialAssets_get(self.__externalRCRef()))
            }
        }
        public var digitalAssets: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.DigitalAssets {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.DigitalAssets.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_digitalAssets_get(self.__externalRCRef()))
            }
        }
        public var encryptedTransactions: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.EncryptedTransactions {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.EncryptedTransactions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_encryptedTransactions_get(self.__externalRCRef()))
            }
        }
        public var faucet: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Faucet {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Faucet.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_faucet_get(self.__externalRCRef()))
            }
        }
        public var fungibleAssets: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.FungibleAssets {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.FungibleAssets.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_fungibleAssets_get(self.__externalRCRef()))
            }
        }
        public var indexer: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Indexer {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Indexer.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_indexer_get(self.__externalRCRef()))
            }
        }
        public var ledger: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Ledger {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Ledger.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_ledger_get(self.__externalRCRef()))
            }
        }
        public var names: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Names {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Names.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_names_get(self.__externalRCRef()))
            }
        }
        public var objects: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Objects {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Objects.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_objects_get(self.__externalRCRef()))
            }
        }
        public var staking: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Staking {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Staking.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_staking_get(self.__externalRCRef()))
            }
        }
        public var tables: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Tables {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Tables.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_tables_get(self.__externalRCRef()))
            }
        }
        public var transactions: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Transactions {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Transactions.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_transactions_get(self.__externalRCRef()))
            }
        }
        public var views: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Views {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Views.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Client_views_get(self.__externalRCRef()))
            }
        }
        public init(
            configuration: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Configuration
        ) throws {
            let __kt = xyz_mcxross_kaptos_swift_Client_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            var __error: UnsafeMutableRawPointer? = nil
            xyz_mcxross_kaptos_swift_Client_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_swift_Configuration__(__kt, configuration.__externalRCRef(), &__error)
            try KotlinRuntimeSupport.raiseKotlinError(__error)
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func clearAbiCache() async throws -> Swift.Void {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Client_clearAbiCache(self.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Void) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        /// Idempotently clears owned private keys and closes the shared Darwin HTTP transport.
        public func close() throws -> Swift.Void {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Client_close(self.__externalRCRef(), &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return { _result; return () }()
        }
        /// Stops workers before clearing accounts and closing the transport.
        public func closeAsync() async throws -> Swift.Void {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Client_closeAsync(self.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Void) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        /// Local accounts are owned by this client and cleared when it closes.
        public func generateAccount(
            scheme: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningSchemeInput
        ) throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Client_generateAccount__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_SigningSchemeInput__(self.__externalRCRef(), scheme.__externalRCRef(), &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: _result)
        }
        public func generateConfidentialKey() throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Client_generateConfidentialKey(self.__externalRCRef(), &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey.__createClassWrapper(externalRCRef: _result)
        }
        /// Accepts an algorithm-prefixed AIP-80 key and an optional rotated account address.
        public func importAccount(
            privateKey: Swift.String,
            address: Swift.String?
        ) throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Client_importAccount__TypesOfArguments__Swift_String_Swift_Optional_Swift_String___(self.__externalRCRef(), privateKey, address ?? nil, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account.__createClassWrapper(externalRCRef: _result)
        }
        public func worker(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            capacity: Swift.Int32
        ) throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Worker {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Client_worker__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_Swift_Int32__(self.__externalRCRef(), signer.__externalRCRef(), capacity, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Worker.__createClassWrapper(externalRCRef: _result)
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Coins: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func buildTransfer(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            recipient: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            amount: Swift.UInt64,
            coinType: Swift.String,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Coins_buildTransfer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_UInt64_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), recipient.__externalRCRef(), amount, coinType, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    public final class ConfidentialActivityPage: KotlinRuntime.KotlinBase {
        public var hasNextPage: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_swift_ConfidentialActivityPage_hasNextPage_get(self.__externalRCRef())
            }
        }
        public var items: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetActivity] {
            get {
                return xyz_mcxross_kaptos_swift_ConfidentialActivityPage_items_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetActivity>
            }
        }
        public var request: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_ConfidentialActivityPage_request_get(self.__externalRCRef()))
            }
        }
        public var totalCount: Swift.Int32 {
            get {
                return xyz_mcxross_kaptos_swift_ConfidentialActivityPage_totalCount_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            items: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetActivity],
            totalCount: Swift.Int32,
            request: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest,
            hasNextPage: Swift.Bool
        ) {
            let __kt = xyz_mcxross_kaptos_swift_ConfidentialActivityPage_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_swift_ConfidentialActivityPage_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity__Swift_Int32_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest_Swift_Bool__(__kt, items, totalCount, request.__externalRCRef(), hasNextPage); return () }()
        }
        public func copy(
            items: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetActivity],
            totalCount: Swift.Int32,
            request: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest,
            hasNextPage: Swift.Bool
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialActivityPage {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialActivityPage.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_ConfidentialActivityPage_copy__TypesOfArguments__Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialAssetActivity__Swift_Int32_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest_Swift_Bool__(self.__externalRCRef(), items, totalCount, request.__externalRCRef(), hasNextPage))
        }
    }
    /// Concrete throwing boundary for the optional ConfidentialAssets module.
    public final class ConfidentialAssets: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func buildDeposit(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            amount: Swift.UInt64,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_buildDeposit__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), token.__externalRCRef(), amount, options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildNormalizeBalance(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_buildNormalizeBalance__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildRegisterBalance(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_buildRegisterBalance__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildRolloverPendingBalance(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            pauseIncoming: Swift.Bool,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_buildRolloverPendingBalance__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Bool_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), token.__externalRCRef(), pauseIncoming, options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildRotateEncryptionKey(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            currentKey: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            newKey: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            unpauseIncoming: Swift.Bool,
            requireEmptyPendingBalance: Swift.Bool,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_buildRotateEncryptionKey__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Swift_Bool_Swift_Bool_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), token.__externalRCRef(), currentKey.__externalRCRef(), newKey.__externalRCRef(), unpauseIncoming, requireEmptyPendingBalance, options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildSetIncomingTransfersPaused(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            paused: Swift.Bool,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_buildSetIncomingTransfersPaused__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Bool_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), token.__externalRCRef(), paused, options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildTransfer(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            recipient: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            amount: Swift.UInt64,
            voluntaryAuditors: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey],
            memo: ExportedKotlinPackages.kotlin.ByteArray,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_buildTransfer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Swift_UInt64_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), recipient.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), amount, voluntaryAuditors, memo.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildWithdraw(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            amount: Swift.UInt64,
            recipient: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_buildWithdraw__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Swift_UInt64_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), amount, recipient.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func clearCache() async throws -> Swift.Void {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_clearCache(self.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Void) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func deposit(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            amount: Swift.UInt64,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_deposit__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), token.__externalRCRef(), amount, options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getActivities(
            query: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialActivityQuery
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialActivityPage {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_getActivities__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialActivityQuery__(self.__externalRCRef(), query.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialActivityPage) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getAssetAuditorEncryptionKey(
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            useCache: Swift.Bool
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_getAssetAuditorEncryptionKey__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Bool__(self.__externalRCRef(), token.__externalRCRef(), useCache, Unmanaged.passRetained((continuation as (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getBalance(
            account: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            useCache: Swift.Bool
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialBalance {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_getBalance__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Swift_Bool__(self.__externalRCRef(), account.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), useCache, Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialBalance) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getEffectiveAuditorHint(
            account: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.EffectiveAuditorHint? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_getEffectiveAuditorHint__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), account.__externalRCRef(), token.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.EffectiveAuditorHint>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getEncryptionKey(
            account: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            useCache: Swift.Bool
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_getEncryptionKey__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Bool__(self.__externalRCRef(), account.__externalRCRef(), token.__externalRCRef(), useCache, Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getStatus(
            account: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetStatus {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_getStatus__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), account.__externalRCRef(), token.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetStatus) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func isEmergencyPaused() async throws -> Swift.Bool {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_isEmergencyPaused(self.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Bool) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func normalizeBalance(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_normalizeBalance__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func registerBalance(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_registerBalance__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func rolloverPendingBalance(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            pauseIncoming: Swift.Bool,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_rolloverPendingBalance__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Bool_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), token.__externalRCRef(), pauseIncoming, options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func rotateEncryptionKey(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            currentKey: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            newKey: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            unpauseIncoming: Swift.Bool,
            requireEmptyPendingBalance: Swift.Bool,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_rotateEncryptionKey__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Swift_Bool_Swift_Bool_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), token.__externalRCRef(), currentKey.__externalRCRef(), newKey.__externalRCRef(), unpauseIncoming, requireEmptyPendingBalance, options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func setIncomingTransfersPaused(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            paused: Swift.Bool,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_setIncomingTransfersPaused__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Bool_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), token.__externalRCRef(), paused, options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func transfer(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            recipient: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            amount: Swift.UInt64,
            voluntaryAuditors: [ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey],
            memo: ExportedKotlinPackages.kotlin.ByteArray,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_transfer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Swift_UInt64_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey__ExportedKotlinPackages_kotlin_ByteArray_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), recipient.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), amount, voluntaryAuditors, memo.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func withdraw(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            token: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey,
            amount: Swift.UInt64,
            recipient: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_ConfidentialAssets_withdraw__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialDecryptionKey_Swift_UInt64_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), token.__externalRCRef(), key.__externalRCRef(), amount, recipient.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Swift configuration keeps transport and serializer implementation types out of call sites.
    public final class Configuration: KotlinRuntime.KotlinBase {
        public var expirationSecondsFromNow: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_swift_Configuration_expirationSecondsFromNow_get(self.__externalRCRef())
            }
        }
        public var faucet: Swift.String? {
            get {
                return xyz_mcxross_kaptos_swift_Configuration_faucet_get(self.__externalRCRef())
            }
        }
        public var fullNode: Swift.String? {
            get {
                return xyz_mcxross_kaptos_swift_Configuration_fullNode_get(self.__externalRCRef())
            }
        }
        public var headers: [Swift.String: Swift.String] {
            get {
                return xyz_mcxross_kaptos_swift_Configuration_headers_get(self.__externalRCRef()) as! Swift.Dictionary<Swift.String,Swift.String>
            }
        }
        public var indexer: Swift.String? {
            get {
                return xyz_mcxross_kaptos_swift_Configuration_indexer_get(self.__externalRCRef())
            }
        }
        public var indexerWaitTimeoutMillis: Swift.Int64 {
            get {
                return xyz_mcxross_kaptos_swift_Configuration_indexerWaitTimeoutMillis_get(self.__externalRCRef())
            }
        }
        public var maxGasAmount: Swift.UInt64 {
            get {
                return xyz_mcxross_kaptos_swift_Configuration_maxGasAmount_get(self.__externalRCRef())
            }
        }
        public var network: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Network {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.model.Network(__externalRCRefUnsafe: xyz_mcxross_kaptos_swift_Configuration_network_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var requestTimeoutMillis: Swift.Int64 {
            get {
                return xyz_mcxross_kaptos_swift_Configuration_requestTimeoutMillis_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            network: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Network,
            fullNode: Swift.String?,
            indexer: Swift.String?,
            faucet: Swift.String?,
            headers: [Swift.String: Swift.String],
            requestTimeoutMillis: Swift.Int64,
            indexerWaitTimeoutMillis: Swift.Int64,
            maxGasAmount: Swift.UInt64,
            expirationSecondsFromNow: Swift.UInt64
        ) {
            let __kt = xyz_mcxross_kaptos_swift_Configuration_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_swift_Configuration_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_Network_Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Dictionary_Swift_String_Swift_String__Swift_Int64_Swift_Int64_Swift_UInt64_Swift_UInt64__(__kt, network.__externalRCRef(), fullNode ?? nil, indexer ?? nil, faucet ?? nil, headers, requestTimeoutMillis, indexerWaitTimeoutMillis, maxGasAmount, expirationSecondsFromNow); return () }()
        }
        public func copy(
            network: ExportedKotlinPackages.xyz.mcxross.kaptos.model.Network,
            fullNode: Swift.String?,
            indexer: Swift.String?,
            faucet: Swift.String?,
            headers: [Swift.String: Swift.String],
            requestTimeoutMillis: Swift.Int64,
            indexerWaitTimeoutMillis: Swift.Int64,
            maxGasAmount: Swift.UInt64,
            expirationSecondsFromNow: Swift.UInt64
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Configuration {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Configuration.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Configuration_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_Network_Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Dictionary_Swift_String_Swift_String__Swift_Int64_Swift_Int64_Swift_UInt64_Swift_UInt64__(self.__externalRCRef(), network.__externalRCRef(), fullNode ?? nil, indexer ?? nil, faucet ?? nil, headers, requestTimeoutMillis, indexerWaitTimeoutMillis, maxGasAmount, expirationSecondsFromNow))
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class DigitalAssets: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func buildAddProperty(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            property: ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetProperty,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildAddProperty__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), property.__externalRCRef(), assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildBurn(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildBurn__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildCreateCollection(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            name: Swift.String,
            description: Swift.String,
            uri: Swift.String,
            collection: ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetCollectionOptions,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildCreateCollection__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_Swift_String_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetCollectionOptions_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), name, description, uri, collection.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildFreezeTransfer(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildFreezeTransfer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildMint(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            collection: Swift.String,
            name: Swift.String,
            description: Swift.String,
            uri: Swift.String,
            properties: [ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetProperty],
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildMint__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), collection, name, description, uri, properties, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildMintSoulbound(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            recipient: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            collection: Swift.String,
            name: Swift.String,
            description: Swift.String,
            uri: Swift.String,
            properties: [ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetProperty],
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildMintSoulbound__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_Swift_String_Swift_String_Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), recipient.__externalRCRef(), collection, name, description, uri, properties, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildRemoveProperty(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            key: Swift.String,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildRemoveProperty__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), key, assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildSetDescription(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            description: Swift.String,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildSetDescription__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), description, assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildSetName(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            name: Swift.String,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildSetName__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), name, assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildSetUri(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            uri: Swift.String,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildSetUri__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), uri, assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildTransfer(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            recipient: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildTransfer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), recipient.__externalRCRef(), assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildUnfreezeTransfer(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildUnfreezeTransfer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildUpdateProperty(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            property: ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.DigitalAssetProperty,
            assetType: ExportedKotlinPackages.xyz.mcxross.kaptos.model.StructTag,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_buildUpdateProperty__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_DigitalAssetProperty_ExportedKotlinPackages_xyz_mcxross_kaptos_model_StructTag_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), assetId.__externalRCRef(), property.__externalRCRef(), assetType.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getActivity(
            assetId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_getActivity__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), assetId.__externalRCRef(), page.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getCollection(
            collectionId: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.CollectionRecord? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_getCollection__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), collectionId.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.CollectionRecord>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getCollection(
            creator: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            name: Swift.String
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.CollectionRecord? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_getCollection__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String__(self.__externalRCRef(), creator.__externalRCRef(), name, Unmanaged.passRetained((continuation as (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.CollectionRecord>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getOwned(
            owner: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_DigitalAssets_getOwned__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), owner.__externalRCRef(), page.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Concrete throwing boundary for the optional EncryptedTransactions module.
    public final class EncryptedTransactions: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func clearCache() async throws -> Swift.Void {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_EncryptedTransactions_clearCache(self.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Void) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func encrypt(
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.encrypted.EncryptedTransactionOptions
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_EncryptedTransactions_encrypt__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_ExportedKotlinPackages_xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions__(self.__externalRCRef(), transaction.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func signAndSubmit(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            transactionOptions: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions?,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.encrypted.EncryptedTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_EncryptedTransactions_signAndSubmit__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__ExportedKotlinPackages_xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), payload.__externalRCRef(), transactionOptions.map { it in it.__externalRCRef() } ?? nil, options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func signAndSubmit(
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction,
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            secondarySigners: [any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner],
            feePayer: (any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner)?,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.encrypted.EncryptedTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_EncryptedTransactions_signAndSubmit__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner__ExportedKotlinPackages_xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions__(self.__externalRCRef(), transaction.__externalRCRef(), sender.__externalRCRef(), secondarySigners, feePayer.map { it in it.__externalRCRef() } ?? nil, options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func submitAndWait(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            transactionOptions: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions?,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.encrypted.EncryptedTransactionOptions,
            waitOptions: ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_EncryptedTransactions_submitAndWait__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__ExportedKotlinPackages_xyz_mcxross_kaptos_encrypted_EncryptedTransactionOptions_ExportedKotlinPackages_xyz_mcxross_kaptos_model_WaitForTransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), payload.__externalRCRef(), transactionOptions.map { it in it.__externalRCRef() } ?? nil, options.__externalRCRef(), waitOptions.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Faucet: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func fund(
            address: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            amount: Swift.UInt64,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Faucet_fund__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_model_WaitForTransactionOptions__(self.__externalRCRef(), address.__externalRCRef(), amount, options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class FungibleAssets: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func buildStoreTransfer(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            fromStore: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            toStore: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            amount: Swift.UInt64,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_FungibleAssets_buildStoreTransfer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), fromStore.__externalRCRef(), toStore.__externalRCRef(), amount, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildTransfer(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            metadataAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            recipient: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            amount: Swift.UInt64,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_FungibleAssets_buildTransfer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_UInt64_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), metadataAddress.__externalRCRef(), recipient.__externalRCRef(), amount, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    public final class Indexer: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func query(
            document: Swift.String,
            variablesJson: Swift.String
        ) async throws -> Swift.String {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Indexer_query__TypesOfArguments__Swift_String_Swift_String__(self.__externalRCRef(), document, variablesJson, Unmanaged.passRetained((continuation as (Swift.String) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Ledger: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func blockAtHeight(
            height: Swift.UInt64
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Ledger_blockAtHeight__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), height, Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func blockAtVersion(
            version: Swift.UInt64
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Ledger_blockAtVersion__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), version, Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func info() async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerState {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Ledger_info(self.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerState) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Explicit Move values; large integers use validated decimal strings without floating point.
    public final class Move: KotlinRuntime.KotlinBase {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Move {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Move.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_get())
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
        public func address(
            value: Swift.String
        ) throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Move_address__TypesOfArguments__Swift_String__(self.__externalRCRef(), value, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: _result, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func bool(
            value: Swift.Bool
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_bool__TypesOfArguments__Swift_Bool__(self.__externalRCRef(), value), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func bytes(
            value: ExportedKotlinPackages.kotlin.ByteArray
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_bytes__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), value.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func option(
            value: (any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument)?
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_option__TypesOfArguments__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func preSerialized(
            bcs: ExportedKotlinPackages.kotlin.ByteArray
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_preSerialized__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bcs.__externalRCRef()), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func string(
            value: Swift.String
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_string__TypesOfArguments__Swift_String__(self.__externalRCRef(), value), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func `struct`(
            fields: [Swift.String: any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_struct__TypesOfArguments__Swift_Dictionary_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), fields), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func u128(
            decimal: Swift.String
        ) throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Move_u128__TypesOfArguments__Swift_String__(self.__externalRCRef(), decimal, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: _result, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func u16(
            value: Swift.UInt16
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_u16__TypesOfArguments__Swift_UInt16__(self.__externalRCRef(), value), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func u256(
            decimal: Swift.String
        ) throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Move_u256__TypesOfArguments__Swift_String__(self.__externalRCRef(), decimal, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: _result, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func u32(
            value: Swift.UInt32
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_u32__TypesOfArguments__Swift_UInt32__(self.__externalRCRef(), value), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func u64(
            value: Swift.UInt64
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_u64__TypesOfArguments__Swift_UInt64__(self.__externalRCRef(), value), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func u8(
            value: Swift.UInt8
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_u8__TypesOfArguments__Swift_UInt8__(self.__externalRCRef(), value), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func variant(
            name: Swift.String,
            fields: [Swift.String: any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_variant__TypesOfArguments__Swift_String_Swift_Dictionary_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), name, fields), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
        public func vector(
            values: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
        ) -> any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Move_vector__TypesOfArguments__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), values), conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Names: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func buildClearTarget(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            name: Swift.String,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_buildClearTarget__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), name, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildRegister(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            name: Swift.String,
            expiration: any ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameExpirationPolicy,
            transferable: Swift.Bool,
            target: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?,
            owner: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_buildRegister__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameExpirationPolicy_Swift_Bool_Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), name, expiration.__externalRCRef(), transferable, target.map { it in it.__externalRCRef() } ?? nil, owner.map { it in it.__externalRCRef() } ?? nil, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildRenew(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            name: Swift.String,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_buildRenew__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), name, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildSetPrimary(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            name: Swift.String?,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_buildSetPrimary__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Optional_Swift_String__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), name ?? nil, options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildSetTarget(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            name: Swift.String,
            target: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_buildSetTarget__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_String_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__(self.__externalRCRef(), sender.__externalRCRef(), name, target.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func expiration(
            name: Swift.String
        ) async throws -> Swift.UInt64 {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_expiration__TypesOfArguments__Swift_String__(self.__externalRCRef(), name, Unmanaged.passRetained((continuation as (Swift.UInt64) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getAccountDomains(
            accountAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_getAccountDomains__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), accountAddress.__externalRCRef(), page.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getAccountNames(
            accountAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_getAccountNames__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), accountAddress.__externalRCRef(), page.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getAccountSubdomains(
            accountAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_getAccountSubdomains__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), accountAddress.__externalRCRef(), page.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getDomainSubdomains(
            domain: Swift.String,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_getDomainSubdomains__TypesOfArguments__Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), domain, page.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getName(
            name: Swift.String
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameRecord? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_getName__TypesOfArguments__Swift_String__(self.__externalRCRef(), name, Unmanaged.passRetained((continuation as (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameRecord>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func owner(
            name: Swift.String
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_owner__TypesOfArguments__Swift_String__(self.__externalRCRef(), name, Unmanaged.passRetained((continuation as (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func primaryName(
            accountAddress: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> Swift.String? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_primaryName__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), accountAddress.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Optional<Swift.String>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func target(
            name: Swift.String
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Names_target__TypesOfArguments__Swift_String__(self.__externalRCRef(), name, Unmanaged.passRetained((continuation as (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Objects: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func get(
            address: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.objects.ObjectRecord? {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Objects_get__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), address.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.objects.ObjectRecord>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Avoids Beta2's NSNumber-to-nullable-unsigned input bridge. Swift conveniences accept UInt64?.
    public final class OptionValues: KotlinRuntime.KotlinBase {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.OptionValues {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.OptionValues.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_OptionValues_get())
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
        public func transaction(
            maxGasAmountDecimal: Swift.String?,
            gasUnitPriceDecimal: Swift.String?,
            expirationTimestampSecsDecimal: Swift.String?,
            expirationSecondsFromNowDecimal: Swift.String?,
            sequenceNumberDecimal: Swift.String?,
            nonceDecimal: Swift.String?
        ) throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_OptionValues_transaction__TypesOfArguments__Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Optional_Swift_String__Swift_Optional_Swift_String___(self.__externalRCRef(), maxGasAmountDecimal ?? nil, gasUnitPriceDecimal ?? nil, expirationTimestampSecsDecimal ?? nil, expirationSecondsFromNowDecimal ?? nil, sequenceNumberDecimal ?? nil, nonceDecimal ?? nil, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions.__createClassWrapper(externalRCRef: _result)
        }
    }
    /// Swift export omits the model's hash property because KotlinBase inherits NSObject.hash.
    public final class ResponseValues: KotlinRuntime.KotlinBase {
        public static var shared: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ResponseValues {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ResponseValues.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_ResponseValues_get())
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
        public func transactionHash(
            response: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse
        ) -> Swift.String {
            return xyz_mcxross_kaptos_swift_ResponseValues_transactionHash__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponse__(self.__externalRCRef(), response.__externalRCRef())
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Staking: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func getActivities(
            pool: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            delegator: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> [ExportedKotlinPackages.xyz.mcxross.kaptos.staking.DelegatedStakingActivity] {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Staking_getActivities__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), pool.__externalRCRef(), delegator.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.staking.DelegatedStakingActivity>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getDelegatorCount(
            pool: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput
        ) async throws -> Swift.UInt64 {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Staking_getDelegatorCount__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__(self.__externalRCRef(), pool.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.UInt64) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getDelegatorCounts() async throws -> [ExportedKotlinPackages.xyz.mcxross.kaptos.staking.StakingPoolDelegatorCount] {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Staking_getDelegatorCounts(self.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.staking.StakingPoolDelegatorCount>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Await cancellation stops observing a request; queued work keeps its own lifetime.
    public final class Submission: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func `await`() async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Submission_await(self.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Concrete table page avoids exporting the generic AptosSlice across the Swift boundary.
    public final class TablePage: KotlinRuntime.KotlinBase {
        public var hasNextPage: Swift.Bool {
            get {
                return xyz_mcxross_kaptos_swift_TablePage_hasNextPage_get(self.__externalRCRef())
            }
        }
        public var items: [ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableItem] {
            get {
                return xyz_mcxross_kaptos_swift_TablePage_items_get(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableItem>
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            items: [ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableItem],
            hasNextPage: Swift.Bool
        ) {
            let __kt = xyz_mcxross_kaptos_swift_TablePage_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { xyz_mcxross_kaptos_swift_TablePage_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_table_TableItem__Swift_Bool__(__kt, items, hasNextPage); return () }()
        }
        public func copy(
            items: [ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableItem],
            hasNextPage: Swift.Bool
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.swift.TablePage {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.TablePage.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_TablePage_copy__TypesOfArguments__Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_table_TableItem__Swift_Bool__(self.__externalRCRef(), items, hasNextPage))
        }
    }
    public final class Tables: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func getItem(
            handle: Swift.String,
            keyType: Swift.String,
            valueType: Swift.String,
            keyJson: Swift.String,
            ledgerVersionDecimal: Swift.String?
        ) async throws -> Swift.String {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Tables_getItem__TypesOfArguments__Swift_String_Swift_String_Swift_String_Swift_String_Swift_Optional_Swift_String___(self.__externalRCRef(), handle, keyType, valueType, keyJson, ledgerVersionDecimal ?? nil, Unmanaged.passRetained((continuation as (Swift.String) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getItems(
            handle: Swift.String,
            page: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PageRequest
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.swift.TablePage {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Tables_getItems__TypesOfArguments__Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PageRequest__(self.__externalRCRef(), handle, page.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.swift.TablePage) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func getMetadata(
            handle: Swift.String
        ) async throws -> [ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableMetadata] {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Tables_getMetadata__TypesOfArguments__Swift_String__(self.__externalRCRef(), handle, Unmanaged.passRetained((continuation as (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableMetadata>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Concrete throwing operations for Swift; models and business logic remain in the SDK.
    public final class Transactions: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func build(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions?
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_build__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions___(self.__externalRCRef(), sender.__externalRCRef(), payload.__externalRCRef(), options.map { it in it.__externalRCRef() } ?? nil, Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildFeePayer(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            secondarySigners: [any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput],
            feePayer: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput)?,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions?
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_buildFeePayer__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions___(self.__externalRCRef(), sender.__externalRCRef(), payload.__externalRCRef(), secondarySigners, feePayer.map { it in it.__externalRCRef() } ?? nil, options.map { it in it.__externalRCRef() } ?? nil, Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func buildMultiAgent(
            sender: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput,
            secondarySigners: [any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddressInput],
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions?
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_buildMultiAgent__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddressInput__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions___(self.__externalRCRef(), sender.__externalRCRef(), secondarySigners, payload.__externalRCRef(), options.map { it in it.__externalRCRef() } ?? nil, Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func entryFunctionPayload(
            function: Swift.String,
            typeArguments: [ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag],
            arguments: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument]
        ) async throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_entryFunctionPayload__TypesOfArguments__Swift_String_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TypeTag__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument___(self.__externalRCRef(), function, typeArguments, arguments, Unmanaged.passRetained((continuation as (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func externalFeePayerRequest(
            transaction: XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer,
            senderAuthenticator: any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator,
            secondaryAuthenticators: [any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator]
        ) throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.ExternalFeePayerRequest {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Transactions_externalFeePayerRequest__TypesOfArguments__XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator___(self.__externalRCRef(), transaction.__externalRCRef(), senderAuthenticator.__externalRCRef(), secondaryAuthenticators, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return ExportedKotlinPackages.xyz.mcxross.kaptos.model.ExternalFeePayerRequest.__createClassWrapper(externalRCRef: _result)
        }
        /// Imports canonical RawTransaction BCS, preserving the original network and expiration fields.
        public func fromBcs(
            bytes: ExportedKotlinPackages.kotlin.ByteArray
        ) throws -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Transactions_fromBcs__TypesOfArguments__ExportedKotlinPackages_kotlin_ByteArray__(self.__externalRCRef(), bytes.__externalRCRef(), &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple.__createClassWrapper(externalRCRef: _result)
        }
        public func sign(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
        ) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_sign__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(self.__externalRCRef(), signer.__externalRCRef(), transaction.__externalRCRef(), Unmanaged.passRetained((continuation as (any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func signAndSubmit(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction,
            secondaryAuthenticators: [any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator],
            feePayerAuthenticator: (any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator)?
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_signAndSubmit__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator___(self.__externalRCRef(), signer.__externalRCRef(), transaction.__externalRCRef(), secondaryAuthenticators, feePayerAuthenticator.map { it in it.__externalRCRef() } ?? nil, Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func signAndSubmit(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions?,
            secondarySigners: [any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner],
            feePayer: (any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner)?
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_signAndSubmit__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner___(self.__externalRCRef(), signer.__externalRCRef(), payload.__externalRCRef(), options.map { it in it.__externalRCRef() } ?? nil, secondarySigners, feePayer.map { it in it.__externalRCRef() } ?? nil, Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func simulate(
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction,
            senderPublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey,
            secondarySignerPublicKeys: [ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey],
            feePayerPublicKey: ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey?,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SimulationOptions
        ) async throws -> [ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse] {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_simulate__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_PublicKey__ExportedKotlinPackages_xyz_mcxross_kaptos_model_SimulationOptions__(self.__externalRCRef(), transaction.__externalRCRef(), senderPublicKey.__externalRCRef(), secondarySignerPublicKeys, feePayerPublicKey.map { it in it.__externalRCRef() } ?? nil, options.__externalRCRef(), Unmanaged.passRetained((continuation as (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse>) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func submit(
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction,
            senderAuthenticator: any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator,
            secondaryAuthenticators: [any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator],
            feePayerAuthenticator: (any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator)?
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_submit__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator___(self.__externalRCRef(), transaction.__externalRCRef(), senderAuthenticator.__externalRCRef(), secondaryAuthenticators, feePayerAuthenticator.map { it in it.__externalRCRef() } ?? nil, Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func submitAndWait(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction,
            secondaryAuthenticators: [any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator],
            feePayerAuthenticator: (any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator)?,
            waitOptions: ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_submitAndWait__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__ExportedKotlinPackages_xyz_mcxross_kaptos_model_WaitForTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), transaction.__externalRCRef(), secondaryAuthenticators, feePayerAuthenticator.map { it in it.__externalRCRef() } ?? nil, waitOptions.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func submitAndWait(
            signer: any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner,
            payload: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload,
            transactionOptions: ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions?,
            secondarySigners: [any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner],
            feePayer: (any ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner)?,
            waitOptions: ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_submitAndWait__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionOptions__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_account_TransactionSigner__ExportedKotlinPackages_xyz_mcxross_kaptos_model_WaitForTransactionOptions__(self.__externalRCRef(), signer.__externalRCRef(), payload.__externalRCRef(), transactionOptions.map { it in it.__externalRCRef() } ?? nil, secondarySigners, feePayer.map { it in it.__externalRCRef() } ?? nil, waitOptions.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func userTransactionHash(
            transaction: any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction,
            senderAuthenticator: any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator,
            secondaryAuthenticators: [any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator],
            feePayerAuthenticator: (any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator)?
        ) throws -> Swift.String {
            var _out_error: UnsafeMutableRawPointer? = nil
            let _result = xyz_mcxross_kaptos_swift_Transactions_userTransactionHash__TypesOfArguments__anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator_Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__Swift_Optional_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator___(self.__externalRCRef(), transaction.__externalRCRef(), senderAuthenticator.__externalRCRef(), secondaryAuthenticators, feePayerAuthenticator.map { it in it.__externalRCRef() } ?? nil, &_out_error)
            try KotlinRuntimeSupport.raiseKotlinError(_out_error)
            return _result
        }
        public func waitForTransaction(
            hash: Swift.String,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_waitForTransaction__TypesOfArguments__Swift_String_ExportedKotlinPackages_xyz_mcxross_kaptos_model_WaitForTransactionOptions__(self.__externalRCRef(), hash, options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func waitForTransaction(
            transaction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse,
            options: ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Transactions_waitForTransaction__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_model_PendingTransactionResponse_ExportedKotlinPackages_xyz_mcxross_kaptos_model_WaitForTransactionOptions__(self.__externalRCRef(), transaction.__externalRCRef(), options.__externalRCRef(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Raw JSON remains a UTF-8 string, preserving Aptos decimal strings for Swift Decodable.
    public final class Views: KotlinRuntime.KotlinBase {
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func call(
            function: Swift.String,
            typeArguments: [Swift.String],
            arguments: [any ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument],
            ledgerVersionDecimal: Swift.String?
        ) async throws -> Swift.String {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Views_call__TypesOfArguments__Swift_String_Swift_Array_Swift_String__Swift_Array_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_move_MoveArgument__Swift_Optional_Swift_String___(self.__externalRCRef(), function, typeArguments, arguments, ledgerVersionDecimal ?? nil, Unmanaged.passRetained((continuation as (Swift.String) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func callRaw(
            function: Swift.String,
            typeArguments: [Swift.String],
            argumentsJson: Swift.String,
            ledgerVersionDecimal: Swift.String?
        ) async throws -> Swift.String {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Views_callRaw__TypesOfArguments__Swift_String_Swift_Array_Swift_String__Swift_String_Swift_Optional_Swift_String___(self.__externalRCRef(), function, typeArguments, argumentsJson, ledgerVersionDecimal ?? nil, Unmanaged.passRetained((continuation as (Swift.String) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// A client-owned pipeline. Call shutdown before closing its client or clearing its signer.
    public final class Worker: KotlinRuntime.KotlinBase {
        /// Swift export maps this flow to KotlinTypedFlow and asAsyncSequence().
        public var events: any KotlinCoroutineSupport.KotlinTypedFlow<ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEvent> {
            get {
                return KotlinCoroutineSupport._KotlinTypedFlowImpl<ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEvent>.create(KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: xyz_mcxross_kaptos_swift_Worker_events_get(self.__externalRCRef()), conformsTo: ExportedKotlinPackages.kotlinx.coroutines.flow.Flow.Type.self) as! any ExportedKotlinPackages.kotlinx.coroutines.flow.Flow, ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEvent.Type.self)
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func shutdown(
            cancelPending: Swift.Bool
        ) async throws -> Swift.Void {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Worker_shutdown__TypesOfArguments__Swift_Bool__(self.__externalRCRef(), cancelPending, Unmanaged.passRetained((continuation as (Swift.Void) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
        public func submit(
            build: @escaping (Swift.UInt64) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
        ) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Submission {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = xyz_mcxross_kaptos_swift_Worker_submit__TypesOfArguments__U28Swift_UInt64U2920asyncU20throwsU202D_U20anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(self.__externalRCRef(), Unmanaged.passRetained((build as (Swift.UInt64) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) as AnyObject).toOpaque(), Unmanaged.passRetained((continuation as (ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Submission) -> Swift.Void) as AnyObject).toOpaque(), Unmanaged.passRetained((exception as (Swift.Optional<Swift.Error>) -> Swift.Void) as AnyObject).toOpaque(), cancellation.__externalRCRef())
            }
        }
    }
    /// Nullable fields are populated only for the corresponding event kind.
    public final class WorkerEvent: KotlinRuntime.KotlinBase {
        public var error: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.AptosException? {
            get {
                return { switch xyz_mcxross_kaptos_swift_WorkerEvent_error_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.AptosException.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        public var kind: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEventKind {
            get {
                return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEventKind(__externalRCRefUnsafe: xyz_mcxross_kaptos_swift_WorkerEvent_kind_get(self.__externalRCRef()), options: .asBestFittingWrapper)
            }
        }
        public var requestId: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_swift_WorkerEvent_requestId_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var sequenceNumber: Swift.UInt64? {
            get {
                return xyz_mcxross_kaptos_swift_WorkerEvent_sequenceNumber_get(self.__externalRCRef()).map { it in it.uint64Value }
            }
        }
        public var transaction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse? {
            get {
                return { switch xyz_mcxross_kaptos_swift_WorkerEvent_transaction_get(self.__externalRCRef()) { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse.__createClassWrapper(externalRCRef: res); } }()
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func copy(
            kind: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEventKind,
            requestId: Swift.UInt64?,
            sequenceNumber: Swift.UInt64?,
            transaction: ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse?,
            error: ExportedKotlinPackages.xyz.mcxross.kaptos.swift.AptosException?
        ) -> ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEvent {
            return ExportedKotlinPackages.xyz.mcxross.kaptos.swift.WorkerEvent.__createClassWrapper(externalRCRef: xyz_mcxross_kaptos_swift_WorkerEvent_copy__TypesOfArguments__ExportedKotlinPackages_xyz_mcxross_kaptos_swift_WorkerEventKind_Swift_Optional_Swift_UInt64__Swift_Optional_Swift_UInt64__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PendingTransactionResponse__Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_swift_AptosException___(self.__externalRCRef(), kind.__externalRCRef(), requestId.map { it in NSNumber(value: it) } ?? nil, sequenceNumber.map { it in NSNumber(value: it) } ?? nil, transaction.map { it in it.__externalRCRef() } ?? nil, error.map { it in it.__externalRCRef() } ?? nil))
        }
    }
}
@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAbstractionStatus__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAbstractionStatus__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAbstractionStatus) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountAbstractionStatus.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialAssetStatus__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialAssetStatus__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetStatus) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialAssetStatus.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialBalance__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialBalance__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialBalance) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialBalance.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_ledger_LedgerBlock__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_ledger_LedgerBlock__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_ledger_LedgerState__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_ledger_LedgerState__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerState) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerState.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountData__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountData__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosPage__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosPage__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosPage.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PendingTransactionResponse__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_PendingTransactionResponse__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponse__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionResponse__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_swift_ConfidentialActivityPage__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_swift_ConfidentialActivityPage__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialActivityPage) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.swift.ConfidentialActivityPage.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_swift_Submission__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_swift_Submission__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Submission) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.swift.Submission.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_swift_TablePage__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_swift_TablePage__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.xyz.mcxross.kaptos.swift.TablePage) -> Swift.Void)(ExportedKotlinPackages.xyz.mcxross.kaptos.swift.TablePage.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountInfo___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountInfo___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Any) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountInfo>) -> Swift.Void)(_1 as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.account.AccountInfo>)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_account_Account___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_account_Account___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Any) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account>) -> Swift.Void)(_1 as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account>)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_UserTransactionResponse___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_model_UserTransactionResponse___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Any) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse>) -> Swift.Void)(_1 as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse>)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_staking_DelegatedStakingActivity___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_staking_DelegatedStakingActivity___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Any) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.staking.DelegatedStakingActivity>) -> Swift.Void)(_1 as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.staking.DelegatedStakingActivity>)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_staking_StakingPoolDelegatorCount___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_staking_StakingPoolDelegatorCount___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Any) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.staking.StakingPoolDelegatorCount>) -> Swift.Void)(_1 as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.staking.StakingPoolDelegatorCount>)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_table_TableMetadata___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Array_ExportedKotlinPackages_xyz_mcxross_kaptos_table_TableMetadata___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Any) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableMetadata>) -> Swift.Void)(_1 as! Swift.Array<ExportedKotlinPackages.xyz.mcxross.kaptos.table.TableMetadata>)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Bool__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.Bool) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Bool) -> Swift.Void)(_1)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_ConfidentialEncryptionKey___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.ConfidentialEncryptionKey.__createClassWrapper(externalRCRef: res); } }())
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_EffectiveAuditorHint___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_confidential_EffectiveAuditorHint___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.EffectiveAuditorHint>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.confidential.EffectiveAuditorHint.__createClassWrapper(externalRCRef: res); } }())
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_CollectionRecord___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_digitalasset_CollectionRecord___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.CollectionRecord>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.digitalasset.CollectionRecord.__createClassWrapper(externalRCRef: res); } }())
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_model_AccountAddress___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress.__createClassWrapper(externalRCRef: res); } }())
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameRecord___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_names_NameRecord___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameRecord>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.names.NameRecord.__createClassWrapper(externalRCRef: res); } }())
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_objects_ObjectRecord___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_ExportedKotlinPackages_xyz_mcxross_kaptos_objects_ObjectRecord___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<ExportedKotlinPackages.xyz.mcxross.kaptos.objects.ObjectRecord>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: ExportedKotlinPackages.xyz.mcxross.kaptos.objects.ObjectRecord.__createClassWrapper(externalRCRef: res); } }())
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<Swift.Error>) -> Swift.Void)({ switch _1 { case nil: .none; case let res?: KotlinRuntimeSupport.swiftError(fromKotlinThrowable: KotlinRuntime.KotlinBase.__createClassWrapper(externalRCRef: res)); } }())
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String___")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_String___(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.String?) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Optional<Swift.String>) -> Swift.Void)(_1)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.String) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.String) -> Swift.Void)(_1)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UInt64) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.UInt64) -> Swift.Void)(_1)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Void__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Void__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.Bool) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.Void) -> Swift.Void)({ _1; return () }())
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction) -> Swift.Void)(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer) -> Swift.Void)(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_FeePayer.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent) -> Swift.Void)(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_MultiAgent.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_XyzMcxrossKaptosKaptos__ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple) -> Swift.Void)(XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction_Simple.__createClassWrapper(externalRCRef: _1))
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_AptosResult__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult) -> Swift.Void)(KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: _1, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.AptosResult)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void)(KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: _1, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__")
package func AptosKit_internal_functional_type_callee_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let _result: Swift.Void = (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator) -> Swift.Void)(KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: _1, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator)
    return { _result; return true }()
}

@_cdecl("AptosKit_internal_functional_type_callee_async_ExportedKotlinPackagesU2ExyzU2EmcxrossU2EkaptosU2EcoreU2EcryptoU2ESignature__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__")
package func AptosKit_internal_functional_type_callee_async_ExportedKotlinPackagesU2ExyzU2EmcxrossU2EkaptosU2EcoreU2EcryptoU2ESignature__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_kotlin_ByteArray__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let __continuation: (ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { AptosKit_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(pointerToBlock.__externalRCRef(), _1.__externalRCRef()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { AptosKit_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (ExportedKotlinPackages.kotlin.ByteArray) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature)(ExportedKotlinPackages.kotlin.ByteArray.__createClassWrapper(externalRCRef: _1))
    }
    return true
}

@_cdecl("AptosKit_internal_functional_type_callee_async_ExportedKotlinPackagesU2ExyzU2EmcxrossU2EkaptosU2EcoreU2EcryptoU2ESignature__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__")
package func AptosKit_internal_functional_type_callee_async_ExportedKotlinPackagesU2ExyzU2EmcxrossU2EkaptosU2EcoreU2EcryptoU2ESignature__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_String__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.String, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let __continuation: (ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { AptosKit_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_ExportedKotlinPackages_xyz_mcxross_kaptos_core_crypto_Signature__(pointerToBlock.__externalRCRef(), _1.__externalRCRef()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { AptosKit_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.String) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature)(_1)
    }
    return true
}

@_cdecl("AptosKit_internal_functional_type_callee_async_anyU20ExportedKotlinPackagesU2ExyzU2EmcxrossU2EkaptosU2EmodelU2EUnsignedTransaction__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__")
package func AptosKit_internal_functional_type_callee_async_anyU20ExportedKotlinPackagesU2ExyzU2EmcxrossU2EkaptosU2EmodelU2EUnsignedTransaction__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_UInt64__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UInt64, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let __continuation: (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { AptosKit_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(pointerToBlock.__externalRCRef(), _1.__externalRCRef()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { AptosKit_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (Swift.UInt64) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction)(_1)
    }
    return true
}

@_cdecl("AptosKit_internal_functional_type_callee_async_anyU20ExportedKotlinPackagesU2ExyzU2EmcxrossU2EkaptosU2EtransactionU2EauthenticatorU2EAccountAuthenticator__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__")
package func AptosKit_internal_functional_type_callee_async_anyU20ExportedKotlinPackagesU2ExyzU2EmcxrossU2EkaptosU2EtransactionU2EauthenticatorU2EAccountAuthenticator__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_model_UnsignedTransaction__(_ pointerToClosure: Swift.UnsafeMutableRawPointer, _ _1: Swift.UnsafeMutableRawPointer, _ continuation: Swift.UnsafeMutableRawPointer, _ exception: Swift.UnsafeMutableRawPointer, _ cancellation: Swift.UnsafeMutableRawPointer) -> Swift.Bool {
    let __continuation: (any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: continuation, options: .asBestFittingWrapper)
    return { _1 in return { AptosKit_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_anyU20ExportedKotlinPackages_xyz_mcxross_kaptos_transaction_authenticator_AccountAuthenticator__(pointerToBlock.__externalRCRef(), _1.__externalRCRef()); return () }() }
}()
    let __exception: (Swift.Optional<Swift.Error>) -> Swift.Void = {
    let pointerToBlock = KotlinRuntime.KotlinBase(__externalRCRefUnsafe: exception, options: .asBestFittingWrapper)
    return { _1 in return { AptosKit_internal_functional_type_caller_SwiftU2EVoid__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_Swift_Error___(pointerToBlock.__externalRCRef(), _1.map { it in KotlinRuntimeSupport.kotlinThrowableRCRef(for: it) } ?? nil); return () }() }
}()
    let __cancellation: KotlinCoroutineSupport.KotlinTask = KotlinCoroutineSupport.KotlinTask.__createClassWrapper(externalRCRef: cancellation)
    withKotlinTask(__continuation, __exception, __cancellation) {
        try await (Unmanaged<AnyObject>.fromOpaque(pointerToClosure).takeUnretainedValue() as! (any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction) async throws -> any ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator)(KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: _1, conformsTo: ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Type.self) as! any ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction)
    }
    return true
}

