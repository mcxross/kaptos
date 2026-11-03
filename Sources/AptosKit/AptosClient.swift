import Foundation
import ExportedKotlinPackages
import XyzMcxrossKaptosKaptos

public struct ClientConfiguration: Sendable {
    public var network: Network
    public var fullNode: URL?
    public var indexer: URL?
    public var faucet: URL?
    public var headers: [String: String]
    public var requestTimeout: TimeInterval
    public var indexerWaitTimeout: TimeInterval
    public var maxGasAmount: UInt64
    public var expirationSecondsFromNow: UInt64

    public init(network: Network = .devnet, fullNode: URL? = nil, indexer: URL? = nil,
                faucet: URL? = nil, headers: [String: String] = [:], requestTimeout: TimeInterval = 10,
                indexerWaitTimeout: TimeInterval = 10, maxGasAmount: UInt64 = 2_000_000,
                expirationSecondsFromNow: UInt64 = 20) {
        self.network = network
        self.fullNode = fullNode
        self.indexer = indexer
        self.faucet = faucet
        self.headers = headers
        self.requestTimeout = requestTimeout
        self.indexerWaitTimeout = indexerWaitTimeout
        self.maxGasAmount = maxGasAmount
        self.expirationSecondsFromNow = expirationSecondsFromNow
    }

    internal func bridge() throws -> Configuration {
        func milliseconds(_ seconds: TimeInterval) throws -> Int64 {
            guard seconds.isFinite, seconds > 0, seconds * 1_000 < Double(Int64.max) else {
                throw AptosError.validation(.init(message: "Timeout must be finite and positive"))
            }
            return max(1, Int64(seconds * 1_000))
        }
        return try Configuration.make(network: network.bridge, fullNode: fullNode?.absoluteString,
            indexer: indexer?.absoluteString, faucet: faucet?.absoluteString, headers: headers,
            requestTimeoutMillis: milliseconds(requestTimeout),
            indexerWaitTimeoutMillis: milliseconds(indexerWaitTimeout), maxGasAmount: maxGasAmount,
            expirationSecondsFromNow: expirationSecondsFromNow)
    }
}

@MainActor
internal final class ClientState {
    let raw: Client
    var closed = false
    var closing = false
    var closeTask: Task<Void, Error>?
    init(_ raw: Client) { self.raw = raw }
    func requireOpen() throws {
        guard !closed && !closing else {
            throw AptosError.validation(.init(message: "Client is closed or closing"))
        }
    }
}

/// Own a client and its accounts in one application lifecycle. Finish in-flight work before closing.
@MainActor
public final class AptosClient {
    internal let state: ClientState

    public convenience init(network: Network = .devnet) throws {
        try self.init(configuration: ClientConfiguration(network: network))
    }

    public init(configuration: ClientConfiguration) throws {
        state = try ClientState(aptosInput { try Client(configuration: configuration.bridge()) })
    }

    public var accounts: AptosAccounts { AptosAccounts(state) }
    public var transactions: AptosTransactions { AptosTransactions(state) }
    public var views: AptosViews { AptosViews(state) }
    public var indexer: AptosIndexer { AptosIndexer(state) }
    public var tables: AptosTables { AptosTables(state) }

    public var ledger: AptosLedger { AptosLedger(state) }
    public var objects: Objects { state.raw.objects }
    public var staking: Staking { state.raw.staking }
    public var digitalAssets: DigitalAssets { state.raw.digitalAssets }
    public var faucet: Faucet { state.raw.faucet }
    public var fungibleAssets: FungibleAssets { state.raw.fungibleAssets }
    public var names: Names { state.raw.names }
    public var abstraction: Abstraction { state.raw.abstraction }
    public var coins: Coins { state.raw.coins }
    public var encryptedTransactions: EncryptedTransactions { state.raw.encryptedTransactions }
    public var confidentialAssets: ConfidentialAssets { state.raw.confidentialAssets }

    /// Advanced access to the complete exported SDK surface.
    public var underlyingClient: Client { state.raw }

