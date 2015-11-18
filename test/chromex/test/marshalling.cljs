(ns chromex.test.marshalling)

(defn to-native-mock-param1 [v]
  (str v "!!!"))

(defn from-native-mock-callback-param1 [v]
  (str "<" v ">"))

(defn from-native-mock-return [v]
  (str "[" v "]"))

(defn from-native-prop1-type [v]
  (str "@(" v ")"))

(defn from-native-event-param1-type [v]
  (str "~" v))