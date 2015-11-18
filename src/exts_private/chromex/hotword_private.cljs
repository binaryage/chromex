(ns chromex.hotword-private (:require-macros [chromex.hotword-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions ------------------------------------------------------------------------------------------------------

(defn set-enabled* [config state]
  (gen-wrap :function ::set-enabled config state))

(defn get-status* [config get-optional-fields]
  (gen-wrap :function ::get-status config get-optional-fields))

(defn get-localized-strings* [config]
  (gen-wrap :function ::get-localized-strings config))

(defn set-audio-logging-enabled* [config state]
  (gen-wrap :function ::set-audio-logging-enabled config state))

(defn set-hotword-always-on-search-enabled* [config state]
  (gen-wrap :function ::set-hotword-always-on-search-enabled config state))

(defn set-hotword-session-state* [config started]
  (gen-wrap :function ::set-hotword-session-state config started))

(defn notify-hotword-recognition* [config type log]
  (gen-wrap :function ::notify-hotword-recognition config type log))

(defn get-launch-state* [config]
  (gen-wrap :function ::get-launch-state config))

(defn start-training* [config]
  (gen-wrap :function ::start-training config))

(defn finalize-speaker-model* [config]
  (gen-wrap :function ::finalize-speaker-model config))

(defn notify-speaker-model-saved* [config]
  (gen-wrap :function ::notify-speaker-model-saved config))

(defn stop-training* [config]
  (gen-wrap :function ::stop-training config))

(defn set-audio-history-enabled* [config enabled]
  (gen-wrap :function ::set-audio-history-enabled config enabled))

(defn get-audio-history-enabled* [config]
  (gen-wrap :function ::get-audio-history-enabled config))

(defn speaker-model-exists-result* [config exists]
  (gen-wrap :function ::speaker-model-exists-result config exists))

; -- events ---------------------------------------------------------------------------------------------------------

(defn on-enabled-changed* [config channel]
  (gen-wrap :event ::on-enabled-changed config channel))

(defn on-hotword-session-requested* [config channel]
  (gen-wrap :event ::on-hotword-session-requested config channel))

(defn on-hotword-session-stopped* [config channel]
  (gen-wrap :event ::on-hotword-session-stopped config channel))

(defn on-finalize-speaker-model* [config channel]
  (gen-wrap :event ::on-finalize-speaker-model config channel))

(defn on-speaker-model-saved* [config channel]
  (gen-wrap :event ::on-speaker-model-saved config channel))

(defn on-hotword-triggered* [config channel]
  (gen-wrap :event ::on-hotword-triggered config channel))

(defn on-delete-speaker-model* [config channel]
  (gen-wrap :event ::on-delete-speaker-model config channel))

(defn on-speaker-model-exists* [config channel]
  (gen-wrap :event ::on-speaker-model-exists config channel))

(defn on-microphone-state-changed* [config channel]
  (gen-wrap :event ::on-microphone-state-changed config channel))

