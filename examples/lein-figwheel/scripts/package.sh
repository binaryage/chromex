#!/usr/bin/env bash

pushd `dirname "${BASH_SOURCE[0]}"` > /dev/null
source "./config.sh"

pushd "$ROOT"

RELEASES="$ROOT/releases"
RELEASE_BUILD="$ROOT/resources/release"
RELEASE_BUILD_COMPILED="$RELEASE_BUILD/compiled"

if [ ! -d "$RELEASE_BUILD" ] ; then
  echo "'$RELEASE_BUILD' does not exist, run 'lein release' first"
  exit 1
fi

if [ ! -d "$RELEASE_BUILD_COMPILED" ] ; then
  echo "'$RELEASE_BUILD_COMPILED' does not exist, run 'lein release' to fully build the project"
  exit 2
fi

if [ ! -d "$RELEASES" ] ; then
  mkdir -p "$RELEASES"
fi

VERSION_WITH_QUOTES=`cat project.clj | grep "defproject" | cut -d' ' -f3`
VERSION=`echo "${VERSION_WITH_QUOTES//\"}"`

PACKAGE_DIR="$RELEASES/chromex-sample-$VERSION"

if [ -d "$PACKAGE_DIR" ] ; then
  rm -rf "$PACKAGE_DIR"
fi

# this will copy actual files, not symlinks
# Tread carefully here, this is system-dependent
#  -r works on Mac but not Linux
#  -rL works on Linux (tested on Ubuntu 16.04) but not Mac
#  -RL works on both
cp -RL "$RELEASE_BUILD" "$PACKAGE_DIR"


# prune release directory from extra files/folders
rm -rf "$PACKAGE_DIR/compiled/background"
rm -rf "$PACKAGE_DIR/compiled/content_script"
rm -rf "$PACKAGE_DIR/compiled/popup"

echo "'$PACKAGE_DIR' prepared for packing"
echo "  use Chrome's Window -> Extensions -> 'Pack extension...' to package it"
echo "  or => https://developer.chrome.com/extensions/packaging#packaging"

popd
