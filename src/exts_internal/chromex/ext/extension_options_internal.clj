(ns chromex.ext.extension-options-internal
  "Internal API for the &lt;extensiontoptions&gt; tag

     * available since Chrome 55"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-close-events
  "
   Events will be put on the |channel| with signature [::on-close []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-close &form channel args)))

(defmacro tap-on-load-events
  "
   Events will be put on the |channel| with signature [::on-load []].

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-load &form channel args)))

(defmacro tap-on-preferred-size-changed-events
  "
   Events will be put on the |channel| with signature [::on-preferred-size-changed [options]] where:

     |options| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-preferred-size-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.extension-options-internal namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.extensionOptionsInternal",
   :since "55",
   :events
   [{:id ::on-close, :name "onClose"}
    {:id ::on-load, :name "onLoad"}
    {:id ::on-preferred-size-changed, :name "onPreferredSizeChanged", :params [{:name "options", :type "object"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))