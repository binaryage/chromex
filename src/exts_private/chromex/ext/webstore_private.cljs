(ns chromex.ext.webstore-private (:require-macros [chromex.ext.webstore-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn install* [config expected-id]
  (gen-wrap :function ::install config expected-id))

(defn begin-install-with-manifest3* [config details]
  (gen-wrap :function ::begin-install-with-manifest3 config details))

(defn complete-install* [config expected-id]
  (gen-wrap :function ::complete-install config expected-id))

(defn enable-app-launcher* [config]
  (gen-wrap :function ::enable-app-launcher config))

(defn get-browser-login* [config]
  (gen-wrap :function ::get-browser-login config))

(defn get-store-login* [config]
  (gen-wrap :function ::get-store-login config))

(defn set-store-login* [config login]
  (gen-wrap :function ::set-store-login config login))

(defn get-web-gl-status* [config]
  (gen-wrap :function ::get-web-gl-status config))

(defn get-is-launcher-enabled* [config]
  (gen-wrap :function ::get-is-launcher-enabled config))

(defn is-in-incognito-mode* [config]
  (gen-wrap :function ::is-in-incognito-mode config))

(defn get-ephemeral-apps-enabled* [config]
  (gen-wrap :function ::get-ephemeral-apps-enabled config))

(defn launch-ephemeral-app* [config id]
  (gen-wrap :function ::launch-ephemeral-app config id))

(defn is-pending-custodian-approval* [config id]
  (gen-wrap :function ::is-pending-custodian-approval config id))

(defn get-referrer-chain* [config]
  (gen-wrap :function ::get-referrer-chain config))

(defn get-extension-status* [config id]
  (gen-wrap :function ::get-extension-status config id))

(defn request-extension* [config id]
  (gen-wrap :function ::request-extension config id))

