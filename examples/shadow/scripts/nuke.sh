#!/usr/bin/env bash

set -e -o pipefail

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

cd "$ROOT"

./scripts/clean.sh

rm -rf .cpcache
rm -rf .shadow-cljs
rm -rf node_modules
rm -rf releases
