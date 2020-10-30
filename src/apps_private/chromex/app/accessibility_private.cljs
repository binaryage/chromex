(ns chromex.app.accessibility-private (:require-macros [chromex.app.accessibility-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-display-name-for-locale* [config locale-code-to-translate display-locale-code]
  (gen-wrap :function ::get-display-name-for-locale config locale-code-to-translate display-locale-code))

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

(defn forward-key-events-to-switch-access* [config should-forward]
  (gen-wrap :function ::forward-key-events-to-switch-access config should-forward))

(defn update-switch-access-bubble* [config bubble show anchor actions]
  (gen-wrap :function ::update-switch-access-bubble config bubble show anchor actions))

(defn activate-point-scan* [config]
  (gen-wrap :function ::activate-point-scan config))

(defn set-native-chrome-vox-arc-support-for-current-app* [config enabled]
  (gen-wrap :function ::set-native-chrome-vox-arc-support-for-current-app config enabled))

(defn send-synthetic-key-event* [config key-event]
  (gen-wrap :function ::send-synthetic-key-event config key-event))

(defn enable-chrome-vox-mouse-events* [config enabled]
  (gen-wrap :function ::enable-chrome-vox-mouse-events config enabled))

(defn send-synthetic-mouse-event* [config mouse-event]
  (gen-wrap :function ::send-synthetic-mouse-event config mouse-event))

(defn set-select-to-speak-state* [config state]
  (gen-wrap :function ::set-select-to-speak-state config state))

(defn handle-scrollable-bounds-for-point-found* [config rect]
  (gen-wrap :function ::handle-scrollable-bounds-for-point-found config rect))

(defn move-magnifier-to-rect* [config rect]
  (gen-wrap :function ::move-magnifier-to-rect config rect))

(defn toggle-dictation* [config]
  (gen-wrap :function ::toggle-dictation config))

(defn set-virtual-keyboard-visible* [config is-visible]
  (gen-wrap :function ::set-virtual-keyboard-visible config is-visible))

(defn open-settings-subpage* [config subpage]
  (gen-wrap :function ::open-settings-subpage config subpage))

(defn perform-accelerator-action* [config accelerator-action]
  (gen-wrap :function ::perform-accelerator-action config accelerator-action))

(defn is-feature-enabled* [config feature]
  (gen-wrap :function ::is-feature-enabled config feature))

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

(defn on-scrollable-bounds-for-point-requested* [config channel & args]
  (gen-wrap :event ::on-scrollable-bounds-for-point-requested config channel args))

(defn on-magnifier-bounds-changed* [config channel & args]
  (gen-wrap :event ::on-magnifier-bounds-changed config channel args))

(defn on-custom-spoken-feedback-toggled* [config channel & args]
  (gen-wrap :event ::on-custom-spoken-feedback-toggled config channel args))

