#!/usr/bin/env bash

set -e -o pipefail

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

cd "$ROOT"

if [[ ! -d "$RELEASE_BUILD_COMPILED_DIR" ]]; then
  echo "'$RELEASE_BUILD_COMPILED_DIR' does not exist, run './scripts/release.sh' to fully build the project"
  exit 2
fi

if [[ ! -d "$RELEASES_DIR" ]]; then
  mkdir -p "$RELEASES_DIR"
fi

VERSION_WITH_QUOTES=$(grep "version" <package.json | cut -d' ' -f4)
VERSION=${VERSION_WITH_QUOTES//[\",]/}

PACKAGE_NAME="$RELEASES_DIR/chromex-sample-$VERSION"
ZIP_NAME="$PACKAGE_NAME.zip"

if [[ -f "$ZIP_NAME" ]]; then
  rm "$ZIP_NAME"
fi

cd "$RELEASE_BUILD_DIR"
zip -qr -9 -x manifest.edn -X "$ROOT/$ZIP_NAME" .
cd "$ROOT"

# this lists content of the packaged file just for review
unzip -l "$ZIP_NAME"

echo
echo "'$ZIP_NAME' is ready for upload"
echo "=> https://chrome.google.com/webstore/developer/dashboard"
