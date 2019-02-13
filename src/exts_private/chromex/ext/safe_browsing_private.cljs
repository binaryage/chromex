(ns chromex.ext.safe-browsing-private (:require-macros [chromex.ext.safe-browsing-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-referrer-chain* [config tab-id]
  (gen-wrap :function ::get-referrer-chain config tab-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-policy-specified-password-reuse-detected* [config channel & args]
  (gen-wrap :event ::on-policy-specified-password-reuse-detected config channel args))

(defn on-policy-specified-password-changed* [config channel & args]
  (gen-wrap :event ::on-policy-specified-password-changed config channel args))

(defn on-dangerous-download-opened* [config channel & args]
  (gen-wrap :event ::on-dangerous-download-opened config channel args))

(defn on-security-interstitial-shown* [config channel & args]
  (gen-wrap :event ::on-security-interstitial-shown config channel args))

(defn on-security-interstitial-proceeded* [config channel & args]
  (gen-wrap :event ::on-security-interstitial-proceeded config channel args))

