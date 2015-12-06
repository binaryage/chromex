(ns chromex.core
  (:require [chromex.config :as config]
            [chromex.marshalling]
            [chromex.chrome-event-subscription]
            [chromex.chrome-event-channel]
            [chromex.chrome-port]
            [chromex.chrome-storage-area]))

(def get-active-config config/get-active-config)
(def set-active-config! config/set-active-config!)