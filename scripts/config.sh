#!/usr/bin/env bash

pushd () {
  command pushd "$@" > /dev/null
}

popd () {
  command popd "$@" > /dev/null
}

pushd `dirname "${BASH_SOURCE[0]}"`

cd ..

ROOT=`pwd`
PROJECT_FILE="project.clj"
PROJECT_VERSION_FILE="src/lib/chromex/version.clj"

popd
