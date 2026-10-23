import AptosKit
import Foundation
import Testing

@Suite(.timeLimit(.minutes(1)))
struct AptosKitRegressionTests {
    @MainActor
    @Test(arguments: ["malformed-json", "empty-body", "wrong-shape", "u64-overflow", "u64-negative"])
    func malformedResponsesAreTypedAndDoNotPoisonTransport(_ scenario: String) async throws {
        let fixture = try Scenario(scenario)
        defer { try? fixture.client.close() }
        await expectError(.serialization) { _ = try await fixture.client.accounts.get(AccountAddress("0x3")) }
        #expect(try await fixture.client.ledger.info().version == 1)
        #expect(try await fixture.stats()["accounts"] == 1)
    }

    @MainActor
    @Test(arguments: ["rate-limit", "unavailable", "unsupported"])
    func httpFailuresPreserveCodesWithoutRetrying(_ scenario: String) async throws {
        let fixture = try Scenario(scenario)
        defer { try? fixture.client.close() }
        let category: AptosError.Category = scenario == "unsupported" ? .unsupportedFeature : .api
        let code = ["rate-limit": "rate_limited", "unavailable": "internal_error", "unsupported": "feature_under_gating"][scenario]!
        do {
            _ = try await fixture.client.accounts.get(AccountAddress("0x3"))
            Issue.record("Expected HTTP rejection")
        } catch let error as AptosError {
            #expect(error.category == category)
            #expect(error.apiCode == code)
            #expect(error.vmCode == Int64.max)
        }
        #expect(try await fixture.stats()["accounts"] == 1)
        #expect(try await fixture.client.ledger.info().chainId.chainId == 4)
    }

    @MainActor @Test
    func unknownResponseFieldsRemainForwardCompatible() async throws {
        let fixture = try Scenario("unknown-fields")
        defer { try? fixture.client.close() }
        #expect(try await fixture.client.accounts.get(AccountAddress("0x3")).sequenceNumber == 7)
    }

    @MainActor
    @Test(arguments: ["{", "[", "{\"value\":"])
    func malformedJSONInputsFailBeforeAnyRequest(_ input: String) async throws {
        let fixture = try Scenario()
        defer { try? fixture.client.close() }
        await expectError(.validation) {
            _ = try await fixture.client.views.callRaw("0x1::test::read", argumentsJSON: input)
        }
        await expectError(.validation) {
            _ = try await fixture.client.indexer.queryRaw("query { items { id } }", variablesJSON: input)
        }
        await expectError(.validation) {
            _ = try await fixture.client.tables.itemRaw(handle: "0x1", keyType: "u64", valueType: "u64", keyJSON: input)
        }
        #expect(try await fixture.stats().isEmpty)
    }

    @MainActor @Test
    func wrongJSONInputShapesAreRejectedWithoutNetworkWork() async throws {
        let fixture = try Scenario()
        defer { try? fixture.client.close() }
        for value in ["{}", "null", "true", "1", "\"text\""] {
            await expectError(.validation) {
                _ = try await fixture.client.views.callRaw("0x1::test::read", argumentsJSON: value)
            }
        }
        for value in ["[]", "null", "true", "1", "\"text\""] {
            await expectError(.validation) {
                _ = try await fixture.client.indexer.queryRaw("query { items { id } }", variablesJSON: value)
            }
        }
        #expect(try await fixture.stats().isEmpty)
    }

    @MainActor @Test
    func transactionPropagationRetriesOnlyNotFound() async throws {
        let fixture = try Scenario("not-found-then-commit")
        defer { try? fixture.client.close() }
        let confirmed = try await fixture.client.transactions.waitForTransaction(testHash, timeout: 5)
        #expect(confirmed.transactionHash == testHash)
        #expect(try await fixture.stats()["polls"] == 3)
    }

    @MainActor @Test
    func failedCommittedTransactionRespectsCheckSuccess() async throws {
        let fixture = try Scenario("failed-execution")
        defer { try? fixture.client.close() }
        do {
            _ = try await fixture.client.transactions.waitForTransaction(testHash)
            Issue.record("Expected an execution failure")
        } catch let error as AptosError {
            #expect(error.category == .api)
            #expect(error.apiCode == "transaction_execution_failed")
            #expect(error.details.message.contains("Move abort"))
        }
        let failed = try await fixture.client.transactions.waitForTransaction(testHash, checkSuccess: false)
        #expect(failed.transactionHash == testHash)
        #expect(try await fixture.stats()["polls"] == 2)
    }

