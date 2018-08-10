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

(defn update-printer* [config printer]
  (gen-wrap :function ::update-printer config printer))

(defn remove-printer* [config printer-id]
  (gen-wrap :function ::remove-printer config printer-id))

(defn set-play-store-enabled* [config enabled]
  (gen-wrap :function ::set-play-store-enabled config enabled))

(defn run-crostini-installer* [config]
  (gen-wrap :function ::run-crostini-installer config))

