(ns chromex.app.users-private
  "Use the chrome.usersPrivate API to manage users.
   
     * available since Chrome 48
     * https://developer.chrome.com/extensions/usersPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-whitelisted-users
  "Gets a list of the currently whitelisted users.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-whitelisted-users &form)))

(defmacro add-whitelisted-user
  "Adds a new user with the given email to the whitelist. The callback is called with true if the user was added succesfully,
   or with false if not (e.g. because the user was already present, or the current user isn't the owner).
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([email #_callback] (gen-call :function ::add-whitelisted-user &form email)))

(defmacro remove-whitelisted-user
  "Removes the user with the given email from the whitelist. The callback is called with true if the user was removed
   succesfully, or with false if not (e.g. because the user was not already present, or the current user isn't the owner).
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([email #_callback] (gen-call :function ::remove-whitelisted-user &form email)))

(defmacro is-current-user-owner
  "Whether the current user is the owner of the device.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::is-current-user-owner &form)))

(defmacro is-whitelist-managed
  "Whether the whitelist is managed by enterprise.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::is-whitelist-managed &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.usersPrivate",
   :since "48",
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
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))