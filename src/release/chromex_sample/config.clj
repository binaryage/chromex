(ns chromex-sample.config
  (:require [chromex-lib.config :as config]))

; in release mode, we want to elide all logging code
(alter-var-root #'config/*elide-verbose-logging* (constantly true))