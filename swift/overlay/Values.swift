import Foundation
import ExportedKotlinPackages
import XyzMcxrossKaptosKaptos

public enum Network: String, CaseIterable, Codable, Sendable {
    case mainnet, testnet, devnet, shelbynet, netna, local, custom

    internal var bridge: RawNetwork {
        switch self {
        case .mainnet: return .MAINNET
        case .testnet: return .TESTNET
        case .devnet: return .DEVNET
        case .shelbynet: return .SHELBYNET
        case .netna: return .NETNA
        case .local: return .LOCAL
        case .custom: return .CUSTOM
        }
    }
}

public struct AccountAddress: Hashable, Codable, Sendable, CustomStringConvertible {
    public let description: String

    public init(_ value: String) throws {
        do { description = try Addresses.shared.parse(value: value).value }
        catch { throw AptosError.validation(.init(message: String(describing: error))) }
    }

    internal init(_ address: RawAccountAddress) { description = address.value }
    public var underlyingAddress: RawAccountAddress { get throws { try bridge() } }
    internal func bridge() throws -> RawAccountAddress {
        try aptosCall { try Addresses.shared.parse(value: description) }
    }

    public init(from decoder: Decoder) throws {
        let container = try decoder.singleValueContainer()
        try self.init(container.decode(String.self))
    }

    public func encode(to encoder: Encoder) throws {
        var container = encoder.singleValueContainer()
        try container.encode(description)
    }
}

public struct TransactionOptions: Equatable, Sendable {
    public var maxGasAmount: UInt64?
    public var gasUnitPrice: UInt64?
    public var expirationTimestampSecs: UInt64?
    public var expirationSecondsFromNow: UInt64?
    public var sequenceNumber: UInt64?
    public var nonce: UInt64?

    public init(maxGasAmount: UInt64? = nil, gasUnitPrice: UInt64? = nil,
                expirationTimestampSecs: UInt64? = nil, expirationSecondsFromNow: UInt64? = nil,
                sequenceNumber: UInt64? = nil, nonce: UInt64? = nil) {
        self.maxGasAmount = maxGasAmount
        self.gasUnitPrice = gasUnitPrice
        self.expirationTimestampSecs = expirationTimestampSecs
        self.expirationSecondsFromNow = expirationSecondsFromNow
        self.sequenceNumber = sequenceNumber
        self.nonce = nonce
    }

    internal func bridge() throws -> RawTransactionOptions {
        try aptosInput {
            try RawTransactionOptions.make(maxGasAmount: maxGasAmount, gasUnitPrice: gasUnitPrice,
                expirationTimestampSecs: expirationTimestampSecs,
                expirationSecondsFromNow: expirationSecondsFromNow,
                sequenceNumber: sequenceNumber, nonce: nonce)
        }
    }
    public var underlyingOptions: RawTransactionOptions { get throws { try bridge() } }
}

public indirect enum MoveValue: Sendable {
    case bool(Bool), u8(UInt8), u16(UInt16), u32(UInt32), u64(UInt64)
    case u128(String), u256(String), string(String), address(AccountAddress), bytes(Data)
    case vector([MoveValue]), option(MoveValue?), structure([String: MoveValue])
    case variant(String, fields: [String: MoveValue]), serialized(Data)

    internal func bridge() throws -> any MoveArgument {
        try aptosInput {
            switch self {
            case .bool(let value): return Move.shared.bool(value: value)
            case .u8(let value): return Move.shared.u8(value: value)
            case .u16(let value): return Move.shared.u16(value: value)
            case .u32(let value): return Move.shared.u32(value: value)
            case .u64(let value): return Move.shared.u64(value: value)
            case .u128(let value): return try Move.shared.u128(decimal: value)
            case .u256(let value): return try Move.shared.u256(decimal: value)
            case .string(let value): return Move.shared.string(value: value)
            case .address(let value): return try Move.shared.address(value: value.description)
            case .bytes(let value): return Move.shared.bytes(value: try value.kotlinBytes())
            case .vector(let values): return Move.shared.vector(values: try values.map { try $0.bridge() })
            case .option(let value): return Move.shared.option(value: try value?.bridge())
            case .structure(let fields): return Move.shared.struct(fields: try fields.mapValues { try $0.bridge() })
            case .variant(let name, let fields): return Move.shared.variant(name: name, fields: try fields.mapValues { try $0.bridge() })
            case .serialized(let data): return Move.shared.preSerialized(bcs: try data.kotlinBytes())
            }
        }
    }

    public func bcs() throws -> Data { Data(kotlinBytes: try bridge().toBcs()) }
    public var underlyingArgument: any MoveArgument { get throws { try bridge() } }
}

