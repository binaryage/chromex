(ns chromex.ext.autofill-private (:require-macros [chromex.ext.autofill-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn save-address* [config address]
  (gen-wrap :function ::save-address config address))

(defn get-address-components* [config country-code]
  (gen-wrap :function ::get-address-components config country-code))

(defn save-credit-card* [config card]
  (gen-wrap :function ::save-credit-card config card))

(defn remove-entry* [config guid]
  (gen-wrap :function ::remove-entry config guid))

(defn validate-phone-numbers* [config params]
  (gen-wrap :function ::validate-phone-numbers config params))

(defn mask-credit-card* [config guid]
  (gen-wrap :function ::mask-credit-card config guid))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-address-list-changed* [config channel & args]
  (gen-wrap :event ::on-address-list-changed config channel args))
(defn on-credit-card-list-changed* [config channel & args]
  (gen-wrap :event ::on-credit-card-list-changed config channel args))

