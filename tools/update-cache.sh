#!/usr/bin/env bash

CHROMIUM_SRC=${CHROMIUM_SRC:?"Please set CHROMIUM_SRC env var pointing to your chrome/src checkout directory"}

set -e

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")/.."

ROOT=$(pwd)
TOOLS="$ROOT/tools"
WORKDIR="$TOOLS/_workdir"

APIS_CACHE_FILE="$WORKDIR/apis.cache"
APIS_LAST_FILE="$WORKDIR/apis.last"

if [[ ! -d "$WORKDIR" ]]; then
  mkdir -p "$WORKDIR"
fi

cd "${CHROMIUM_SRC}/chrome/common/extensions/docs"

if [[ -r "$APIS_LAST_FILE" ]]; then
  LAST_SHA=$(cat "$APIS_LAST_FILE")
fi

CURRENT_SHA=$(git rev-parse HEAD)
if [[ "$CURRENT_SHA" == "$LAST_SHA" ]]; then
  echo "chromium sources have been already cached for this commit ($CURRENT_SHA)"
  echo "maybe you forgot to pull latest changes?"
  echo "cd \"$CHROMIUM_SRC\" && git pull && gclient sync --with_branch_heads"
  exit 3
fi

if [[ ! -r "$APIS_CACHE_FILE" ]]; then
  echo "'$APIS_CACHE_FILE' does not exist, will running full generation"
  LOAD_ARGS=""
else
  LOAD_ARGS="--load-file=\"$APIS_CACHE_FILE\""
fi

python ./server2/update_cache.py --no-push "$LOAD_ARGS" --save-file="$APIS_CACHE_FILE"

cd "${CHROMIUM_SRC}"
SHA=$(git rev-parse HEAD)
echo "$SHA" > "$APIS_LAST_FILE"