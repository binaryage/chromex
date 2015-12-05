(ns chromex-lib.marshalling)

(defn marshall-to-chrome [_id type param]
  (case type
    "runtime.Port" `(chromex-lib.marshalling/to-native-chrome-port ~param)
    "storage.StorageArea" `(chromex-lib.marshalling/to-native-chrome-storage-area ~param)
    param))

(defn marshall-from-chrome [_id type param]
  (case type
    "runtime.Port" `(chromex-lib.marshalling/from-native-chrome-port ~param)
    "storage.StorageArea" `(chromex-lib.marshalling/from-native-chrome-storage-area ~param)
    param))

(defn gen-marshalling [direction id type param]
  (case direction
    :to-chrome (marshall-to-chrome id type param)
    :from-chrome (marshall-from-chrome id type param)))
