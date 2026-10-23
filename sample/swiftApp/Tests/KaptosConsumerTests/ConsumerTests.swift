import Foundation
import Testing
import Kaptos

struct ConsumerTests {
    @MainActor
    @Test
    func testLocalTransportStagesErrorsAndWorker() async throws {
        let url = try #require(ProcessInfo.processInfo.environment["KAPTOS_TEST_URL"], "Run scripts/test-swift-package to start the local fixture")
        let client = try Client(configuration: .make(network: .CUSTOM, fullNode: url))
        let account = try client.importAccount(privateKey: "ed25519-priv-0x" + String(repeating: "11", count: 32), address: "0x1")
        let address = try Addresses.shared.parse(value: "0xa")
        let state = try await client.accounts.get(address: address)
        #expect(state.sequenceNumber == UInt64.max)
        let balance = try await client.accounts.balance(address: address)
        #expect(balance == UInt64.max)
        for version: UInt64? in [nil, 100, UInt64.max] {
            let raw = try await client.views.callRaw(function: "0x1::test::read", ledgerVersion: version)
            #expect(try AptosJSON.decode([String].self, from: raw) == [version.map(String.init) ?? "none"])
            let item = try await client.tables.getItem(handle: "0x1", keyType: "u64", valueType: "u64", keyJson: "0", ledgerVersion: version)
            #expect(try AptosJSON.decode(String.self, from: item) == (version.map(String.init) ?? "none"))
        }
        let recipient = try Move.shared.address(value: "0xb")
        let amount = Move.shared.u64(value: 123)
        let payload = try await client.transactions.entryFunction(function: "0x1::test::transfer", arguments: [recipient, amount])
        let options = try TransactionOptions.make(maxGasAmount: 2_000_000, gasUnitPrice: 100,
                                         expirationTimestampSecs: 9_999_999_999, expirationSecondsFromNow: nil,
                                         sequenceNumber: nil)
        let transaction = try await client.transactions.build(sender: account.accountAddress, payload: payload, options: options)
        let simulation = try await client.transactions.simulate(transaction: transaction, senderPublicKey: account.publicKey,
            secondarySignerPublicKeys: [], feePayerPublicKey: nil,
            options: SimulationOptions(estimateGasUnitPrice: false, estimateMaxGasAmount: false, estimatePrioritizedGasUnitPrice: false))
        #expect(simulation.first?.success == true)
        let auth = try await client.transactions.sign(signer: account, transaction: transaction)
        let expectedHash = try client.transactions.userTransactionHash(transaction: transaction, senderAuthenticator: auth,
                                                                       secondaryAuthenticators: [], feePayerAuthenticator: nil)
        let pending = try await client.transactions.submit(transaction: transaction, senderAuthenticator: auth,
                                                           secondaryAuthenticators: [], feePayerAuthenticator: nil)
        #expect(pending.transactionHash == expectedHash)
        let confirmed = try await client.transactions.waitForTransaction(hash: pending.transactionHash,
            options: WaitForTransactionOptions(timeoutSecs: 5, checkSuccess: true, waitForIndexer: false))
        #expect(confirmed.transactionHash == expectedHash)
        do {
            _ = try await client.accounts.get(address: Addresses.shared.parse(value: "0x404"))
            Issue.record("Expected API failure")
        } catch let error as AptosException {
            #expect(error.category == .Api)
            #expect(error.apiCode == "sequence_number_too_old")
            #expect(error.vmCode == 42)
        }
        let worker = try client.worker(signer: account, capacity: 2)
        let observation = Task { () throws -> Bool in
            for try await event in worker.events.asAsyncSequence() {
                if event.kind == .Submitted {
                    #expect(event.requestId == 0)
                    #expect(event.sequenceNumber == 7)
                    return true
                }
            }
            return false
        }
        try await Task.sleep(nanoseconds: 50_000_000)
        let submission = try await worker.submit { sequence in
            #expect(sequence == 7)
            return transaction
        }
        let submitted = try await submission.await()
        #expect(submitted.transactionHash == expectedHash)
        let timeout = Task { try await Task.sleep(nanoseconds: 5_000_000_000); observation.cancel() }
        let observed = try await observation.value
        timeout.cancel()
        #expect(observed)
        try await worker.shutdown(cancelPending: false)
        try await client.closeAsync()
        #expect(account.isPrivateKeyCleared)
    }

    @Test
    func testOctetRoundTripAndUnsignedWidths() throws {
        let original = Data((0...255).map { UInt8($0) })
        #expect(Data(kotlinBytes: try original.kotlinBytes()) == original)
        #expect(Configuration.make(maxGasAmount: UInt64.max).maxGasAmount == UInt64.max)
        #expect(try TransactionOptions.make().gasUnitPrice == nil)
        #expect(try TransactionOptions.make(gasUnitPrice: 100).gasUnitPrice == 100)
        #expect(try TransactionOptions.make(gasUnitPrice: UInt64.max).gasUnitPrice == UInt64.max)
        #expect(Data(kotlinBytes: Move.shared.u64(value: UInt64.max).toBcs()) == Data(repeating: 255, count: 8))
        let u128 = try Move.shared.u128(decimal: "340282366920938463463374607431768211455")
        #expect(Data(kotlinBytes: u128.toBcs()) == Data(repeating: 255, count: 16))
        let u256 = try Move.shared.u256(decimal: "115792089237316195423570985008687907853269984665640564039457584007913129639935")
        #expect(Data(kotlinBytes: u256.toBcs()) == Data(repeating: 255, count: 32))
        #expect(throws: (any Error).self) { try Move.shared.u128(decimal: "340282366920938463463374607431768211456") }
    }

