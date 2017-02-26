(ns chromex.version
  (:require-macros [chromex.version :refer [get-current-version check-env!]]))

(check-env!)

(def current-version (get-current-version))

(defn get-current-version []
  current-version)
