(ns chromex.test-utils
  (:require [chromex.support :as chromex-support]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [environ.core :refer [env]]))

(defn get-test-mode []
  (cond
    (:running-dev-test env) "dev"
    (:running-advanced-test env) "advanced"
    :else "unknown"))

(defmacro test-mode []
  (get-test-mode))

(def compiler-out-file ".compiler-out.txt")

(defn output-path []
  (io/file "test" compiler-out-file))                                                                                         ; relative to project root

(defn init! []
  (spit (output-path) ""))

(defn test-compiler-println [& args]
  (binding [*out* *err*]
    (spit (output-path) (string/join " " args) :append true)
    (apply println args)))

(defmacro valid-api-version? [target since until]
  (chromex-support/valid-api-version? {:target-api-version target} since until))
