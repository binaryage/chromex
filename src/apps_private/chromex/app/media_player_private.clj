(ns chromex.app.media-player-private
  "  * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-next-track-events
  "Notifies that the next track was requested.

   Events will be put on the |channel| with signature [::on-next-track []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-next-track &form channel args)))

(defmacro tap-on-prev-track-events
  "Notifies that the previous tack was requested.

   Events will be put on the |channel| with signature [::on-prev-track []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-prev-track &form channel args)))

(defmacro tap-on-toggle-play-state-events
  "Notifies that a play/pause toggle was requested.

   Events will be put on the |channel| with signature [::on-toggle-play-state []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-toggle-play-state &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.media-player-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.mediaPlayerPrivate",
   :since "38",
   :events
   [{:id ::on-next-track, :name "onNextTrack"}
    {:id ::on-prev-track, :name "onPrevTrack"}
    {:id ::on-toggle-play-state, :name "onTogglePlayState"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))