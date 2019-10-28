#!/usr/bin/env bash

set -e

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

./scripts/list-jar.sh

lein install
