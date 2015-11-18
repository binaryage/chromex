(ns chromex.passwords-private
  "Use the chrome.passwordsPrivate API to add or remove password
   data from the settings UI.
   
     * available since Chrome 47
     * https://developer.chrome.com/extensions/passwordsPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro remove-saved-password
  "Removes the saved password corresponding to |loginPair|. If no saved password for this pair exists, this function
   is a no-op.
   
     |loginPair| - The LoginPair corresponding to the entry to remove."
  [login-pair]
  (gen-call :function ::remove-saved-password (meta &form) login-pair))

(defmacro remove-password-exception
  "Removes the saved password exception corresponding to |exceptionUrl|. If no exception with this URL exists, this
   function is a no-op.
   
     |exceptionUrl| - The URL corresponding to the exception to remove."
  [exception-url]
  (gen-call :function ::remove-password-exception (meta &form) exception-url))

(defmacro request-plaintext-password
  "Returns the plaintext password corresponding to |loginPair|. Note that on some operating systems, this call may
   result in an OS-level reauthentication. Once the password has been fetched, it will be returned via the
   onPlaintextPasswordRetrieved event.
   
     |loginPair| - The LoginPair corresponding to the entry whose password     is to be returned."
  [login-pair]
  (gen-call :function ::request-plaintext-password (meta &form) login-pair))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-saved-passwords-list-changed
  "Fired when the saved passwords list has changed, meaning that an entry has been added or removed. Note that this
   event fires as soon as a  listener is added."
  [channel]
  (gen-call :event ::on-saved-passwords-list-changed (meta &form) channel))

(defmacro tap-on-password-exceptions-list-changed
  "Fired when the password exceptions list has changed, meaning that an entry has been added or removed. Note that
   this event fires as soon as a listener is added."
  [channel]
  (gen-call :event ::on-password-exceptions-list-changed (meta &form) channel))

(defmacro tap-on-plaintext-password-retrieved
  "Fired when a plaintext password has been fetched in response to a call to
   chrome.passwordsPrivate.requestPlaintextPassword()."
  [channel]
  (gen-call :event ::on-plaintext-password-retrieved (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.passwordsPrivate",
   :since "47",
   :functions
   [{:id ::remove-saved-password,
     :name "removeSavedPassword",
     :params [{:name "login-pair", :type "passwordsPrivate.LoginPair"}]}
    {:id ::remove-password-exception,
     :name "removePasswordException",
     :params [{:name "exception-url", :type "string"}]}
    {:id ::request-plaintext-password,
     :name "requestPlaintextPassword",
     :params [{:name "login-pair", :type "passwordsPrivate.LoginPair"}]}],
   :events
   [{:id ::on-saved-passwords-list-changed,
     :name "onSavedPasswordsListChanged",
     :params [{:name "entries", :type "[array-of-objects]"}]}
    {:id ::on-password-exceptions-list-changed,
     :name "onPasswordExceptionsListChanged",
     :params [{:name "exceptions", :type "[array-of-strings]"}]}
    {:id ::on-plaintext-password-retrieved,
     :name "onPlaintextPasswordRetrieved",
     :params [{:name "dict", :type "object"}]}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))