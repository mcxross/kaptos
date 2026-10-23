import Foundation
import Testing
import AptosKit

struct ConsumerTests {
    @MainActor
    @Test
    func testLocalTransportStagesErrorsAndWorker() async throws {
        let url = try #require(ProcessInfo.processInfo.environment["KAPTOS_TEST_URL"], "Run ./gradlew :kaptos-swift:checkSwiftPackage to start the local fixture")
        let client = try AptosClient(configuration: .init(network: .custom, fullNode: URL(string: url)))
        let ledger = try await client.ledger.info()
        #expect(ledger.chainId.chainId == 4)
        #expect(ledger.version == 1)
        let account = try client.importAccount(privateKey: "ed25519-priv-0x" + String(repeating: "11", count: 32), address: AccountAddress("0x1"))
        let address = try AccountAddress("0xa")
        let state = try await client.accounts.get(address)
        #expect(state.sequenceNumber == UInt64.max)
        let balance = try await client.accounts.balance(of: address)
        #expect(balance == UInt64.max)
        for version: UInt64? in [nil, 100, UInt64.max] {
            let values = try await client.views.call("0x1::test::read", ledgerVersion: version, as: [String].self)
            #expect(values == [version.map(String.init) ?? "none"])
            let raw = try await client.views.callRaw("0x1::test::read", ledgerVersion: version)
            #expect(try AptosJSON.decode([String].self, from: raw) == values)
            let item = try await client.tables.item(handle: "0x1", keyType: "u64", valueType: "u64", key: 0, ledgerVersion: version, as: String.self)
            #expect(item == (version.map(String.init) ?? "none"))
        }
        do {
            _ = try await client.views.call("0x1::test::read", as: Int.self)
            Issue.record("Expected a decoding error")
        } catch let error as AptosError { #expect(error.category == .serialization) }
        let recipient = try AccountAddress("0xb")
        let payload = try await client.transactions.entryFunction("0x1::test::transfer", arguments: [.address(recipient), .u64(123)])
        let options = TransactionOptions(maxGasAmount: 2_000_000, gasUnitPrice: 100, expirationTimestampSecs: 9_999_999_999)
        for price: UInt64? in [nil, 100, UInt64.max] {
            let boundary = try await client.transactions.build(payload, sender: account.address,
                options: TransactionOptions(gasUnitPrice: price))
            #expect(boundary.rawTransaction.gasUnitPrice == (price ?? 100))
        }
        for invalid in [TransactionOptions(maxGasAmount: 1),
                        TransactionOptions(expirationTimestampSecs: 1, expirationSecondsFromNow: 1),
                        TransactionOptions(sequenceNumber: 1, nonce: 1)] {
            do {
                _ = try await client.transactions.build(payload, sender: account.address, options: invalid)
                Issue.record("Expected invalid transaction options")
            } catch let error as AptosError { #expect(error.category == .validation) }
        }
        let transaction = try await client.transactions.build(payload, sender: account.address, options: options)
        let simulation = try await client.transactions.simulate(transaction, using: account)
        #expect(simulation.first?.success == true)
        let auth = try await client.transactions.sign(transaction, using: account)
        let expectedHash = try client.transactions.hash(of: transaction, authenticator: auth)
        let pending = try await client.transactions.submit(transaction, authenticator: auth)
        #expect(pending.transactionHash == expectedHash)
        let confirmed = try await client.transactions.waitForTransaction(pending.transactionHash)
        #expect(confirmed.transactionHash == expectedHash)
        let convenient = try await client.transactions.signAndSubmit(payload, using: account, options: options)
        #expect(convenient.transactionHash == expectedHash)
        do {
            _ = try await client.accounts.get(AccountAddress("0x404"))
            Issue.record("Expected API failure")
        } catch AptosError.api(let details) {
            #expect(details.apiCode == "sequence_number_too_old")
            #expect(details.vmCode == 42)
        }
        let request = Task { _ = try await client.accounts.get(AccountAddress("0x2")) }
        try await Task.sleep(nanoseconds: 50_000_000)
        request.cancel()
        do { _ = try await request.value; Issue.record("Expected transport cancellation") }
        catch is CancellationError { }
        let worker = try client.worker(using: account, capacity: 2)
        let observation = Task { () throws -> Bool in
            for try await event in worker.events {
                if event.kind == .submitted {
                    #expect(event.requestID == 0)
                    #expect(event.sequenceNumber == 7)
                    return true
                }
            }
            return false
        }
        try await Task.sleep(nanoseconds: 50_000_000)
        let submission = try await worker.submit { sequence in
            MainActor.preconditionIsolated()
            #expect(sequence == 7)
            return transaction
        }
        let submitted = try await submission.value
        #expect(submitted.transactionHash == expectedHash)
        let timeout = Task { try await Task.sleep(nanoseconds: 5_000_000_000); observation.cancel() }
        let observed = try await observation.value
        timeout.cancel()
        #expect(observed)
        try await worker.shutdown()
        try await client.closeAsync()
        #expect(account.isPrivateKeyCleared)
        #expect(throws: AptosError.self) { try client.worker(using: account) }
    }

    @Test
    func testOctetRoundTripAndUnsignedWidths() throws {
        let original = Data((0...255).map { UInt8($0) })
        #expect(Data(kotlinBytes: try original.kotlinBytes()) == original)
        #expect(ClientConfiguration(maxGasAmount: UInt64.max).maxGasAmount == UInt64.max)
        #expect(TransactionOptions().gasUnitPrice == nil)
        #expect(TransactionOptions(gasUnitPrice: 100).gasUnitPrice == 100)
        #expect(TransactionOptions(gasUnitPrice: UInt64.max).gasUnitPrice == UInt64.max)
        #expect(try MoveValue.u64(UInt64.max).bcs() == Data(repeating: 255, count: 8))
        #expect(try MoveValue.u128("340282366920938463463374607431768211455").bcs() == Data(repeating: 255, count: 16))
        #expect(try MoveValue.u256("115792089237316195423570985008687907853269984665640564039457584007913129639935").bcs() == Data(repeating: 255, count: 32))
        #expect(throws: AptosError.self) { try MoveValue.u128("340282366920938463463374607431768211456").bcs() }
    }

    @MainActor
    @Test
    func testAccountOwnershipAndImport() throws {
        let client = try AptosClient(network: .devnet)
        let account = try client.importAccount(privateKey: "ed25519-priv-0x" + String(repeating: "11", count: 32))
        #expect(!account.isPrivateKeyCleared)
        try client.close()
        #expect(account.isPrivateKeyCleared)
        try client.close()
        #expect(try AccountAddress("0x01") == AccountAddress("0x1"))
        let address = try AccountAddress("0x1")
        #expect(try JSONDecoder().decode(AccountAddress.self, from: JSONEncoder().encode(address)) == address)
        do { _ = try AccountAddress("invalid"); Issue.record("Expected invalid address") }
        catch let error as AptosError { #expect(error.category == .validation) }
        #expect(throws: AptosError.self) { try AptosClient(configuration: .init(requestTimeout: .infinity)) }
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
        let client = try AptosClient()
        defer { try? client.close() }
        let account = try client.importAccount(privateKey: "ed25519-priv-0x" + String(repeating: "11", count: 32), address: AccountAddress("0x1"))
        let raw = Data(hex: "0000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004")
        let transaction = try client.transactions.fromBCS(raw)
        #expect(transaction.bcsData == raw)
        #expect(transaction.signingData == Data(hex: "b5e97db07fa0bd0e5598aa3643a9bc6f6693bddc1a9fec9e674a461eaa00b1930000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004"))
        let authenticator = try await client.transactions.sign(transaction, using: account)
        #expect(authenticator.bcsData == Data(hex: "0020d04ab232742bb4ab3a1368bd4615e4e6d0224ab71a016baf8520a332c977873740e7c53229c382c250706560fc523b6d9d0733d7220175b585605d59267447a8dc1381f48f0cf507ffa6ff3d817d513c873cb5ea50a06e7f7e1331d82a3a61fe02"))
        let hash = try client.transactions.hash(of: transaction, authenticator: authenticator)
        #expect(hash == "0x46a1dbca2867d2bc0856c9b441a68dc95f757efb9d2fbd1ad196951fb9798c44")
        let signer = try SwiftSigner(address: account.address, publicKey: account.publicKey,
            signBytes: { _ in throw WalletFailure.denied }, signText: { _ in throw WalletFailure.denied },
            signTransaction: { tx in
                MainActor.preconditionIsolated()
                return try await client.transactions.sign(tx, using: account)
            })
        let callbackAuth = try await client.transactions.sign(transaction, using: signer)
        #expect(callbackAuth.bcsData == authenticator.bcsData)
    }

    @MainActor
    @Test
    func testCallbackErrorAndCancellation() async throws {
        let client = try AptosClient()
        defer { try? client.close() }
        let account = try client.generateAccount()
        let tx = try client.transactions.fromBCS(Data(hex: "0000000000000000000000000000000000000000000000000000000000000001070000000000000002000000000000000000000000000000000000000000000000000000000000000104636f696e087472616e736665720001087b0000000000000080841e000000000064000000000000003f420f000000000004"))
        let denied = try SwiftSigner(address: account.address, publicKey: account.publicKey,
            signBytes: { _ in throw WalletFailure.denied }, signText: { _ in throw WalletFailure.denied },
            signTransaction: { _ in throw WalletFailure.denied })
        do {
            _ = try await client.transactions.sign(tx, using: denied)
            Issue.record("Expected a typed signing error")
        } catch let error as AptosError { #expect(error.category == .crypto) }
        var started = false
        let delayed = try SwiftSigner(address: account.address, publicKey: account.publicKey,
            signBytes: { _ in throw WalletFailure.denied }, signText: { _ in throw WalletFailure.denied },
            signTransaction: { _ in
                MainActor.preconditionIsolated()
                started = true
                try await Task.sleep(nanoseconds: 30_000_000_000)
                throw WalletFailure.denied
            })
        let task = Task { _ = try await client.transactions.sign(tx, using: delayed) }
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
