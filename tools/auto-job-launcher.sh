#!/usr/bin/env bash

set -ex

die_if_dirty_working_copy () {
  if [[ -n "$(git status -uno --porcelain)" ]]; then
    echo "working copy is not clean in '$(pwd)'"
    exit 1
  fi
}

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")"
cd ..

ROOT="$(pwd)"
TOOLS="$ROOT/tools"

die_if_dirty_working_copy

# update our code to latest
git fetch origin
git checkout -B master origin/master

# run the job
exec "$TOOLS/auto-job.sh"