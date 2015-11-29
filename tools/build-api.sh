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

if [ ! "$GENONLY" ] ; then
  echo "reminder: you might consider fetching latest chromium sources and running ./update-cache.sh first"

  pushd .
  cd "$TOOLS/api-extractor"
  ./api-extractor.py --load-file="$APIS_CACHE_FILE" --save-file="$APIS_JSON_FILE" --server2-dir="$SERVER2_DIR"
  popd

  pushd .
  cd "$TOOLS/api-distiller"
  lein run -- --input="$APIS_JSON_FILE" --output="$APIS_FILTERED_JSON_FILE"
  popd
fi

pushd .

rm -rf "$API_SOURCE_DIR"
mkdir -p "$API_SOURCE_DIR"
cd "$TOOLS/api-gen"
SHA=`cat "$APIS_LAST_FILE"`
EXTS_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts" --chromium-sha="$SHA" --filter="exts" | grep "RESULT:"`
EXTS_PRIVATE_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts_private" --chromium-sha="$SHA" --filter="exts-private" | grep "RESULT:"`
EXTS_INTERNAL_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts_internal" --chromium-sha="$SHA" --filter="exts-internal" | grep "RESULT:"`
APPS_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/apps" --chromium-sha="$SHA" --filter="apps" | grep "RESULT:"`
APPS_PRIVATE_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/apps_private" --chromium-sha="$SHA" --filter="apps-private" | grep "RESULT:"`
APPS_INTERNAL_RESULT=`lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/apps_internal" --chromium-sha="$SHA" --filter="apps-internal" | grep "RESULT:"`

popd

rm -rf "$SRC_EXTS"
rm -rf "$SRC_EXTS_PRIVATE"
rm -rf "$SRC_EXTS_INTERNAL"
rm -rf "$SRC_APPS"
rm -rf "$SRC_APPS_PRIVATE"
rm -rf "$SRC_APPS_INTERNAL"

mkdir -p "$SRC_EXTS"
mkdir -p "$SRC_EXTS_PRIVATE"
mkdir -p "$SRC_EXTS_INTERNAL"
mkdir -p "$SRC_APPS"
mkdir -p "$SRC_APPS_PRIVATE"
mkdir -p "$SRC_APPS_INTERNAL"

cp -R "$API_SOURCE_DIR/"* "$SRC"

popd

STATS_HEADER1="| API family | namespaces | properties | functions | events |"
STATS_HEADER2="| --- | --- | --- | --- | --- |"

EXTS_PUBLIC_APIS="${EXTS_RESULT/RESULT:/| [Public Chrome Extension APIs](src/exts) |}"
EXTS_PRIVATE_APIS="${EXTS_PRIVATE_RESULT/RESULT:/| [Private Chrome Extension APIs](src/exts_private) |}"
EXTS_INTERNAL_APIS="${EXTS_INTERNAL_RESULT/RESULT:/| [Internal Chrome Extension APIs](src/exts_internal) |}"
APPS_PUBLIC_APIS="${APPS_RESULT/RESULT:/| [Public Chrome App APIs](src/apps) |}"
APPS_PRIVATE_APIS="${APPS_PRIVATE_RESULT/RESULT:/| [Private Chrome App APIs](src/apps_private) |}"
APPS_INTERNAL_APIS="${APPS_INTERNAL_RESULT/RESULT:/| [Internal Chrome App APIs](src/apps_internal) |}"

STATS_TABLE="
$STATS_HEADER1
$STATS_HEADER2
$EXTS_PUBLIC_APIS
$EXTS_PRIVATE_APIS
$EXTS_INTERNAL_APIS
$APPS_PUBLIC_APIS
$APPS_PRIVATE_APIS
$APPS_INTERNAL_APIS"

README_WITH_MARKER=`perl -pe 'BEGIN{undef $/;} s/provides following wrappers:\n.*?Note: Chromex generator uses/provides following wrappers:\nDYNAMICMARKER\n\nNote: Chromex generator uses/smg' "$ROOT_README"`
NEW_README="${README_WITH_MARKER/DYNAMICMARKER/$STATS_TABLE}"

echo "$NEW_README" > "$ROOT_README"

echo "$STATS_TABLE

written to $ROOT_README"