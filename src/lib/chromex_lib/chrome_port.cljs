(ns chromex-lib.chrome-port
  (:require [chromex-lib.support :refer-macros [oget ocall]]
            [chromex-lib.protocols :as protocols :refer [IChromePort IChromePortState]]
            [cljs.core.async.impl.protocols :as core-async]
            [cljs.core.async :refer [put! chan]]))

; wrapping https://developer.chrome.com/extensions/runtime#type-Port

(declare *on-message-fn-factory*)
(declare *on-disconnect-fn-factory*)

; exception handlers, see https://www.youtube.com/watch?v=zp0OEDcAro0
(declare *disconnect-called-on-disconnected-port*)
(declare *post-message-called-on-disconnected-port*)
(declare *on-disconnect-called-on-disconnected-port*)
(declare *on-message-called-on-disconnected-port*)
(declare *post-message-called-with-nil*)

; -- ChromePort -------------------------------------------------------------------------------------------------------------

(deftype ChromePort [native-chrome-port channel ^:mutable connected?]

  IChromePort
  (get-native-port [_this]
    native-chrome-port)
  (get-name [_this]
    (oget native-chrome-port "name"))
  (get-sender [_this]
    (oget native-chrome-port "sender"))
  (post-message! [this message]
    (if (nil? message)
      (*post-message-called-with-nil* this)
      (if connected?
        (ocall native-chrome-port "postMessage" message)
        (*post-message-called-on-disconnected-port* this))))
  (disconnect! [this]
    (if connected?
      (ocall native-chrome-port "disconnect")
      (*disconnect-called-on-disconnected-port* this)))
  (on-disconnect! [this callback]
    (if connected?
      (let [on-disconnect-event (oget native-chrome-port "onDisconnect")]
        (assert on-disconnect-event)
        (ocall on-disconnect-event "addListener" callback))
      (*on-disconnect-called-on-disconnected-port* this)))
  (on-message! [this callback]
    (if connected?
      (let [on-message-event (oget native-chrome-port "onMessage")]
        (assert on-message-event)
        (ocall on-message-event "addListener" callback))
      (*on-message-called-on-disconnected-port* this)))

  IChromePortState
  (set-connected! [_this val]
    (set! connected? val))
  (put-message! [_this message]
    (put! channel message))
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

(defn make-chrome-port
  ([native-chrome-port] (make-chrome-port native-chrome-port (chan)))
  ([native-chrome-port channel]
   (let [chrome-port (ChromePort. native-chrome-port channel true)]
     (protocols/on-message! chrome-port (*on-message-fn-factory* chrome-port))
     (protocols/on-disconnect! chrome-port (*on-disconnect-fn-factory* chrome-port))
     chrome-port)))

; -- default factories ------------------------------------------------------------------------------------------------------

(defn ^:dynamic *on-message-fn-factory* [chrome-port]
  (fn [message]
    (protocols/put-message! chrome-port message)
    nil))

(defn ^:dynamic *on-disconnect-fn-factory* [chrome-port]
  (fn []
    (protocols/close-resources! chrome-port)
    (protocols/set-connected! chrome-port false)
    nil))

; -- default exception handlers ---------------------------------------------------------------------------------------------

(defn ^:dynamic *disconnect-called-on-disconnected-port* [_chrome-port]
  (assert false "ChromePort: disconnect called on already disconnected port")
  nil)

(defn ^:dynamic *post-message-called-on-disconnected-port* [_chrome-port]
  (assert false "ChromePort: post-message called on already disconnected port")
  nil)

(defn ^:dynamic *on-disconnect-called-on-disconnected-port* [_chrome-port]
  (assert false "ChromePort: on-disconnect called on already disconnected port")
  nil)

(defn ^:dynamic *on-message-called-on-disconnected-port* [_chrome-port]
  (assert false "ChromePort: on-message called on already disconnected port")
  nil)

(defn ^:dynamic *post-message-called-with-nil* [_chrome-port]
  (assert false "ChromePort: post-message called with nil message. Nil cannot be delivered via a core.async channel.")
  nil)