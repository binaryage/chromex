(ns chromex.ext.login-screen-storage (:require-macros [chromex.ext.login-screen-storage :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn store-persistent-data* [config extension-ids data]
  (gen-wrap :function ::store-persistent-data config extension-ids data))

(defn retrieve-persistent-data* [config owner-id]
  (gen-wrap :function ::retrieve-persistent-data config owner-id))

(defn store-credentials* [config extension-id credentials]
  (gen-wrap :function ::store-credentials config extension-id credentials))

(defn retrieve-credentials* [config]
  (gen-wrap :function ::retrieve-credentials config))

