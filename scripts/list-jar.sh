#!/usr/bin/env bash

set -e

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

./scripts/check-versions.sh

LEIN_VERSION=$(grep "defproject" < "$PROJECT_FILE" | cut -d' ' -f3 | cut -d\" -f2)

BASE_FILE="chromex-$LEIN_VERSION"
POM_PATH="META-INF/maven/binaryage/chromex/pom.xml"
INSPECT_DIR="inspect"

cd "target"
unzip -l "$BASE_FILE.jar"
unzip -o "$BASE_FILE.jar" "$POM_PATH" -d "$INSPECT_DIR"

echo
echo "approx. pom.xml dependencies:"
grep -E -i "artifactId|version" < "$INSPECT_DIR/$POM_PATH"

echo
echo "----------------------------"
echo
