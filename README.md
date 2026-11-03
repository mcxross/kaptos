# AptosKit

An Aptos SDK for Swift: query accounts, build and sign transactions, call Move functions, and work
with digital assets.

## Installation

In Xcode, add `https://github.com/mcxross/kaptos.git` under **Package Dependencies**, choose a
version, and select the **AptosKitLibrary** product for your app target.

For a Swift package, add this dependency to `Package.swift`:

```swift
.package(url: "https://github.com/mcxross/kaptos.git", from: "1.0.1")
```

Then add the product to your target's dependencies:

```swift
.product(name: "AptosKitLibrary", package: "kaptos")
```

For a snapshot, select its complete version from GitHub Releases using Xcode's **Exact Version**
rule. Snapshot versions contain `-SNAPSHOT`, a commit timestamp, and a commit hash; they follow
a separate version sequence from stable releases.

Requires iOS 18+ or macOS 15+ on Apple Silicon. Both Apple Silicon and Intel iOS simulators are
supported.

## Read an account

```swift
import AptosKit

@MainActor
func readAccount() async throws {
    let client = try AptosClient(network: .devnet)
    defer { try? client.close() }

    let address = try AccountAddress("0x1")
    let account = try await client.accounts.get(address)
    let balance = try await client.accounts.balance(of: address)
    print(account.sequenceNumber, balance)
}
```

Use `ClientConfiguration` to set custom endpoints, request timeouts, or headers.

## Send a transaction

With an existing `client` and funded `account`, transfer 123 octas:

```swift
let recipient = try AccountAddress("0xb")
let payload = try await client.transactions.entryFunction(
    "0x1::aptos_account::transfer",
    arguments: [.address(recipient), .u64(123)]
)
let pending = try await client.transactions.signAndSubmit(
    payload,
    using: account,
    options: TransactionOptions(gasUnitPrice: 100)
)
let confirmed = try await client.transactions.waitForTransaction(pending.transactionHash)
```

Build, simulate, sign, and submit are also available separately. Create a local signing account
with `client.generateAccount()` or import an AIP-80 private key with
`client.importAccount(privateKey:)`. Generating a key does not create or fund an on-chain account.
Use `SwiftSigner` to connect a wallet's asynchronous signing callbacks.

## Read Move data

View functions decode directly into your `Decodable` types:

```swift
let balances = try await client.views.call(
    "0x1::coin::balance",
    typeArguments: ["0x1::aptos_coin::AptosCoin"],
    arguments: [.address(address)],
    as: [String].self
)
```

Indexer queries and table reads also support `Decodable` results. Use their raw methods when you
need the JSON response directly. Large integer values remain decimal strings.

## Errors and lifecycle

Catch `AptosError` to handle API, validation, transport, and signing failures. API errors include
`apiCode` and `vmCode`; task cancellation throws `CancellationError`. Cancelling a task does not
reverse a submitted transaction.

Keep the client and its signing accounts in a `@MainActor` model. Finish outstanding work before
calling `close()`, which clears client-owned private keys. With transaction workers, use
`try await client.closeAsync()` to stop workers before closing. Observe worker events with
`for try await event in worker.events`.
