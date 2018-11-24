(ns chromex.protocols.chrome-storage-area
  (:refer-clojure :exclude [get set remove]))

(defprotocol IChromeStorageArea
  "a wrapper for https://developer.chrome.com/extensions/storage#type-StorageArea"
  (get-native-storage-area [this])
  (get [this] [this keys])
  (get-bytes-in-use [this] [this keys])
  (set [this items])
  (remove [this keys])
  (clear [this]))
