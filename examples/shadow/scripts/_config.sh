#!/usr/bin/env bash

pushd() {
  command pushd "$@" >/dev/null
}

popd() {
  command popd >/dev/null
}

pushd .

cd "$(dirname "${BASH_SOURCE[0]}")/.." || exit 11

ROOT=$(pwd -P)
RELEASES_DIR="releases"
RELEASE_BUILD_DIR="resources/unpacked"
RELEASE_BUILD_COMPILED_DIR="$RELEASE_BUILD_DIR/out"

popd || exit 11
