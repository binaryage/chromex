#!/usr/bin/env bash

set -e -o pipefail

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

cd "$ROOT"

if [[ -d "$RELEASE_BUILD_COMPILED_DIR" ]]; then
  rm -rf "$RELEASE_BUILD_COMPILED_DIR"
fi
