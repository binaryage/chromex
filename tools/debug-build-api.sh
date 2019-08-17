#!/usr/bin/env bash

set -e -o pipefail

CHROMIUM_SRC=${CHROMIUM_SRC:?"Please set CHROMIUM_SRC env var pointing to your chrome/src checkout directory"}

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")/.."

ROOT=$(pwd)
TOOLS="$ROOT/tools"
WORKDIR="$TOOLS/_workdir"

APIS_CACHE_FILE="$WORKDIR/apis.cache"
APIS_FILTERED_JSON_FILE="$WORKDIR/apis-filtered.json"
APIS_LAST_FILE="$WORKDIR/apis.last"
API_SOURCE_DIR="$WORKDIR/api"

if [[ ! -d "$WORKDIR" ]]; then
  mkdir -p "$WORKDIR"
fi

if [[ ! -r "$APIS_CACHE_FILE" ]]; then
  echo "'$APIS_CACHE_FILE' does not exist, run ./build-cache.sh"
  exit 1
fi

cd "$TOOLS/api-gen"
SHA=$(cat "$APIS_LAST_FILE")
lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts" --chromium-sha="$SHA" --filter="exts" --subns="ext"
lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts_private" --chromium-sha="$SHA" --filter="exts-private" --subns="ext"
lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/exts_internal" --chromium-sha="$SHA" --filter="exts-internal" --subns="ext"
lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/apps" --chromium-sha="$SHA" --filter="apps" --subns="app"
lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/apps_private" --chromium-sha="$SHA" --filter="apps-private" --subns="app"
lein run -- --input="$APIS_FILTERED_JSON_FILE" --outdir="$API_SOURCE_DIR/apps_internal" --chromium-sha="$SHA" --filter="apps-internal" --subns="app"