    public func generateAccount(scheme: SigningScheme = .ed25519) throws -> LocalAccount {
        try state.requireOpen()
        return try LocalAccount(aptosCall { try state.raw.generateAccount(scheme: scheme.bridge) })
    }

    public func importAccount(privateKey: String, address: AccountAddress? = nil) throws -> LocalAccount {
        try state.requireOpen()
        return try LocalAccount(aptosInput {
            try state.raw.importAccount(privateKey: privateKey, address: address?.description)
        })
    }

    public func worker(using signer: any TransactionSigning, capacity: Int = 64) throws -> TransactionWorker {
        try state.requireOpen()
        guard let capacity = Int32(exactly: capacity), capacity > 0 else {
            throw AptosError.validation(.init(message: "Worker capacity must be positive and fit Int32"))
        }
        return try TransactionWorker(aptosCall {
            try state.raw.worker(signer: signer.underlyingSigner, capacity: capacity)
        }, state: state)
    }

    public func clearABICache() async throws {
        try state.requireOpen()
        try await aptosCall { try await state.raw.clearAbiCache() }
    }

    /// Shut down workers before calling this synchronous close operation.
    public func close() throws {
        if state.closed { return }
        try state.requireOpen()
        try aptosCall { try state.raw.close() }
        state.closed = true
    }

    /// Cancels and joins workers before clearing client-owned private keys. Concurrent callers
    /// share cleanup; cancelling a waiter does not interrupt resource cleanup.
    public func closeAsync() async throws {
        if state.closed { return }
        if let closing = state.closeTask { return try await closing.value }
        state.closing = true
        let closing = Task { @MainActor [state] in
            defer { state.closing = false; state.closeTask = nil }
            try await aptosCall { try await state.raw.closeAsync() }
            state.closed = true
        }
        state.closeTask = closing
        try await closing.value
    }
}

@MainActor
public struct AptosLedger {
    private let state: ClientState
    internal init(_ state: ClientState) { self.state = state }

    public func info() async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerState {
        try state.requireOpen()
        return try await aptosCall { try await state.raw.ledger.info() }
    }

    public func blockAtHeight(height: UInt64) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock {
        try state.requireOpen()
        return try await aptosCall { try await state.raw.ledger.blockAtHeight(height: height) }
    }

    public func blockAtVersion(version: UInt64) async throws -> ExportedKotlinPackages.xyz.mcxross.kaptos.ledger.LedgerBlock {
        try state.requireOpen()
        return try await aptosCall { try await state.raw.ledger.blockAtVersion(version: version) }
    }
}

@MainActor
public struct AptosAccounts {
    private let state: ClientState
    internal init(_ state: ClientState) { self.state = state }

    public func get(_ address: AccountAddress) async throws -> AccountData {
        try state.requireOpen()
        return try await aptosCall { try await state.raw.accounts.get(address: address.bridge()) }
    }

    public func balance(of address: AccountAddress) async throws -> UInt64 {
        try state.requireOpen()
        return try await aptosCall { try await state.raw.accounts.balance(address: address.bridge()) }
    }
}

@MainActor
public struct AptosTransactions {
    private let state: ClientState
    internal init(_ state: ClientState) { self.state = state }

