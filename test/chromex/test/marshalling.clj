(ns chromex.test.marshalling)

(defn marshall-to-chrome [_id type param]
  (case type
    "marshalled-type" `(chromex.test.marshalling/to-native-marshalled-type ~param)
    param))

(defn marshall-from-chrome [_id type param]
  (case type
    "marshalled-type" `(chromex.test.marshalling/from-native-marshalled-type ~param)
    param))

(defn custom-gen-marshalling [direction id type param]
  (case direction
    :to-chrome (marshall-to-chrome id type param)
    :from-chrome (marshall-from-chrome id type param)))
