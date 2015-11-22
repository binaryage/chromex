(ns chromex-lib.marshalling)

(defn marshall-in [_id type param]
  (case type
    "Port" `(chromex_lib.marshalling/to-native-chrome-port ~param)
    param))

(defn marshall-out [_id type param]
  (case type
    "Port" `(chromex_lib.marshalling/from-native-chrome-port ~param)
    param))

(defn gen-marshalling [direction id type param]
  (case direction
    :in (marshall-in id type param)
    :out (marshall-out id type param)))
