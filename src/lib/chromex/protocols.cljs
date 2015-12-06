(ns chromex.protocols
  (:refer-clojure :exclude [get set remove]))

(defprotocol IChromeEventSubscription
  (subscribe! [this] [this extra-args])
  (unsubscribe! [this]))

(defprotocol IChromeEventChannel
  (register! [this subscription])
  (unregister! [this subscription])
  (unsubscribe-all! [this]))

(defprotocol IChromePort
  "a wrapper for https://developer.chrome.com/extensions/runtime#type-Port"
  (get-native-port [this])
  (get-name [this])
  (get-sender [this])
  (post-message! [this message])
  (disconnect! [this])
  (on-disconnect! [this callback])
  (on-message! [this callback]))

(defprotocol IChromePortState
  (set-connected! [this val])
  (put-message! [this message])
  (close-resources! [this]))

(defprotocol IChromeStorageArea
  "a wrapper for https://developer.chrome.com/extensions/storage#type-StorageArea"
  (get-native-storage-area [this])
  (get [this] [this keys])
  (get-bytes-in-use [this] [this keys])
  (set [this items])
  (remove [this keys])
  (clear [this]))
