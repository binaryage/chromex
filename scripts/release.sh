#!/usr/bin/env bash

set -e

cd `dirname "${BASH_SOURCE[0]}"` && source "./config.sh"  && cd "$ROOT"

lein clean

./scripts/check-versions.sh
./scripts/prepare-jar.sh
./scripts/check-release.sh
./scripts/deploy-clojars.sh