public enum AptosError: Error, LocalizedError, Equatable, Sendable {
    public struct Details: Equatable, Sendable {
        public let message: String
        public let apiCode: String?
        public let vmCode: Int64?

        public init(message: String, apiCode: String? = nil, vmCode: Int64? = nil) {
            self.message = message
            self.apiCode = apiCode
            self.vmCode = vmCode
        }
    }

    public enum Category: String, Codable, Sendable {
        case transport, api, indexer, validation, serialization, crypto, timeout, unsupportedFeature, unexpected
    }

    case transport(Details), api(Details), indexer(Details), validation(Details), serialization(Details)
    case crypto(Details), timeout(Details), unsupportedFeature(Details), unexpected(Details)

    public var details: Details {
        switch self {
        case .transport(let value), .api(let value), .indexer(let value), .validation(let value),
             .serialization(let value), .crypto(let value), .timeout(let value),
             .unsupportedFeature(let value), .unexpected(let value): return value
        }
    }
    public var category: Category {
        switch self {
        case .transport: return .transport
        case .api: return .api
        case .indexer: return .indexer
        case .validation: return .validation
        case .serialization: return .serialization
        case .crypto: return .crypto
        case .timeout: return .timeout
        case .unsupportedFeature: return .unsupportedFeature
        case .unexpected: return .unexpected
        }
    }
    public var apiCode: String? { details.apiCode }
    public var vmCode: Int64? { details.vmCode }
    public var errorDescription: String? { details.message }
}

internal func mappedAptosError(_ error: any Error) -> any Error {
    if error is CancellationError || error is AptosError { return error }
    if let exception = error as? AptosException {
        let details = AptosError.Details(message: exception.message ?? String(describing: exception),
                                        apiCode: exception.apiCode, vmCode: exception.vmCode)
        switch exception.category {
        case .Transport: return AptosError.transport(details)
        case .Api: return AptosError.api(details)
        case .Indexer: return AptosError.indexer(details)
        case .Validation: return AptosError.validation(details)
        case .Serialization: return AptosError.serialization(details)
        case .Crypto: return AptosError.crypto(details)
        case .Timeout: return AptosError.timeout(details)
        case .UnsupportedFeature: return AptosError.unsupportedFeature(details)
        @unknown default: return AptosError.unexpected(details)
        }
    }
    if error is DecodingError || error is EncodingError {
        return AptosError.serialization(.init(message: String(describing: error)))
    }
    return AptosError.unexpected(.init(message: String(describing: error)))
}

internal func aptosCall<T>(_ operation: () throws -> T) throws -> T {
    do { return try operation() } catch { throw mappedAptosError(error) }
}

// These operations only parse or validate inputs. Let the SDK own its validation rules.
internal func aptosInput<T>(_ operation: () throws -> T) throws -> T {
    do { return try operation() }
    catch {
        if error is AptosException || error is AptosError || error is CancellationError {
            throw mappedAptosError(error)
        }
        throw AptosError.validation(.init(message: String(describing: error)))
    }
}

@MainActor
internal func aptosCall<T>(_ operation: () async throws -> T) async throws -> T {
    do { return try await operation() } catch { throw mappedAptosError(error) }
}

public typealias PublicKey = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.PublicKey
public typealias Signature = ExportedKotlinPackages.xyz.mcxross.kaptos.core.crypto.Signature
public typealias Authenticator = ExportedKotlinPackages.xyz.mcxross.kaptos.transaction.authenticator.AccountAuthenticator
public typealias TransactionPayload = ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload
public typealias SimpleTransaction = ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction.Simple
public typealias TypeTag = ExportedKotlinPackages.xyz.mcxross.kaptos.model.TypeTag
public typealias AccountData = ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountData
public typealias SimulatedTransaction = ExportedKotlinPackages.xyz.mcxross.kaptos.model.UserTransactionResponse
