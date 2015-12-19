#!/usr/bin/env bash

echo "-----------------------------------------------------------------------------------------------------------------------"
echo "running auto-job.sh on $(date)"

export CHROMIUM_SRC=~/tasks/chromium/src/

set -e

pushd () {
    command pushd "$@" > /dev/null
}

popd () {
    command popd "$@" > /dev/null
}

die_if_dirty_working_copy () {
  git add --all
  set +e
  git diff-index --exit-code HEAD > /dev/null
  if [ $? -ne 0 ] ; then
    echo "working copy is not clean in '$(pwd)'"
    exit 1
  fi
  set -e
}

#############################################################################################################################

pushd .

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")"; cd ../..

ROOT=$(pwd)
CHROMEX="$ROOT/chromex"
CHROMEX_README="$CHROMEX/readme.md"

#############################################################################################################################
# pull latest chromium

pushd "$CHROMIUM_SRC"

die_if_dirty_working_copy

git fetch origin

git checkout master
git reset --hard origin/master
gclient sync --with_branch_heads

CHROMIUM_SHA=$(git rev-parse HEAD)
CHROMIUM_SHORT_SHA=$(git rev-parse --short HEAD)

popd

#############################################################################################################################
# generate chromex APIs

pushd "$CHROMEX"

die_if_dirty_working_copy

git fetch origin

git checkout master
git reset --hard origin/master

git checkout auto
git reset --hard origin/auto
git merge -m "merge changes from master" master

./tools/update-cache.sh
./tools/build-api.sh

git add --all

set +e
git diff-index --exit-code HEAD > /dev/null
if [ $? -eq 0 ] ; then
    echo "no changes from previous version in '$(pwd)'"
    echo "nothing to commit => exit"
    popd
    popd
    exit 42
fi
set -e

# add information about last generation into readme
GENERATION_DATE=$(date "+%Y-%m-%d")
SOURCE_LINK="https://chromium.googlesource.com/chromium/src.git/+/$CHROMIUM_SHA"
SOURCE_INFO="[**Chromium @ $CHROMIUM_SHORT_SHA**]($SOURCE_LINK)"
DATE_SOURCE_INFO="Current version was **generated on $GENERATION_DATE** from $SOURCE_INFO."
README_WITH_MARKER=`perl -pe 'BEGIN{undef $/;} s/Current version was.*?Looking for a nightly version/DATESOURCEMARKER\n\nLooking for a nightly version/smg' "$CHROMEX_README"`
NEW_README="${README_WITH_MARKER/DATESOURCEMARKER/$DATE_SOURCE_INFO}"

echo "$NEW_README" > "$CHROMEX_README"

git add --all
git commit -m "regenerate APIs from Chromium @ $CHROMIUM_SHORT_SHA" -m "$SOURCE_LINK"

git push

popd

popd