(ns chromex-lib.chrome-event-subscription
  (:require [chromex-lib.protocols :as protocols :refer [IChromeEventSubscription IChromeEventChannel]]
            [chromex-lib.support :refer-macros [oget ocall]]))

; exception handlers, see https://www.youtube.com/watch?v=zp0OEDcAro0
(declare *subscribe-called-while-subscribed*)
(declare *unsubscribe-called-while-not-subscribed*)

; -- ChromeEventSubscription ----------------------------------------------------------------------------------------

(deftype ChromeEventSubscription [chrome-event listener chan ^:mutable subscribed-count]

  IChromeEventSubscription
  (subscribe! [this]
    (if-not (= subscribed-count 0)
      (*subscribe-called-while-subscribed* this)
      (do
        (if (satisfies? IChromeEventChannel chan)
          (protocols/register! chan this))
        (set! subscribed-count (inc subscribed-count))
        (ocall chrome-event "addListener" listener))))
  (unsubscribe! [this]
    (if-not (= subscribed-count 1)
      (*unsubscribe-called-while-not-subscribed* this)
      (do
        (set! subscribed-count (dec subscribed-count))
        (ocall chrome-event "removeListener" listener)
        (if (satisfies? IChromeEventChannel chan)
          (protocols/unregister! chan this))))))

; -- constructor ----------------------------------------------------------------------------------------------------

(defn make-chrome-event-subscription [chrome-event listener chan]
  (ChromeEventSubscription. chrome-event listener chan 0))

; -- default exception handlers -------------------------------------------------------------------------------------

(defn ^:dynamic *subscribe-called-while-subscribed* [_chrome-event-subscription]
  (assert false "ChromeEventSubscription: subscribe called while already subscribed")
  nil)

(defn ^:dynamic *unsubscribe-called-while-not-subscribed* [_chrome-event-subscription]
  (assert false "ChromeEventSubscription: unsubscribe called while not subscribed")
  nil)