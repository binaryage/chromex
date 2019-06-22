(ns chromex.ext.accessibility-private (:require-macros [chromex.ext.accessibility-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-battery-description* [config]
  (gen-wrap :function ::get-battery-description config))

(defn set-native-accessibility-enabled* [config enabled]
  (gen-wrap :function ::set-native-accessibility-enabled config enabled))

(defn set-focus-rings* [config focus-rings]
  (gen-wrap :function ::set-focus-rings config focus-rings))

(defn set-highlights* [config rects color]
  (gen-wrap :function ::set-highlights config rects color))

(defn set-keyboard-listener* [config enabled capture]
  (gen-wrap :function ::set-keyboard-listener config enabled capture))

(defn darken-screen* [config enabled]
  (gen-wrap :function ::darken-screen config enabled))

(defn set-switch-access-keys* [config key-codes]
  (gen-wrap :function ::set-switch-access-keys config key-codes))

(defn set-switch-access-menu-state* [config show element-bounds item-count]
  (gen-wrap :function ::set-switch-access-menu-state config show element-bounds item-count))

(defn forward-key-events-to-switch-access* [config should-forward]
  (gen-wrap :function ::forward-key-events-to-switch-access config should-forward))

(defn set-native-chrome-vox-arc-support-for-current-app* [config enabled]
  (gen-wrap :function ::set-native-chrome-vox-arc-support-for-current-app config enabled))

(defn send-synthetic-key-event* [config key-event]
  (gen-wrap :function ::send-synthetic-key-event config key-event))

(defn enable-chrome-vox-mouse-events* [config enabled]
  (gen-wrap :function ::enable-chrome-vox-mouse-events config enabled))

(defn send-synthetic-mouse-event* [config mouse-event]
  (gen-wrap :function ::send-synthetic-mouse-event config mouse-event))

(defn on-select-to-speak-state-changed* [config state]
  (gen-wrap :function ::on-select-to-speak-state-changed config state))

(defn toggle-dictation* [config]
  (gen-wrap :function ::toggle-dictation config))

(defn set-virtual-keyboard-visible* [config is-visible]
  (gen-wrap :function ::set-virtual-keyboard-visible config is-visible))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-introduce-chrome-vox* [config channel & args]
  (gen-wrap :event ::on-introduce-chrome-vox config channel args))

(defn on-accessibility-gesture* [config channel & args]
  (gen-wrap :event ::on-accessibility-gesture config channel args))

(defn on-two-finger-touch-start* [config channel & args]
  (gen-wrap :event ::on-two-finger-touch-start config channel args))

(defn on-two-finger-touch-stop* [config channel & args]
  (gen-wrap :event ::on-two-finger-touch-stop config channel args))

(defn on-select-to-speak-state-change-requested* [config channel & args]
  (gen-wrap :event ::on-select-to-speak-state-change-requested config channel args))

(defn on-switch-access-command* [config channel & args]
  (gen-wrap :event ::on-switch-access-command config channel args))

(defn on-announce-for-accessibility* [config channel & args]
  (gen-wrap :event ::on-announce-for-accessibility config channel args))

