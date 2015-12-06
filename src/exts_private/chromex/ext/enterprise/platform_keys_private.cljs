(ns chromex.ext.enterprise.platform-keys-private (:require-macros [chromex.ext.enterprise.platform-keys-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn challenge-machine-key* [config challenge]
  (gen-wrap :function ::challenge-machine-key config challenge))

(defn challenge-user-key* [config challenge register-key]
  (gen-wrap :function ::challenge-user-key config challenge register-key))

