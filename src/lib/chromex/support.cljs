(ns chromex.support
  (:require [goog.object]))

(defn prepare-final-args-array [arg-descriptors api]
  (let [should-omit? (fn [[val param-name can-be-omitted?]]
                       (when (cljs.core/keyword-identical? val :omit)
                         (assert can-be-omitted?
                                 (str "Parameter '" param-name "' cannot be omitted in a call to '" api "'."
                                      " Parameter not declared as optional."))
                         true))]
    (->> arg-descriptors
         (remove should-omit?)
         (map first)
         (into-array))))