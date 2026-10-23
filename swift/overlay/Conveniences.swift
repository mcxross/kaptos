import Foundation
@_exported import XyzMcxrossKaptosKaptos
import KotlinStdlib

public typealias Network = ExportedKotlinPackages.xyz.mcxross.kaptos.model.Network
public typealias AccountAddress = ExportedKotlinPackages.xyz.mcxross.kaptos.model.AccountAddress
public typealias Account = ExportedKotlinPackages.xyz.mcxross.kaptos.account.Account
public typealias TransactionSigner = ExportedKotlinPackages.xyz.mcxross.kaptos.account.TransactionSigner
public typealias UnsignedTransaction = ExportedKotlinPackages.xyz.mcxross.kaptos.model.UnsignedTransaction
public typealias TransactionOptions = ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionOptions
public typealias SimulationOptions = ExportedKotlinPackages.xyz.mcxross.kaptos.model.SimulationOptions
public typealias WaitForTransactionOptions = ExportedKotlinPackages.xyz.mcxross.kaptos.model.WaitForTransactionOptions
public typealias PendingTransaction = ExportedKotlinPackages.xyz.mcxross.kaptos.model.PendingTransactionResponse
public typealias TransactionResponse = ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionResponse
public typealias MoveArgument = ExportedKotlinPackages.xyz.mcxross.kaptos.move.MoveArgument
public typealias KotlinByteArray = ExportedKotlinPackages.kotlin.ByteArray

public extension TransactionResponse {
    var transactionHash: String { ResponseValues.shared.transactionHash(response: self) }
}

public extension TransactionOptions {
    static func make(maxGasAmount: UInt64? = nil, gasUnitPrice: UInt64? = nil,
                     expirationTimestampSecs: UInt64? = nil, expirationSecondsFromNow: UInt64? = nil,
                     sequenceNumber: UInt64? = nil, nonce: UInt64? = nil) throws -> TransactionOptions {
        try OptionValues.shared.transaction(
            maxGasAmountDecimal: maxGasAmount.map(String.init),
            gasUnitPriceDecimal: gasUnitPrice.map(String.init),
            expirationTimestampSecsDecimal: expirationTimestampSecs.map(String.init),
            expirationSecondsFromNowDecimal: expirationSecondsFromNow.map(String.init),
            sequenceNumberDecimal: sequenceNumber.map(String.init), nonceDecimal: nonce.map(String.init))
    }
}

public extension Views {
    func call(function: String, typeArguments: [String] = [], arguments: [any MoveArgument] = [],
              ledgerVersion: UInt64? = nil) async throws -> String {
        try await call(function: function, typeArguments: typeArguments, arguments: arguments,
                       ledgerVersionDecimal: ledgerVersion.map(String.init))
    }

    func callRaw(function: String, typeArguments: [String] = [], argumentsJson: String = "[]",
                 ledgerVersion: UInt64? = nil) async throws -> String {
        try await callRaw(function: function, typeArguments: typeArguments, argumentsJson: argumentsJson,
                          ledgerVersionDecimal: ledgerVersion.map(String.init))
    }
}

public extension Tables {
    func getItem(handle: String, keyType: String, valueType: String, keyJson: String,
                 ledgerVersion: UInt64? = nil) async throws -> String {
        try await getItem(handle: handle, keyType: keyType, valueType: valueType, keyJson: keyJson,
                          ledgerVersionDecimal: ledgerVersion.map(String.init))
    }
}

public extension Configuration {
    static func make(
        network: Network = .DEVNET,
        fullNode: String? = nil,
        indexer: String? = nil,
        faucet: String? = nil,
        headers: [String: String] = [:],
        requestTimeoutMillis: Int64 = 10_000,
        indexerWaitTimeoutMillis: Int64 = 10_000,
        maxGasAmount: UInt64 = 2_000_000,
        expirationSecondsFromNow: UInt64 = 20
    ) -> Configuration {
        Configuration(network: network, fullNode: fullNode, indexer: indexer, faucet: faucet,
                      headers: headers, requestTimeoutMillis: requestTimeoutMillis,
                      indexerWaitTimeoutMillis: indexerWaitTimeoutMillis,
                      maxGasAmount: maxGasAmount, expirationSecondsFromNow: expirationSecondsFromNow)
    }
}

public extension Data {
    init(kotlinBytes: KotlinByteArray) {
        self.init(ByteCodec.shared.toOctets(bytes: kotlinBytes).map { UInt8($0) })
    }

    func kotlinBytes() throws -> KotlinByteArray {
        try ByteCodec.shared.fromOctets(values: map { Int32($0) })
    }
}

/// JSON stays UTF-8 and Aptos decimal strings stay strings until the caller chooses a Decodable type.
public enum AptosJSON {
    public static func decode<T: Decodable>(
        _ type: T.Type, from json: String, using decoder: JSONDecoder = JSONDecoder()
    ) throws -> T {
        try decoder.decode(type, from: Data(json.utf8))
    }
}

public extension Accounts {
    func balance(address: AccountAddress) async throws -> UInt64 {
        let asset = XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_account_AccountAsset_Companion.shared
            .coin(type: "0x1::aptos_coin::AptosCoin")
        return try await getBalance(address: address, asset: asset)
    }
}

public extension Transactions {
    func entryFunction(function: String, arguments: [any MoveArgument] = []) async throws
        -> XyzMcxrossKaptosKaptos._ExportedKotlinPackages_xyz_mcxross_kaptos_model_TransactionPayload_EntryFunction {
        try await entryFunctionPayload(function: function, typeArguments: [], arguments: arguments)
    }
}
