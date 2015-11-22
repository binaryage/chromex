(ns chromex-lib.protocols)

(defprotocol IChromeEventSubscription
  (subscribe [this])
  (unsubscribe [this]))

(defprotocol IChromeEventChannel
  (register [this subscription])
  (unregister [this subscription])
  (unsubscribe-all [this]))

(defprotocol IChromePort
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