    @MainActor
    @Test(arguments: ["pending", "slow-poll"])
    func confirmationDeadlineBoundsPollingAndSlowRequests(_ scenario: String) async throws {
        let fixture = try Scenario(scenario)
        defer { try? fixture.client.close() }
        let start = ContinuousClock.now
        await expectError(.timeout) {
            _ = try await fixture.client.transactions.waitForTransaction(testHash, timeout: 1)
        }
        #expect(start.duration(to: .now) < .seconds(4))
        #expect(try await fixture.client.ledger.info().version == 1)
    }

    @MainActor @Test
    func cancellingConfirmationIsNotReportedAsTimeout() async throws {
        let fixture = try Scenario("pending")
        defer { try? fixture.client.close() }
        let task = Task { @MainActor in
            _ = try await fixture.client.transactions.waitForTransaction(testHash, timeout: 30)
        }
        try await fixture.waitFor("polls")
        task.cancel()
        await expectCancellation { try await task.value }
        let finished = try await fixture.stats()["polls"] ?? 0
        try await Task.sleep(for: .milliseconds(350))
        #expect(try await fixture.stats()["polls"] == finished)
    }

    @MainActor @Test
    func requestTimeoutDoesNotCancelTheClient() async throws {
        let fixture = try Scenario(requestTimeout: 0.5)
        defer { try? fixture.client.close() }
        await expectError(.transport) { _ = try await fixture.client.accounts.get(AccountAddress("0x2")) }
        #expect(try await fixture.client.accounts.get(AccountAddress("0x3")).sequenceNumber == 7)
    }

