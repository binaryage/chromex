(ns chromex.test.marshalling)

(defn to-native-marshalled-type [_config v]
  (str "to-native[" v "]"))

(defn from-native-marshalled-type [_config v]
  (str "from-native[" v "]"))