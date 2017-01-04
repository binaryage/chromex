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

pushd .
cd "${CHROMIUM_SRC}chrome/common/extensions/docs/server2"

# without this bootstrapping subsequent update_cache.py would fail
# with 'ImportError: No module named third_party.json_schema_compiler.memoize'
python << EOF
import build_server
build_server.main()
EOF

python update_cache.py --no-push --save-file="$APIS_CACHE_FILE"

popd

cd "${CHROMIUM_SRC}"
SHA=`git rev-parse HEAD`
echo "$SHA" > "$APIS_LAST_FILE"

popd
