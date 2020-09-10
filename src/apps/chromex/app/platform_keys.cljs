(ns chromex.app.platform-keys (:require-macros [chromex.app.platform-keys :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn select-client-certificates* [config details]
  (gen-wrap :function ::select-client-certificates config details))

(defn get-key-pair* [config certificate parameters]
  (gen-wrap :function ::get-key-pair config certificate parameters))

(defn get-key-pair-by-spki* [config public-key-spki-der parameters]
  (gen-wrap :function ::get-key-pair-by-spki config public-key-spki-der parameters))

(defn subtle-crypto* [config]
  (gen-wrap :function ::subtle-crypto config))

(defn verify-tls-server-certificate* [config details]
  (gen-wrap :function ::verify-tls-server-certificate config details))

