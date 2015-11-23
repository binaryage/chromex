(ns chromex.system.storage
  "Use the chrome.system.storage API to query storage device
   information and be notified when a removable storage device is attached and
   detached.
   
     * available since Chrome 30
     * https://developer.chrome.com/extensions/system.storage"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-info
  "Get the storage information from the system. The argument passed to the callback is an array of StorageUnitInfo
   objects."
  ([#_callback] (gen-call :function ::get-info (meta &form))))

(defmacro eject-device
  "Ejects a removable storage device."
  ([id #_callback] (gen-call :function ::eject-device (meta &form) id)))

(defmacro get-available-capacity
  "Get the available capacity of a specified |id| storage device. The |id| is the transient device ID from
   StorageUnitInfo."
  ([id #_callback] (gen-call :function ::get-available-capacity (meta &form) id)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-attached-events
  "Fired when a new removable storage is attached to the system."
  [channel]
  (gen-call :event ::on-attached (meta &form) channel))

(defmacro tap-on-detached-events
  "Fired when a removable storage is detached from the system."
  [channel]
  (gen-call :event ::on-detached (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.system.storage",
   :since "30",
   :functions
   [{:id ::get-info,
     :name "getInfo",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "info", :type "[array-of-system.storage.StorageUnitInfos]"}]}}]}
    {:id ::eject-device,
     :name "ejectDevice",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "unknown-type"}]}}]}
    {:id ::get-available-capacity,
     :name "getAvailableCapacity",
     :since "48",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "info", :type "object"}]}}]}],
   :events
   [{:id ::on-attached, :name "onAttached", :params [{:name "info", :type "system.storage.StorageUnitInfo"}]}
    {:id ::on-detached, :name "onDetached", :params [{:name "id", :type "string"}]}]})

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