    @MainActor
    @Test
    func testAccountOwnershipAndImport() throws {
        let client = try Client(configuration: .make())
        let account = try client.importAccount(privateKey: "ed25519-priv-0x" + String(repeating: "11", count: 32), address: nil)
        #expect(!account.isPrivateKeyCleared)
        try client.close()
        #expect(account.isPrivateKeyCleared)
        try client.close()
    }

    @Test
    func testDecodableRetainsLargeDecimals() throws {
        struct Amount: Decodable { let amount: String }
        let value = try AptosJSON.decode(Amount.self, from: "{\"amount\":\"18446744073709551615\"}")
        #expect(value.amount == "18446744073709551615")
    }
    // Raw transaction/signing message: pinned Aptos TS SDK 7.3.1 fixture. Ed25519 signature:
    // independently generated with OpenSSL; transaction hash: Python hashlib.sha3_256.
    @MainActor
    @Test
    func testSigningBcsHashAndSwiftCallback() async throws {
        let client = try Client(configuration: .make())
        defer { try? client.close() }
        let account = try client.importAccount(privateKey: "ed25519-priv-0x" + String(repeating: "11", count: 32), address: "0x1")
        let raw = Data(hex: "0000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004")
        let transaction = try client.transactions.fromBcs(bytes: raw.kotlinBytes())
        #expect(Data(kotlinBytes: transaction.signingBcs()) == raw)
        #expect(Data(kotlinBytes: transaction.signingMessage()) == Data(hex: "b5e97db07fa0bd0e5598aa3643a9bc6f6693bddc1a9fec9e674a461eaa00b1930000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004"))
        let authenticator = try await client.transactions.sign(signer: account, transaction: transaction)
        #expect(Data(kotlinBytes: authenticator.toBcs()) == Data(hex: "0020d04ab232742bb4ab3a1368bd4615e4e6d0224ab71a016baf8520a332c977873740e7c53229c382c250706560fc523b6d9d0733d7220175b585605d59267447a8dc1381f48f0cf507ffa6ff3d817d513c873cb5ea50a06e7f7e1331d82a3a61fe02"))
        let hash = try client.transactions.userTransactionHash(transaction: transaction, senderAuthenticator: authenticator, secondaryAuthenticators: [], feePayerAuthenticator: nil)
        #expect(hash == "0x46a1dbca2867d2bc0856c9b441a68dc95f757efb9d2fbd1ad196951fb9798c44")
        let signer = CallbackSigner(accountAddress: account.accountAddress, publicKey: account.publicKey,
                                    bytes: { _ in throw WalletFailure.denied },
                                    text: { _ in throw WalletFailure.denied },
                                    transaction: { tx in try await client.transactions.sign(signer: account, transaction: tx) })
        let callbackAuth = try await client.transactions.sign(signer: signer, transaction: transaction)
        #expect(Data(kotlinBytes: callbackAuth.toBcs()) == Data(kotlinBytes: authenticator.toBcs()))
    }

    @MainActor
    @Test
    func testCallbackErrorAndCancellation() async throws {
        let client = try Client(configuration: .make())
        defer { try? client.close() }
        let account = try client.generateAccount(scheme: .Ed25519)
        let tx = try client.transactions.fromBcs(bytes: Data(hex: "0000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004").kotlinBytes())
        let denied = CallbackSigner(accountAddress: account.accountAddress, publicKey: account.publicKey,
                                    bytes: { _ in throw WalletFailure.denied },
                                    text: { _ in throw WalletFailure.denied },
                                    transaction: { _ in throw WalletFailure.denied })
        do {
            _ = try await client.transactions.sign(signer: denied, transaction: tx)
            Issue.record("Expected a typed signing error")
        } catch let error as AptosException {
            #expect(error.category == .Crypto)
        }
        var started = false
        let delayed = CallbackSigner(accountAddress: account.accountAddress, publicKey: account.publicKey,
                                     bytes: { _ in throw WalletFailure.denied },
                                     text: { _ in throw WalletFailure.denied },
                                     transaction: { _ in
                                         started = true
                                         try await Task.sleep(nanoseconds: 30_000_000_000)
                                         throw WalletFailure.denied
                                     })
        let task = Task { try await client.transactions.sign(signer: delayed, transaction: tx) }
        for _ in 0..<100 where !started { try await Task.sleep(nanoseconds: 10_000_000) }
        #expect(started)
        task.cancel()
        do { _ = try await task.value; Issue.record("Expected cancellation") }
        catch is CancellationError { }
    }

}

private enum WalletFailure: Error { case denied }

private extension Data {
    init(hex: String) {
        self.init(stride(from: 0, to: hex.count, by: 2).map { offset in
            let start = hex.index(hex.startIndex, offsetBy: offset)
            return UInt8(hex[start..<hex.index(start, offsetBy: 2)], radix: 16)!
        })
    }
}
