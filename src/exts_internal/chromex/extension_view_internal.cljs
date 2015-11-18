(ns chromex.extension-view-internal (:require-macros [chromex.extension-view-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn load-src* [config instance-id src]
  (gen-wrap :function ::load-src config instance-id src))

(defn parse-src* [config src]
  (gen-wrap :function ::parse-src config src))

