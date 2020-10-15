(ns chromex.ext.storage
  "Use the chrome.storage API to store, retrieve, and track changes to user data.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/storage"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-sync
  "Items in the sync storage area are synced using Chrome Sync.

   https://developer.chrome.com/extensions/storage#property-sync."
  ([] (gen-call :property ::sync &form)))

(defmacro get-local
  "Items in the local storage area are local to each machine.

   https://developer.chrome.com/extensions/storage#property-local."
  ([] (gen-call :property ::local &form)))

(defmacro get-managed
  "Items in the managed storage area are set by the domain administrator, and are read-only for the extension; trying to
   modify this namespace results in an error.

   https://developer.chrome.com/extensions/storage#property-managed."
  ([] (gen-call :property ::managed &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-changed-events
  "Fired when one or more items change.

   Events will be put on the |channel| with signature [::on-changed [changes area-name]] where:

     |changes| - Object mapping each key that changed to its corresponding 'storage.StorageChange' for that item.
     |area-name| - The name of the storage area ('sync', 'local' or 'managed') the changes are for.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/storage#event-onChanged."
  ([channel & args] (apply gen-call :event ::on-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.storage namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.storage",
   :since "38",
   :properties
   [{:id ::sync, :name "sync", :return-type "storage.StorageArea"}
    {:id ::local, :name "local", :return-type "storage.StorageArea"}
    {:id ::managed, :name "managed", :return-type "storage.StorageArea"}],
   :events
   [{:id ::on-changed,
     :name "onChanged",
     :params [{:name "changes", :type "object"} {:name "area-name", :type "string"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))