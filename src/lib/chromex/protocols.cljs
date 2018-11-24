(ns chromex.protocols
  "DON'T USE THIS NAMESPACE. Instead use specific protocol under chromex.protocols.<protocol>.
  See https://github.com/binaryage/chromex/issues/17."
  (:refer-clojure :exclude [get set remove])
  (:require [chromex.protocols.chrome-event-subscription]
            [chromex.protocols.chrome-event-channel]
            [chromex.protocols.chrome-port]
            [chromex.protocols.chrome-port-state]
            [chromex.protocols.chrome-storage-area]))

; I had to move all protocols form here under separate namespaces, sorry for that :-/

; ---------------------------------------------------------------------------------------------------------------------------
; forward old protocol IChromeEventSubscription usage

(defn ^:deprecated subscribe!
  ([this]
   (chromex.protocols.chrome-event-subscription/subscribe! this))
  ([this extra-args]
   (chromex.protocols.chrome-event-subscription/subscribe! this extra-args)))

(defn ^:deprecated unsubscribe! [this]
  (chromex.protocols.chrome-event-subscription/unsubscribe! this))

; ---------------------------------------------------------------------------------------------------------------------------
; forward old protocol IChromeEventChannel usage

(defn ^:deprecated register! [this subscription]
  (chromex.protocols.chrome-event-channel/register! this subscription))
(defn ^:deprecated unregister! [this subscription]
  (chromex.protocols.chrome-event-channel/unregister! this subscription))
(defn ^:deprecated unsubscribe-all! [this]
  (chromex.protocols.chrome-event-channel/unsubscribe-all! this))

; ---------------------------------------------------------------------------------------------------------------------------
; forward old protocol IChromePort usage

(defn ^:deprecated get-native-port [this]
  (chromex.protocols.chrome-port/get-native-port this))

(defn ^:deprecated get-name [this]
  (chromex.protocols.chrome-port/get-name this))

(defn ^:deprecated get-sender [this]
  (chromex.protocols.chrome-port/get-sender this))

(defn ^:deprecated post-message! [this message]
  (chromex.protocols.chrome-port/post-message! this message))

(defn ^:deprecated disconnect! [this]
  (chromex.protocols.chrome-port/disconnect! this))

(defn ^:deprecated on-disconnect! [this callback]
  (chromex.protocols.chrome-port/on-disconnect! this callback))

(defn ^:deprecated on-message! [this callback]
  (chromex.protocols.chrome-port/on-message! this callback))

; ---------------------------------------------------------------------------------------------------------------------------
; forward old protocol IChromePortState usage

(defn ^:deprecated set-connected! [this val]
  (chromex.protocols.chrome-port-state/set-connected! this val))

(defn ^:deprecated put-message! [this message]
  (chromex.protocols.chrome-port-state/put-message! this message))

(defn ^:deprecated close-resources! [this]
  (chromex.protocols.chrome-port-state/close-resources! this))

; ---------------------------------------------------------------------------------------------------------------------------
; forward old protocol IChromeStorageArea usage

(defn ^:deprecated get-native-storage-area [this]
  (chromex.protocols.chrome-storage-area/get-native-storage-area this))

(defn ^:deprecated get [this] [this keys]
  (chromex.protocols.chrome-storage-area/get this keys))

(defn ^:deprecated get-bytes-in-use
  ([this]
   (chromex.protocols.chrome-storage-area/get-bytes-in-use this))
  ([this keys]
   (chromex.protocols.chrome-storage-area/get-bytes-in-use this keys)))

(defn ^:deprecated set [this items]
  (chromex.protocols.chrome-storage-area/set this items))

(defn ^:deprecated remove [this keys]
  (chromex.protocols.chrome-storage-area/remove this keys))

(defn ^:deprecated clear [this]
  (chromex.protocols.chrome-storage-area/clear this))
