(ns chromex.app.certificate-provider (:require-macros [chromex.app.certificate-provider :refer [gen-wrap]])
    (:require [chromex.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-certificates-requested* [config channel & args]
  (gen-wrap :event ::on-certificates-requested config channel args))

(defn on-sign-digest-requested* [config channel & args]
  (gen-wrap :event ::on-sign-digest-requested config channel args))

