(ns chromex.ext.declarative-net-request
  "The chrome.declarativeNetRequest API is used to block or modify
   network requests by specifying declarative rules. This lets extensions
   modify network requests without intercepting them and viewing their content,
   thus providing more privacy.

     * available since Chrome 84
     * https://developer.chrome.com/extensions/declarativeNetRequest"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-max-number-of-rules
  "The maximum number of rules that an extension can specify across its enabled static rulesets. Any excess rules will be
   ignored and an install warning will be raised.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_NUMBER_OF_RULES."
  ([] (gen-call :property ::max-number-of-rules &form)))

(defmacro get-max-number-of-dynamic-rules
  "The maximum number of dynamic rules that an extension can add.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_NUMBER_OF_DYNAMIC_RULES."
  ([] (gen-call :property ::max-number-of-dynamic-rules &form)))

(defmacro get-getmatchedrules-quota-interval
  "Time interval within which MAX_GETMATCHEDRULES_CALLS_PER_INTERVAL getMatchedRules calls can be made, specified in minutes.
   Additional calls will fail immediately and set 'runtime.lastError'. Note: getMatchedRules calls associated with a user
   gesture are exempt from the quota.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-GETMATCHEDRULES_QUOTA_INTERVAL."
  ([] (gen-call :property ::getmatchedrules-quota-interval &form)))

(defmacro get-max-getmatchedrules-calls-per-interval
  "The number of times getMatchedRules can be called within a period of GETMATCHEDRULES_QUOTA_INTERVAL.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_GETMATCHEDRULES_CALLS_PER_INTERVAL."
  ([] (gen-call :property ::max-getmatchedrules-calls-per-interval &form)))

(defmacro get-max-number-of-regex-rules
  "The maximum number of regular expression rules that an extension can add. This limit is evaluated separately for the set of
   dynamic rules and those specified in the rule resources file.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_NUMBER_OF_REGEX_RULES."
  ([] (gen-call :property ::max-number-of-regex-rules &form)))

(defmacro get-max-number-of-static-rulesets
  "The maximum number of static Rulesets an extension can specify as part of the 'rule_resources' manifest key.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-MAX_NUMBER_OF_STATIC_RULESETS."
  ([] (gen-call :property ::max-number-of-static-rulesets &form)))

(defmacro get-dynamic-ruleset-id
  "Ruleset ID for the dynamic rules added by the extension.

   https://developer.chrome.com/extensions/declarativeNetRequest#property-DYNAMIC_RULESET_ID."
  ([] (gen-call :property ::dynamic-ruleset-id &form)))

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro update-dynamic-rules
  "Modifies the current set of dynamic rules for the extension. The rules with IDs listed in options.removeRuleIds are first
   removed, and then the rules given in options.addRules are added. Notes:  This update happens as a single atomic operation:
   either all specified rules are added and removed, or an error is returned. These rules are persisted across browser
   sessions and across extension updates. Static rules specified as part of the extension package can not be removed using
   this function. 'MAX_NUMBER_OF_DYNAMIC_RULES' is the maximum number of dynamic rules an extension can add.

     |options| - https://developer.chrome.com/extensions/declarativeNetRequest#property-updateDynamicRules-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-updateDynamicRules."
  ([options] (gen-call :function ::update-dynamic-rules &form options)))

(defmacro get-dynamic-rules
  "Returns the current set of dynamic rules for the extension.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [rules] where:

     |rules| - https://developer.chrome.com/extensions/declarativeNetRequest#property-callback-rules.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-getDynamicRules."
  ([] (gen-call :function ::get-dynamic-rules &form)))

(defmacro update-enabled-rulesets
  "Updates the set of enabled static rulesets for the extension. The rulesets with IDs listed in options.disableRulesetIds are
   first removed, and then the rulesets listed in options.enableRulesetIds are added. Note that the set of enabled static
   rulesets is persisted across sessions but not across extension updates, i.e. the rule_resources manifest key will determine
   the set of enabled static rulesets on each extension update.

     |options| - https://developer.chrome.com/extensions/declarativeNetRequest#property-updateEnabledRulesets-options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-updateEnabledRulesets."
  ([options] (gen-call :function ::update-enabled-rulesets &form options)))

