(ns chromex.app.identity-private (:require-macros [chromex.app.identity-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-consent-result* [config result window-id]
  (gen-wrap :function ::set-consent-result config result window-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-web-flow-request* [config channel & args]
  (gen-wrap :event ::on-web-flow-request config channel args))

