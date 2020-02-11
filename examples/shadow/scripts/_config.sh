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
RELEASES="$ROOT/releases"
RELEASE_BUILD="$ROOT/resources/unpacked"
RELEASE_BUILD_COMPILED="$RELEASE_BUILD/out"

popd || exit 11
