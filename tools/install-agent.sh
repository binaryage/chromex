#!/usr/bin/env bash

AGENTS_DIR=~/Library/LaunchAgents
PLIST_NAME=com.binaryage.chromex.plist
PLIST_PATH="$AGENTS_DIR/$PLIST_NAME"

sudo cp com.binaryage.chromex.plist "$AGENTS_DIR"
sudo launchctl unload "$PLIST_PATH"
sudo launchctl load -w "$PLIST_PATH"