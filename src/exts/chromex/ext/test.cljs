(ns chromex.ext.test (:require-macros [chromex.ext.test :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-config* [config]
  (gen-wrap :function ::get-config config))

(defn notify-fail* [config message]
  (gen-wrap :function ::notify-fail config message))

(defn notify-pass* [config message]
  (gen-wrap :function ::notify-pass config message))

(defn log* [config message]
  (gen-wrap :function ::log config message))

(defn send-message* [config message]
  (gen-wrap :function ::send-message config message))

(defn callback-added* [config]
  (gen-wrap :function ::callback-added config))

(defn run-next-test* [config]
  (gen-wrap :function ::run-next-test config))

(defn fail* [config message]
  (gen-wrap :function ::fail config message))

(defn succeed* [config message]
  (gen-wrap :function ::succeed config message))

(defn run-with-natives-enabled* [config]
  (gen-wrap :function ::run-with-natives-enabled config))

(defn get-module-system* [config context]
  (gen-wrap :function ::get-module-system config context))

(defn assert-true* [config test message]
  (gen-wrap :function ::assert-true config test message))

(defn assert-false* [config test message]
  (gen-wrap :function ::assert-false config test message))

(defn assert-bool* [config test expected message]
  (gen-wrap :function ::assert-bool config test expected message))

(defn check-deep-eq* [config expected actual]
  (gen-wrap :function ::check-deep-eq config expected actual))

(defn assert-eq* [config expected actual message]
  (gen-wrap :function ::assert-eq config expected actual message))

(defn assert-no-last-error* [config]
  (gen-wrap :function ::assert-no-last-error config))

(defn assert-last-error* [config expected-error]
  (gen-wrap :function ::assert-last-error config expected-error))

(defn assert-throws* [config self args message]
  (gen-wrap :function ::assert-throws config self args message))

(defn callback* [config expected-error]
  (gen-wrap :function ::callback config expected-error))

(defn listen-once* [config event]
  (gen-wrap :function ::listen-once config event))

(defn listen-forever* [config event]
  (gen-wrap :function ::listen-forever config event))

(defn callback-pass* [config]
  (gen-wrap :function ::callback-pass config))

(defn callback-fail* [config expected-error]
  (gen-wrap :function ::callback-fail config expected-error))

(defn run-tests* [config tests]
  (gen-wrap :function ::run-tests config tests))

(defn get-api-features* [config]
  (gen-wrap :function ::get-api-features config))

(defn get-api-definitions* [config api-names]
  (gen-wrap :function ::get-api-definitions config api-names))

(defn is-processing-user-gesture* [config]
  (gen-wrap :function ::is-processing-user-gesture config))

(defn run-with-user-gesture* [config]
  (gen-wrap :function ::run-with-user-gesture config))

(defn run-without-user-gesture* [config]
  (gen-wrap :function ::run-without-user-gesture config))

(defn wait-for-round-trip* [config message]
  (gen-wrap :function ::wait-for-round-trip config message))

(defn set-exception-handler* [config]
  (gen-wrap :function ::set-exception-handler config))

(defn get-wake-event-page* [config]
  (gen-wrap :function ::get-wake-event-page config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-message* [config channel & args]
  (gen-wrap :event ::on-message config channel args))

