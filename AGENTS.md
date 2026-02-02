# Agent Notes

## Repository structure and connections (kaptos)

- Root Gradle build includes `:lib` and `:sample:jvmApp` (`settings.gradle.kts`).
- `:lib` is renamed to `:kaptos` and is the main Kotlin Multiplatform SDK module.
- `sample/jvmApp` depends on local module `project(":kaptos")`.
- `sample/kmm` is a separate standalone KMP demo project with its own Gradle settings; it consumes published `xyz.mcxross.kaptos:kaptos`.

## SDK layering

- `Aptos` is the facade entrypoint and delegates feature APIs (`Aptos.kt`).
- `protocol/`: public API contracts (interfaces).
- `api/`: protocol implementations, mostly thin wrappers.
- `internal/`: core business logic, REST/GraphQL execution, pagination, tx flows.
- `client/`: transport layer (Ktor/Apollo); `expect/actual` HTTP client setup per platform.
- `model/`: shared domain/request/response/DSL types used across all layers.

## Network + platform

- Endpoints are resolved through `AptosConfig` and maps in `util/ApiEndpoint.kt`.
- HTTP client is `expect`ed in `client/Core.kt` and implemented in:
  - `androidMain` (OkHttp)
  - `jvmMain` (CIO)
  - `appleMain` (Darwin)
  - `jsMain` (Js)
  - `mingwMain` (WinHttp)

## GraphQL subsystem

- Queries and schema are in `lib/src/commonMain/graphql/`.
- Apollo generates typed code under `lib/build/generated/source/apollo/.../xyz/mcxross/kaptos/generated`.
- Query execution uses `getGraphqlClient()` + `handleQuery()` in `internal/`.
- GraphQL input DSL helpers are in `model/types/*Builder.kt`.

## Transactions and crypto flow

- High-level flow: `api` -> `internal` -> `transaction/builder` -> signer/account -> BCS submit.
- Transaction building/signing lives in `transaction/` and `internal/TransactionSubmission.kt`.
- Account abstractions live in `account/`; key signing primitives are in `core/crypto` (`expect/actual`).

## Tests, CI, release

- Tests live in `lib/src/commonTest` (unit + e2e).
- CI runs GraphQL generation before docs/tests.
- Release workflow calls `scripts/release`, which decides publish mode from commit messages.
