(ns chromex.ext.i18n (:require-macros [chromex.ext.i18n :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-accept-languages* [config]
  (gen-wrap :function ::get-accept-languages config))

(defn get-message* [config message-name substitutions options]
  (gen-wrap :function ::get-message config message-name substitutions options))

(defn get-ui-language* [config]
  (gen-wrap :function ::get-ui-language config))

(defn detect-language* [config text]
  (gen-wrap :function ::detect-language config text))

