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

popd || exit 11
