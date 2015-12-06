(ns chromex.ext.enterprise.platform-keys-internal (:require-macros [chromex.ext.enterprise.platform-keys-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-tokens* [config]
  (gen-wrap :function ::get-tokens config))

(defn generate-key* [config token-id modulus-length]
  (gen-wrap :function ::generate-key config token-id modulus-length))

