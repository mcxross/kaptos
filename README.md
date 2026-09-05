<h1 align="center">Kaptos - KMP SDK for Aptos</h1>

Kaptos is a Kotlin Multiplatform SDK for Aptos. It provides a common API for interacting with Aptos services across
multiple platforms.

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.4.10-B125EA?logo=kotlin)](https://kotlinlang.org)
![Docs](https://github.com/mcxross/kaptos/actions/workflows/docs.yml/badge.svg)
[![Maven Central](https://img.shields.io/maven-central/v/xyz.mcxross.kaptos/kaptos.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/xyz.mcxross.kaptos/kaptos)
[![Snapshot](https://img.shields.io/nexus/s/xyz.mcxross.kaptos/kaptos?server=https%3A%2F%2Fcentral.sonatype.com&nexusVersion=3&label=Snapshot)](https://central.sonatype.com/repository/maven-snapshots/xyz/mcxross/kaptos/)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

![badge-android](http://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android)
![badge-ios](http://img.shields.io/badge/Platform-iOS-orange.svg?logo=apple)
![badge-jvm](http://img.shields.io/badge/Platform-JVM-red.svg?logo=openjdk)
![badge-macos](http://img.shields.io/badge/Platform-macOS-orange.svg?logo=apple)

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Encrypted transactions](#encrypted-transactions)
- [Confidential assets](#confidential-assets)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Kotlin-native API**: namespaced services, coroutines, sealed models, unsigned Aptos values, and
  typed results.
- **Modern transactions**: standard, sponsored, multi-agent, orderless, script, multisig-v2, and
  account-abstraction flows.
- **Multiplatform**: Android, JVM, iOS, and macOS are compiled and tested release targets.
- **Optional cryptography**: matching artifacts provide batch-encrypted transactions and
  Confidential Assets without increasing the core dependency surface.

<details>
<summary><h2>Installation</h2></summary>

### Multiplatform Project

Add the following to your common source set:

```kotlin
commonMain.dependencies {
    implementation("xyz.mcxross.kaptos:kaptos:<version>")
}
```

Encrypted transaction construction is deliberately optional:

```kotlin
commonMain.dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-encrypted-transactions:<version>")
}
```

Confidential balances, proofs, and asset operations are provided by a separate artifact:

```kotlin
commonMain.dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-confidential-assets:<version>")
}
```

### Platform-Specific Project

To add Kaptos to a platform-specific project, you can add the following to your platform-specific source set:

#### Android

Kaptos provides two flavors for Android: `kaptos-android` and `kaptos-android-debug`. The `kaptos-android` flavor is
optimized for release builds, while the `kaptos-android-debug` flavor is optimized for debug builds.

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-android:<version>")
}
```

#### iOS

Kaptos provides artifacts for both iOS arm64 and x64 architectures. You can add the following to your iOS project:

##### iOS Arm64

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-iosArm64:<version>")
}
```

##### iOS x64

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-iosX64:<version>")
}
```

#### macOS

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-macos:<version>")
}
```

#### JVM

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-jvm:<version>")
}
```

> [!NOTE]
> Snapshots are available via Sonatype Central's snapshots repository. To use snapshots, add the following to your project:

```kotlin
repositories {
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}
```

</details>

## Usage

Use the scoped `aptos` entry point for one workflow. It closes the shared transport and clears
accounts created through the client, whether the workflow succeeds or fails.

```kotlin
suspend fun main() = aptos(AptosConfig(network = Network.TESTNET)) {
    val ledger = ledger.info()
    println("Chain ${ledger.getOrNull()?.chainId}")
}
```

Services are grouped by domain and share one configured transport. Construct `Aptos`
directly only when its lifetime is managed by a long-lived application scope.

```kotlin
aptos {
    val framework = AccountAddress.fromString("0x1")
    val account = accounts.get(framework)
    val ledger = ledger.info()
    val names = names.getAccountNames(framework)
}
```

### Read an account balance

```kotlin
aptos {
    accounts.getBalance(
        address = framework,
        asset = AccountAsset.coin("0x1::aptos_coin::AptosCoin"),
    ).fold(
        onSuccess = { println("Balance: $it octas") },
        onFailure = { println("Balance lookup failed: ${it.message}") },
    )
}
```

### Account lifecycle

```kotlin
aptos {
    val generated = account()
    val imported = ed25519Account("ed25519-priv-0x...")
    val message = "hello, Aptos"
    val signature = imported.signText(message)
        .getOrElse { failure -> error(failure.message) }
    check(imported.verifySignature(message.encodeToByteArray(), signature))
}
```

`generated` and `imported` are SDK-owned and cleared at the end of the block. An account supplied
by an external wallet remains caller-owned; Kaptos does not unexpectedly invalidate it.

### Submit transaction

The same service composes building, signing, submission, and confirmation. Amounts remain unsigned
throughout the call.

```kotlin
aptos(AptosConfig(network = Network.TESTNET)) {
    val alice = ed25519Account("ed25519-priv-0x...")
    val payload = TransactionPayload.entryFunction(
        function = "0x1::aptos_account::transfer",
        arguments = listOf(
            MoveArgument.Address(recipient),
            MoveArgument.U64(1_000_000u),
        ),
    )
    val committed = transactions.submitAndWait(alice, payload)
        .getOrElse { failure -> error(failure.message) }

    println("Committed ${committed.hash}")
}
```

### Encrypted transactions

Add `kaptos-encrypted-transactions` alongside the core SDK, using the same version. Import
`encryptedTransactions` to create a service on an existing `Aptos` instance. The service encrypts
the executable payload, signs the encrypted transaction, and submits it through the shared transport.

The configured fullnode must advertise an encryption key in its ledger response. Otherwise the
service returns `AptosError.UnsupportedFeature`. Use a funded standard signer; the encrypted
submission helpers reject Keyless signers, and encrypted transactions cannot be simulated.

```kotlin
import xyz.mcxross.kaptos.encrypted.encryptedTransactions
import xyz.mcxross.kaptos.model.TransactionPayload
import xyz.mcxross.kaptos.move.MoveArgument

// config selects a network with encrypted-transaction support; recipient is an AccountAddress.
aptos(config) {
    val sender = ed25519Account("ed25519-priv-0x...")
    val encrypted = encryptedTransactions()
    val committed = encrypted.submitAndWait(
        sender = sender,
        payload = TransactionPayload.entryFunction(
            function = "0x1::aptos_account::transfer",
            arguments = listOf(
                MoveArgument.Address(recipient),
                MoveArgument.U64(1_000_000uL),
            ),
        ),
    ).getOrElse { failure -> error(failure.message) }
    println("Committed ${committed.hash}")
}
```

For multi-agent, fee-payer, or orderless flows, build an `UnsignedTransaction` through
`transactions`, then pass it to `encrypted.signAndSubmit(transaction, sender, ...)` with the
matching signers. Use `encrypt(transaction)` when signing and submission happen separately;
sign the returned encrypted transaction. `EncryptedTransactionOptions` accepts explicit signer
authentication keys and a claimed entry function. Reuse the service within a workflow and call
`encrypted.clearCache()` after a committed authentication-key rotation.

The [encrypted transaction sample](sample/jvmApp/src/main/kotlin/xyz/mcxross/kaptos/sample/EncryptedTransaction.kt)
runs with `--args=encrypted` and requires `APTOS_PRIVATE_KEY`, `APTOS_RECIPIENT`, and an
`APTOS_NETWORK` that supports encrypted transactions.

### Confidential assets

Add `kaptos-confidential-assets` alongside the core SDK, using the same version. Its
`confidentialAssets()` extension provides encrypted balance reads, proof-backed transfers and
withdrawals, deposits, and encryption-key management. The default Move module is
`0x1::confidential_asset`; use `ConfidentialAssetConfig(moduleAddress = ...)` for another deployment.
The selected network and fungible asset must support these operations. `token` is the asset's
metadata address, and the transaction signer needs funds for fees.

Confidential decryption keys are separate from transaction-signing keys. The following example
imports a canonical 32-byte key supplied by the application's key storage and registers a new
confidential balance. For an existing balance, import its registered key and omit registration.

```kotlin
import xyz.mcxross.kaptos.confidential.ConfidentialDecryptionKey
import xyz.mcxross.kaptos.confidential.confidentialAssets

// config, token (AccountAddress), and storedDecryptionKeyBytes come from the application.
aptos(config) {
    val signer = ed25519Account("ed25519-priv-0x...")
    val key = own(ConfidentialDecryptionKey.fromBytes(storedDecryptionKeyBytes))
    val assets = confidentialAssets()

    assets.registerBalance(signer = signer, token = token, key = key)
        .getOrElse { failure -> error(failure.message) }

    val balance = assets.getBalance(signer.accountAddress, token, key)
        .getOrElse { failure -> error(failure.message) }
    // balance.availableAmount and balance.pendingAmount contain the decrypted amounts.
}
```

`own(...)` clears the imported key when the `aptos` scope closes; the application remains
responsible for retaining its stored key and clearing the source byte array. For disposable
registration examples, `confidentialDecryptionKey()` generates a key with the same managed
lifecycle. Keep a recoverable key for any balance that will hold funds.

Use `deposit(signer, token, amount)` to move public funds into the confidential balance and
`rolloverPendingBalance(signer, token)` to move pending funds into the available balance.
`transfer(signer, recipient, token, key, amount)` generates the transfer proofs; the recipient
must have a registered encryption key. `withdraw(signer, token, key, amount)` moves funds back
to a public balance. Amount arguments use `ULong` in the asset's smallest units.

Mutation methods wait for commitment and invalidate affected caches. Their `build...` counterparts
return unsigned transactions for separate signing or external fee-payer workflows.
`ConfidentialTransactionOptions` accepts gas settings and either a local fee-payer signer or an
external fee-payer address; submitting with an external payer requires separate signing.
The service also exposes normalization, incoming-transfer pause controls, key rotation, auditor
queries, and paginated activity. Balance reads are fresh by default; call `assets.clearCache()`
to discard cached balances and keys after changes made outside the service.

The [confidential asset sample](sample/jvmApp/src/main/kotlin/xyz/mcxross/kaptos/sample/ConfidentialAssetTransaction.kt)
runs with `--args=confidential`. It registers a generated key and requires `APTOS_PRIVATE_KEY`,
`APTOS_CONFIDENTIAL_ASSET` (the metadata address), and `APTOS_NETWORK` for the selected deployment.

### Typed view calls

Transactions and views share `MoveArgument` from `xyz.mcxross.kaptos.move` and `TypeTag`.
Typed views validate the function ABI and send BCS arguments; returned JSON values stay lossless.

```kotlin
import xyz.mcxross.kaptos.view.serialization.decodeValue
import kotlinx.serialization.builtins.serializer
import xyz.mcxross.kaptos.move.MoveArgument
import xyz.mcxross.kaptos.model.TypeTag
import xyz.mcxross.kaptos.model.flatMap

aptos {
    val balance = views.call(
        function = "0x1::coin::balance",
        typeArguments = listOf(TypeTag.fromString("0x1::aptos_coin::AptosCoin")),
        arguments = listOf(MoveArgument.Address(owner)),
    ).flatMap { it.decodeValue(0, ULong.serializer()) }
}
```

Validation checks view status, argument and type-argument counts, concrete types, integer widths,
struct field names, and supported value shapes. Typed views reject opaque `PreSerialized` values,
including nested ones. Enum arguments use the ABI's explicit `variants` list and named fields;
missing layouts, unknown variants, and missing or extra fields are rejected. The codec never infers
variant layouts from struct fields. It does not infer JSON representations from BCS bytes. Move execution
and generic ability constraints are validated by the fullnode.

`ledgerVersion` applies to both ABI lookup and execution. Historical calls use an isolated codec;
current calls share the transaction codec and its preloaded module ABIs.

Use `views.callRaw(function, typeArguments, arguments, ledgerVersion)` for explicit JSON access.
Its type arguments are strings and arguments are `JsonElement` values; the fullnode validates
those raw values. `decodeValue(index, deserializer)` selects one return value using an explicit
serializer and reports invalid indexes or incompatible return types as typed errors.

### ABI freshness and transport integration

Fetched ABIs expire after 60 seconds by default, with at most 128 fetched modules retained per
codec. Configure `AptosConfig.abiCachePolicy` to change those limits; a zero TTL disables fetched
caching. Failed refreshes return an error rather than using an expired ABI. Explicitly preloaded
ABIs stay pinned for offline builds. Call `aptos.clearAbiCache()` after a deployment to clear both
fetched and preloaded ABIs. Historical view calls keep their ABI lookup isolated at the requested
ledger version.

Ktor injection is available through an explicit adapter. The application retains ownership of an
injected transport; the SDK manages its default transport.

```kotlin
import xyz.mcxross.kaptos.transport.ktor.asAptosTransport

val client = Aptos(AptosConfig(transport = httpClient.asAptosTransport()))
```

Serializer-based view decoding is an extension in `xyz.mcxross.kaptos.view.serialization`.
The explicit raw JSON APIs continue to use kotlinx.serialization JSON values; these are intentional
integration boundaries, while transaction and typed-view arguments use SDK-owned types.

### Solana derivable account abstraction

The framework-native helper derives the Aptos account address and constructs the exact SIWS
message from each entry-function transaction. A local Ed25519 account is convenient for tests;
production applications can provide a `SolanaMessageSigner` backed by their wallet UI.

```kotlin
aptos(AptosConfig(network = Network.TESTNET)) {
    val fundingAccount = ed25519Account("ed25519-priv-0x...")
    val abstracted = SolanaDerivableAccount.fromEd25519(
        signer = fundingAccount,
        domain = "wallet.example",
    )

    transactions.submitAndWait(
        signer = abstracted,
        payload = TransactionPayload.entryFunction(
            function = "0x1::aptos_account::transfer",
            arguments = listOf(
                MoveArgument.Address(recipient),
                MoveArgument.U64(1_000_000u),
            ),
        ),
    )
}
```

The derivable account must be funded before it can reserve transaction fees. Aptos removed
permissioned signers; do not use `0x1::permissioned_delegation::authenticate` for new flows.

Runnable standard, sponsored, orderless, abstraction, encrypted-transaction, and
Confidential Asset examples are in [`sample/jvmApp`](sample/jvmApp).

Run a JVM example directly with Gradle. `standard` is the default when `--args` is omitted:

```shell
APTOS_PRIVATE_KEY='ed25519-priv-0x...' \
APTOS_RECIPIENT='0x...' \
APTOS_NETWORK='TESTNET' \
./gradlew :sample:jvmApp:run --args=standard
```

Other accepted names are `sponsored`, `orderless`, `abstraction`, `encrypted`,
`confidential`, `account`, and `multi-key`. Each sample reports any additional environment values
it requires.

## Documentation and examples

- For full SDK documentation, check out the [Kotlin SDK documentation](https://preview.aptos.dev/sdks/kotlin-sdk/)
- For reference documentation, check out the [API reference documentation](https://mcxross.github.io/kaptos/) for the associated version.
- For in-depth examples, check out the [sample](./sample) folder with ready-made examples.

## Testing

To run the SDK tests, simply run from the root of this repository:

> [!NOTE] 
> For a better experience, make sure there is an aptos local node process up and running (can check if there is a
> process running on port 8080).

```shell
./gradlew test 
```

## Contribution

All contributions to Kaptos are welcome. Before opening a PR, please submit an issue detailing the bug or feature. When opening a PR, please ensure that your contribution builds on the KMM toolchain, has been linted with `ktfmt <GOOGLE (INTERNAL)>`, and contains tests when applicable. For more information, please see the [contribution guidelines](CONTRIBUTING.md).

## License

    Copyright 2024 McXross

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[maven-central]: https://search.maven.org/search?q=g:xyz.mcxross.kaptos
