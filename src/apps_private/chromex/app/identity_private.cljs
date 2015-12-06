(ns chromex.app.identity-private (:require-macros [chromex.app.identity-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-web-flow-request* [config channel & args]
  (gen-wrap :event ::on-web-flow-request config channel args))

