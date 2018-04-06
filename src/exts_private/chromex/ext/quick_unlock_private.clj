(ns chromex.ext.quick-unlock-private
  "Use the chrome.quickUnlockPrivate API to change whether the
   lock screen is enabled and which modes are allowed (active) for unlocking a
   Chrome OS device from the lock screen. The API is also used to set quick
   unlock credentials.
   Note: The API is named 'quickUnlock' for historical reasons but it should be
   used for all lock screen settings.
   Note: This API can not be used to actually unlock the device.

     * available since Chrome 53"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-auth-token
  "Returns a token that can be used for future operations and the number of seconds until the token expires.

     |account-password| - The account password for the logged in user.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([account-password] (gen-call :function ::get-auth-token &form account-password)))

(defmacro set-lock-screen-enabled
  "Sets the lock screen enabled state. NOTE: The lock enabled state is reflected in the settings.enable_screen_lock pref,
   which can be read but not written using the settings_private API (which also provides policy information). This API must be
   used to change the pref.

     |token| - The token returned by 'getAuthToken'.
     |enabled| - Whether to enable the lock screen.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([token enabled] (gen-call :function ::set-lock-screen-enabled &form token enabled)))

(defmacro get-available-modes
  "Returns the set of quick unlock modes that are available for the user to use. Some quick unlock modes may be disabled by
   policy.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [modes] where:

     |modes| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-available-modes &form)))

(defmacro get-active-modes
  "Returns the quick unlock modes that are currently enabled and usable on the lock screen.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [modes] where:

     |modes| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-active-modes &form)))

(defmacro check-credential
  "Checks if the given credential can be used for the given unlock mode. Enterprise policy can change credential requirements.

     |mode| - The quick unlock mode that is used.
     |credential| - The given credential.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [check] where:

     |check| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([mode credential] (gen-call :function ::check-credential &form mode credential)))

(defmacro get-credential-requirements
  "Gets the credential requirements for the given unlock mode.

     |mode| - The quick unlock mode that is used.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [requirements] where:

     |requirements| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([mode] (gen-call :function ::get-credential-requirements &form mode)))

(defmacro set-modes
  "Update the set of quick unlock modes that are currently active/enabled.

     |token| - The token returned by 'getAuthToken'.
     |modes| - The quick unlock modes that should be active.
     |credentials| - The associated credential for each mode. To keep the     credential the same for the associated mode,
                     pass an empty string.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([token modes credentials] (gen-call :function ::set-modes &form token modes credentials)))

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
  "Taps all valid non-deprecated events in chromex.ext.quick-unlock-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.quickUnlockPrivate",
   :since "53",
   :functions
   [{:id ::get-auth-token,
     :name "getAuthToken",
     :since "67",
     :callback? true,
     :params
     [{:name "account-password", :type "string"}
      {:name "on-complete", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}
    {:id ::set-lock-screen-enabled,
     :name "setLockScreenEnabled",
     :since "67",
     :callback? true,
     :params
     [{:name "token", :type "string"}
      {:name "enabled", :type "boolean"}
      {:name "on-complete", :optional? true, :type :callback}]}
    {:id ::get-available-modes,
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
     [{:name "token", :type "string"}
      {:name "modes", :type "[array-of-quickUnlockPrivate.QuickUnlockModes]"}
      {:name "credentials", :type "[array-of-strings]"}
      {:name "on-complete", :type :callback}]}],
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