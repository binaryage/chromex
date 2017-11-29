#!/usr/bin/env bash

set -e

pushd `dirname "${BASH_SOURCE[0]}"` > /dev/null
source "./config.sh"

pushd "$ROOT"

./scripts/check-versions.sh

LEIN_VERSION=`cat "$PROJECT_FILE" | grep "defproject" | cut -d' ' -f3 | cut -d\" -f2`

BASE_FILE="chromex-$LEIN_VERSION"
POM_PATH="META-INF/maven/binaryage/chromex/pom.xml"
INSPECT_DIR="inspect"

cd "target"
unzip -l "$BASE_FILE.jar"
unzip -o "$BASE_FILE.jar" "$POM_PATH" -d "$INSPECT_DIR"

echo
echo "approx. pom.xml dependencies:"
cat "$INSPECT_DIR/$POM_PATH" | grep -E -i "artifactId|version"

echo
echo "----------------------------"
echo

popd

popd
