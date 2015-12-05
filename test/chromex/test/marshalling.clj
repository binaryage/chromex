(ns chromex.test.marshalling
  (:require [chromex-lib.marshalling :as default-marshalling]))

(defn marshall-to-chrome [config id type param]
  (case type
    "marshalled-type" `(chromex.test.marshalling/to-native-marshalled-type ~config ~param)
    (default-marshalling/marshall-to-chrome config id type param)))

(defn marshall-from-chrome [config id type param]
  (case type
    "marshalled-type" `(chromex.test.marshalling/from-native-marshalled-type ~config ~param)
    (default-marshalling/marshall-from-chrome config id type param)))

(defn custom-gen-marshalling [config direction id type param]
  (case direction
    :to-chrome (marshall-to-chrome config id type param)
    :from-chrome (marshall-from-chrome config id type param)))
