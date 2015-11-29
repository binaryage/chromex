(ns chromex.platform-keys (:require-macros [chromex.platform-keys :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn select-client-certificates* [config details]
  (gen-wrap :function ::select-client-certificates config details))

(defn get-key-pair* [config certificate parameters]
  (gen-wrap :function ::get-key-pair config certificate parameters))

(defn subtle-crypto* [config]
  (gen-wrap :function ::subtle-crypto config))

(defn verify-tls-server-certificate* [config details]
  (gen-wrap :function ::verify-tls-server-certificate config details))

