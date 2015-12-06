(ns chromex.chrome-port
  (:require [chromex.support :refer-macros [oget ocall call-hook get-hook]]
            [chromex.protocols :as protocols :refer [IChromePort IChromePortState]]
            [cljs.core.async.impl.protocols :as core-async]
            [cljs.core.async :refer [put! chan]]))

; -- ChromePort -------------------------------------------------------------------------------------------------------------
; wrapping https://developer.chrome.com/extensions/runtime#type-Port

(deftype ChromePort [config native-chrome-port channel ^:mutable connected?]

  IChromePort
  (get-native-port [_this]
    native-chrome-port)
  (get-name [_this]
    (oget native-chrome-port "name"))
  (get-sender [_this]
    (oget native-chrome-port "sender"))
  (post-message! [this message]
    (if (nil? message)
      (call-hook config :chrome-port-post-message-called-with-nil this)
      (if connected?
        (ocall native-chrome-port "postMessage" message)
        (call-hook config :chrome-port-post-message-called-on-disconnected-port this))))
  (disconnect! [this]
    (if connected?
      (ocall native-chrome-port "disconnect")
      (call-hook config :chrome-port-disconnect-called-on-disconnected-port this)))
  (on-disconnect! [this callback]
    (if connected?
      (let [on-disconnect-event (oget native-chrome-port "onDisconnect")]
        (assert on-disconnect-event)
        (ocall on-disconnect-event "addListener" callback))
      (call-hook config :chrome-port-on-disconnect-called-on-disconnected-port this)))
  (on-message! [this callback]
    (if connected?
      (let [on-message-event (oget native-chrome-port "onMessage")]
        (assert on-message-event)
        (ocall on-message-event "addListener" callback))
      (call-hook config :chrome-port-on-message-called-on-disconnected-port this)))

  IChromePortState
  (set-connected! [_this val]
    (set! connected? val))
  (put-message! [this message]
    (if connected?
      (put! channel message)
      (call-hook config :chrome-port-put-message-called-on-disconnected-port this message)))
  (close-resources! [_this]
    (core-async/close! channel))

  core-async/ReadPort
  (take! [_this handler]
    (core-async/take! channel handler))

  core-async/Channel
  (closed? [_this]
    (core-async/closed? channel))
  (close! [this]
    (protocols/disconnect! this)))

; -- constructor ------------------------------------------------------------------------------------------------------------

(defn make-chrome-port [config native-chrome-port]
  {:pre [native-chrome-port]}
  (let [channel (call-hook config :chrome-port-channel-factory)
        chrome-port (ChromePort. config native-chrome-port channel true)]
    (protocols/on-message! chrome-port (call-hook config :chrome-port-on-message-fn-factory chrome-port))
    (protocols/on-disconnect! chrome-port (call-hook config :chrome-port-on-disconnect-fn-factory chrome-port))
    chrome-port))