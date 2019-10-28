#!/usr/bin/env bash

set -e

pushd () {
  command pushd "$@" > /dev/null
}

popd () {
  command popd > /dev/null
}

cd "$(dirname "${BASH_SOURCE[0]}")/.."

ROOT=$(pwd -P)
PROJECT_FILE="project.clj"
PROJECT_VERSION_FILE="src/lib/chromex/version.clj"
EXAMPLES_SAMPLE_PROJECT_FILE="examples/sample/project.clj"
