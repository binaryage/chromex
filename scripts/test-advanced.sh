#!/usr/bin/env bash

set -e

cd `dirname "${BASH_SOURCE[0]}"` && source "./config.sh"  && cd "$ROOT"

export RUNNING_ADVANCED_TEST=1
export CHROMEX_ELIDE_VERBOSE_LOGGING=1
export CHROMEX_ELIDE_MISSING_API_CHECKS=1

lein with-profile +test-advanced cljsbuild once tests

exec phantomjs test/phantom.js test/runner_advanced.html
