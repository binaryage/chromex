(ns chromex.test.marshalling)

(defn marshall-in [_id type param]
  (case type
    "mock-param1" `(chromex.test.marshalling/to-native-mock-param1 ~param)
    param))

(defn marshall-out [_id type param]
  (case type
    "mock-callback-param1" `(chromex.test.marshalling/from-native-mock-callback-param1 ~param)
    "mock-return" `(chromex.test.marshalling/from-native-mock-return ~param)
    "prop1-type" `(chromex.test.marshalling/from-native-prop1-type ~param)
    "event-param-type1" `(chromex.test.marshalling/from-native-event-param1-type ~param)
    param))

(defn custom-gen-marshalling [direction id type param]
  (case direction
    :in (marshall-in id type param)
    :out (marshall-out id type param)))
