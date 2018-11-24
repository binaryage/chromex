(ns chromex.protocols.chrome-event-subscription)

(defprotocol IChromeEventSubscription
  (subscribe! [this] [this extra-args])
  (unsubscribe! [this]))
