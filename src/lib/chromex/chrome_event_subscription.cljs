(ns chromex.chrome-event-subscription
  (:require [chromex.protocols.chrome-event-subscription :refer [IChromeEventSubscription]]
            [chromex.protocols.chrome-event-channel :refer [IChromeEventChannel]]
            [oops.core :refer [ocall! oapply!]]))

; exception handlers, see https://www.youtube.com/watch?v=zp0OEDcAro0
(declare ^:dynamic *subscribe-called-while-subscribed*)
(declare ^:dynamic *unsubscribe-called-while-not-subscribed*)

; -- ChromeEventSubscription ------------------------------------------------------------------------------------------------

(deftype ChromeEventSubscription [chrome-event listener chan ^:mutable subscribed-count]

  IChromeEventSubscription
  (subscribe! [this]
    (chromex.protocols.chrome-event-subscription/subscribe! this nil))
  (subscribe! [this extra-args]
    {:pre [(or (nil? extra-args) (seq? extra-args))]}
    (if-not (= subscribed-count 0)
      (*subscribe-called-while-subscribed* this)
      (do
        (when (satisfies? IChromeEventChannel chan)
          (chromex.protocols.chrome-event-channel/register! chan this))
        (set! subscribed-count (inc subscribed-count))
        (oapply! chrome-event "addListener" (cons listener extra-args)))))                                                    ; see https://developer.chrome.com/extensions/events#filtered or 'Registering event listeners' at https://developer.chrome.com/extensions/webRequest
  (unsubscribe! [this]
    (if-not (= subscribed-count 1)
      (*unsubscribe-called-while-not-subscribed* this)
      (do
        (set! subscribed-count (dec subscribed-count))
        (ocall! chrome-event "removeListener" listener)
        (if (satisfies? IChromeEventChannel chan)
          (chromex.protocols.chrome-event-channel/unregister! chan this))))))

; -- constructor ------------------------------------------------------------------------------------------------------------

(defn make-chrome-event-subscription [chrome-event listener chan]
  {:pre [chrome-event listener chan]}
  (ChromeEventSubscription. chrome-event listener chan 0))

; -- default exception handlers ---------------------------------------------------------------------------------------------

(defn ^:dynamic *subscribe-called-while-subscribed* [_chrome-event-subscription]
  (assert false "ChromeEventSubscription: subscribe called while already subscribed"))

(defn ^:dynamic *unsubscribe-called-while-not-subscribed* [_chrome-event-subscription]
  (assert false "ChromeEventSubscription: unsubscribe called while not subscribed"))
