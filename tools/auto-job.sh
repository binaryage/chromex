#!/usr/bin/env bash

echo "-----------------------------------------------------------------------------------------------------------------------"
echo "running auto-job.sh on $(date)"

finish () {
  echo "finished auto-job.sh on $(date)"
  echo
  echo
}
trap finish EXIT

CHROMIUM_SRC=${CHROMIUM_SRC:?please specify CHROMIUM_SRC} # should be set to ~/tasks/chromium/src/ on nightly machine
CHROMEX_DRY_RUN=${CHROMEX_DRY_RUN}

set -ex

die_if_dirty_working_copy () {
  if [[ -n "$(git status -uno --porcelain)" ]]; then
    echo "working copy is not clean in '$(pwd)'"
    exit 1
  fi
}

#############################################################################################################################

# ensure we start in root folder
cd "$(dirname "${BASH_SOURCE[0]}")/.."

ROOT="$(pwd)"
CHROMEX="$ROOT"
CHROMEX_README="$CHROMEX/readme.md"
TOOLS="$CHROMEX/tools"
WORKDIR="$TOOLS/_workdir"

#############################################################################################################################
# print some info

if [[ -n "$CHROMEX_DRY_RUN" ]]; then
  echo "running in DRY mode because CHROMEX_DRY_RUN"
fi

command -v python
python --version

command -v git
git --version

#############################################################################################################################
# pull latest chromium

cd "$CHROMIUM_SRC"

die_if_dirty_working_copy

git fetch origin

git checkout master
git reset --hard origin/master
git clean -fd
gclient sync --with_branch_heads --reset --delete_unversioned_trees
git fetch --tags

CHROMIUM_SHA=$(git rev-parse HEAD)
CHROMIUM_SHORT_SHA=$(git rev-parse --short HEAD)

#############################################################################################################################
# generate chromex APIs

cd "$CHROMEX"

die_if_dirty_working_copy

git fetch origin

git checkout -B nightly origin/nightly
git rebase origin/master

if [[ -z "$CHROMEX_DRY_RUN" ]]; then
  git push -f origin nightly
else
  echo "not pushing nightly rebase because CHROMEX_DRY_RUN is set"
fi

# hack - update-cache.sh does not work reliably (or I'm not using it properly)
# since we run this as a batch task on a server, we don't care about speed that much
# time ./tools/update-cache.sh
rm -rf "$WORKDIR"
time ./tools/build-cache.sh
time ./tools/build-api.sh

git add --all

set +e
if git diff-index --exit-code HEAD > /dev/null; then
    echo "no changes from previous version in '$(pwd)'"
    echo "nothing to commit => exit"
    exit 42
fi
set -e

# add information about last generation into readme
GENERATION_DATE=$(date "+%Y-%m-%d")
SOURCE_LINK="https://chromium.googlesource.com/chromium/src.git/+/$CHROMIUM_SHA"
SOURCE_INFO="[**Chromium @ $CHROMIUM_SHORT_SHA**]($SOURCE_LINK)"
DATE_SOURCE_INFO="Current version was **generated on $GENERATION_DATE** from $SOURCE_INFO."
README_WITH_MARKER=$(perl -pe 'BEGIN{undef $/;} s/Current version was.*?Looking for a nightly version/DATESOURCEMARKER\n\nLooking for a nightly version/smg' "$CHROMEX_README")
NEW_README="${README_WITH_MARKER/DATESOURCEMARKER/$DATE_SOURCE_INFO}"

echo "$NEW_README" > "$CHROMEX_README"

git add --all
git commit -m "regenerate APIs from Chromium @ $CHROMIUM_SHORT_SHA" \
           -m "$SOURCE_LINK" \
           --author="BinaryAge Bot <bot@binaryage.com>"

if [[ -z "$CHROMEX_DRY_RUN" ]]; then
  git push
else
  echo "not pushing because CHROMEX_DRY_RUN is set"
fi