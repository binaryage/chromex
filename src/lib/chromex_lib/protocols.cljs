(ns chromex-lib.protocols)

(defprotocol IChromeEventSubscription
  (subscribe [this])
  (unsubscribe [this]))

(defprotocol IChromeEventChannel
  (register [this subscription])
  (unregister [this subscription])
  (unsubscribe-all [this]))
