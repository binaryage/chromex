#!/usr/bin/env bash

launchctl start com.binaryage.chromex

# note: you may want to
#
#   sudo launchctl log level debug
#   sudo tail -f /var/log/system.log
# 
# => http://serverfault.com/questions/249199/manually-start-scheduled-launchd-job
