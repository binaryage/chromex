(ns chromex.test.marshalling)

(defn to-native-marshalled-type [v]
  (str "to-native[" v "]"))

(defn from-native-marshalled-type [v]
  (str "from-native[" v "]"))