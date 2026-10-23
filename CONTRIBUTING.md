# Contributing

All added or modified Kotlin source and Gradle Kotlin scripts (`.kt` and `.kts`) must pass
`ktfmt --google-style` before committing or stashing. Use ktfmt 0.64 to match CI.

Install ktfmt (on macOS: `brew install ktfmt`) and enable the shared hook once per clone:

```sh
./scripts/install-git-hooks
```

Format changed and untracked Kotlin files, review the result, and stage the intended changes:

```sh
./scripts/format-kotlin
git diff
git add <files>
git commit
```

The pre-commit hook checks exact staged content without rewriting the index, preserving partial
staging. Formatting failures or a missing formatter block commits containing Kotlin changes.

Use `git stash-formatted` instead of `git stash push`:

```sh
git stash-formatted -u -m 'work in progress'
```

The wrapper checks both staged and working-tree Kotlin files and forwards arguments to
`git stash push`. If checks fail, format the files and restage the intended changes first.
Use ordinary `git stash list`, `pop`, and `apply` for retrieval. Git has no pre-stash hook,
so ordinary `git stash push` bypasses this check.

CI independently checks changed Kotlin files on pull requests and pushes to `main`.
Maintainers should require the `Kotlin formatting` status check in branch protection;
local hooks alone can be bypassed with `--no-verify`.

Run `./scripts/format-kotlin --all --check` to audit all tracked Kotlin files. The default
workflow checks changed files, avoiding unrelated formatting edits. Non-Kotlin files and
ignored generated build output are outside ktfmt's scope.

## Swift consumer regressions

Run `./gradlew :kaptos-swift:checkReleaseSwiftPackage` on macOS to install the Release archive
and test its Swift 6 consumer against an isolated HTTP server. The suite covers ledger and account
reads, numeric boundaries, transaction stages, signing callbacks, cancellation, and key cleanup.
It uses synthetic keys and never submits to a public network.

To repeat the suite up to ten times, stopping on the first failing iteration:

```sh
./gradlew :kaptos-swift:checkReleaseSwiftPackage -PswiftPackageTestRepetitions=10
```

`checkSwiftPackageDistribution` runs the same suite after resolving a Git version and downloading
the checksummed binary. It accepts the same repetition option.

## Releases

Maintain `version` in `gradle.properties` independently on each release branch. A commit titled
`release v<version>` on `main` publishes a stable Maven version; a commit titled
`release v<version>-SNAPSHOT` on `snapshot` publishes a Maven snapshot. The marker must match the
checked-out version. Ordinary commits do not publish either package.

After Maven publication succeeds, the same workflow publishes AptosKit from the same SDK commit
and verifies installation through SwiftPM. Stable Swift versions retain the SDK version and use
the `swift-package` distribution branch. Snapshot versions use `swift-package-snapshot` and append
the source commit's Unix timestamp and hash, such as `1.5.3-SNAPSHOT.1790000000.g0123456789ab`.
Swift tags and binary URLs are immutable, so successive Maven snapshots need distinct Swift versions.

If Maven succeeds but AptosKit fails, rerun the failed jobs in the release workflow. The
**SwiftPM distribution** workflow also supports manual recovery: use the original SDK commit as
`source_ref`, its unchanged Kaptos version as `version`, and enable `publish`. Existing tags and
release assets are checked rather than overwritten.
