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
