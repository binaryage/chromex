(ns chromex.app.extension-view-internal (:require-macros [chromex.app.extension-view-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn load-src* [config instance-id src]
  (gen-wrap :function ::load-src config instance-id src))

(defn parse-src* [config src]
  (gen-wrap :function ::parse-src config src))

