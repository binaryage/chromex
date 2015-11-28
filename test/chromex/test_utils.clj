(ns chromex.test-utils
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [environ.core :refer [env]]))

(defn get-test-mode []
  (cond
    (:running-dev-test env) "dev"
    (:running-advanced-test env) "advanced"
    :else "unknown"))

(def compiler-out-file (str ".compiler-out.txt"))

(defn output-path []
  (io/file "test" compiler-out-file)) ; relative to project root

(defn init! []
  (spit (output-path) ""))

(defn test-compiler-println [& args]
  (binding [*out* *err*]
    (spit (output-path) (string/join " " args) :append true)
    (apply println args)))