#!/usr/bin/env bash

pushd () {
    command pushd "$@" > /dev/null
}

popd () {
    command popd "$@" > /dev/null
}

pushd .

cd "$(dirname "${BASH_SOURCE[0]}")"; cd ..

ROOT=`pwd`
PROJECT_FILE="project.clj"

popd