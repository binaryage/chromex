#!/usr/bin/env bash

set -e

cd `dirname "${BASH_SOURCE[0]}"` && source "./config.sh"

cd "$ROOT"

export RUNNING_DEV_TEST=1

lein with-profile +test-none cljsbuild once tests

exec phantomjs test/phantom.js test/runner_none.html