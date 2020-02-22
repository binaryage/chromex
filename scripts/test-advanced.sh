#!/usr/bin/env bash

set -e

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

export RUNNING_ADVANCED_TEST=1
export CHROMEX_ELIDE_VERBOSE_LOGGING=1
export CHROMEX_ELIDE_MISSING_API_CHECKS=1

lein with-profile +test-advanced cljsbuild once tests

exec node --unhandled-rejections=strict test/resources/puppeteer.js test/resources runner_advanced.html
