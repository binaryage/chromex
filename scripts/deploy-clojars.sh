#!/usr/bin/env bash

set -e

pushd `dirname "${BASH_SOURCE[0]}"` > /dev/null
source "./config.sh"

pushd "$ROOT"

./scripts/list-jar.sh

LEIN_VERSION=`cat "$PROJECT_FILE" | grep "defproject" | cut -d' ' -f3 | cut -d\" -f2`

# http://stackoverflow.com/a/1885534/84283
echo "Are you sure to publish version ${LEIN_VERSION}? [Yy]"
read -n 1 -r
if [[ "$REPLY" =~ ^[Yy]$ ]]; then
  lein with-profile lib deploy clojars
else
  exit 1
fi

popd

popd
