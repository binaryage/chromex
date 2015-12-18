#!/usr/bin/env bash

export CHROMIUM_SRC=~/tasks/chromium/src/

set -e

pushd () {
    command pushd "$@" > /dev/null
}

popd () {
    command popd "$@" > /dev/null
}

die_if_dirty_working_copy () {
  git add --all
  set +e
  git diff-index --exit-code HEAD > /dev/null
  if [ $? -ne 0 ] ; then
    echo "working copy is not clean in '$(pwd)'"
    exit 1
  fi
  set -e
}

pushd .

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")"; cd ../..

ROOT=$(pwd)
CHROMEX="$ROOT/chromex"

# pull latest chromium
pushd "$CHROMIUM_SRC"

die_if_dirty_working_copy

git pull --ff-only
gclient sync --with_branch_heads

CHROMIUM_SHA=$(git rev-parse HEAD)
CHROMIUM_SHORT_SHA=$(git rev-parse --short HEAD)
popd

# generate chromex APIs
pushd "$CHROMEX"

die_if_dirty_working_copy

git checkout master
git pull --ff-only

git checkout auto
git pull --ff-only
git merge -m "merge changes from master" master

./tools/update-cache.sh
./tools/build-api.sh

git add -A .
git commit -m "regenerate APIs from Chromium @ $CHROMIUM_SHORT_SHA" -m "https://chromium.googlesource.com/chromium/src.git/+/$CHROMIUM_SHA"

git push

popd

popd