(ns chromex.app.app.runtime (:require-macros [chromex.app.app.runtime :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-embed-requested* [config channel & args]
  (gen-wrap :event ::on-embed-requested config channel args))
(defn on-launched* [config channel & args]
  (gen-wrap :event ::on-launched config channel args))
(defn on-restarted* [config channel & args]
  (gen-wrap :event ::on-restarted config channel args))

