#!/usr/bin/env bash

set -e -o pipefail

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

cd "$ROOT"

RELEASES="$ROOT/releases"
RELEASE_BUILD="$ROOT/resources/unpacked"
RELEASE_BUILD_COMPILED="$RELEASE_BUILD/out"

if [[ ! -d "$RELEASE_BUILD_COMPILED" ]]; then
  echo "'$RELEASE_BUILD_COMPILED' does not exist, run './scripts/release.sh' to fully build the project"
  exit 2
fi

if [[ ! -d "$RELEASES" ]]; then
  mkdir -p "$RELEASES"
fi

VERSION_WITH_QUOTES=$(grep "version" <package.json | cut -d' ' -f4)
VERSION=${VERSION_WITH_QUOTES//[\",]/}

PACKAGE_NAME="$RELEASES/chromex-sample-$VERSION"
ZIP_NAME="$PACKAGE_NAME.zip"

if [[ -f "$ZIP_NAME" ]]; then
  rm "$ZIP_NAME"
fi

cd "$RELEASE_BUILD"

zip -qr -9 -x manifest.edn -X "$ZIP_NAME" .

# this lists content of the packaged file just for review
unzip -l "$ZIP_NAME"

echo "'$ZIP_NAME' ready for upload => https://chrome.google.com/webstore/developer/dashboard"
