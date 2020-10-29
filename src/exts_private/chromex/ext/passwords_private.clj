(ns chromex.ext.passwords-private
  "Use the chrome.passwordsPrivate API to add or remove password
   data from the settings UI.

     * available since Chrome master"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro record-passwords-page-access-in-settings
  "Function that logs that the Passwords page was accessed from the Chrome Settings WebUI."
  ([] (gen-call :function ::record-passwords-page-access-in-settings &form)))

(defmacro change-saved-password
  "Changes the saved password corresponding to |ids|. Since the password can be stored in Google Account and on device, in
   this case we want to change the password for accountId and deviceId. Invokes |callback| or raises an error depending on
   whether the operation succeeded.

     |ids| - The ids for the password entry being updated.
     |new-username| - The new username.
     |new-password| - The new password.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([ids new-username new-password] (gen-call :function ::change-saved-password &form ids new-username new-password)))

(defmacro remove-saved-password
  "Removes the saved password corresponding to |id|. If no saved password for this pair exists, this function is a no-op.

     |id| - The id for the password entry being removed."
  ([id] (gen-call :function ::remove-saved-password &form id)))

(defmacro remove-saved-passwords
  "Removes the saved password corresponding to |ids|. If no saved password exists for a certain id, that id is ignored.
   Undoing this operation via undoRemoveSavedPasswordOrException will restore all the removed passwords in the batch.

     |ids| - ?"
  ([ids] (gen-call :function ::remove-saved-passwords &form ids)))

(defmacro remove-password-exception
  "Removes the saved password exception corresponding to |id|. If no exception with this id exists, this function is a no-op.

     |id| - The id for the exception url entry being removed."
  ([id] (gen-call :function ::remove-password-exception &form id)))

(defmacro remove-password-exceptions
  "Removes the saved password exceptions corresponding to |ids|. If no exception exists for a certain id, that id is ignored.
   Undoing this operation via undoRemoveSavedPasswordOrException will restore all the removed exceptions in the batch.

     |ids| - ?"
  ([ids] (gen-call :function ::remove-password-exceptions &form ids)))

(defmacro undo-remove-saved-password-or-exception
  "Undoes the last removal of saved password(s) or exception(s)."
  ([] (gen-call :function ::undo-remove-saved-password-or-exception &form)))

(defmacro request-plaintext-password
  "Returns the plaintext password corresponding to |id|. Note that on some operating systems, this call may result in an
   OS-level reauthentication. Once the password has been fetched, it will be returned via |callback|.

     |id| - The id for the password entry being being retrieved.
     |reason| - The reason why the plaintext password is requested.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [password] where:

     |password| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id reason] (gen-call :function ::request-plaintext-password &form id reason)))

(defmacro get-saved-password-list
  "Returns the list of saved passwords.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-saved-password-list &form)))

(defmacro get-password-exception-list
  "Returns the list of password exceptions.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [exceptions] where:

     |exceptions| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-password-exception-list &form)))

(defmacro move-passwords-to-account
  "Moves passwords currently stored on the device to being stored in the signed-in, non-syncing Google Account. For each id,
   the result is a no-op if any of these is true: |id| is invalid; |id| corresponds to a password already stored in the
   account; or the user is not using the account-scoped password storage.

     |ids| - The ids for the password entries being moved."
  ([ids] (gen-call :function ::move-passwords-to-account &form ids)))

(defmacro import-passwords
  "Triggers the Password Manager password import functionality."
  ([] (gen-call :function ::import-passwords &form)))

(defmacro export-passwords
  "Triggers the Password Manager password export functionality. Completion Will be signaled by the
   onPasswordsFileExportProgress event. |callback| will be called when the request is started or rejected. If rejected
   'runtime.lastError' will be set to 'in-progress' or 'reauth-failed'.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::export-passwords &form)))

(defmacro request-export-progress-status
  "Requests the export progress status. This is the same as the last value seen on the onPasswordsFileExportProgress event.
   This function is useful for checking if an export has already been initiated from an older tab, where we might have missed
   the original event.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::request-export-progress-status &form)))

(defmacro cancel-export-passwords
  "Stops exporting passwords and cleans up any passwords, which were already written to the filesystem."
  ([] (gen-call :function ::cancel-export-passwords &form)))

