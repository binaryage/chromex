(ns chromex.chrome-event-channel
  (:require [cljs.core.async.impl.protocols :as core-async]
            [chromex.protocols :as protocols :refer [IChromeEventChannel]]))

(deftype ChromeEventChannel [chan ^:mutable subscriptions]

  IChromeEventChannel
  (register! [_this subscription]
    (set! subscriptions (conj subscriptions subscription)))
  (unregister! [_this subscription]
    (set! subscriptions (disj subscriptions subscription)))
  (unsubscribe-all! [_this]
    (doseq [subscription subscriptions]
      (protocols/unsubscribe! subscription))
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
    (protocols/unsubscribe-all! this)
    (core-async/close! chan)))

(defn make-chrome-event-channel [chan]
  (ChromeEventChannel. chan #{}))
