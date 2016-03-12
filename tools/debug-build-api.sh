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
SRC="$ROOT/src"
TOOLS="$ROOT/tools"
WORKDIR="$TOOLS/_workdir"
ROOT_README="$ROOT/readme.md"
SRC_EXTS="$SRC/exts"
SRC_EXTS_PRIVATE="$SRC/exts_private"
SRC_EXTS_INTERNAL="$SRC/exts_internal"
SRC_APPS="$SRC/apps"
SRC_APPS_PRIVATE="$SRC/apps_private"
SRC_APPS_INTERNAL="$SRC/apps_internal"

SERVER2_DIR="${CHROMIUM_SRC}chrome/common/extensions/docs/server2"
APIS_CACHE_FILE="$WORKDIR/apis.cache"
APIS_JSON_FILE="$WORKDIR/apis.json"
APIS_FILTERED_JSON_FILE="$WORKDIR/apis-filtered.json"
APIS_LAST_FILE="$WORKDIR/apis.last"
API_SOURCE_DIR="$WORKDIR/api"

if [ ! -d "$WORKDIR" ] ; then
  mkdir -p "$WORKDIR"
fi

if [ ! -r "$APIS_CACHE_FILE" ] ; then
  echo "'$APIS_CACHE_FILE' does not exist, run ./build-cache.sh"
  popd
  exit 1
fi

cd "$TOOLS/api-gen"
SHA=`cat "$APIS_LAST_FILE"`
lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts" --chromium-sha="$SHA" --filter="exts" --subns="ext"