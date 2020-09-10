(ns chromex.ext.login
  "Use the chrome.login API to launch and exit user sessions.

     * available since Chrome 77
     * https://developer.chrome.com/extensions/login"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro launch-managed-guest-session
  "Launches a managed guest session if one is set up via the admin console. If there are several managed guest sessions set
   up, it will launch the first available one.

     |password| - If provided, the launched managed guest session will be lockable, and can only be unlocked by calling
                  unlockManagedGuestSession() with the same password.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/login#method-launchManagedGuestSession."
  ([password] (gen-call :function ::launch-managed-guest-session &form password))
  ([] `(launch-managed-guest-session :omit)))

(defmacro exit-current-session
  "Exits the current session.

     |data-for-next-login-attempt| - If set, stores data which can be read by fetchDataForNextLoginAttempt() from the login
                                     screen. If unset, any currently stored data will be cleared.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/login#method-exitCurrentSession."
  ([data-for-next-login-attempt] (gen-call :function ::exit-current-session &form data-for-next-login-attempt))
  ([] `(exit-current-session :omit)))

(defmacro fetch-data-for-next-login-attempt
  "Reads the dataForNextLoginAttempt set by exitCurrentSession(). Clears the previously stored data after reading so it can
   only be read once.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/login#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/login#method-fetchDataForNextLoginAttempt."
  ([] (gen-call :function ::fetch-data-for-next-login-attempt &form)))

(defmacro lock-managed-guest-session
  "Locks the current managed guest session. The session has to have been launched by launchManagedGuestSession() with a
   password.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/login#method-lockManagedGuestSession."
  ([] (gen-call :function ::lock-managed-guest-session &form)))

(defmacro unlock-managed-guest-session
  "Unlocks a managed guest session which was launched by launchManagedGuestSession() with a password. The session will unlock
   if the provided password matches the one provided earlier.

     |password| - The password which will be used to unlock the session.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/login#method-unlockManagedGuestSession."
  ([password] (gen-call :function ::unlock-managed-guest-session &form password)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.login namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.login",
   :since "77",
   :functions
   [{:id ::launch-managed-guest-session,
     :name "launchManagedGuestSession",
     :callback? true,
     :params
     [{:name "password", :optional? true, :since "81", :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::exit-current-session,
     :name "exitCurrentSession",
     :callback? true,
     :params
     [{:name "data-for-next-login-attempt", :optional? true, :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::fetch-data-for-next-login-attempt,
     :name "fetchDataForNextLoginAttempt",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "string"}]}}]}
    {:id ::lock-managed-guest-session,
     :name "lockManagedGuestSession",
     :since "81",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::unlock-managed-guest-session,
     :name "unlockManagedGuestSession",
     :since "81",
     :callback? true,
     :params [{:name "password", :type "string"} {:name "callback", :optional? true, :type :callback}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))