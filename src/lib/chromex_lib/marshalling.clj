(ns chromex-lib.marshalling)

(defn marshall-to-chrome [config _id type param]
  (case type
    "runtime.Port" `(chromex-lib.marshalling/to-native-chrome-port ~config ~param)
    "storage.StorageArea" `(chromex-lib.marshalling/to-native-chrome-storage-area ~config ~param)
    param))

(defn marshall-from-chrome [config _id type param]
  (case type
    "runtime.Port" `(chromex-lib.marshalling/from-native-chrome-port ~config ~param)
    "storage.StorageArea" `(chromex-lib.marshalling/from-native-chrome-storage-area ~config ~param)
    param))

(defn gen-marshalling [config direction id type param]
  (case direction
    :to-chrome (marshall-to-chrome config id type param)
    :from-chrome (marshall-from-chrome config id type param)))
