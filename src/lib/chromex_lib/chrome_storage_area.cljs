(ns chromex-lib.chrome-storage-area
  (:require [chromex-lib.support :refer-macros [ocall]]
            [chromex-lib.protocols :as protocols :refer [IChromeStorageArea]]))

; -- ChromeStorageArea ------------------------------------------------------------------------------------------------------

(deftype ChromeStorageArea [native-chrome-storage-area channel-factory callback-factory]

  IChromeStorageArea
  (get-native-storage-area [_this]
    native-chrome-storage-area)
  (get [this]
    (protocols/get this nil))
  (get [_this keys]
    (let [channel (channel-factory)]
      (ocall native-chrome-storage-area "get" keys (callback-factory channel))
      channel))
  (get-bytes-in-use [this]
    (protocols/get-bytes-in-use this nil))
  (get-bytes-in-use [_this keys]
    (let [channel (channel-factory)]
      (ocall native-chrome-storage-area "getBytesInUse" keys (callback-factory channel))
      channel))
  (set [_this items]
    (let [channel (channel-factory)]
      (ocall native-chrome-storage-area "set" items (callback-factory channel))
      channel))
  (remove [_this keys]
    (let [channel (channel-factory)]
      (ocall native-chrome-storage-area "remove" keys (callback-factory channel))
      channel))
  (clear [_this]
    (let [channel (channel-factory)]
      (ocall native-chrome-storage-area "clear" (callback-factory channel))
      channel)))

; -- constructor ------------------------------------------------------------------------------------------------------------

(defn make-chrome-storage-area [native-chrome-storage-area config]
  {:pre [native-chrome-storage-area
         (fn? (:chrome-storage-area-callback-channel-factory config))
         (fn? (:chrome-storage-area-callback-fn-factory config))]}
  (let [channel-factory (:chrome-storage-area-callback-channel-factory config)
        callback-factory (:chrome-storage-area-callback-fn-factory config)]
    (ChromeStorageArea. native-chrome-storage-area
                        (partial channel-factory config)
                        (partial callback-factory config))))