(ns chromex.protocols.chrome-event-channel)

(defprotocol IChromeEventChannel
  (register! [this subscription])
  (unregister! [this subscription])
  (unsubscribe-all! [this]))
