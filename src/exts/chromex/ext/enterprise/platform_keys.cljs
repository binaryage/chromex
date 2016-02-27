(ns chromex.ext.enterprise.platform-keys (:require-macros [chromex.ext.enterprise.platform-keys :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-tokens* [config]
  (gen-wrap :function ::get-tokens config))

(defn get-certificates* [config token-id]
  (gen-wrap :function ::get-certificates config token-id))

(defn import-certificate* [config token-id certificate]
  (gen-wrap :function ::import-certificate config token-id certificate))

(defn remove-certificate* [config token-id certificate]
  (gen-wrap :function ::remove-certificate config token-id certificate))

(defn challenge-machine-key* [config challenge]
  (gen-wrap :function ::challenge-machine-key config challenge))

(defn challenge-user-key* [config challenge register-key]
  (gen-wrap :function ::challenge-user-key config challenge register-key))

