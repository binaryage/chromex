(ns chromex.echo-private (:require-macros [chromex.echo-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn set-offer-info* [config id offer-info]
  (gen-wrap :function ::set-offer-info config id offer-info))

(defn get-offer-info* [config id]
  (gen-wrap :function ::get-offer-info config id))

(defn get-registration-code* [config type]
  (gen-wrap :function ::get-registration-code config type))

(defn get-oobe-timestamp* [config]
  (gen-wrap :function ::get-oobe-timestamp config))

(defn get-user-consent* [config consent-requester]
  (gen-wrap :function ::get-user-consent config consent-requester))

