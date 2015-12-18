#!/usr/bin/env bash

sudo cp com.binaryage.chromex.plist /Library/LaunchDaemons
sudo launchctl load -w /Library/LaunchDaemons/com.binaryage.chromex.plist