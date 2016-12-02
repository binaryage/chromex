(ns chromex.ext.certificate-provider (:require-macros [chromex.ext.certificate-provider :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn request-pin* [config details]
  (gen-wrap :function ::request-pin config details))

(defn stop-pin-request* [config details]
  (gen-wrap :function ::stop-pin-request config details))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-certificates-requested* [config channel & args]
  (gen-wrap :event ::on-certificates-requested config channel args))

(defn on-sign-digest-requested* [config channel & args]
  (gen-wrap :event ::on-sign-digest-requested config channel args))

