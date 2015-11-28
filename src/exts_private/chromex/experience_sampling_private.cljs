(ns chromex.experience-sampling-private (:require-macros [chromex.experience-sampling-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-browser-info* [config]
  (gen-wrap :function ::get-browser-info config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-displayed* [config channel & args]
  (gen-wrap :event ::on-displayed config channel args))
(defn on-decision* [config channel & args]
  (gen-wrap :event ::on-decision config channel args))

