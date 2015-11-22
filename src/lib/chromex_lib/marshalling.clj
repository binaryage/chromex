(ns chromex-lib.marshalling)

(defn marshall-to-chrome [_id type param]
  (case type
    "Port" `(chromex_lib.marshalling/to-native-chrome-port ~param)
    param))

(defn marshall-from-chrome [_id type param]
  (case type
    "Port" `(chromex_lib.marshalling/from-native-chrome-port ~param)
    param))

(defn gen-marshalling [direction id type param]
  (case direction
    :to-chrome (marshall-to-chrome id type param)
    :from-chrome (marshall-from-chrome id type param)))
