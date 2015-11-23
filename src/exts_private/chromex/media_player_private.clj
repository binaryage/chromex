(ns chromex.media-player-private
  "  * available since Chrome 13
     * https://developer.chrome.com/extensions/mediaPlayerPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------

(defmacro tap-on-next-track-events
  "Notifies that the next track was requested."
  ([channel] (gen-call :event ::on-next-track &form channel)))

(defmacro tap-on-prev-track-events
  "Notifies that the previous tack was requested."
  ([channel] (gen-call :event ::on-prev-track &form channel)))

(defmacro tap-on-toggle-play-state-events
  "Notifies that a play/pause toggle was requested."
  ([channel] (gen-call :event ::on-toggle-play-state &form channel)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
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