(ns chromex.log-private (:require-macros [chromex.log-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn get-historical* [config filter]
  (gen-wrap :function ::get-historical config filter))

(defn start-event-recorder* [config event-type sink]
  (gen-wrap :function ::start-event-recorder config event-type sink))

(defn stop-event-recorder* [config event-type]
  (gen-wrap :function ::stop-event-recorder config event-type))

(defn dump-logs* [config]
  (gen-wrap :function ::dump-logs config))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-captured-events* [config channel]
  (gen-wrap :event ::on-captured-events config channel))

