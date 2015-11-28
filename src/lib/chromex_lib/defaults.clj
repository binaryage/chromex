(ns chromex-lib.defaults
  (:require [chromex-lib.marshalling :refer [gen-marshalling]]))

(defn default-gen-active-config []
  `(chromex-lib.config/get-active-config))

(def default-gen-marshalling gen-marshalling)

(defn default-compiler-println [& args]
  (binding [*out* *err*]
    (apply println args)))