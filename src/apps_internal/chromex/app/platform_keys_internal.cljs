(ns chromex.app.platform-keys-internal (:require-macros [chromex.app.platform-keys-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn select-client-certificates* [config details]
  (gen-wrap :function ::select-client-certificates config details))

(defn sign* [config token-id public-key hash-algorithm-name data]
  (gen-wrap :function ::sign config token-id public-key hash-algorithm-name data))

(defn get-public-key* [config certificate algorithm-name]
  (gen-wrap :function ::get-public-key config certificate algorithm-name))

