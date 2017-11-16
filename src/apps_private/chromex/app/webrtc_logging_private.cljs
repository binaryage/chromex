(ns chromex.app.webrtc-logging-private (:require-macros [chromex.app.webrtc-logging-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-meta-data* [config request security-origin meta-data]
  (gen-wrap :function ::set-meta-data config request security-origin meta-data))

(defn start* [config request security-origin]
  (gen-wrap :function ::start config request security-origin))

(defn set-upload-on-render-close* [config request security-origin should-upload]
  (gen-wrap :function ::set-upload-on-render-close config request security-origin should-upload))

(defn stop* [config request security-origin]
  (gen-wrap :function ::stop config request security-origin))

(defn store* [config request security-origin log-id]
  (gen-wrap :function ::store config request security-origin log-id))

(defn upload-stored* [config request security-origin log-id]
  (gen-wrap :function ::upload-stored config request security-origin log-id))

(defn upload* [config request security-origin]
  (gen-wrap :function ::upload config request security-origin))

(defn discard* [config request security-origin]
  (gen-wrap :function ::discard config request security-origin))

(defn start-rtp-dump* [config request security-origin incoming outgoing]
  (gen-wrap :function ::start-rtp-dump config request security-origin incoming outgoing))

(defn stop-rtp-dump* [config request security-origin incoming outgoing]
  (gen-wrap :function ::stop-rtp-dump config request security-origin incoming outgoing))

(defn start-audio-debug-recordings* [config request security-origin seconds]
  (gen-wrap :function ::start-audio-debug-recordings config request security-origin seconds))

(defn stop-audio-debug-recordings* [config request security-origin]
  (gen-wrap :function ::stop-audio-debug-recordings config request security-origin))

(defn start-web-rtc-event-logging* [config request security-origin seconds]
  (gen-wrap :function ::start-web-rtc-event-logging config request security-origin seconds))

(defn stop-web-rtc-event-logging* [config request security-origin]
  (gen-wrap :function ::stop-web-rtc-event-logging config request security-origin))

(defn get-logs-directory* [config]
  (gen-wrap :function ::get-logs-directory config))

