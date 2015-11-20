(ns chromex.storage
  "Use the chrome.storage API to store, retrieve, and track changes to user data.
   
     * available since Chrome 20
     * https://developer.chrome.com/extensions/storage"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -----------------------------------------------------------------------------------------------------

(defmacro get-sync
  "Items in the sync storage area are synced using Chrome Sync."
  []
  (gen-call :property ::sync (meta &form)))

(defmacro get-local
  "Items in the local storage area are local to each machine."
  []
  (gen-call :property ::local (meta &form)))

(defmacro get-managed
  "Items in the managed storage area are set by the domain administrator, and are read-only for the extension; trying
   to modify this namespace results in an error."
  []
  (gen-call :property ::managed (meta &form)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-changed
  "Fired when one or more items change."
  [channel]
  (gen-call :event ::on-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.storage",
   :since "20",
   :properties
   [{:id ::sync, :name "sync", :return-type "storage.StorageArea"}
    {:id ::local, :name "local", :return-type "storage.StorageArea"}
    {:id ::managed, :name "managed", :since "33", :return-type "storage.StorageArea"}],
   :events
   [{:id ::on-changed,
     :name "onChanged",
     :params [{:name "changes", :type "object"} {:name "area-name", :type "string"}]}]})

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