(ns chromex.ext.users-private
  "Use the chrome.usersPrivate API to manage users.

     * available since Chrome master"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-users
  "Gets a list of known users.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [users] where:

     |users| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-users &form)))

(defmacro is-user-in-list
  "Checks to see if the user is already present in the user list.

     |email| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [found] where:

     |found| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([email] (gen-call :function ::is-user-in-list &form email)))

(defmacro add-user
  "Adds a new user with the given email to the user list. The callback is called with true if the user was added succesfully,
   or with false if not (e.g. because the user was already present, or the current user isn't the owner).

     |email| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([email] (gen-call :function ::add-user &form email)))

(defmacro remove-user
  "Removes the user with the given email from the user list. The callback is called with true if the user was removed
   succesfully, or with false if not (e.g. because the user was not already present, or the current user isn't the owner).

     |email| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [success] where:

     |success| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([email] (gen-call :function ::remove-user &form email)))

(defmacro is-user-list-managed
  "Whether the user list is managed by enterprise.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [managed] where:

     |managed| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-user-list-managed &form)))

(defmacro get-current-user
  "Returns the current user.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [user] where:

     |user| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-current-user &form)))

(defmacro get-login-status
  "Get login status.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [status] where:

     |status| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-login-status &form)))

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
   :since "master",
   :functions
   [{:id ::get-users,
     :name "getUsers",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "users", :type "[array-of-usersPrivate.Users]"}]}}]}
    {:id ::is-user-in-list,
     :name "isUserInList",
     :callback? true,
     :params
     [{:name "email", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "found", :type "boolean"}]}}]}
    {:id ::add-user,
     :name "addUser",
     :callback? true,
     :params
     [{:name "email", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::remove-user,
     :name "removeUser",
     :callback? true,
     :params
     [{:name "email", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "success", :type "boolean"}]}}]}
    {:id ::is-user-list-managed,
     :name "isUserListManaged",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "managed", :type "boolean"}]}}]}
    {:id ::get-current-user,
     :name "getCurrentUser",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "user", :type "usersPrivate.User"}]}}]}
    {:id ::get-login-status,
     :name "getLoginStatus",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "status", :type "object"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))