(ns chromex.chrome-storage-area
  (:require [chromex.protocols.chrome-storage-area :refer [IChromeStorageArea]]
            [chromex.support :refer [get-hook]]
            [oops.core :refer [ocall ocall!]]))

; -- ChromeStorageArea ------------------------------------------------------------------------------------------------------
; wrapping https://developer.chrome.com/extensions/storage#type-StorageArea

(deftype ChromeStorageArea [native-chrome-storage-area channel-factory callback-factory]

  IChromeStorageArea
  (get-native-storage-area [_this]
    native-chrome-storage-area)
  (get [this]
    (chromex.protocols.chrome-storage-area/get this nil))
  (get [_this keys]
    (let [channel (channel-factory)]
      (ocall native-chrome-storage-area "get" keys (callback-factory channel))
      channel))
  (get-bytes-in-use [this]
    (chromex.protocols.chrome-storage-area/get-bytes-in-use this nil))
  (get-bytes-in-use [_this keys]
    (let [channel (channel-factory)]
      (ocall native-chrome-storage-area "getBytesInUse" keys (callback-factory channel))
      channel))
  (set [_this items]
    (let [channel (channel-factory)]
      (ocall! native-chrome-storage-area "set" items (callback-factory channel))
      channel))
  (remove [_this keys]
    (let [channel (channel-factory)]
      (ocall! native-chrome-storage-area "remove" keys (callback-factory channel))
      channel))
  (clear [_this]
    (let [channel (channel-factory)]
      (ocall! native-chrome-storage-area "clear" (callback-factory channel))
      channel)))

; -- constructor ------------------------------------------------------------------------------------------------------------

(defn make-chrome-storage-area [config native-chrome-storage-area]
  {:pre [native-chrome-storage-area]}
  (ChromeStorageArea. native-chrome-storage-area
                      (get-hook config :chrome-storage-area-callback-channel-factory)
                      (get-hook config :chrome-storage-area-callback-fn-factory)))
