(ns chromex.ext.accessibility-features (:require-macros [chromex.ext.accessibility-features :refer [gen-wrap]])
    (:require [chromex.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn spoken-feedback* [config]
  (gen-wrap :property ::spoken-feedback config))

(defn large-cursor* [config]
  (gen-wrap :property ::large-cursor config))

(defn sticky-keys* [config]
  (gen-wrap :property ::sticky-keys config))

(defn high-contrast* [config]
  (gen-wrap :property ::high-contrast config))

(defn screen-magnifier* [config]
  (gen-wrap :property ::screen-magnifier config))

(defn autoclick* [config]
  (gen-wrap :property ::autoclick config))

(defn virtual-keyboard* [config]
  (gen-wrap :property ::virtual-keyboard config))

(defn caret-highlight* [config]
  (gen-wrap :property ::caret-highlight config))

(defn cursor-highlight* [config]
  (gen-wrap :property ::cursor-highlight config))

(defn cursor-color* [config]
  (gen-wrap :property ::cursor-color config))

(defn focus-highlight* [config]
  (gen-wrap :property ::focus-highlight config))

(defn select-to-speak* [config]
  (gen-wrap :property ::select-to-speak config))

(defn switch-access* [config]
  (gen-wrap :property ::switch-access config))

(defn animation-policy* [config]
  (gen-wrap :property ::animation-policy config))