(defmacro is-opted-in-for-account-storage
  "Requests the account-storage opt-in state of the current user.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [opted-in] where:

     |opted-in| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-opted-in-for-account-storage &form)))

(defmacro opt-in-for-account-storage
  "Triggers the opt-in or opt-out flow for the account storage.

     |opt-in| - ?"
  ([opt-in] (gen-call :function ::opt-in-for-account-storage &form opt-in)))

(defmacro get-compromised-credentials
  "Requests the latest compromised credentials.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [insecure-credentials] where:

     |insecure-credentials| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-compromised-credentials &form)))

(defmacro get-weak-credentials
  "Requests the latest weak credentials.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [insecure-credentials] where:

     |insecure-credentials| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-weak-credentials &form)))

(defmacro get-plaintext-insecure-password
  "Requests the plaintext password for |credential|. |callback| gets invoked with the same |credential|, whose |password

   field will be set.

     |credential| - The insecure credential whose password is being retrieved.
     |reason| - The reason why the plaintext password is requested.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [credential] where:

     |credential| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([credential reason] (gen-call :function ::get-plaintext-insecure-password &form credential reason)))

(defmacro change-insecure-credential
  "Requests to change the password of |credential| to |new_password|. Invokes |callback| or raises an error depending on
   whether the operation succeeded.

     |credential| - The credential whose password should be changed.
     |new-password| - The new password.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([credential new-password] (gen-call :function ::change-insecure-credential &form credential new-password)))

(defmacro remove-insecure-credential
  "Requests to remove |credential| from the password store. Invokes |callback| on completion.

     |credential| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([credential] (gen-call :function ::remove-insecure-credential &form credential)))

(defmacro start-password-check
  "Starts a check for insecure passwords. Invokes |callback| on completion.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::start-password-check &form)))

(defmacro stop-password-check
  "Stops checking for insecure passwords. Invokes |callback| on completion.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::stop-password-check &form)))

(defmacro get-password-check-status
  "Returns the current status of the check via |callback|.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-password-check-status &form)))

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

(defmacro tap-on-passwords-file-export-progress-events
  "Fired when the status of the export has changed.

   Events will be put on the |channel| with signature [::on-passwords-file-export-progress [status]] where:

     |status| - The progress status and an optional UI message.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-passwords-file-export-progress &form channel args)))

(defmacro tap-on-account-storage-opt-in-state-changed-events
  "Fired when the opt-in state for the account-scoped storage has changed.

   Events will be put on the |channel| with signature [::on-account-storage-opt-in-state-changed [opted-in]] where:

     |opted-in| - The new opt-in state.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-account-storage-opt-in-state-changed &form channel args)))

(defmacro tap-on-compromised-credentials-changed-events
  "Fired when the compromised credentials changed.

   Events will be put on the |channel| with signature [::on-compromised-credentials-changed [compromised-credentials]] where:

     |compromised-credentials| - The updated compromised credentials.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-compromised-credentials-changed &form channel args)))

(defmacro tap-on-weak-credentials-changed-events
  "Fired when the weak credentials changed.

   Events will be put on the |channel| with signature [::on-weak-credentials-changed [weak-credentials]] where:

     |weak-credentials| - The updated weak credentials.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-weak-credentials-changed &form channel args)))

