(ns chromex.extension (:require-macros [chromex.extension :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn last-error* [config]
  (gen-wrap :property ::last-error config))

(defn in-incognito-context* [config]
  (gen-wrap :property ::in-incognito-context config))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn send-request* [config extension-id request]
  (gen-wrap :function ::send-request config extension-id request))

(defn get-url* [config path]
  (gen-wrap :function ::get-url config path))

(defn get-views* [config fetch-properties]
  (gen-wrap :function ::get-views config fetch-properties))

(defn get-background-page* [config]
  (gen-wrap :function ::get-background-page config))

(defn get-extension-tabs* [config window-id]
  (gen-wrap :function ::get-extension-tabs config window-id))

(defn is-allowed-incognito-access* [config]
  (gen-wrap :function ::is-allowed-incognito-access config))

(defn is-allowed-file-scheme-access* [config]
  (gen-wrap :function ::is-allowed-file-scheme-access config))

(defn set-update-url-data* [config data]
  (gen-wrap :function ::set-update-url-data config data))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-request* [config channel]
  (gen-wrap :event ::on-request config channel))

(defn on-request-external* [config channel]
  (gen-wrap :event ::on-request-external config channel))

