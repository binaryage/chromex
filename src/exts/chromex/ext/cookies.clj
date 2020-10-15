(ns chromex.ext.cookies
  "Use the chrome.cookies API to query and modify cookies, and to be notified when they change.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/cookies"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get
  "Retrieves information about a single cookie. If more than one cookie of the same name exists for the given URL, the one
   with the longest path will be returned. For cookies with the same path length, the cookie with the earliest creation time
   will be returned.

     |details| - Details to identify the cookie being retrieved.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [cookie] where:

     |cookie| - Contains details about the cookie. This parameter is null if no such cookie was found.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/cookies#method-get."
  ([details] (gen-call :function ::get &form details)))

(defmacro get-all
  "Retrieves all cookies from a single cookie store that match the given information.  The cookies returned will be sorted,
   with those with the longest path first.  If multiple cookies have the same path length, those with the earliest creation
   time will be first.

     |details| - Information to filter the cookies being retrieved.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [cookies] where:

     |cookies| - All the existing, unexpired cookies that match the given cookie info.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/cookies#method-getAll."
  ([details] (gen-call :function ::get-all &form details)))

(defmacro set
  "Sets a cookie with the given cookie data; may overwrite equivalent cookies if they exist.

     |details| - Details about the cookie being set.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [cookie] where:

     |cookie| - Contains details about the cookie that's been set.  If setting failed for any reason, this will be 'null', and
                'runtime.lastError' will be set.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/cookies#method-set."
  ([details] (gen-call :function ::set &form details)))

(defmacro remove
  "Deletes a cookie by name.

     |details| - Information to identify the cookie to remove.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [details] where:

     |details| - Contains details about the cookie that's been removed.  If removal failed for any reason, this will be
                 'null', and 'runtime.lastError' will be set.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/cookies#method-remove."
  ([details] (gen-call :function ::remove &form details)))

(defmacro get-all-cookie-stores
  "Lists all existing cookie stores.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [cookie-stores] where:

     |cookie-stores| - All the existing cookie stores.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/cookies#method-getAllCookieStores."
  ([] (gen-call :function ::get-all-cookie-stores &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-changed-events
  "Fired when a cookie is set or removed. As a special case, note that updating a cookie's properties is implemented as a two
   step process: the cookie to be updated is first removed entirely, generating a notification with 'cause' of 'overwrite' .
   Afterwards, a new cookie is written with the updated values, generating a second notification with 'cause' 'explicit'.

   Events will be put on the |channel| with signature [::on-changed [change-info]] where:

     |change-info| - https://developer.chrome.com/extensions/cookies#property-onChanged-changeInfo.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/cookies#event-onChanged."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.cookies namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.cookies",
   :since "38",
   :functions
   [{:id ::get,
     :name "get",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "cookie", :optional? true, :type "cookies.Cookie"}]}}]}
    {:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "cookies", :type "[array-of-cookies.Cookies]"}]}}]}
    {:id ::set,
     :name "set",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "cookie", :optional? true, :type "cookies.Cookie"}]}}]}
    {:id ::remove,
     :name "remove",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "details", :optional? true, :type "object"}]}}]}
    {:id ::get-all-cookie-stores,
     :name "getAllCookieStores",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "cookie-stores", :type "[array-of-cookies.CookieStores]"}]}}]}],
   :events [{:id ::on-changed, :name "onChanged", :params [{:name "change-info", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))