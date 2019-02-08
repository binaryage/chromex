(ns chromex.ext.autotest-private (:require-macros [chromex.ext.autotest-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn logout* [config]
  (gen-wrap :function ::logout config))

(defn restart* [config]
  (gen-wrap :function ::restart config))

(defn shutdown* [config force]
  (gen-wrap :function ::shutdown config force))

(defn login-status* [config]
  (gen-wrap :function ::login-status config))

(defn lock-screen* [config]
  (gen-wrap :function ::lock-screen config))

(defn get-extensions-info* [config]
  (gen-wrap :function ::get-extensions-info config))

(defn simulate-asan-memory-bug* [config]
  (gen-wrap :function ::simulate-asan-memory-bug config))

(defn set-touchpad-sensitivity* [config value]
  (gen-wrap :function ::set-touchpad-sensitivity config value))

(defn set-tap-to-click* [config enabled]
  (gen-wrap :function ::set-tap-to-click config enabled))

(defn set-three-finger-click* [config enabled]
  (gen-wrap :function ::set-three-finger-click config enabled))

(defn set-tap-dragging* [config enabled]
  (gen-wrap :function ::set-tap-dragging config enabled))

(defn set-natural-scroll* [config enabled]
  (gen-wrap :function ::set-natural-scroll config enabled))

(defn set-mouse-sensitivity* [config value]
  (gen-wrap :function ::set-mouse-sensitivity config value))

(defn set-primary-button-right* [config right]
  (gen-wrap :function ::set-primary-button-right config right))

(defn set-mouse-reverse-scroll* [config enabled]
  (gen-wrap :function ::set-mouse-reverse-scroll config enabled))

(defn get-visible-notifications* [config]
  (gen-wrap :function ::get-visible-notifications config))

(defn get-play-store-state* [config]
  (gen-wrap :function ::get-play-store-state config))

(defn get-printer-list* [config]
  (gen-wrap :function ::get-printer-list config))

(defn is-app-shown* [config app-id]
  (gen-wrap :function ::is-app-shown config app-id))

(defn is-arc-provisioned* [config]
  (gen-wrap :function ::is-arc-provisioned config))

(defn get-arc-app* [config app-id]
  (gen-wrap :function ::get-arc-app config app-id))

(defn get-arc-package* [config package-name]
  (gen-wrap :function ::get-arc-package config package-name))

(defn launch-arc-app* [config app-id intent]
  (gen-wrap :function ::launch-arc-app config app-id intent))

(defn launch-app* [config app-id]
  (gen-wrap :function ::launch-app config app-id))

(defn close-app* [config app-id]
  (gen-wrap :function ::close-app config app-id))

(defn update-printer* [config printer]
  (gen-wrap :function ::update-printer config printer))

(defn remove-printer* [config printer-id]
  (gen-wrap :function ::remove-printer config printer-id))

(defn set-play-store-enabled* [config enabled]
  (gen-wrap :function ::set-play-store-enabled config enabled))

(defn get-histogram* [config name]
  (gen-wrap :function ::get-histogram config name))

(defn run-crostini-installer* [config]
  (gen-wrap :function ::run-crostini-installer config))

(defn run-crostini-uninstaller* [config]
  (gen-wrap :function ::run-crostini-uninstaller config))

(defn set-crostini-enabled* [config enabled]
  (gen-wrap :function ::set-crostini-enabled config enabled))

(defn take-screenshot* [config]
  (gen-wrap :function ::take-screenshot config))

(defn bootstrap-machine-learning-service* [config]
  (gen-wrap :function ::bootstrap-machine-learning-service config))

(defn set-assistant-enabled* [config enabled timeout-ms]
  (gen-wrap :function ::set-assistant-enabled config enabled timeout-ms))

(defn send-assistant-text-query* [config query timeout-ms]
  (gen-wrap :function ::send-assistant-text-query config query timeout-ms))

(defn set-whitelisted-pref* [config pref-name value]
  (gen-wrap :function ::set-whitelisted-pref config pref-name value))

(defn set-crostini-app-scaled* [config app-id scaled]
  (gen-wrap :function ::set-crostini-app-scaled config app-id scaled))

(defn ensure-window-service-client-has-drawn-window* [config client-name timeout-ms]
  (gen-wrap :function ::ensure-window-service-client-has-drawn-window config client-name timeout-ms))

(defn get-primary-display-scale-factor* [config]
  (gen-wrap :function ::get-primary-display-scale-factor config))

