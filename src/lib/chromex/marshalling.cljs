(ns chromex.marshalling
  (:require-macros [chromex.marshalling])
  (:require [chromex.protocols :as protocols]
            [chromex.config :refer [get-active-config]]
            [chromex.chrome-port :refer [make-chrome-port]]
            [chromex.chrome-storage-area :refer [make-chrome-storage-area]]))

(defn from-native-chrome-port [config native-chrome-port]
  (when (some? native-chrome-port)
    (make-chrome-port config native-chrome-port)))

(defn to-native-chrome-port [_config chrome-port]
  (when (some? chrome-port)
    (assert (satisfies? protocols/IChromePort chrome-port))
    (protocols/get-native-port chrome-port)))

(defn from-native-chrome-storage-area [config native-chrome-storage-area]
  (when (some? native-chrome-storage-area)
    (make-chrome-storage-area config native-chrome-storage-area)))

(defn to-native-chrome-storage-area [_config chrome-storage-area]
  (when (some? chrome-storage-area)
    (satisfies? protocols/IChromeStorageArea chrome-storage-area)
    (protocols/get-native-storage-area chrome-storage-area)))
