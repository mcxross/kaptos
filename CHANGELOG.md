# Kaptos Change Log

All notable changes to the "Kaptos" project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---
**[1.5.0-SNAPSHOT] - 2024-06-22**

**Added**
- Tnx build, sign and submit
- ANS feature support
- BCS ser/de for txns
- Txn status query
- Txn JVM sample
- The `Option` type now has an `expect` method that allows you to provide a custom error message when an `Option` is `None`.

**[Unreleased]**

**Changed**
- Txn status now uses 401 exception instead of all xxx

**[1.0.4-SNAPSHOT] - 2024-06-1**

**Added**
- Crypto layer. Key generation
- GraphQL definitions
- Move view support (JSON)

**[1.0.3-SNAPSHOT] - 2024-05-14**

**Added**
- Processor status retrieval
- Indexer last success version
- Added tests for account address

**Updated**
- Account address class

**[1.0.2-SNAPSHOT] - 2024-05-09**

**Added**
 - Faucet for the Kaptos network.
 - Added Kotlin Multiplatform Mobile (KMM) sample for Android and iOS.

**[1.0.1-SNAPSHOT] - 2024-04-30**

**Added**
- Client configuration options for all supported platforms.

**[1.0.0-SNAPSHOT] - 2024-04-26**

**Added**
- Initial release.
---