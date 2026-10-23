import Foundation
import Kaptos

// Run without arguments for offline key creation. --read-devnet performs read-only network calls.
@main
struct KaptosSample {
    @MainActor
    static func main() async throws {
        let client = try Client(configuration: .make())
        defer { try? client.close() }
        let account = try client.generateAccount(scheme: .Ed25519)
        print("Local account: \(account.accountAddress)")
        if CommandLine.arguments.contains("--read-devnet") {
            let address = try Addresses.shared.parse(value: "0x1")
            let state = try await client.accounts.get(address: address)
            print("0x1 sequence: \(state.sequenceNumber)")
            print("0x1 balance: \(try await client.accounts.balance(address: address))")
        }
    }
}
