(ns chromex.printer-provider-internal
  "printerProviderInternal
   Internal API used to run callbacks passed to chrome.printerProvider API
   events.
   When dispatching a chrome.printerProvider API event, its arguments will be
   massaged in custom bindings so a callback is added. The callback uses
   chrome.printerProviderInternal API to report the event results.
   In order to identify the event for which the callback is called, the event
   is internally dispatched having a requestId argument (which is removed from
   the argument list before the event actually reaches the event listeners). The
   requestId is forwarded to the chrome.printerProviderInternal API functions.
   
     * available since Chrome 44
     * https://developer.chrome.com/extensions/printerProviderInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro report-printers
  "Runs callback to printerProvider.onGetPrintersRequested event.
   
     |requestId| - Parameter identifying the event instance for which the     callback is run.
     |printers| - List of printers reported by the extension."
  ([request-id printers] (gen-call :function ::report-printers &form request-id printers))
  ([request-id] `(report-printers ~request-id :omit)))

(defmacro report-usb-printer-info
  "Runs callback to printerProvider.onUsbAccessGranted event.
   
     |requestId| - Parameter identifying the event instance for which the     callback is run.
     |printerInfo| - Printer information reported by the extension."
  ([request-id printer-info] (gen-call :function ::report-usb-printer-info &form request-id printer-info))
  ([request-id] `(report-usb-printer-info ~request-id :omit)))

(defmacro report-printer-capability
  "Runs callback to printerProvider.onGetCapabilityRequested event."
  ([request-id capability] (gen-call :function ::report-printer-capability &form request-id capability))
  ([request-id] `(report-printer-capability ~request-id :omit)))

(defmacro report-print-result
  "Runs callback to printerProvider.onPrintRequested event.
   
     |error| - The requested print job result."
  ([request-id error] (gen-call :function ::report-print-result &form request-id error))
  ([request-id] `(report-print-result ~request-id :omit)))

(defmacro get-print-data
  "Gets information needed to create a print data blob for a print request. The blob will be dispatched to the extension via
   printerProvider.onPrintRequested event.
   
     |requestId| - The request id for the print request for which data is     needed.
     |callback| - Callback called with the information needed to create a blob     of print data.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([request-id #_callback] (gen-call :function ::get-print-data &form request-id)))

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
  {:namespace "chrome.printerProviderInternal",
   :since "44",
   :functions
   [{:id ::report-printers,
     :name "reportPrinters",
     :params
     [{:name "request-id", :type "integer"}
      {:name "printers", :optional? true, :type "[array-of-printerProvider.PrinterInfos]"}]}
    {:id ::report-usb-printer-info,
     :name "reportUsbPrinterInfo",
     :since "45",
     :params
     [{:name "request-id", :type "integer"}
      {:name "printer-info", :optional? true, :type "printerProvider.PrinterInfo"}]}
    {:id ::report-printer-capability,
     :name "reportPrinterCapability",
     :params [{:name "request-id", :type "integer"} {:name "capability", :optional? true, :type "object"}]}
    {:id ::report-print-result,
     :name "reportPrintResult",
     :params [{:name "request-id", :type "integer"} {:name "error", :optional? true, :type "unknown-type"}]}
    {:id ::get-print-data,
     :name "getPrintData",
     :callback? true,
     :params
     [{:name "request-id", :type "integer"}
      {:name "callback", :type :callback, :callback {:params [{:name "blob-info", :type "object"}]}}]}]})

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