(defmacro tap-on-password-check-status-changed-events
  "Fired when the status of the password check changes.

   Events will be put on the |channel| with signature [::on-password-check-status-changed [status]] where:

     |status| - The updated status of the password check.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-password-check-status-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.passwords-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.passwordsPrivate",
   :since "master",
   :functions
   [{:id ::record-passwords-page-access-in-settings, :name "recordPasswordsPageAccessInSettings"}
    {:id ::change-saved-password,
     :name "changeSavedPassword",
     :callback? true,
     :params
     [{:name "ids", :type "[array-of-integers]"}
      {:name "new-username", :type "string"}
      {:name "new-password", :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-saved-password, :name "removeSavedPassword", :params [{:name "id", :type "integer"}]}
    {:id ::remove-saved-passwords, :name "removeSavedPasswords", :params [{:name "ids", :type "[array-of-integers]"}]}
    {:id ::remove-password-exception, :name "removePasswordException", :params [{:name "id", :type "integer"}]}
    {:id ::remove-password-exceptions,
     :name "removePasswordExceptions",
     :params [{:name "ids", :type "[array-of-integers]"}]}
    {:id ::undo-remove-saved-password-or-exception, :name "undoRemoveSavedPasswordOrException"}
    {:id ::request-plaintext-password,
     :name "requestPlaintextPassword",
     :callback? true,
     :params
     [{:name "id", :type "integer"}
      {:name "reason", :type "passwordsPrivate.PlaintextReason"}
      {:name "callback", :type :callback, :callback {:params [{:name "password", :type "string"}]}}]}
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
    {:id ::move-passwords-to-account,
     :name "movePasswordsToAccount",
     :params [{:name "ids", :type "[array-of-integers]"}]}
    {:id ::import-passwords, :name "importPasswords"}
    {:id ::export-passwords, :name "exportPasswords", :callback? true, :params [{:name "callback", :type :callback}]}
    {:id ::request-export-progress-status,
     :name "requestExportProgressStatus",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "passwordsPrivate.ExportProgressStatus"}]}}]}
    {:id ::cancel-export-passwords, :name "cancelExportPasswords"}
    {:id ::is-opted-in-for-account-storage,
     :name "isOptedInForAccountStorage",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "opted-in", :type "boolean"}]}}]}
    {:id ::opt-in-for-account-storage, :name "optInForAccountStorage", :params [{:name "opt-in", :type "boolean"}]}
    {:id ::get-compromised-credentials,
     :name "getCompromisedCredentials",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "insecure-credentials", :type "[array-of-passwordsPrivate.InsecureCredentials]"}]}}]}
    {:id ::get-weak-credentials,
     :name "getWeakCredentials",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "insecure-credentials", :type "[array-of-passwordsPrivate.InsecureCredentials]"}]}}]}
    {:id ::get-plaintext-insecure-password,
     :name "getPlaintextInsecurePassword",
     :callback? true,
     :params
     [{:name "credential", :type "passwordsPrivate.InsecureCredential"}
      {:name "reason", :type "passwordsPrivate.PlaintextReason"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "credential", :type "passwordsPrivate.InsecureCredential"}]}}]}
    {:id ::change-insecure-credential,
     :name "changeInsecureCredential",
     :callback? true,
     :params
     [{:name "credential", :type "passwordsPrivate.InsecureCredential"}
      {:name "new-password", :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-insecure-credential,
     :name "removeInsecureCredential",
     :callback? true,
     :params
     [{:name "credential", :type "passwordsPrivate.InsecureCredential"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::start-password-check,
     :name "startPasswordCheck",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::stop-password-check,
     :name "stopPasswordCheck",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::get-password-check-status,
     :name "getPasswordCheckStatus",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "status", :type "passwordsPrivate.PasswordCheckStatus"}]}}]}],
   :events
   [{:id ::on-saved-passwords-list-changed,
     :name "onSavedPasswordsListChanged",
     :params [{:name "entries", :type "[array-of-passwordsPrivate.PasswordUiEntrys]"}]}
    {:id ::on-password-exceptions-list-changed,
     :name "onPasswordExceptionsListChanged",
     :params [{:name "exceptions", :type "[array-of-passwordsPrivate.ExceptionEntrys]"}]}
    {:id ::on-passwords-file-export-progress,
     :name "onPasswordsFileExportProgress",
     :params [{:name "status", :type "object"}]}
    {:id ::on-account-storage-opt-in-state-changed,
     :name "onAccountStorageOptInStateChanged",
     :params [{:name "opted-in", :type "boolean"}]}
    {:id ::on-compromised-credentials-changed,
     :name "onCompromisedCredentialsChanged",
     :params [{:name "compromised-credentials", :type "[array-of-passwordsPrivate.InsecureCredentials]"}]}
    {:id ::on-weak-credentials-changed,
     :name "onWeakCredentialsChanged",
     :params [{:name "weak-credentials", :type "[array-of-passwordsPrivate.InsecureCredentials]"}]}
    {:id ::on-password-check-status-changed,
     :name "onPasswordCheckStatusChanged",
     :params [{:name "status", :type "passwordsPrivate.PasswordCheckStatus"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))