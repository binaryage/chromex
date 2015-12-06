(ns chromex.app.accessibility-private (:require-macros [chromex.app.accessibility-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-native-accessibility-enabled* [config enabled]
  (gen-wrap :function ::set-native-accessibility-enabled config enabled))

(defn set-focus-ring* [config rects]
  (gen-wrap :function ::set-focus-ring config rects))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-introduce-chrome-vox* [config channel & args]
  (gen-wrap :event ::on-introduce-chrome-vox config channel args))