    @MainActor @Test
    func concurrentReadsAndCancellationRemainIndependent() async throws {
        let fixture = try Scenario()
        defer { try? fixture.client.close() }
        let delayed = (0..<32).map { _ in Task { @MainActor in
            _ = try await fixture.client.accounts.get(AccountAddress("0x2"))
        } }
        try await fixture.waitFor("accounts")
        let healthy = (0..<64).map { _ in Task { @MainActor in
            try await fixture.client.accounts.balance(of: AccountAddress("0x3"))
        } }
        delayed.forEach { $0.cancel() }
        for task in delayed { await expectCancellation { try await task.value } }
        for task in healthy { #expect(try await task.value == UInt64.max) }
        #expect(try await fixture.stats()["balances"] == 64)
        #expect(try await fixture.client.ledger.info().version == 1)
    }

    @MainActor @Test
    func workerAssignsUniqueSequencesUnderConcurrentBackpressure() async throws {
        let fixture = try Scenario()
        let signer = try fixture.signer()
        let worker = try fixture.client.worker(using: signer, capacity: 2)
        var sequences: [UInt64] = []
        let tasks = (0..<128).map { _ in Task { @MainActor in
            let submission = try await worker.submit { sequence in
                MainActor.preconditionIsolated()
                sequences.append(sequence)
                return try fixture.transaction(sequence: sequence)
            }
            return try await submission.value.transactionHash
        } }
        var hashes = Set<String>()
        for task in tasks { hashes.insert(try await task.value) }
        try await worker.shutdown()
        #expect(sequences == Array(UInt64(7)..<135))
        #expect(hashes.count == 128)
        #expect(try await fixture.stats()["submissions"] == 128)
        #expect(try await fixture.stats()["accounts"] == 1)
        try await fixture.client.closeAsync()
        #expect(signer.isPrivateKeyCleared)
    }

    @MainActor
    @Test(arguments: ["sequence-retry", "sequence-always-reject", "reject-submit"])
    func workerRetriesOnlySequenceErrorsAndOnlyOnce(_ scenario: String) async throws {
        let fixture = try Scenario(scenario)
        let worker = try fixture.client.worker(using: fixture.signer(), capacity: 1)
        var built: [UInt64] = []
        let submission = try await worker.submit { sequence in
            built.append(sequence)
            return try fixture.transaction(sequence: sequence)
        }
        if scenario == "sequence-retry" {
            #expect(try await submission.value.sequenceNumber == "9")
        } else {
            await expectError(.api) { _ = try await submission.value }
        }
        try await worker.shutdown()
        #expect(built == (scenario == "reject-submit" ? [7] : [7, 9]))
        #expect(try await fixture.stats()["submissions"] == (scenario == "reject-submit" ? 1 : 2))
        try await fixture.client.closeAsync()
    }

    @MainActor @Test
    func cancellingAnObserverDoesNotCancelItsQueuedTransaction() async throws {
        let fixture = try Scenario()
        let worker = try fixture.client.worker(using: fixture.signer(), capacity: 1)
        let gate = BuildGate()
        let submission = try await worker.submit { sequence in
            try await gate.wait()
            return try fixture.transaction(sequence: sequence)
        }
        try await waitUntil { gate.started }
        let observer = Task { @MainActor in _ = try await submission.value }
        observer.cancel()
        await expectCancellation { try await observer.value }
        gate.isOpen = true
        #expect(try await submission.value.sequenceNumber == "7")
        try await worker.shutdown()
        #expect(try await fixture.stats()["submissions"] == 1)
        try await fixture.client.closeAsync()
    }

    @MainActor @Test
    func closeCancelsActiveQueuedAndBackpressuredWorkBeforeClearingKeys() async throws {
        let fixture = try Scenario()
        let signer = try fixture.signer()
        let worker = try fixture.client.worker(using: signer, capacity: 1)
        let gate = BuildGate()
        let active = try await worker.submit { sequence in
            try await gate.wait()
            return try fixture.transaction(sequence: sequence)
        }
        try await waitUntil { gate.started }
        var queuedWasBuilt = false
        let queued = try await worker.submit { sequence in
            queuedWasBuilt = true
            return try fixture.transaction(sequence: sequence)
        }
        var blockedStarted = false
        let blocked = Task { @MainActor in
            blockedStarted = true
            let submission = try await worker.submit { sequence in
                queuedWasBuilt = true
                return try fixture.transaction(sequence: sequence)
            }
            _ = try await submission.value
        }
        try await waitUntil { blockedStarted }
        #expect(!signer.isPrivateKeyCleared)
        #expect(throws: AptosError.self) { try fixture.client.close() }
        #expect(!signer.isPrivateKeyCleared)
        try await fixture.client.closeAsync()
        await expectCancellation { _ = try await active.value }
        await expectCancellation { _ = try await queued.value }
        await expectCancellation { try await blocked.value }
        #expect(!queuedWasBuilt)
        #expect(signer.isPrivateKeyCleared)
        #expect(try await fixture.stats()["submissions"] == nil)
    }

    @MainActor @Test
    func concurrentAsyncCloseIsIdempotent() async throws {
        let fixture = try Scenario()
        let signer = try fixture.signer()
        let worker = try fixture.client.worker(using: signer)
        let gate = BuildGate()
        let active = try await worker.submit { sequence in
            try await gate.wait()
            return try fixture.transaction(sequence: sequence)
        }
        try await waitUntil { gate.started }
        let closers = (0..<16).map { _ in Task { @MainActor in try await fixture.client.closeAsync() } }
        for task in closers { try await task.value }
        await expectCancellation { _ = try await active.value }
        #expect(signer.isPrivateKeyCleared)
        try fixture.client.close()
    }

    @MainActor @Test
    func cancelledCloseWaiterStillFinishesKeyCleanup() async throws {
        let fixture = try Scenario()
        let signer = try fixture.signer()
        let worker = try fixture.client.worker(using: signer)
        let gate = BuildGate()
        let active = try await worker.submit { sequence in
            try await gate.wait()
            return try fixture.transaction(sequence: sequence)
        }
        try await waitUntil { gate.started }
        let closer = Task { @MainActor in try await fixture.client.closeAsync() }
        closer.cancel()
        try await closer.value
        await expectCancellation { _ = try await active.value }
        #expect(signer.isPrivateKeyCleared)
        try await fixture.client.closeAsync()
        #expect(try await fixture.stats()["submissions"] == nil)
    }

    @MainActor @Test
    func walletDenialOrCancellationNeverSubmitsATransaction() async throws {
        let fixture = try Scenario()
        defer { try? fixture.client.close() }
        let account = try fixture.signer()
        let transaction = try fixture.transaction()
        var callbackStarted = false
        for cancel in [false, true] {
            let wallet = try SwiftSigner(address: account.address, publicKey: account.publicKey,
                signBytes: { _ in throw WalletRejection.denied },
                signText: { _ in throw WalletRejection.denied },
                signTransaction: { _ in
                    MainActor.preconditionIsolated()
                    callbackStarted = true
                    if cancel { try await Task.sleep(for: .seconds(30)) }
                    throw WalletRejection.denied
                })
            if cancel {
                callbackStarted = false
                let task = Task { @MainActor in
                    _ = try await fixture.client.transactions.signAndSubmit(transaction, using: wallet)
                }
                try await waitUntil { callbackStarted }
                task.cancel()
                await expectCancellation { try await task.value }
            } else {
                await expectError(.crypto) {
                    _ = try await fixture.client.transactions.signAndSubmit(transaction, using: wallet)
                }
            }
        }
        #expect(try await fixture.stats()["submissions"] == nil)
        #expect(!account.isPrivateKeyCleared)
    }

    @MainActor @Test
    func buildFailureDoesNotKillWorkerOrSubmitFailedWork() async throws {
        let fixture = try Scenario()
        let worker = try fixture.client.worker(using: fixture.signer())
        let failed = try await worker.submit { _ in throw WalletRejection.denied }
        await expectError(.crypto) { _ = try await failed.value }
        let recovered = try await worker.submit { try fixture.transaction(sequence: $0) }
        _ = try await recovered.value
        try await worker.shutdown()
        #expect(try await fixture.stats()["submissions"] == 1)
        try await fixture.client.closeAsync()
    }

    @MainActor @Test
    func closingBorrowingClientDoesNotClearExternallyOwnedSigner() async throws {
        let owner = try AptosClient()
        defer { try? owner.close() }
        let fixture = try Scenario()
        let signer = try owner.generateAccount()
        let transaction = try fixture.transaction()
        _ = try await fixture.client.transactions.sign(transaction, using: signer)
        try fixture.client.close()
        #expect(!signer.isPrivateKeyCleared)
        _ = try await owner.transactions.sign(transaction, using: signer)
        try owner.close()
        #expect(signer.isPrivateKeyCleared)
        #expect(try await fixture.stats().isEmpty)
    }

    @MainActor @Test
    func invalidWorkerAndWaitOptionsDoNotTouchTransport() async throws {
        let fixture = try Scenario()
        defer { try? fixture.client.close() }
        let signer = try fixture.signer()
        for capacity in [0, -1, Int(Int32.max) + 1] {
            #expect(throws: AptosError.self) { try fixture.client.worker(using: signer, capacity: capacity) }
        }
        for timeout in [0, -1, Int(Int32.max) + 1] {
            await expectError(.validation) {
                _ = try await fixture.client.transactions.waitForTransaction(testHash, timeout: timeout)
            }
        }
        #expect(try await fixture.stats().isEmpty)
    }

    @MainActor @Test
    func cachedServicesRejectUseAfterCloseWithoutRequests() async throws {
        let fixture = try Scenario()
        let client = fixture.client
        let ledger = client.ledger
        let accounts = client.accounts
        let transactions = client.transactions
        let views = client.views
        let indexer = client.indexer
        let tables = client.tables
        let signer = try fixture.signer()
        let transaction = try fixture.transaction()
        try client.close()
        await expectError(.validation) { _ = try await ledger.info() }
        await expectError(.validation) { _ = try await accounts.get(AccountAddress("0x3")) }
        await expectError(.validation) { _ = try await accounts.balance(of: AccountAddress("0x3")) }
        await expectError(.validation) { _ = try await transactions.sign(transaction, using: signer) }
        await expectError(.validation) { _ = try await transactions.waitForTransaction(testHash) }
        await expectError(.validation) { _ = try await transactions.entryFunction("0x1::test::transfer") }
        await expectError(.validation) { _ = try await views.callRaw("0x1::test::read") }
        await expectError(.validation) { _ = try await indexer.queryRaw("query { items { id } }") }
        await expectError(.validation) { _ = try await tables.itemRaw(handle: "0x1", keyType: "u64", valueType: "u64", keyJSON: "0") }
        #expect(throws: AptosError.self) { try client.generateAccount() }
        #expect(throws: AptosError.self) { try transactions.fromBCS(rawTransaction) }
        #expect(try await fixture.stats().isEmpty)
    }

    @MainActor @Test
    func truncatedAndTrailingBCSAreRejectedWithoutCrashing() throws {
        let fixture = try Scenario()
        defer { try? fixture.client.close() }
        for length in 0..<rawTransaction.count {
            #expect(throws: AptosError.self) { try fixture.client.transactions.fromBCS(rawTransaction.prefix(length)) }
        }
        #expect(throws: AptosError.self) { try fixture.client.transactions.fromBCS(rawTransaction + Data([0])) }
        #expect(try fixture.client.transactions.fromBCS(rawTransaction).bcsData == rawTransaction)
    }

    @MainActor
    @Test(arguments: [Double.nan, .infinity, -.infinity, 0, -1, .greatestFiniteMagnitude])
    func invalidTimeoutsCannotTrapIntegerConversion(_ value: Double) {
        #expect(throws: AptosError.self) { try AptosClient(configuration: .init(requestTimeout: value)) }
        #expect(throws: AptosError.self) { try AptosClient(configuration: .init(indexerWaitTimeout: value)) }
    }

    @Test(arguments: ["", "0x", "0xgg", "0x1\u{0}", "0x" + String(repeating: "f", count: 65)])
    func invalidAddressesAreRecoverableErrors(_ value: String) {
        #expect(throws: AptosError.self) { try AccountAddress(value) }
    }

    @Test
    func byteAndIntegerBoundariesStayLossless() throws {
        for size in [0, 1, 127, 128, 255, 256, 4_096, 65_536] {
            let data = Data((0..<size).map { UInt8(truncatingIfNeeded: $0 &* 73 &+ 19) })
            #expect(Data(kotlinBytes: try data.kotlinBytes()) == data)
        }
        for value in ["-1", "", "1.1", "1e3", "184467440737095516160000000000000000000000000000000000000000000000000000000000000000000"] {
            #expect(throws: AptosError.self) { try MoveValue.u128(value).bcs() }
            #expect(throws: AptosError.self) { try MoveValue.u256(value).bcs() }
        }
        #expect(try MoveValue.u128("0").bcs() == Data(repeating: 0, count: 16))
        #expect(try MoveValue.u256("0").bcs() == Data(repeating: 0, count: 32))
    }
}

@MainActor
private struct Scenario {
    let client: AptosClient
    private let root: URL
    private let id = UUID().uuidString

