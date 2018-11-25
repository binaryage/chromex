(ns chromex.chrome-event-channel
  (:require [chromex.protocols.chrome-event-channel :refer [IChromeEventChannel]]
            [chromex.protocols.chrome-event-subscription]
            [cljs.core.async.impl.protocols :as core-async]))

(deftype ChromeEventChannel [chan ^:mutable subscriptions]

  IChromeEventChannel
  (register! [_this subscription]
    (set! subscriptions (conj subscriptions subscription)))
  (unregister! [_this subscription]
    (set! subscriptions (disj subscriptions subscription)))
  (unsubscribe-all! [_this]
    (doseq [subscription subscriptions]
      (chromex.protocols.chrome-event-subscription/unsubscribe! subscription))
    (set! subscriptions #{}))

  core-async/WritePort
  (put! [_this val handler]
    (core-async/put! chan val handler))

  core-async/ReadPort
  (take! [_this handler]
    (core-async/take! chan handler))

  core-async/Channel
  (closed? [_this]
    (core-async/closed? chan))
  (close! [this]
    (chromex.protocols.chrome-event-channel/unsubscribe-all! this)
    (core-async/close! chan)))

(defn make-chrome-event-channel [chan]
  (ChromeEventChannel. chan #{}))
