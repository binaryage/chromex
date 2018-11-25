(ns chromex.protocols.chrome-content-setting
  (:refer-clojure :exclude [get set]))

(defprotocol IChromeContentSetting
  "a wrapper for https://developer.chrome.com/extensions/contentSettings#type-ContentSetting"
  (get-native-content-setting [this])
  (get [this details])
  (set [this details])
  (clear [this details])
  (get-resource-identifiers [this]))
