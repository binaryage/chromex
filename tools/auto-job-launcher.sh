#!/usr/bin/env bash

# ensure we start in tools folder
cd "$(dirname "${BASH_SOURCE[0]}")";

TOOLS=`pwd`

"$TOOLS/auto-job.sh" 2>&1