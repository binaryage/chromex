(ns chromex.config
  (:require [chromex.defaults :refer [default-config]]))

; -- active config ----------------------------------------------------------------------------------------------------------

(def ^:dynamic *active-config* default-config)

(defn set-active-config! [config]
  (set! *active-config* config))

(defn get-active-config []
  *active-config*)