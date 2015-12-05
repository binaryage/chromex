(ns chromex.test.marshalling
  (:require [chromex-lib.marshalling :as default-marshalling]))

(defn marshall-to-chrome [id type param]
  (case type
    "marshalled-type" `(chromex.test.marshalling/to-native-marshalled-type ~param)
    (default-marshalling/marshall-to-chrome id type param)))

(defn marshall-from-chrome [id type param]
  (case type
    "marshalled-type" `(chromex.test.marshalling/from-native-marshalled-type ~param)
    (default-marshalling/marshall-from-chrome id type param)))

(defn custom-gen-marshalling [direction id type param]
  (case direction
    :to-chrome (marshall-to-chrome id type param)
    :from-chrome (marshall-from-chrome id type param)))
