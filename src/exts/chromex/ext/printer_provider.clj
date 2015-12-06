(ns chromex.ext.printer-provider
  "The chrome.printerProvider API exposes events used by print
   manager to query printers controlled by extensions, to query their
   capabilities and to submit print jobs to these printers.
   
     * available since Chrome 44
     * https://developer.chrome.com/extensions/printerProvider"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-get-printers-requested-events
  "Event fired when print manager requests printers provided by extensions.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-get-printers-requested &form channel args)))
(defmacro tap-on-get-usb-printer-info-requested-events
  "Event fired when print manager requests information about a USB device that may be a printer. Note: An application should
   not rely on this event being fired more than once per device. If a connected device is supported it should be returned in
   the 'onGetPrintersRequested' event.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-get-usb-printer-info-requested &form channel args)))
(defmacro tap-on-get-capability-requested-events
  "Event fired when print manager requests printer capabilities.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-get-capability-requested &form channel args)))
(defmacro tap-on-print-requested-events
  "Event fired when print manager requests printing.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-print-requested &form channel args)))

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
  {:namespace "chrome.printerProvider",
   :since "44",
   :events
   [{:id ::on-get-printers-requested,
     :name "onGetPrintersRequested",
     :params [{:name "result-callback", :type :callback}]}
    {:id ::on-get-usb-printer-info-requested,
     :name "onGetUsbPrinterInfoRequested",
     :since "45",
     :params [{:name "device", :type "usb.Device"} {:name "result-callback", :type :callback}]}
    {:id ::on-get-capability-requested,
     :name "onGetCapabilityRequested",
     :params [{:name "printer-id", :type "string"} {:name "result-callback", :type :callback}]}
    {:id ::on-print-requested,
     :name "onPrintRequested",
     :params [{:name "print-job", :type "object"} {:name "result-callback", :type :callback}]}]})

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