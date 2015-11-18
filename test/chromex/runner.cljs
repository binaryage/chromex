(ns chromex.runner
  (:require [cljs.test :as test :refer-macros [run-tests] :refer [report]]
            [chromex-lib.config :refer [get-active-config set-active-config!]]
            [chromex-lib.defaults :refer [console-log]]
            [chromex.test.playground]
            [chromex.playground]))

(enable-console-print!)

(defmethod report [::test/default :summary] [m]
  (println "\nRan" (:test m) "tests containing"
    (+ (:pass m) (:fail m) (:error m)) "assertions.")
  (println (:fail m) "failures," (:error m) "errors.")
  (aset js/window "test-failures" (+ (:fail m) (:error m)))
  (println "TESTS DONE"))

(defn friendly-args [args]
  (map #(if (fn? %) "<fn>" %)  args))

(defn test-logger [& args]
  (apply console-log "[chromex (test)]" (friendly-args args)))

(let [new-config (-> (get-active-config) (assoc :logger test-logger))]
  (set-active-config! new-config))

(test/run-tests
  (cljs.test/empty-env ::test/default)
  'chromex.test.playground)
