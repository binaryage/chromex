(ns chromex.core
  (:require [chromex.chrome-event-channel]
            [chromex.chrome-event-subscription]
            [chromex.chrome-port]
            [chromex.chrome-storage-area]
            [chromex.config :as config]
            [chromex.marshalling]
            [chromex.version]))

(def get-active-config config/get-active-config)
(def set-active-config! config/set-active-config!)
