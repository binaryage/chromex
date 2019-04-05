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
SRC_APPS="$SRC/apps"
SRC_APPS_PRIVATE="$SRC/apps_private"
GENONLY=${GENONLY}
SKIP_EXTRACTION=${SKIP_EXTRACTION}
SKIP_DISTILLING=${SKIP_DISTILLING}

SERVER2_DIR="${CHROMIUM_SRC}chrome/common/extensions/docs/server2"
APIS_CACHE_FILE="$WORKDIR/apis.cache"
APIS_JSON_FILE="$WORKDIR/apis.json"
APIS_FILTERED_JSON_FILE="$WORKDIR/apis-filtered.json"
APIS_LAST_FILE="$WORKDIR/apis.last"
API_SOURCE_DIR="$WORKDIR/api"

if [[ ! -d "$WORKDIR" ]]; then
  mkdir -p "$WORKDIR"
fi

if [[ ! -r "$APIS_CACHE_FILE" ]]; then
  echo "'$APIS_CACHE_FILE' does not exist, run ./build-cache.sh"
  popd
  exit 1
fi

if [[ ! "$GENONLY" ]]; then
  echo "reminder: you might consider fetching latest chromium sources and running ./update-cache.sh first"

  if [[ -z "$SKIP_EXTRACTION" ]]; then
    pushd .
    cd "$TOOLS/api-extractor"
    ./api-extractor.py --load-file="$APIS_CACHE_FILE" --save-file="$APIS_JSON_FILE" --server2-dir="$SERVER2_DIR"
    popd
  fi

  if [[ -z "$SKIP_DISTILLING" ]]; then
    pushd .
    cd "$TOOLS/api-distiller"
    lein run -- --input="$APIS_JSON_FILE" --output="$APIS_FILTERED_JSON_FILE"
    popd
  fi
fi

pushd .

rm -rf "$API_SOURCE_DIR"
mkdir -p "$API_SOURCE_DIR"
cd "$TOOLS/api-gen"
SHA=`cat "$APIS_LAST_FILE"`
EXTS_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts" --chromium-sha="$SHA" --filter="exts" --subns="ext" | grep "RESULT:"`
EXTS_PRIVATE_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts_private" --chromium-sha="$SHA" --filter="exts-private" --subns="ext" | grep "RESULT:"`
APPS_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/apps" --chromium-sha="$SHA" --filter="apps" --subns="app" | grep "RESULT:"`
APPS_PRIVATE_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/apps_private" --chromium-sha="$SHA" --filter="apps-private" --subns="app"| grep "RESULT:"`

popd

rm -rf "$SRC_EXTS"
rm -rf "$SRC_EXTS_PRIVATE"
rm -rf "$SRC_APPS"
rm -rf "$SRC_APPS_PRIVATE"

mkdir -p "$SRC_EXTS"
mkdir -p "$SRC_EXTS_PRIVATE"
mkdir -p "$SRC_APPS"
mkdir -p "$SRC_APPS_PRIVATE"

cp -R "$API_SOURCE_DIR/"* "$SRC"

popd

STATS_HEADER1="| API family | namespaces | properties | functions | events |"
STATS_HEADER2="| --- | --- | --- | --- | --- |"

EXTS_PUBLIC_APIS="${EXTS_RESULT/RESULT:/| [Public Chrome Extension APIs](src/exts) |}"
EXTS_PRIVATE_APIS="${EXTS_PRIVATE_RESULT/RESULT:/| [Private Chrome Extension APIs](src/exts_private) |}"
APPS_PUBLIC_APIS="${APPS_RESULT/RESULT:/| [Public Chrome App APIs](src/apps) |}"
APPS_PRIVATE_APIS="${APPS_PRIVATE_RESULT/RESULT:/| [Private Chrome App APIs](src/apps_private) |}"

STATS_TABLE="
$STATS_HEADER1
$STATS_HEADER2
$EXTS_PUBLIC_APIS
$APPS_PUBLIC_APIS
$EXTS_PRIVATE_APIS
$APPS_PRIVATE_APIS"

README_WITH_MARKER=`perl -pe 'BEGIN{undef $/;} s/also for Chrome Apps:\n.*?Note: Chromex generator uses/also for Chrome Apps:\nDYNAMICMARKER\n\nNote: Chromex generator uses/smg' "$ROOT_README"`
NEW_README="${README_WITH_MARKER/DYNAMICMARKER/$STATS_TABLE}"

echo "$NEW_README" > "$ROOT_README"

echo "$STATS_TABLE

written to $ROOT_README"