(defmacro get-enabled-rulesets
  "Returns the ids for the current set of enabled static rulesets.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [ruleset-ids] where:

     |ruleset-ids| - https://developer.chrome.com/extensions/declarativeNetRequest#property-callback-rulesetIds.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-getEnabledRulesets."
  ([] (gen-call :function ::get-enabled-rulesets &form)))

(defmacro get-matched-rules
  "Returns all rules matched for the extension. Callers can optionally filter the list of matched rules by specifying a
   |filter|. This method is only available to extensions with the declarativeNetRequestFeedback permission or having the
   activeTab permission granted for the tabId specified in filter. Note: Rules not associated with an active document that
   were matched more than five minutes ago will not be returned.

     |filter| - An object to filter the list of matched rules.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [details] where:

     |details| - https://developer.chrome.com/extensions/declarativeNetRequest#property-callback-details.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-getMatchedRules."
  ([filter] (gen-call :function ::get-matched-rules &form filter))
  ([] `(get-matched-rules :omit)))

(defmacro set-extension-action-options
  "Configures how matched actions will be displayed on the extension action. This preference is persisted across sessions.

     |options| - https://developer.chrome.com/extensions/declarativeNetRequest#property-setExtensionActionOptions-options.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-setExtensionActionOptions."
  ([options] (gen-call :function ::set-extension-action-options &form options)))

(defmacro is-regex-supported
  "Checks if the given regular expression will be supported as a regexFilter rule condition.

     |regex-options| - The regular expression to check.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - https://developer.chrome.com/extensions/declarativeNetRequest#property-callback-result.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/declarativeNetRequest#method-isRegexSupported."
  ([regex-options] (gen-call :function ::is-regex-supported &form regex-options)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-rule-matched-debug-events
  "Fired when a rule is matched with a request. Only available for unpacked extensions with the declarativeNetRequestFeedback
   permission as this is intended to be used for debugging purposes only.

   Events will be put on the |channel| with signature [::on-rule-matched-debug [info]] where:

     |info| - The rule that has been matched along with information about the associated request.

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
   :since "84",
   :properties
   [{:id ::max-number-of-rules, :name "MAX_NUMBER_OF_RULES", :return-type "unknown-type"}
    {:id ::max-number-of-dynamic-rules, :name "MAX_NUMBER_OF_DYNAMIC_RULES", :return-type "unknown-type"}
    {:id ::getmatchedrules-quota-interval, :name "GETMATCHEDRULES_QUOTA_INTERVAL", :return-type "unknown-type"}
    {:id ::max-getmatchedrules-calls-per-interval,
     :name "MAX_GETMATCHEDRULES_CALLS_PER_INTERVAL",
     :return-type "unknown-type"}
    {:id ::max-number-of-regex-rules, :name "MAX_NUMBER_OF_REGEX_RULES", :return-type "unknown-type"}
    {:id ::max-number-of-static-rulesets, :name "MAX_NUMBER_OF_STATIC_RULESETS", :return-type "unknown-type"}
    {:id ::dynamic-ruleset-id, :name "DYNAMIC_RULESET_ID", :return-type "unknown-type"}],
   :functions
   [{:id ::update-dynamic-rules,
     :name "updateDynamicRules",
     :callback? true,
     :params [{:name "options", :since "future", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-dynamic-rules,
     :name "getDynamicRules",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "rules", :type "[array-of-declarativeNetRequest.Rules]"}]}}]}
    {:id ::update-enabled-rulesets,
     :name "updateEnabledRulesets",
     :callback? true,
     :params [{:name "options", :since "future", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::get-enabled-rulesets,
     :name "getEnabledRulesets",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "ruleset-ids", :type "[array-of-strings]"}]}}]}
    {:id ::get-matched-rules,
     :name "getMatchedRules",
     :callback? true,
     :params
     [{:name "filter", :optional? true, :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "details", :type "object"}]}}]}
    {:id ::set-extension-action-options,
     :name "setExtensionActionOptions",
     :since "future",
     :params [{:name "options", :type "object"}]}
    {:id ::is-regex-supported,
     :name "isRegexSupported",
     :since "future",
     :callback? true,
     :params
     [{:name "regex-options", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "object"}]}}]}],
   :events
   [{:id ::on-rule-matched-debug, :name "onRuleMatchedDebug", :params [{:name "info", :since "85", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))