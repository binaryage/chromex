(ns chromex.app.metrics-private
  "  * available since Chrome 50
     * https://developer.chrome.com/extensions/metricsPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-is-crash-reporting-enabled
  "Returns true if the user opted in to sending crash reports.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [is_enabled] where:
   
     |is_enabled| - See https://developer.chrome.com/extensions/metricsPrivate#property-callback-is_enabled.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-getIsCrashReportingEnabled."
  ([#_callback] (gen-call :function ::get-is-crash-reporting-enabled &form)))

(defmacro get-field-trial
  "Returns the group name chosen for the named trial, or the empty string if the trial does not exist or is not enabled.
   
     |name| - See https://developer.chrome.com/extensions/metricsPrivate#property-getFieldTrial-name.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [group] where:
   
     |group| - See https://developer.chrome.com/extensions/metricsPrivate#property-callback-group.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-getFieldTrial."
  ([name #_callback] (gen-call :function ::get-field-trial &form name)))

(defmacro get-variation-params
  "Returns variation parameters for the named trial if available, or undefined otherwise.
   
     |name| - See https://developer.chrome.com/extensions/metricsPrivate#property-getVariationParams-name.
   
   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [params] where:
   
     |params| - See https://developer.chrome.com/extensions/metricsPrivate#property-callback-params.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-getVariationParams."
  ([name #_callback] (gen-call :function ::get-variation-params &form name)))

(defmacro record-user-action
  "Records an action performed by the user.
   
     |name| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordUserAction-name.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordUserAction."
  ([name] (gen-call :function ::record-user-action &form name)))

(defmacro record-percentage
  "Records a percentage value from 1 to 100.
   
     |metricName| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordPercentage-metricName.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordPercentage-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordPercentage."
  ([metric-name value] (gen-call :function ::record-percentage &form metric-name value)))

(defmacro record-count
  "Records a value than can range from 1 to 1,000,000.
   
     |metricName| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordCount-metricName.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordCount-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordCount."
  ([metric-name value] (gen-call :function ::record-count &form metric-name value)))

(defmacro record-small-count
  "Records a value than can range from 1 to 100.
   
     |metricName| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordSmallCount-metricName.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordSmallCount-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordSmallCount."
  ([metric-name value] (gen-call :function ::record-small-count &form metric-name value)))

(defmacro record-medium-count
  "Records a value than can range from 1 to 10,000.
   
     |metricName| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordMediumCount-metricName.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordMediumCount-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordMediumCount."
  ([metric-name value] (gen-call :function ::record-medium-count &form metric-name value)))

(defmacro record-time
  "Records an elapsed time of no more than 10 seconds.  The sample value is specified in milliseconds.
   
     |metricName| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordTime-metricName.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordTime-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordTime."
  ([metric-name value] (gen-call :function ::record-time &form metric-name value)))

(defmacro record-medium-time
  "Records an elapsed time of no more than 3 minutes.  The sample value is specified in milliseconds.
   
     |metricName| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordMediumTime-metricName.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordMediumTime-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordMediumTime."
  ([metric-name value] (gen-call :function ::record-medium-time &form metric-name value)))

(defmacro record-long-time
  "Records an elapsed time of no more than 1 hour.  The sample value is specified in milliseconds.
   
     |metricName| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordLongTime-metricName.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordLongTime-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordLongTime."
  ([metric-name value] (gen-call :function ::record-long-time &form metric-name value)))

(defmacro record-sparse-value
  "Increments the count associated with |value| in the sparse histogram defined by the |metricName|.
   
     |metricName| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordSparseValue-metricName.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordSparseValue-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordSparseValue."
  ([metric-name value] (gen-call :function ::record-sparse-value &form metric-name value)))

(defmacro record-value
  "Adds a value to the given metric.
   
     |metric| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordValue-metric.
     |value| - See https://developer.chrome.com/extensions/metricsPrivate#property-recordValue-value.
   
   See https://developer.chrome.com/extensions/metricsPrivate#method-recordValue."
  ([metric value] (gen-call :function ::record-value &form metric value)))

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
  {:namespace "chrome.metricsPrivate",
   :since "50",
   :functions
   [{:id ::get-is-crash-reporting-enabled,
     :name "getIsCrashReportingEnabled",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "is-enabled", :type "boolean"}]}}]}
    {:id ::get-field-trial,
     :name "getFieldTrial",
     :callback? true,
     :params
     [{:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "group", :type "string"}]}}]}
    {:id ::get-variation-params,
     :name "getVariationParams",
     :callback? true,
     :params
     [{:name "name", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "params", :optional? true, :type "object"}]}}]}
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
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-value,
     :name "recordValue",
     :params [{:name "metric", :type "metricsPrivate.MetricType"} {:name "value", :type "integer"}]}]})

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