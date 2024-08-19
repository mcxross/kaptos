<h1 align="center">Kaptos - KMP SDK for Aptos</h1>

Kaptos is a Kotlin Multiplatform SDK for Aptos. It provides a common API for interacting with Aptos services across
multiple platforms.

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.9.23-B125EA?logo=kotlin)](https://kotlinlang.org)
![Docs](https://github.com/mcxross/kaptos/actions/workflows/docs.yml/badge.svg)
[![Maven Central](https://img.shields.io/maven-central/v/xyz.mcxross.kaptos/kaptos.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/xyz.mcxross.kaptos/kaptos)
[![Snapshot](https://img.shields.io/nexus/s/xyz.mcxross.kaptos/kaptos?server=https%3A%2F%2Fs01.oss.sonatype.org&label=Snapshot)](https://s01.oss.sonatype.org/content/repositories/snapshots/xyz/mcxross/kaptos/)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

![badge-android](http://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android)
![badge-ios](http://img.shields.io/badge/Platform-iOS-orange.svg?logo=apple)
![badge-tvos](http://img.shields.io/badge/Platform-tvOS-lightgrey.svg?logo=apple)
![badge-watchos](http://img.shields.io/badge/Platform-watchOS-lightgrey.svg?logo=apple)
![badge-js](http://img.shields.io/badge/Platform-NodeJS-yellow.svg?logo=javascript)
![badge-jvm](http://img.shields.io/badge/Platform-JVM-red.svg?logo=openjdk)
![badge-linux](http://img.shields.io/badge/Platform-Linux-lightgrey.svg?logo=linux)
![badge-macos](http://img.shields.io/badge/Platform-macOS-orange.svg?logo=apple)
![badge-windows](http://img.shields.io/badge/Platform-Windows-blue.svg?logo=windows)

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Multiplatform**: Kaptos is a Kotlin Multiplatform library usable across various platforms.
- **Aptos API**: Kaptos offers a unified API for Aptos services.
- **Client Configuration**: Kaptos supports customizable client settings, such as proxy and network configurations.

<details>
<summary><h2>Installation</h2></summary>

### Multiplatform Project

Add the following to your common source set:

```kotlin
commonMain.dependencies {
    implementation("xyz.mcxross.kaptos:kaptos:<version>")
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

#### tvOS

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-tvos:<version>")
}
```

#### watchOS

Kaptos provides artifacts for both watchOS arm32, arm64 and x64 architectures. You can add the following to your watchOS
project:

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-watchosarm32:<version>")
}
```

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-watchosarm64:<version>")
}
```

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-watchosx64:<version>")
}
```

#### Js

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-js:<version>")
}
```

#### JVM

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-jvm:<version>")
}
```

#### Linux

Kaptos provides artifacts for both Linux arm64 and x64 architectures. You can add the following to your Linux project:

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-linuxarm64:<version>")
}
```

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-linuxx64:<version>")
}
```

#### Windows

```kotlin
dependencies {
    implementation("xyz.mcxross.kaptos:kaptos-mingw:<version>")
}
```

> [!NOTE]
> Snapshots are available via the Sonatype snapshots repository. To use snapshots, add the following to your project:

```kotlin
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}
```

</details>

## Usage

Initialize `Aptos` to access the SDK API.

```kotlin
val aptos = Aptos()
```

If you want to pass in a custom configuration, you can do so by passing in a `AptosConfig` object.

```kotlin
val config = AptosConfig(AptosSettings(network = Network.LOCAL))
val aptos = Aptos(config)
```

### Reading Data from chain

```kotlin
val modules = aptos.getAccountModules("0x1".toAccountAddress())
```

### Account management (default to Ed25519)

#### Generate new keys

```kotlin
val account = Account.generate()
```

#### Derive from private key
```kotlin
// Create a private key instance for Ed25519 scheme 
val privateKey = Ed25519PrivateKey("myEd25519privatekeystring")

// Derive an account from private key

// This is used as a local calculation and therefore is used to instantiate an `Account`
// that has not had its authentication key rotated
val account = Account.fromPrivateKey(privateKey)
```

#### Derive from private key and address

```kotlin
// Create a private key instance for Ed25519 scheme 
val privateKey = Ed25519PrivateKey("myEd25519privatekeystring")

// Derive an account from private key and address

// create an AccountAddress instance from the account address string
val address = AccountAddress.fromString()

```

### Submit transaction

Kaptos provides Domain Specific Language (DSL) for building transactions. The following example demonstrates how to build
a simple transaction to transfer coins from one account to another.

```kotlin
val alice = Account.generate()
val bob = Account.generate()

// Creating account credentials does not automatically create an account on-chain.
// You must explicitly create an account on-chain before you can interact with it.
// To do this in testnet, you can use the faucet.
val aliceFaucet = aptos.fundAccount(alice.accountAddress, 1000000000)
val bobFaucet = aptos.fundAccount(bob.accountAddress, 1000000000)

val txn = aptos.buildTransaction.simple(
    sender = alice.accountAddress,
    data = entryFunctionData {
        function = "0x1::coin::transfer"
        typeArguments = typeArguments {
            +TypeTagStruct(type = "0x1::aptos_coin::AptosCoin".toStructTag())
        }
        functionArguments = functionArguments {
            +bob.accountAddress
            +U64(SEND_AMOUNT)
        }
    },
)
```

It also provides pre-built transaction builders for common transactions. For example, to transfer coins from one account
to another, you can use the `transferCoinTransaction` builder.

```kotlin
val txn = aptos.transferCoinTransaction(alice.accountAddress, bob.accountAddress, 1_000_000UL)
```

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

