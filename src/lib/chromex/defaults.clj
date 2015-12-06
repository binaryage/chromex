(ns chromex.defaults
  (:require [chromex.marshalling :refer [gen-marshalling]]))

(defn default-gen-active-config []
  `(chromex.config/get-active-config))

(def default-gen-marshalling gen-marshalling)

(defn default-compiler-println [& args]
  (binding [*out* *err*]
    (apply println args)))