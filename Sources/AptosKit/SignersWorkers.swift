import Foundation
import KotlinCoroutineSupport
import ExportedKotlinPackages
import XyzMcxrossKaptosKaptos

public enum SigningScheme: String, CaseIterable, Sendable {
    case ed25519, secp256k1, secp256r1
    internal var bridge: ExportedKotlinPackages.xyz.mcxross.kaptos.model.SigningSchemeInput {
        switch self {
        case .ed25519: return .Ed25519
        case .secp256k1: return .Secp256k1
        case .secp256r1: return .Secp256r1
        }
    }
}

@MainActor
public protocol TransactionSigning: AnyObject {
    var address: AccountAddress { get }
    var publicKey: PublicKey { get }
    var underlyingSigner: any TransactionSigner { get }
}

@MainActor
public final class LocalAccount: TransactionSigning {
    public let underlyingAccount: Account
    internal init(_ account: Account) { underlyingAccount = account }
    public var address: AccountAddress { AccountAddress(underlyingAccount.accountAddress) }
    public var publicKey: PublicKey { underlyingAccount.publicKey }
    public var isPrivateKeyCleared: Bool { underlyingAccount.isPrivateKeyCleared }
    public var underlyingSigner: any TransactionSigner { underlyingAccount }
}

/// Callbacks execute on the main actor and remain retained for this signer's lifetime.
/// Use weak captures when a callback refers to an owner that also retains this signer or its worker.
@MainActor
public final class SwiftSigner: TransactionSigning {
    public let address: AccountAddress
    public let publicKey: PublicKey
    public let underlyingSigner: any TransactionSigner

    public init(address: AccountAddress, publicKey: PublicKey,
                signBytes: @escaping @MainActor (Data) async throws -> Signature,
                signText: @escaping @MainActor (String) async throws -> Signature,
                signTransaction: @escaping @MainActor (any UnsignedTransaction) async throws -> any Authenticator) throws {
        self.address = address
        self.publicKey = publicKey
        underlyingSigner = CallbackSigner(accountAddress: try address.bridge(), publicKey: publicKey,
            bytes: { try await signBytes(Data(kotlinBytes: $0)) },
            text: { try await signText($0) }, transaction: { try await signTransaction($0) })
    }
}

public extension UnsignedTransaction {
    var signingData: Data { Data(kotlinBytes: signingMessage()) }
    var bcsData: Data { Data(kotlinBytes: signingBcs()) }
}

public extension Authenticator {
    var bcsData: Data { Data(kotlinBytes: toBcs()) }
}

public struct TransactionEvent {
    public enum Kind: String, Sendable { case queued, building, submitted, failed, cancelled, drained }
    public let kind: Kind
    public let requestID: UInt64?
    public let sequenceNumber: UInt64?
    public let transaction: PendingTransaction?
    public let error: AptosError?

    internal init(_ event: WorkerEvent) {
        switch event.kind {
        case .Queued: kind = .queued
        case .Building: kind = .building
        case .Submitted: kind = .submitted
        case .Failed: kind = .failed
        case .Cancelled: kind = .cancelled
        case .Drained: kind = .drained
        @unknown default: kind = .failed
        }
        requestID = event.requestId
        sequenceNumber = event.sequenceNumber
        transaction = event.transaction
        error = event.error.flatMap { mappedAptosError($0) as? AptosError }
    }
}

public struct TransactionEvents: AsyncSequence {
    public typealias Element = TransactionEvent
    private let sequence: KotlinFlowSequence<WorkerEvent>
    internal init(_ worker: Worker) { sequence = worker.events.asAsyncSequence() }

    public struct AsyncIterator: AsyncIteratorProtocol {
        private let iterator: KotlinFlowSequence<WorkerEvent>.Iterator
        internal init(_ iterator: KotlinFlowSequence<WorkerEvent>.Iterator) { self.iterator = iterator }
        public mutating func next() async throws -> TransactionEvent? {
            do { return try await iterator.next().map(TransactionEvent.init) }
            catch { throw mappedAptosError(error) }
        }
    }
    public func makeAsyncIterator() -> AsyncIterator { AsyncIterator(sequence.makeAsyncIterator()) }
}

@MainActor
public final class TransactionSubmission {
    private let raw: Submission
    internal init(_ raw: Submission) { self.raw = raw }
    public var value: PendingTransaction {
        get async throws { try await aptosCall { try await raw.await() } }
    }
}

@MainActor
public final class TransactionWorker {
    private let raw: Worker
    private let state: ClientState
    internal init(_ raw: Worker, state: ClientState) { self.raw = raw; self.state = state }
    public var events: TransactionEvents { TransactionEvents(raw) }

    public func submit(_ build: @escaping @MainActor (UInt64) async throws -> any UnsignedTransaction) async throws -> TransactionSubmission {
        try state.requireOpen()
        return try await aptosCall {
            try await TransactionSubmission(raw.submit(build: { try await build($0) }))
        }
    }

    public func shutdown(cancelPending: Bool = false) async throws {
        try await aptosCall { try await raw.shutdown(cancelPending: cancelPending) }
    }
}
