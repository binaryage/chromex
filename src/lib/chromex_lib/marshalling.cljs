(ns chromex-lib.marshalling
  (:require [chromex-lib.protocols :as protocols]
            [chromex-lib.config :refer [get-active-config]]
            [chromex-lib.chrome-port :refer [make-chrome-port]]
            [chromex-lib.chrome-storage-area :refer [make-chrome-storage-area]]))

(defn from-native-chrome-port [native-chrome-port]
  (make-chrome-port native-chrome-port (get-active-config)))                                                                  ; TODO: config should be provided by marshalling code

(defn to-native-chrome-port [chrome-port]
  {:pre [(satisfies? protocols/IChromePort chrome-port)]}
  (protocols/get-native-port chrome-port))

(defn from-native-chrome-storage-area [native-chrome-storage-area]
  (make-chrome-storage-area native-chrome-storage-area (get-active-config)))                                                  ; TODO: config should be provided by marshalling code

(defn to-native-chrome-storage-area [chrome-storage-area]
  {:pre [(satisfies? protocols/IChromeStorageArea chrome-storage-area)]}
  (protocols/get-native-storage-area chrome-storage-area))
