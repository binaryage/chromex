(ns chromex.ext.guest-view-internal (:require-macros [chromex.ext.guest-view-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn create-guest* [config create-params]
  (gen-wrap :function ::create-guest config create-params))

(defn destroy-guest* [config instance-id]
  (gen-wrap :function ::destroy-guest config instance-id))

(defn set-size* [config instance-id params]
  (gen-wrap :function ::set-size config instance-id params))

