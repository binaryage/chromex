#!/usr/bin/env bash

set -e

cd `dirname "${BASH_SOURCE[0]}"` && source "./config.sh"  && cd "$ROOT"

NIGHTLY_WORKTREE="$ROOT/../chromex-nightly"

# rebase nightly on top of our current master
# see http://stackoverflow.com/a/35097583/84283
if [[ -d "$NIGHTLY_WORKTREE" ]]; then
  rm -rf "$NIGHTLY_WORKTREE"
fi

git fetch origin
git worktree add "$NIGHTLY_WORKTREE" origin/nightly
cd "$NIGHTLY_WORKTREE"
git checkout -B nightly
git reset --hard origin/nightly
git rebase master
cd "$ROOT"
rm -rf "$NIGHTLY_WORKTREE"
git worktree prune

# we should be able to merge nightly as fast-forward thanks to the rebase above
git merge --ff-only nightly
