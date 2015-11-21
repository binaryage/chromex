(ns chromex.test.init-static-config
  (:require [chromex-lib.config :as config]
            [chromex.test.marshalling :as marshalling]))

(alter-var-root #'config/*gen-marshalling* (constantly marshalling/custom-gen-marshalling))
;(alter-var-root #'config/*elide-verbose-logging* (constantly true))