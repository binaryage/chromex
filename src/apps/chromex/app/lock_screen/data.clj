(ns chromex.app.lock-screen.data
  "
     The API that can be used by an app to create and manage data on the
     Chrome OS lock screen.

     The API usability will depend on the user session state:
     
       
         When the user session is locked, the API usage will only be allowed
         from the lock screen context.
       
       
         When the user session is not locked, the API usage will only be
         allowed outside the lock screen context - i.e. from the regular app
         context.
       
     

     Note that apps have reduced access to Chrome apps APIs from the lock screen
     context.

     * available since Chrome 72
     * https://developer.chrome.com/apps/lockScreen.data"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro create
  "Creates a new data item reference - available only in lock screen contexts.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [item] where:

     |item| - https://developer.chrome.com/apps/lockScreen.data#property-callback-item.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/lockScreen.data#method-create."
  ([] (gen-call :function ::create &form)))

(defmacro get-all
  "Gets references to all data items available to the app.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [items] where:

     |items| - https://developer.chrome.com/apps/lockScreen.data#property-callback-items.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/lockScreen.data#method-getAll."
  ([] (gen-call :function ::get-all &form)))

(defmacro get-content
  "Retrieves content of the data item identified by |id|.

     |id| - https://developer.chrome.com/apps/lockScreen.data#property-getContent-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [data] where:

     |data| - https://developer.chrome.com/apps/lockScreen.data#property-callback-data.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/lockScreen.data#method-getContent."
  ([id] (gen-call :function ::get-content &form id)))

(defmacro set-content
  "Sets contents of a data item. |id| - Identifies the target data item. |data| - The data item contents to set.

     |id| - https://developer.chrome.com/apps/lockScreen.data#property-setContent-id.
     |data| - https://developer.chrome.com/apps/lockScreen.data#property-setContent-data.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/lockScreen.data#method-setContent."
  ([id data] (gen-call :function ::set-content &form id data)))

(defmacro delete
  "Deletes a data item. The data item will not be available through this API anymore. |id| - Identifies the data item to
   delete.

     |id| - https://developer.chrome.com/apps/lockScreen.data#property-delete-id.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/lockScreen.data#method-delete."
  ([id] (gen-call :function ::delete &form id)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-data-items-available-events
  "Dispatched when new data items become available to main, non-lock screen app context - this event is not expected to be
   dispatched to the app in the lock screen context.

   Events will be put on the |channel| with signature [::on-data-items-available [event]] where:

     |event| - https://developer.chrome.com/apps/lockScreen.data#property-onDataItemsAvailable-event.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/lockScreen.data#event-onDataItemsAvailable."
  ([channel & args] (apply gen-call :event ::on-data-items-available &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.lock-screen.data namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.lockScreen.data",
   :since "72",
   :functions
   [{:id ::create,
     :name "create",
     :since "72",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "item", :type "lockScreen.data.DataItemInfo"}]}}]}
    {:id ::get-all,
     :name "getAll",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "items", :type "[array-of-lockScreen.data.DataItemInfos]"}]}}]}
    {:id ::get-content,
     :name "getContent",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "data", :type "ArrayBuffer"}]}}]}
    {:id ::set-content,
     :name "setContent",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "data", :type "ArrayBuffer"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::delete,
     :name "delete",
     :callback? true,
     :params [{:name "id", :type "string"} {:name "callback", :optional? true, :type :callback}]}],
   :events [{:id ::on-data-items-available, :name "onDataItemsAvailable", :params [{:name "event", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))