(ns chromex.certificate-provider (:require-macros [chromex.certificate-provider :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-certificates-requested* [config channel]
  (gen-wrap :event ::on-certificates-requested config channel))

(defn on-sign-digest-requested* [config channel]
  (gen-wrap :event ::on-sign-digest-requested config channel))

