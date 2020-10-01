(ns chromex.ext.declarative-net-request (:require-macros [chromex.ext.declarative-net-request :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn max-number-of-rules* [config]
  (gen-wrap :property ::max-number-of-rules config))

(defn max-number-of-dynamic-rules* [config]
  (gen-wrap :property ::max-number-of-dynamic-rules config))

(defn getmatchedrules-quota-interval* [config]
  (gen-wrap :property ::getmatchedrules-quota-interval config))

(defn max-getmatchedrules-calls-per-interval* [config]
  (gen-wrap :property ::max-getmatchedrules-calls-per-interval config))

(defn max-number-of-regex-rules* [config]
  (gen-wrap :property ::max-number-of-regex-rules config))

(defn max-number-of-static-rulesets* [config]
  (gen-wrap :property ::max-number-of-static-rulesets config))

(defn dynamic-ruleset-id* [config]
  (gen-wrap :property ::dynamic-ruleset-id config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn update-dynamic-rules* [config options]
  (gen-wrap :function ::update-dynamic-rules config options))

(defn get-dynamic-rules* [config]
  (gen-wrap :function ::get-dynamic-rules config))

(defn update-enabled-rulesets* [config options]
  (gen-wrap :function ::update-enabled-rulesets config options))

(defn get-enabled-rulesets* [config]
  (gen-wrap :function ::get-enabled-rulesets config))

(defn get-matched-rules* [config filter]
  (gen-wrap :function ::get-matched-rules config filter))

(defn set-action-count-as-badge-text* [config enable]
  (gen-wrap :function ::set-action-count-as-badge-text config enable))

(defn is-regex-supported* [config regex-options]
  (gen-wrap :function ::is-regex-supported config regex-options))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-rule-matched-debug* [config channel & args]
  (gen-wrap :event ::on-rule-matched-debug config channel args))

