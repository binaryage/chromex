(ns chromex.ext.users-private
  "Use the chrome.usersPrivate API to manage users.

     * available since Chrome 58"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-whitelisted-users
  "Gets a list of the currently whitelisted users.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [users] where:

     |users| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-whitelisted-users &form)))

(defmacro add-whitelisted-user
  "Adds a new user with the given email to the whitelist. The callback is called with true if the user was added succesfully,
   or with false if not (e.g. because the user was already present, or the current user isn't the owner).

     |email| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([email] (gen-call :function ::add-whitelisted-user &form email)))

(defmacro remove-whitelisted-user
  "Removes the user with the given email from the whitelist. The callback is called with true if the user was removed
   succesfully, or with false if not (e.g. because the user was not already present, or the current user isn't the owner).

     |email| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([email] (gen-call :function ::remove-whitelisted-user &form email)))

(defmacro is-current-user-owner
  "Whether the current user is the owner of the device.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [is-owner] where:

     |is-owner| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-current-user-owner &form)))

(defmacro is-whitelist-managed
  "Whether the whitelist is managed by enterprise.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [managed] where:

     |managed| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-whitelist-managed &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.users-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.usersPrivate",
   :since "58",
   :functions
   [{:id ::get-whitelisted-users,
     :name "getWhitelistedUsers",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "users", :type "[array-of-objects]"}]}}]}
    {:id ::add-whitelisted-user,
     :name "addWhitelistedUser",
     :callback? true,
     :params
     [{:name "email", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::remove-whitelisted-user,
     :name "removeWhitelistedUser",
     :callback? true,
     :params
     [{:name "email", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::is-current-user-owner,
     :name "isCurrentUserOwner",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "is-owner", :type "boolean"}]}}]}
    {:id ::is-whitelist-managed,
     :name "isWhitelistManaged",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "managed", :type "boolean"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))