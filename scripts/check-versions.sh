#!/usr/bin/env bash

set -e

pushd `dirname "${BASH_SOURCE[0]}"` > /dev/null
source "./config.sh"

pushd "$ROOT"

LEIN_VERSION=`cat "$PROJECT_FILE" | grep "defproject" | cut -d' ' -f3 | cut -d\" -f2`

# same version must be in src/version.clj

PROJECT_VERSION=`cat "$PROJECT_VERSION_FILE" | grep "(def current-version" | cut -d" " -f3 | cut -d\" -f2`
if [ -z "$PROJECT_VERSION" ] ; then
  echo "Unable to retrieve 'current-version' string from '$PROJECT_VERSION_FILE'"
  popd
  exit 1
fi

if [ ! "$LEIN_VERSION" = "$PROJECT_VERSION" ] ; then
  echo "Lein's project.clj version differs from version in '$PROJECT_VERSION_FILE': '$LEIN_VERSION' != '$PROJECT_VERSION'"
  popd
  exit 2
fi

echo "All version strings are consistent: '$LEIN_VERSION'"

popd
