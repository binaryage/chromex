(ns chromex.chrome-port
  (:require [chromex.protocols.chrome-port :refer [IChromePort]]
            [chromex.protocols.chrome-port-state :refer [IChromePortState]]
            [chromex.support :refer [call-hook get-hook]]
            [cljs.core.async :refer [chan put!]]
            [cljs.core.async.impl.protocols :as core-async]
            [oops.core :refer [ocall! oget]]))

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
        (ocall! native-chrome-port "postMessage" message)
        (call-hook config :chrome-port-post-message-called-on-disconnected-port this))))
  (disconnect! [this]
    (if connected?
      (ocall! native-chrome-port "disconnect")
      (call-hook config :chrome-port-disconnect-called-on-disconnected-port this)))
  (on-disconnect! [this callback]
    (if connected?
      (ocall! native-chrome-port "onDisconnect.addListener" callback)
      (call-hook config :chrome-port-on-disconnect-called-on-disconnected-port this)))
  (on-message! [this callback]
    (if connected?
      (ocall! native-chrome-port "onMessage.addListener" callback)
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
    (chromex.protocols.chrome-port/disconnect! this)))

; -- constructor ------------------------------------------------------------------------------------------------------------

(defn make-chrome-port [config native-chrome-port]
  {:pre [native-chrome-port]}
  (let [channel (call-hook config :chrome-port-channel-factory)
        chrome-port (ChromePort. config native-chrome-port channel true)
        on-message-fn-factory (call-hook config :chrome-port-on-message-fn-factory chrome-port)
        on-disconnect-fn-factory (call-hook config :chrome-port-on-disconnect-fn-factory chrome-port)]
    (chromex.protocols.chrome-port/on-message! chrome-port on-message-fn-factory)
    (chromex.protocols.chrome-port/on-disconnect! chrome-port on-disconnect-fn-factory)
    chrome-port))
