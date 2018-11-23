(ns chromex.marshalling
  (:require-macros [chromex.marshalling])
  (:require [chromex.protocols :as protocols]
            [chromex.config :refer [get-active-config]]
            [chromex.chrome-port :refer [make-chrome-port]]
            [chromex.chrome-storage-area :refer [make-chrome-storage-area]]
            [chromex.chrome-content-setting :as cs]))

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

(defn from-native-chrome-content-setting [config native-chrome-content-setting]
  (when (some? native-chrome-content-setting)
    (cs/make-chrome-content-setting config native-chrome-content-setting)))

(defn to-native-chrome-content-setting [_config chrome-content-setting]
  (when (some? chrome-content-setting)
    (satisfies? cs/IChromeContentSetting chrome-content-setting)
    (cs/get-native-content-setting chrome-content-setting)))
