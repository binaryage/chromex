(ns chromex-lib.chrome-event-subscription
  (:require [chromex-lib.protocols :as protocols :refer [IChromeEventSubscription IChromeEventChannel]]))

(deftype ChromeEventSubscription [chrome-event listener chan ^:mutable subscribed-count]

  IChromeEventSubscription
  (subscribe [this]
    (if (satisfies? IChromeEventChannel chan)
      (protocols/register chan this))
    (if-not (= subscribed-count 0)
      (throw "ChromeEventSubscription: subscribe called while subscribed"))
    (set! subscribed-count (inc subscribed-count))
    (.addListener chrome-event listener))
  (unsubscribe [this]
    (if-not (= subscribed-count 1)
      (throw "ChromeEventSubscription: unsubscribe called while not subscribed"))
    (set! subscribed-count (dec subscribed-count))
    (.removeListener chrome-event listener)
    (if (satisfies? IChromeEventChannel chan)
      (protocols/unregister chan this))))

(defn make-chrome-event-subscription [chrome-event listener chan]
  (ChromeEventSubscription. chrome-event listener chan 0))
