(ns chromex.ext.autofill-private (:require-macros [chromex.ext.autofill-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn save-address* [config address]
  (gen-wrap :function ::save-address config address))

(defn get-country-list* [config]
  (gen-wrap :function ::get-country-list config))

(defn get-address-components* [config country-code]
  (gen-wrap :function ::get-address-components config country-code))

(defn get-address-list* [config]
  (gen-wrap :function ::get-address-list config))

(defn save-credit-card* [config card]
  (gen-wrap :function ::save-credit-card config card))

(defn remove-entry* [config guid]
  (gen-wrap :function ::remove-entry config guid))

(defn validate-phone-numbers* [config params]
  (gen-wrap :function ::validate-phone-numbers config params))

(defn get-credit-card-list* [config]
  (gen-wrap :function ::get-credit-card-list config))

(defn mask-credit-card* [config guid]
  (gen-wrap :function ::mask-credit-card config guid))

(defn migrate-credit-cards* [config]
  (gen-wrap :function ::migrate-credit-cards config))

(defn log-server-card-link-clicked* [config]
  (gen-wrap :function ::log-server-card-link-clicked config))

(defn set-credit-card-fido-auth-enabled-state* [config enabled]
  (gen-wrap :function ::set-credit-card-fido-auth-enabled-state config enabled))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-address-list-changed* [config channel & args]
  (gen-wrap :event ::on-address-list-changed config channel args))

(defn on-credit-card-list-changed* [config channel & args]
  (gen-wrap :event ::on-credit-card-list-changed config channel args))

