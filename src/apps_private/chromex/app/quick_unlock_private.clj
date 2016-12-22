(ns chromex.app.quick-unlock-private
  "Use the chrome.quickUnlockPrivate API to change the active quick
   unlock modes and to change their respective credentials.

   Quick unlock only supports unlocking an account that has already been signed
   in.

   The quick unlock authentication facilities are not available through this
   API; they are built directly into the lock screen.

     * available since Chrome 53"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-available-modes
  "Returns the set of quick unlock modes that are available for the user to use. Some quick unlock modes may be disabled by
   policy.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [modes] where:

     |modes| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-available-modes &form)))

(defmacro get-active-modes
  "Returns the quick unlock modes that are currently enabled and usable on the lock screen.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [modes] where:

     |modes| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-active-modes &form)))

(defmacro check-credential
  "Checks if the given credential can be used for the given unlock mode. Enterprise policy can change credential requirements.

     |mode| - The quick unlock mode that is used.
     |credential| - The given credential.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [check] where:

     |check| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([mode credential] (gen-call :function ::check-credential &form mode credential)))

(defmacro get-credential-requirements
  "Gets the credential requirements for the given unlock mode.

     |mode| - The quick unlock mode that is used.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [requirements] where:

     |requirements| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([mode] (gen-call :function ::get-credential-requirements &form mode)))

(defmacro set-modes
  "Update the set of quick unlock modes that are currently active/enabled.

     |account-password| - The password associated with the account (e.g. the GAIA password). This is required to change the
                          quick unlock credentials.
     |modes| - The quick unlock modes that should be active.
     |credentials| - The associated credential for each mode. To keep the credential the same for the associated mode, pass
                     an empty string.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [value] where:

     |value| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([account-password modes credentials] (gen-call :function ::set-modes &form account-password modes credentials)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-active-modes-changed-events
  "Called after the active set of quick unlock modes has changed.

   Events will be put on the |channel| with signature [::on-active-modes-changed [active-modes]] where:

     |active-modes| - The set of quick unlock modes which are now active.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-active-modes-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.quick-unlock-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.quickUnlockPrivate",
   :since "53",
   :functions
   [{:id ::get-available-modes,
     :name "getAvailableModes",
     :callback? true,
     :params
     [{:name "on-complete",
       :type :callback,
       :callback {:params [{:name "modes", :type "[array-of-quickUnlockPrivate.QuickUnlockModes]"}]}}]}
    {:id ::get-active-modes,
     :name "getActiveModes",
     :callback? true,
     :params
     [{:name "on-complete",
       :type :callback,
       :callback {:params [{:name "modes", :type "[array-of-quickUnlockPrivate.QuickUnlockModes]"}]}}]}
    {:id ::check-credential,
     :name "checkCredential",
     :since "57",
     :callback? true,
     :params
     [{:name "mode", :type "quickUnlockPrivate.QuickUnlockMode"}
      {:name "credential", :type "string"}
      {:name "on-complete", :type :callback, :callback {:params [{:name "check", :type "object"}]}}]}
    {:id ::get-credential-requirements,
     :name "getCredentialRequirements",
     :since "57",
     :callback? true,
     :params
     [{:name "mode", :type "quickUnlockPrivate.QuickUnlockMode"}
      {:name "on-complete", :type :callback, :callback {:params [{:name "requirements", :type "object"}]}}]}
    {:id ::set-modes,
     :name "setModes",
     :callback? true,
     :params
     [{:name "account-password", :type "string"}
      {:name "modes", :type "[array-of-quickUnlockPrivate.QuickUnlockModes]"}
      {:name "credentials", :type "[array-of-strings]"}
      {:name "on-complete", :type :callback, :callback {:params [{:name "value", :type "boolean"}]}}]}],
   :events
   [{:id ::on-active-modes-changed,
     :name "onActiveModesChanged",
     :params [{:name "active-modes", :type "[array-of-quickUnlockPrivate.QuickUnlockModes]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))