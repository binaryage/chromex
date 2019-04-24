(ns chromex.ext.safe-browsing-private
  "Use the chrome.safeBrowsingPrivate API to observe events
   or retrieve referrer chain.

     * available since Chrome 68"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-referrer-chain
  "Gets referrer chain for the specified tab.

     |tab-id| - Id of the tab from which to retrieve the referrer.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([tab-id] (gen-call :function ::get-referrer-chain &form tab-id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-policy-specified-password-reuse-detected-events
  "Fired when Chrome detects a reuse of a policy specified password.

   Events will be put on the |channel| with signature [::on-policy-specified-password-reuse-detected [reuse-details]] where:

     |reuse-details| - Details about where the password reuse occurred.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-policy-specified-password-reuse-detected &form channel args)))

(defmacro tap-on-policy-specified-password-changed-events
  "Fired when the user changed their policy specified password.

   Events will be put on the |channel| with signature [::on-policy-specified-password-changed [user-name]] where:

     |user-name| - The user name of the policy specified password.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-policy-specified-password-changed &form channel args)))

(defmacro tap-on-dangerous-download-opened-events
  "Fired when the user opened a dangerous download.

   Events will be put on the |channel| with signature [::on-dangerous-download-opened [dict]] where:

     |dict| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-dangerous-download-opened &form channel args)))

(defmacro tap-on-security-interstitial-shown-events
  "Fired when a security interstitial is shown to the user.

   Events will be put on the |channel| with signature [::on-security-interstitial-shown [dict]] where:

     |dict| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-security-interstitial-shown &form channel args)))

(defmacro tap-on-security-interstitial-proceeded-events
  "Fired when the user clicked-through a security interstitial.

   Events will be put on the |channel| with signature [::on-security-interstitial-proceeded [dict]] where:

     |dict| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-security-interstitial-proceeded &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.safe-browsing-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.safeBrowsingPrivate",
   :since "68",
   :functions
   [{:id ::get-referrer-chain,
     :name "getReferrerChain",
     :since "74",
     :callback? true,
     :params
     [{:name "tab-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "entries", :type "[array-of-objects]"}]}}]}],
   :events
   [{:id ::on-policy-specified-password-reuse-detected,
     :name "onPolicySpecifiedPasswordReuseDetected",
     :params [{:name "reuse-details", :type "object"}]}
    {:id ::on-policy-specified-password-changed,
     :name "onPolicySpecifiedPasswordChanged",
     :params [{:name "user-name", :type "string"}]}
    {:id ::on-dangerous-download-opened, :name "onDangerousDownloadOpened", :params [{:name "dict", :type "object"}]}
    {:id ::on-security-interstitial-shown,
     :name "onSecurityInterstitialShown",
     :params [{:name "dict", :type "safeBrowsingPrivate.InterstitialInfo"}]}
    {:id ::on-security-interstitial-proceeded,
     :name "onSecurityInterstitialProceeded",
     :params [{:name "dict", :type "safeBrowsingPrivate.InterstitialInfo"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))