    init(_ scenario: String = "", requestTimeout: TimeInterval = 10) throws {
        let url = try #require(ProcessInfo.processInfo.environment["KAPTOS_TEST_URL"])
        root = try #require(URL(string: url))
        client = try AptosClient(configuration: .init(network: .custom, fullNode: root, indexer: root,
            headers: ["X-AptosKit-Test-ID": id, "X-AptosKit-Test-Scenario": scenario], requestTimeout: requestTimeout))
    }

    func signer() throws -> LocalAccount {
        try client.importAccount(privateKey: "ed25519-priv-0x" + String(repeating: "11", count: 32), address: AccountAddress("0x1"))
    }

    func transaction(sequence: UInt64 = 7) throws -> SimpleTransaction {
        var data = rawTransaction
        for index in 0..<8 { data[32 + index] = UInt8(truncatingIfNeeded: sequence >> (index * 8)) }
        return try client.transactions.fromBCS(data)
    }

    func stats() async throws -> [String: Int] {
        let url = root.deletingLastPathComponent().appendingPathComponent("control").appendingPathComponent(id)
        let (data, response) = try await URLSession.shared.data(from: url)
        #expect((response as? HTTPURLResponse)?.statusCode == 200)
        return try JSONDecoder().decode([String: Int].self, from: data)
    }

