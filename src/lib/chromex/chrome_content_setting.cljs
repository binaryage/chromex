(ns chromex.chrome-content-setting
  (:refer-clojure :exclude [get set remove])
  (:require [chromex.support :refer [get-hook]]
            [oops.core :refer [ocall ocall!]]))

(defprotocol IChromeContentSetting
  "a wrapper for https://developer.chrome.com/extensions/contentSettings#type-ContentSetting"
  (get-native-content-setting [this])
  (get [this details])
  (set [this details])
  (clear [this details])
  (get-resource-identifiers [this]))

(deftype ChromeContentSetting [native-chrome-content-setting channel-factory callback-factory]
  IChromeContentSetting
  (get-native-content-setting [_this]
    native-chrome-content-setting)
  (get [_this details]
    (let [channel (channel-factory)]
      (ocall native-chrome-content-setting "get" details (callback-factory channel))
      channel))
  (set [_this details]
    (let [channel (channel-factory)]
      (ocall! native-chrome-content-setting "set" details (callback-factory channel))
      channel))
  (clear [_this details]
    (let [channel (channel-factory)]
      (ocall! native-chrome-content-setting "clear" details (callback-factory channel))
      channel))
  (get-resource-identifiers [_this]
    (let [channel (channel-factory)]
      (ocall native-chrome-content-setting "getResourceIdentifiers" (callback-factory channel))
      channel)))

(defn make-chrome-content-setting [config native-chrome-content-setting]
  {:pre [native-chrome-content-setting]}
  (ChromeContentSetting. native-chrome-content-setting
                         (get-hook config :chrome-content-setting-callback-channel-factory)
                         (get-hook config :chrome-content-setting-callback-fn-factory)))
