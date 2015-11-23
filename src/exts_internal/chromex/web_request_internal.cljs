(ns chromex.web-request-internal (:require-macros [chromex.web-request-internal :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn add-event-listener* [config filter extra-info-spec event-name sub-event-name web-view-instance-id]
  (gen-wrap :function ::add-event-listener config filter extra-info-spec event-name sub-event-name web-view-instance-id))

(defn event-handled* [config event-name sub-event-name request-id response]
  (gen-wrap :function ::event-handled config event-name sub-event-name request-id response))