    func waitFor(_ counter: String) async throws {
        let deadline = ContinuousClock.now.advanced(by: .seconds(5))
        while (try await stats()[counter] ?? 0) == 0 {
            try #require(ContinuousClock.now < deadline, "Fixture never received \(counter)")
            try await Task.sleep(for: .milliseconds(10))
        }
    }
}

@MainActor
private final class BuildGate {
    var started = false
    var isOpen = false
    func wait() async throws {
        started = true
        while !isOpen { try await Task.sleep(for: .milliseconds(10)) }
    }
}

@MainActor
private func waitUntil(_ predicate: () -> Bool) async throws {
    let deadline = ContinuousClock.now.advanced(by: .seconds(5))
    while !predicate() {
        try #require(ContinuousClock.now < deadline, "Callback never started")
        try await Task.sleep(for: .milliseconds(10))
    }
}

@MainActor
private func expectError(_ category: AptosError.Category, _ operation: () async throws -> Void) async {
    do { try await operation(); Issue.record("Expected AptosError.\(category)") }
    catch let error as AptosError { #expect(error.category == category) }
    catch { Issue.record("Expected AptosError.\(category), received \(error)") }
}

@MainActor
private func expectCancellation(_ operation: () async throws -> Void) async {
    do { try await operation(); Issue.record("Expected CancellationError") }
    catch is CancellationError { }
    catch { Issue.record("Expected CancellationError, received \(error)") }
}

private let testHash = "0x" + String(repeating: "ab", count: 32)
private enum WalletRejection: Error { case denied }
// Canonical RawTransaction from the independently pinned TS SDK fixture in ConsumerTests.swift.
private let rawTransaction: Data = {
    let hex = "0000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004"
    return Data(stride(from: 0, to: hex.count, by: 2).map { offset in
        let start = hex.index(hex.startIndex, offsetBy: offset)
        return UInt8(hex[start..<hex.index(start, offsetBy: 2)], radix: 16)!
    })
}()
