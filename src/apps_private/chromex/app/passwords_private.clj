(ns chromex.app.passwords-private
  "Use the chrome.passwordsPrivate API to add or remove password
   data from the settings UI.

     * available since Chrome 65"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro remove-saved-password
  "Removes the saved password corresponding to |loginPair|. If no saved password for this pair exists, this function is a
   no-op.

     |index| - The index for the password entry being removed."
  ([index] (gen-call :function ::remove-saved-password &form index)))

(defmacro remove-password-exception
  "Removes the saved password exception corresponding to |exceptionUrl|. If no exception with this URL exists, this function
   is a no-op.

     |index| - The index for the exception url entry being removed."
  ([index] (gen-call :function ::remove-password-exception &form index)))

(defmacro undo-remove-saved-password-or-exception
  "Undoes the last removal of a saved password or exception."
  ([] (gen-call :function ::undo-remove-saved-password-or-exception &form)))

(defmacro request-plaintext-password
  "Returns the plaintext password corresponding to |index|. Note that on some operating systems, this call may result in an
   OS-level reauthentication. Once the password has been fetched, it will be returned via the onPlaintextPasswordRetrieved
   event. TODO(hcarmona): Investigate using a callback for consistency.

     |index| - The index for the password entry being being retrieved."
  ([index] (gen-call :function ::request-plaintext-password &form index)))

(defmacro get-saved-password-list
  "Returns the list of saved passwords.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-saved-password-list &form)))

(defmacro get-password-exception-list
  "Returns the list of password exceptions.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [exceptions] where:

     |exceptions| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-password-exception-list &form)))

(defmacro import-passwords
  "Triggers the Password Manager password import functionality."
  ([] (gen-call :function ::import-passwords &form)))

(defmacro export-passwords
  "Triggers the Password Manager password export functionality. Completion Will be signaled by the
   onPasswordsFileExportProgress event.|callback| will be called when the request is started or rejected. If rejected
   chrome.runtime.lastError will be set to 'in-progress' or 'reauth-failed'.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::export-passwords &form)))

(defmacro request-export-progress-status
  "Requests the export progress status. This is the same as the last value seen on the onPasswordsFileExportProgress event.
   This function is useful for checking if an export has already been initiated from an older tab, where we might have missed
   the original event.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::request-export-progress-status &form)))

(defmacro cancel-export-passwords
  "Stops exporting passwords and cleans up any passwords, which were already written to the filesystem."
  ([] (gen-call :function ::cancel-export-passwords &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-saved-passwords-list-changed-events
  "Fired when the saved passwords list has changed, meaning that an entry has been added or removed.

   Events will be put on the |channel| with signature [::on-saved-passwords-list-changed [entries]] where:

     |entries| - The updated list of password entries.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-saved-passwords-list-changed &form channel args)))

(defmacro tap-on-password-exceptions-list-changed-events
  "Fired when the password exceptions list has changed, meaning that an entry has been added or removed.

   Events will be put on the |channel| with signature [::on-password-exceptions-list-changed [exceptions]] where:

     |exceptions| - The updated list of password exceptions.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-password-exceptions-list-changed &form channel args)))

(defmacro tap-on-plaintext-password-retrieved-events
  "Fired when a plaintext password has been fetched in response to a call to
   chrome.passwordsPrivate.requestPlaintextPassword().

   Events will be put on the |channel| with signature [::on-plaintext-password-retrieved [dict]] where:

     |dict| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-plaintext-password-retrieved &form channel args)))

(defmacro tap-on-passwords-file-export-progress-events
  "Fired when the status of the export has changed.

   Events will be put on the |channel| with signature [::on-passwords-file-export-progress [status]] where:

     |status| - The progress status and an optional UI message.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-passwords-file-export-progress &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.passwords-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.passwordsPrivate",
   :since "65",
   :functions
   [{:id ::remove-saved-password, :name "removeSavedPassword", :params [{:name "index", :type "integer"}]}
    {:id ::remove-password-exception, :name "removePasswordException", :params [{:name "index", :type "integer"}]}
    {:id ::undo-remove-saved-password-or-exception, :name "undoRemoveSavedPasswordOrException"}
    {:id ::request-plaintext-password, :name "requestPlaintextPassword", :params [{:name "index", :type "integer"}]}
    {:id ::get-saved-password-list,
     :name "getSavedPasswordList",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "entries", :type "[array-of-passwordsPrivate.PasswordUiEntrys]"}]}}]}
    {:id ::get-password-exception-list,
     :name "getPasswordExceptionList",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "exceptions", :type "[array-of-passwordsPrivate.ExceptionEntrys]"}]}}]}
    {:id ::import-passwords, :name "importPasswords"}
    {:id ::export-passwords, :name "exportPasswords", :callback? true, :params [{:name "callback", :type :callback}]}
    {:id ::request-export-progress-status,
     :name "requestExportProgressStatus",
     :since "66",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "passwordsPrivate.ExportProgressStatus"}]}}]}
    {:id ::cancel-export-passwords, :name "cancelExportPasswords", :since "66"}],
   :events
   [{:id ::on-saved-passwords-list-changed,
     :name "onSavedPasswordsListChanged",
     :params [{:name "entries", :type "[array-of-passwordsPrivate.PasswordUiEntrys]"}]}
    {:id ::on-password-exceptions-list-changed,
     :name "onPasswordExceptionsListChanged",
     :params [{:name "exceptions", :type "[array-of-passwordsPrivate.ExceptionEntrys]"}]}
    {:id ::on-plaintext-password-retrieved,
     :name "onPlaintextPasswordRetrieved",
     :params [{:name "dict", :type "object"}]}
    {:id ::on-passwords-file-export-progress,
     :name "onPasswordsFileExportProgress",
     :since "66",
     :params [{:name "status", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))