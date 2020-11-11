(ns chromex.ext.autotest-private (:require-macros [chromex.ext.autotest-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn initialize-events* [config]
  (gen-wrap :function ::initialize-events config))

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

(defn get-all-enterprise-policies* [config]
  (gen-wrap :function ::get-all-enterprise-policies config))

(defn refresh-enterprise-policies* [config]
  (gen-wrap :function ::refresh-enterprise-policies config))

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

(defn remove-all-notifications* [config]
  (gen-wrap :function ::remove-all-notifications config))

(defn get-arc-start-time* [config]
  (gen-wrap :function ::get-arc-start-time config))

(defn get-arc-state* [config]
  (gen-wrap :function ::get-arc-state config))

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

(defn wait-for-system-web-apps-install* [config]
  (gen-wrap :function ::wait-for-system-web-apps-install config))

(defn get-registered-system-web-apps* [config]
  (gen-wrap :function ::get-registered-system-web-apps config))

(defn launch-arc-app* [config app-id intent]
  (gen-wrap :function ::launch-arc-app config app-id intent))

(defn launch-app* [config app-id]
  (gen-wrap :function ::launch-app config app-id))

(defn launch-system-web-app* [config app-name url]
  (gen-wrap :function ::launch-system-web-app config app-name url))

(defn close-app* [config app-id]
  (gen-wrap :function ::close-app config app-id))

(defn update-printer* [config printer]
  (gen-wrap :function ::update-printer config printer))

(defn remove-printer* [config printer-id]
  (gen-wrap :function ::remove-printer config printer-id))

(defn set-play-store-enabled* [config enabled]
  (gen-wrap :function ::set-play-store-enabled config enabled))

(defn get-clipboard-text-data* [config]
  (gen-wrap :function ::get-clipboard-text-data config))

(defn set-clipboard-text-data* [config data]
  (gen-wrap :function ::set-clipboard-text-data config data))

(defn run-crostini-installer* [config]
  (gen-wrap :function ::run-crostini-installer config))

(defn run-crostini-uninstaller* [config]
  (gen-wrap :function ::run-crostini-uninstaller config))

(defn set-crostini-enabled* [config enabled]
  (gen-wrap :function ::set-crostini-enabled config enabled))

(defn export-crostini* [config path]
  (gen-wrap :function ::export-crostini config path))

(defn import-crostini* [config path]
  (gen-wrap :function ::import-crostini config path))

(defn set-plugin-vm-policy* [config image-url image-hash license-key]
  (gen-wrap :function ::set-plugin-vm-policy config image-url image-hash license-key))

(defn show-plugin-vm-installer* [config]
  (gen-wrap :function ::show-plugin-vm-installer config))

(defn register-component* [config name path]
  (gen-wrap :function ::register-component config name path))

(defn take-screenshot* [config]
  (gen-wrap :function ::take-screenshot config))

(defn take-screenshot-for-display* [config display-id]
  (gen-wrap :function ::take-screenshot-for-display config display-id))

(defn bootstrap-machine-learning-service* [config]
  (gen-wrap :function ::bootstrap-machine-learning-service config))

(defn set-assistant-enabled* [config enabled timeout-ms]
  (gen-wrap :function ::set-assistant-enabled config enabled timeout-ms))

(defn enable-assistant-and-wait-for-ready* [config]
  (gen-wrap :function ::enable-assistant-and-wait-for-ready config))

(defn send-assistant-text-query* [config query timeout-ms]
  (gen-wrap :function ::send-assistant-text-query config query timeout-ms))

(defn wait-for-assistant-query-status* [config timeout-s]
  (gen-wrap :function ::wait-for-assistant-query-status config timeout-s))

(defn is-arc-package-list-initial-refreshed* [config]
  (gen-wrap :function ::is-arc-package-list-initial-refreshed config))

(defn set-whitelisted-pref* [config pref-name value]
  (gen-wrap :function ::set-whitelisted-pref config pref-name value))

(defn set-crostini-app-scaled* [config app-id scaled]
  (gen-wrap :function ::set-crostini-app-scaled config app-id scaled))

(defn get-primary-display-scale-factor* [config]
  (gen-wrap :function ::get-primary-display-scale-factor config))

(defn is-tablet-mode-enabled* [config]
  (gen-wrap :function ::is-tablet-mode-enabled config))

(defn set-tablet-mode-enabled* [config enabled]
  (gen-wrap :function ::set-tablet-mode-enabled config enabled))

(defn get-all-installed-apps* [config]
  (gen-wrap :function ::get-all-installed-apps config))

(defn get-shelf-items* [config]
  (gen-wrap :function ::get-shelf-items config))

(defn get-shelf-auto-hide-behavior* [config display-id]
  (gen-wrap :function ::get-shelf-auto-hide-behavior config display-id))

(defn set-shelf-auto-hide-behavior* [config display-id behavior]
  (gen-wrap :function ::set-shelf-auto-hide-behavior config display-id behavior))

(defn get-shelf-alignment* [config display-id]
  (gen-wrap :function ::get-shelf-alignment config display-id))

(defn set-shelf-alignment* [config display-id alignment]
  (gen-wrap :function ::set-shelf-alignment config display-id alignment))

(defn pin-shelf-icon* [config app-id]
  (gen-wrap :function ::pin-shelf-icon config app-id))

(defn set-overview-mode-state* [config start]
  (gen-wrap :function ::set-overview-mode-state config start))

(defn show-virtual-keyboard-if-enabled* [config]
  (gen-wrap :function ::show-virtual-keyboard-if-enabled config))

(defn arc-app-tracing-start* [config]
  (gen-wrap :function ::arc-app-tracing-start config))

(defn arc-app-tracing-stop-and-analyze* [config]
  (gen-wrap :function ::arc-app-tracing-stop-and-analyze config))

(defn swap-windows-in-split-view* [config]
  (gen-wrap :function ::swap-windows-in-split-view config))

(defn set-arc-app-window-focus* [config package-name]
  (gen-wrap :function ::set-arc-app-window-focus config package-name))

(defn wait-for-display-rotation* [config display-id rotation]
  (gen-wrap :function ::wait-for-display-rotation config display-id rotation))

(defn get-app-window-list* [config]
  (gen-wrap :function ::get-app-window-list config))

(defn set-app-window-state* [config id change]
  (gen-wrap :function ::set-app-window-state config id change))

(defn close-app-window* [config id]
  (gen-wrap :function ::close-app-window config id))

(defn install-pwa-for-current-url* [config timeout-ms]
  (gen-wrap :function ::install-pwa-for-current-url config timeout-ms))

(defn activate-accelerator* [config accelerator]
  (gen-wrap :function ::activate-accelerator config accelerator))

(defn wait-for-launcher-state* [config launcher-state]
  (gen-wrap :function ::wait-for-launcher-state config launcher-state))

(defn wait-for-overview-state* [config overview-state]
  (gen-wrap :function ::wait-for-overview-state config overview-state))

(defn create-new-desk* [config]
  (gen-wrap :function ::create-new-desk config))

(defn activate-desk-at-index* [config index]
  (gen-wrap :function ::activate-desk-at-index config index))

(defn remove-active-desk* [config]
  (gen-wrap :function ::remove-active-desk config))

(defn activate-adjacent-desks-to-target-index* [config index]
  (gen-wrap :function ::activate-adjacent-desks-to-target-index config index))

(defn mouse-click* [config button]
  (gen-wrap :function ::mouse-click config button))

(defn mouse-press* [config button]
  (gen-wrap :function ::mouse-press config button))

(defn mouse-release* [config button]
  (gen-wrap :function ::mouse-release config button))

(defn mouse-move* [config location duration-in-ms]
  (gen-wrap :function ::mouse-move config location duration-in-ms))

(defn set-metrics-enabled* [config enabled]
  (gen-wrap :function ::set-metrics-enabled config enabled))

(defn start-tracing* [config config]
  (gen-wrap :function ::start-tracing config config))

(defn stop-tracing* [config]
  (gen-wrap :function ::stop-tracing config))

(defn set-arc-touch-mode* [config enabled]
  (gen-wrap :function ::set-arc-touch-mode config enabled))

(defn get-scrollable-shelf-info-for-state* [config state]
  (gen-wrap :function ::get-scrollable-shelf-info-for-state config state))

(defn get-shelf-ui-info-for-state* [config state]
  (gen-wrap :function ::get-shelf-ui-info-for-state config state))

(defn set-window-bounds* [config id bounds display-id]
  (gen-wrap :function ::set-window-bounds config id bounds display-id))

(defn start-smoothness-tracking* [config display-id]
  (gen-wrap :function ::start-smoothness-tracking config display-id))

(defn stop-smoothness-tracking* [config display-id]
  (gen-wrap :function ::stop-smoothness-tracking config display-id))

(defn disable-switch-access-dialog* [config]
  (gen-wrap :function ::disable-switch-access-dialog config))

(defn wait-for-ambient-photo-animation* [config num-completions timeout]
  (gen-wrap :function ::wait-for-ambient-photo-animation config num-completions timeout))

(defn disable-automation* [config]
  (gen-wrap :function ::disable-automation config))

(defn start-throughput-tracker-data-collection* [config]
  (gen-wrap :function ::start-throughput-tracker-data-collection config))

(defn stop-throughput-tracker-data-collection* [config]
  (gen-wrap :function ::stop-throughput-tracker-data-collection config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-clipboard-data-changed* [config channel & args]
  (gen-wrap :event ::on-clipboard-data-changed config channel args))

