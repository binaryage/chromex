#!/usr/bin/env bash

# checks if all version strings are consistent

set -e

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

./scripts/list-jar.sh

LEIN_VERSION=$(grep "defproject" < "$PROJECT_FILE" | cut -d' ' -f3 | cut -d\" -f2)

if [[ "$LEIN_VERSION" =~ "SNAPSHOT" ]]; then
  echo "Publishing SNAPSHOT versions is not allowed. Bump current version $LEIN_VERSION to a non-snapshot version."
  exit 2
fi
