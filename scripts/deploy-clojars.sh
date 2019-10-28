#!/usr/bin/env bash

set -e

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

./scripts/list-jar.sh

LEIN_VERSION=$(grep "defproject" < "$PROJECT_FILE" | cut -d' ' -f3 | cut -d\" -f2)

# http://stackoverflow.com/a/1885534/84283
echo "Are you sure to publish version ${LEIN_VERSION}? [Yy]"
read -n 1 -r
if [[ "$REPLY" =~ ^[Yy]$ ]]; then
  lein deploy clojars
else
  exit 1
fi
