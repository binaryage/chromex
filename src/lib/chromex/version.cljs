(ns chromex.version
  (:require-macros [chromex.version :refer [get-current-version]]))

(def current-version (get-current-version))

(defn get-current-version []
  current-version)
