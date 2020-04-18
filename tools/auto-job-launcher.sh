#!/usr/bin/env bash

set -e -o pipefail

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")/.."

set -x

ROOT="$(pwd)"
TOOLS="$ROOT/tools"

if [[ "$(uname -n)" == maxime* ]]; then
  export PATH=/Users/darwin/bin/depot_tools:/Users/darwin/tasks/py2-venv/bin:/Users/darwin/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin
  export PYTHONPATH=/Users/darwin/tasks/py2-venv
  export CHROMIUM_SRC=/Users/darwin/tasks/chromium/src
fi

# run the job
"$TOOLS/auto-job-wrapper.sh" 2>&1
