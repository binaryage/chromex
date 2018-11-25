(ns chromex.version
  (:require-macros [chromex.version :refer [check-env! get-current-version]]))

(check-env!)

(def current-version (get-current-version))

(defn get-current-version []
  current-version)
