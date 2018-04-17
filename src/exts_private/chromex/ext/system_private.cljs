(ns chromex.ext.system-private (:require-macros [chromex.ext.system-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-incognito-mode-availability* [config]
  (gen-wrap :function ::get-incognito-mode-availability config))

(defn get-update-status* [config]
  (gen-wrap :function ::get-update-status config))

(defn get-api-key* [config]
  (gen-wrap :function ::get-api-key config))

