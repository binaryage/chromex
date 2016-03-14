(ns chromex.ext.media-player-private
  "  * available since Chrome 13"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

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
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.mediaPlayerPrivate",
   :since "13",
   :events
   [{:id ::on-next-track, :name "onNextTrack", :since "23"}
    {:id ::on-prev-track, :name "onPrevTrack", :since "23"}
    {:id ::on-toggle-play-state, :name "onTogglePlayState", :since "23"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))