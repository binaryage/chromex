(ns chromex.app.accessibility-private (:require-macros [chromex.app.accessibility-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-native-accessibility-enabled* [config enabled]
  (gen-wrap :function ::set-native-accessibility-enabled config enabled))

(defn set-focus-ring* [config rects color]
  (gen-wrap :function ::set-focus-ring config rects color))

(defn set-highlights* [config rects color]
  (gen-wrap :function ::set-highlights config rects color))

(defn set-keyboard-listener* [config enabled capture]
  (gen-wrap :function ::set-keyboard-listener config enabled capture))

(defn darken-screen* [config enabled]
  (gen-wrap :function ::darken-screen config enabled))

(defn set-switch-access-keys* [config key-codes]
  (gen-wrap :function ::set-switch-access-keys config key-codes))

(defn set-native-chrome-vox-arc-support-for-current-app* [config enabled]
  (gen-wrap :function ::set-native-chrome-vox-arc-support-for-current-app config enabled))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-introduce-chrome-vox* [config channel & args]
  (gen-wrap :event ::on-introduce-chrome-vox config channel args))

(defn on-accessibility-gesture* [config channel & args]
  (gen-wrap :event ::on-accessibility-gesture config channel args))

(defn on-two-finger-touch-start* [config channel & args]
  (gen-wrap :event ::on-two-finger-touch-start config channel args))

(defn on-two-finger-touch-stop* [config channel & args]
  (gen-wrap :event ::on-two-finger-touch-stop config channel args))

