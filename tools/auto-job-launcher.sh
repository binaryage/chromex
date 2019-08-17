#!/usr/bin/env bash

set -e -o pipefail

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")/.."

set -x

ROOT="$(pwd)"
TOOLS="$ROOT/tools"

# run the job
"$TOOLS/auto-job-wrapper.sh" 2>&1