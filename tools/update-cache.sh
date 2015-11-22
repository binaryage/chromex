#!/usr/bin/env bash

CHROMIUM_SRC=${CHROMIUM_SRC:?"Please set CHROMIUM_SRC env var pointing to your chrome/src checkout directory"}

set -e

pushd () {
    command pushd "$@" > /dev/null
}

popd () {
    command popd "$@" > /dev/null
}

pushd .

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")"; cd ..

ROOT=`pwd`
TOOLS="$ROOT/tools"
WORKDIR="$TOOLS/_workdir"

SERVER2_DIR="${CHROMIUM_SRC}chrome/common/extensions/docs/server2"
APIS_CACHE_FILE="$WORKDIR/apis.cache"
APIS_LAST_FILE="$WORKDIR/apis.last"

if [ ! -d "$WORKDIR" ] ; then
  mkdir -p "$WORKDIR"
fi

if [ ! -r "$APIS_CACHE_FILE" ] ; then
  echo "'$APIS_CACHE_FILE' does not exist, run ./build-cache.sh"
  popd
  exit 1
fi

pushd .
cd "${CHROMIUM_SRC}chrome/common/extensions/docs"

LAST_SHA=`cat "$APIS_LAST_FILE"`
if [ ! "$LAST_SHA" ] ; then
  echo "'$APIS_LAST_FILE' does not exist, run ./build-cache.sh for first run"
  popd
  popd
  exit 2
fi

CURRENT_SHA=`git rev-parse HEAD`
if [ "$CURRENT_SHA" == "$LAST_SHA" ] ; then
  echo "chromium sources have been already cached for this commit ($CURRENT_SHA)"
  echo "maybe you forgot to pull latest changes?"
  echo "cd \"$CHROMIUM_SRC\" && git pull && gclient sync --with_branch_heads"
  popd
  popd
  exit 3
fi

python ./server2/update_cache.py --no-push --save-file="$APIS_CACHE_FILE" --commit="$LAST_SHA"

cd "${CHROMIUM_SRC}"
SHA=`git rev-parse HEAD`
echo "$SHA" > "$APIS_LAST_FILE"

popd

popd