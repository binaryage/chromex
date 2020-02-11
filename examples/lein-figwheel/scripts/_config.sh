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
BROWSER_USER_PROFILE="$ROOT/.chrome-profile"
DEV_EXTENSION_PATH="$ROOT/resources/unpacked"

popd || exit 11
