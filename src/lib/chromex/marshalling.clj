(ns chromex.marshalling)

(defn marshall-to-chrome [config _id type param]
  (case type
    "runtime.Port" `(chromex.marshalling/to-native-chrome-port ~config ~param)
    "storage.StorageArea" `(chromex.marshalling/to-native-chrome-storage-area ~config ~param)
    "contentSettings.ContentSetting" `(chromex.marshalling/to-native-chrome-content-setting ~config ~param)
    param))

(defn marshall-from-chrome [config _id type param]
  (case type
    "runtime.Port" `(chromex.marshalling/from-native-chrome-port ~config ~param)
    "storage.StorageArea" `(chromex.marshalling/from-native-chrome-storage-area ~config ~param)
    "contentSettings.ContentSetting" `(chromex.marshalling/from-native-chrome-content-setting ~config ~param)
    param))

(defn gen-marshalling [config direction id type param]
  (case direction
    :to-chrome (marshall-to-chrome config id type param)
    :from-chrome (marshall-from-chrome config id type param)))