    public func entryFunction(_ function: String, typeArguments: [TypeTag] = [],
                              arguments: [MoveValue] = []) async throws
        -> ExportedKotlinPackages.xyz.mcxross.kaptos.model.TransactionPayload.EntryFunction {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.transactions.entryFunctionPayload(function: function,
                typeArguments: typeArguments, arguments: arguments.map { try $0.bridge() })
        }
    }

    public func build(_ payload: any TransactionPayload, sender: AccountAddress,
                      options: TransactionOptions? = nil) async throws -> SimpleTransaction {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.transactions.build(sender: sender.bridge(), payload: payload,
                                                    options: options?.bridge())
        }
    }

    public func fromBCS(_ data: Data) throws -> SimpleTransaction {
        try state.requireOpen()
        return try aptosCall { try state.raw.transactions.fromBcs(bytes: data.kotlinBytes()) }
    }

    public func simulate(_ transaction: any UnsignedTransaction, using signer: any TransactionSigning,
                         secondarySignerPublicKeys: [PublicKey] = [], feePayerPublicKey: PublicKey? = nil,
                         estimateGasUnitPrice: Bool = false, estimateMaxGasAmount: Bool = false,
                         estimatePrioritizedGasUnitPrice: Bool = false) async throws -> [SimulatedTransaction] {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.transactions.simulate(transaction: transaction,
                senderPublicKey: signer.underlyingSigner.publicKey, secondarySignerPublicKeys: secondarySignerPublicKeys,
                feePayerPublicKey: feePayerPublicKey, options: SimulationOptions(estimateGasUnitPrice: estimateGasUnitPrice,
                    estimateMaxGasAmount: estimateMaxGasAmount,
                    estimatePrioritizedGasUnitPrice: estimatePrioritizedGasUnitPrice))
        }
    }

    public func sign(_ transaction: any UnsignedTransaction,
                     using signer: any TransactionSigning) async throws -> any Authenticator {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.transactions.sign(signer: signer.underlyingSigner, transaction: transaction)
        }
    }

    public func hash(of transaction: any UnsignedTransaction,
                     authenticator: any Authenticator, secondaryAuthenticators: [any Authenticator] = [],
                     feePayerAuthenticator: (any Authenticator)? = nil) throws -> String {
        try state.requireOpen()
        return try aptosCall {
            try state.raw.transactions.userTransactionHash(transaction: transaction,
                senderAuthenticator: authenticator, secondaryAuthenticators: secondaryAuthenticators,
                feePayerAuthenticator: feePayerAuthenticator)
        }
    }

    public func submit(_ transaction: any UnsignedTransaction,
                       authenticator: any Authenticator, secondaryAuthenticators: [any Authenticator] = [],
                       feePayerAuthenticator: (any Authenticator)? = nil) async throws -> PendingTransaction {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.transactions.submit(transaction: transaction,
                senderAuthenticator: authenticator, secondaryAuthenticators: secondaryAuthenticators,
                feePayerAuthenticator: feePayerAuthenticator)
        }
    }

    public func signAndSubmit(_ payload: any TransactionPayload, using signer: any TransactionSigning,
                              options: TransactionOptions? = nil, secondarySigners: [any TransactionSigning] = [],
                              feePayer: (any TransactionSigning)? = nil) async throws -> PendingTransaction {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.transactions.signAndSubmit(signer: signer.underlyingSigner, payload: payload,
                options: options?.bridge(), secondarySigners: secondarySigners.map(\.underlyingSigner),
                feePayer: feePayer?.underlyingSigner)
        }
    }

    public func signAndSubmit(_ transaction: any UnsignedTransaction,
                              using signer: any TransactionSigning,
                              secondaryAuthenticators: [any Authenticator] = [],
                              feePayerAuthenticator: (any Authenticator)? = nil) async throws -> PendingTransaction {
        try state.requireOpen()
        return try await aptosCall {
            try await state.raw.transactions.signAndSubmit(signer: signer.underlyingSigner, transaction: transaction,
                secondaryAuthenticators: secondaryAuthenticators, feePayerAuthenticator: feePayerAuthenticator)
        }
    }

    public func waitForTransaction(_ hash: String, timeout: Int = 20,
                                   checkSuccess: Bool = true,
                                   waitForIndexer: Bool = false) async throws -> TransactionResponse {
        try state.requireOpen()
        guard let seconds = Int32(exactly: timeout), seconds > 0 else {
            throw AptosError.validation(.init(message: "Timeout must be a positive whole number of seconds fitting Int32"))
        }
        return try await aptosCall {
            try await state.raw.transactions.waitForTransaction(hash: hash,
                options: WaitForTransactionOptions(timeoutSecs: seconds,
                    checkSuccess: checkSuccess, waitForIndexer: waitForIndexer))
        }
    }
}
