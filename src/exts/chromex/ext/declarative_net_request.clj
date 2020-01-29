(ns chromex.ext.declarative-net-request
  "The chrome.declarativeNetRequest API is used to block or modify
   network requests by specifying declarative rules.

     * available since Chrome 80
     * https://developer.chrome.com/extensions/declarativeNetRequest"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-number-of-rules
  "The maximum number of rules that an extension can specify in the rule resources file. Any excess rules will be ignored and
   an install warning will be raised.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_NUMBER_OF_RULES."
  ([] (gen-call :property ::max-number-of-rules &form)))

(defmacro get-max-number-of-dynamic-rules
  "The maximum number of dynamic rules that an extension can add.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_NUMBER_OF_DYNAMIC_RULES."
  ([] (gen-call :property ::max-number-of-dynamic-rules &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro update-dynamic-rules
  "Modify the current set of dynamic rules for the extension. The rules with IDs listed in rule_ids_to_remove are first
   removed, and then the rules given in rules_to_add are added. This update happens as a single atomic operation: either all
   specified rules are added and removed, or an error is returned. These rules are persisted across browser sessions. Any ids
   in rule_ids_to_remove that are not present will be ignored. Note that static rules specified as part of the extension
   package can not be removed using this function. Note:  MAX_NUMBER_OF_DYNAMIC_RULES is the maximum number of dynamic rules
   an extension can add.

     |rule-ids-to-remove| - The IDs of rules to remove.
     |rules-to-add| - The rules to add.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-updateDynamicRules."
  ([rule-ids-to-remove rules-to-add] (gen-call :function ::update-dynamic-rules &form rule-ids-to-remove rules-to-add)))

(defmacro get-dynamic-rules
  "Returns the current set of dynamic rules for the extension.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [rules] where:

     |rules| - https://developer.chrome.com/extensions/declarativeNetRequest#property-callback-rules.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-getDynamicRules."
  ([] (gen-call :function ::get-dynamic-rules &form)))

(defmacro set-action-count-as-badge-text
  "Sets whether to automatically badge extension's icon to the matched action count for a tab. This preference is persisted
   across sessions and is false by default.

     |enable| - https://developer.chrome.com/extensions/declarativeNetRequest#property-setActionCountAsBadgeText-enable.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-setActionCountAsBadgeText."
  ([enable] (gen-call :function ::set-action-count-as-badge-text &form enable)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-rule-matched-debug-events
  "Fired when a rule is matched with a request. Only available for unpacked extensions as this is intended to be used for
   debugging purposes only.

   Events will be put on the |channel| with signature [::on-rule-matched-debug [matched-rule-info]] where:

     |matched-rule-info| - The rule that has been matched along with information about the associated request.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/declarativeNetRequest#event-onRuleMatchedDebug."
  ([channel & args] (apply gen-call :event ::on-rule-matched-debug &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.declarative-net-request namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.declarativeNetRequest",
   :since "80",
   :properties
   [{:id ::max-number-of-rules, :name "MAX_NUMBER_OF_RULES", :return-type "unknown-type"}
    {:id ::max-number-of-dynamic-rules, :name "MAX_NUMBER_OF_DYNAMIC_RULES", :return-type "unknown-type"}],
   :functions
   [{:id ::update-dynamic-rules,
     :name "updateDynamicRules",
     :callback? true,
     :params
     [{:name "rule-ids-to-remove", :type "[array-of-integers]"}
      {:name "rules-to-add", :type "[array-of-declarativeNetRequest.Rules]"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-dynamic-rules,
     :name "getDynamicRules",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "rules", :type "[array-of-declarativeNetRequest.Rules]"}]}}]}
    {:id ::set-action-count-as-badge-text,
     :name "setActionCountAsBadgeText",
     :since "future",
     :params [{:name "enable", :type "boolean"}]}],
   :events
   [{:id ::on-rule-matched-debug,
     :name "onRuleMatchedDebug",
     :since "80",
     :params [{:name "matched-rule-info", :since "future", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))