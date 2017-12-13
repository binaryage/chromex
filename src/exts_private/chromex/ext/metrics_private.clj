(ns chromex.ext.metrics-private
  "  * available since Chrome 59"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-is-crash-reporting-enabled
  "Returns true if the user opted in to sending crash reports.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [is-enabled] where:

     |is-enabled| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-is-crash-reporting-enabled &form)))

(defmacro get-field-trial
  "Returns the group name chosen for the named trial, or the empty string if the trial does not exist or is not enabled.

     |name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [group] where:

     |group| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([name] (gen-call :function ::get-field-trial &form name)))

(defmacro get-variation-params
  "Returns variation parameters for the named trial if available, or undefined otherwise.

     |name| - ?

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [params] where:

     |params| - ?

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([name] (gen-call :function ::get-variation-params &form name)))

(defmacro record-user-action
  "Records an action performed by the user.

     |name| - ?"
  ([name] (gen-call :function ::record-user-action &form name)))

(defmacro record-percentage
  "Records a percentage value from 1 to 100.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-percentage &form metric-name value)))

(defmacro record-count
  "Records a value than can range from 1 to 1,000,000.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-count &form metric-name value)))

(defmacro record-small-count
  "Records a value than can range from 1 to 100.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-small-count &form metric-name value)))

(defmacro record-medium-count
  "Records a value than can range from 1 to 10,000.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-medium-count &form metric-name value)))

(defmacro record-time
  "Records an elapsed time of no more than 10 seconds.  The sample value is specified in milliseconds.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-time &form metric-name value)))

(defmacro record-medium-time
  "Records an elapsed time of no more than 3 minutes.  The sample value is specified in milliseconds.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-medium-time &form metric-name value)))

(defmacro record-long-time
  "Records an elapsed time of no more than 1 hour.  The sample value is specified in milliseconds.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-long-time &form metric-name value)))

(defmacro record-sparse-hashable
  "Increments the count associated with the hash of |value| in the sparse histogram defined by the |metricName|.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-sparse-hashable &form metric-name value)))

(defmacro record-sparse-value
  "Increments the count associated with |value| in the sparse histogram defined by the |metricName|.

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-sparse-value &form metric-name value)))

(defmacro record-value
  "Adds a value to the given metric.

     |metric| - ?
     |value| - ?"
  ([metric value] (gen-call :function ::record-value &form metric value)))

(defmacro record-boolean
  "Records a boolean value to the given metric. Analogous to base::UmaHistogramBoolean().

     |metric-name| - ?
     |value| - ?"
  ([metric-name value] (gen-call :function ::record-boolean &form metric-name value)))

(defmacro record-enumeration-value
  "Records an enumeration value to the given metric. Analogous to base::UmaHistogramEnumeration(). Use recordSparseValue for
   sparse enums or enums not starting at 0.

     |metric-name| - ?
     |value| - ?
     |enum-size| - ?"
  ([metric-name value enum-size] (gen-call :function ::record-enumeration-value &form metric-name value enum-size)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.metrics-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.metricsPrivate",
   :since "59",
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
    {:id ::record-sparse-hashable,
     :name "recordSparseHashable",
     :since "62",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "string"}]}
    {:id ::record-sparse-value,
     :name "recordSparseValue",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "integer"}]}
    {:id ::record-value,
     :name "recordValue",
     :params [{:name "metric", :type "metricsPrivate.MetricType"} {:name "value", :type "integer"}]}
    {:id ::record-boolean,
     :name "recordBoolean",
     :since "master",
     :params [{:name "metric-name", :type "string"} {:name "value", :type "boolean"}]}
    {:id ::record-enumeration-value,
     :name "recordEnumerationValue",
     :since "master",
     :params
     [{:name "metric-name", :type "string"} {:name "value", :type "integer"} {:name "enum-size", :type "integer"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))