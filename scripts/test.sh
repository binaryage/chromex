#!/usr/bin/env bash

set -e

# shellcheck source=_config.sh
source "$(dirname "${BASH_SOURCE[0]}")/_config.sh"

export RUNNING_DEV_TEST=1

lein with-profile +test-none cljsbuild once tests

exec node --unhandled-rejections=strict test/resources/puppeteer.js test/resources runner_none.html
