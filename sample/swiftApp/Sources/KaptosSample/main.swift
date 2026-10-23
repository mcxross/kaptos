import Foundation
import AptosKit

// Run without arguments for offline key creation. --read-devnet performs read-only network calls.
@main
struct KaptosSample {
    @MainActor
    static func main() async throws {
        let client = try AptosClient(network: .devnet)
        defer { try? client.close() }
        let account = try client.generateAccount()
        print("Local account: \(account.address)")
        if CommandLine.arguments.contains("--read-devnet") {
            let address = try AccountAddress("0x1")
            let state = try await client.accounts.get(address)
            print("0x1 sequence: \(state.sequenceNumber)")
            print("0x1 balance: \(try await client.accounts.balance(of: address))")
        }
    }
}
