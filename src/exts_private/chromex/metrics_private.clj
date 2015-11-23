(ns chromex.metrics-private
  "  * available since Chrome 17
     * https://developer.chrome.com/extensions/metricsPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-is-crash-reporting-enabled
  "Returns true if the user opted in to sending crash reports."
  ([#_callback] (gen-call :function ::get-is-crash-reporting-enabled (meta &form))))

(defmacro get-field-trial
  "Returns the group name chosen for the named trial, or the empty string if the trial does not exist or is not
   enabled."
  ([name #_callback] (gen-call :function ::get-field-trial (meta &form) name)))

(defmacro get-variation-params
  "Returns variation parameters for the named trial if available, or undefined otherwise."
  ([name #_callback] (gen-call :function ::get-variation-params (meta &form) name)))

(defmacro record-user-action
  "Records an action performed by the user."
  ([name] (gen-call :function ::record-user-action (meta &form) name)))

(defmacro record-percentage
  "Records a percentage value from 1 to 100."
  ([metric-name value] (gen-call :function ::record-percentage (meta &form) metric-name value)))

(defmacro record-count
  "Records a value than can range from 1 to 1,000,000."
  ([metric-name value] (gen-call :function ::record-count (meta &form) metric-name value)))

(defmacro record-small-count
  "Records a value than can range from 1 to 100."
  ([metric-name value] (gen-call :function ::record-small-count (meta &form) metric-name value)))

(defmacro record-medium-count
  "Records a value than can range from 1 to 10,000."
  ([metric-name value] (gen-call :function ::record-medium-count (meta &form) metric-name value)))

(defmacro record-time
  "Records an elapsed time of no more than 10 seconds.  The sample value is specified in milliseconds."
  ([metric-name value] (gen-call :function ::record-time (meta &form) metric-name value)))

(defmacro record-medium-time
  "Records an elapsed time of no more than 3 minutes.  The sample value is specified in milliseconds."
  ([metric-name value] (gen-call :function ::record-medium-time (meta &form) metric-name value)))

(defmacro record-long-time
  "Records an elapsed time of no more than 1 hour.  The sample value is specified in milliseconds."
  ([metric-name value] (gen-call :function ::record-long-time (meta &form) metric-name value)))

(defmacro record-sparse-value
  "Increments the count associated with |value| in the sparse histogram defined by the |metricName|."
  ([metric-name value] (gen-call :function ::record-sparse-value (meta &form) metric-name value)))

(defmacro record-value
  "Adds a value to the given metric."
  ([metric value] (gen-call :function ::record-value (meta &form) metric value)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.metricsPrivate",
   :since "17",
   :functions
   [{:id ::get-is-crash-reporting-enabled,
     :name "getIsCrashReportingEnabled",
     :since "29",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "is-enabled", :type "boolean"}]}}]}
    {:id ::get-field-trial,
     :name "getFieldTrial",
     :since "29",
     :callback? true,
     :params
     [{:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "group", :type "string"}]}}]}
    {:id ::get-variation-params,
     :name "getVariationParams",
     :since "30",
     :callback? true,
     :params
     [{:name "name", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "params", :optional? true, :type "object"}]}}]}
    {:id ::record-user-action, :name "recordUserAction", :params [{:name "name", :type "string"}]}
    {:id ::record-percentage,
     :name "recordPercentage",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-count,
     :name "recordCount",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-small-count,
     :name "recordSmallCount",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-medium-count,
     :name "recordMediumCount",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-time,
     :name "recordTime",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-medium-time,
     :name "recordMediumTime",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-long-time,
     :name "recordLongTime",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-sparse-value,
     :name "recordSparseValue",
     :since "35",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-value,
     :name "recordValue",
     :params [{:name "metric", :type "metricsPrivate.MetricType"} {:name "value", :type "integer"}]}]})

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