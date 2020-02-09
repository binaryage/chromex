#!/usr/bin/env bash

# updates all version strings

set -e

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

VERSION=$1

if [[ -z "$VERSION" ]]; then
  echo "please specify version as the first argument"
  popd
  exit 1
fi

sed -i "" -e "s/defproject binaryage\/chromex \".*\"/defproject binaryage\/chromex \"$VERSION\"/g" "$PROJECT_FILE"
sed -i "" -e "s/def current-version \".*\"/def current-version \"$VERSION\"/g" "$PROJECT_VERSION_FILE"
sed -i "" -e "s/binaryage\/chromex \".*\"/binaryage\/chromex \"$VERSION\"/g" "$EXAMPLES_LEIN_FIGWHEEL_PROJECT_FILE"

# this is just a sanity check
./scripts/check-versions.sh
