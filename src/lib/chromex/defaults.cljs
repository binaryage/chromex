(ns chromex.defaults
  (:require-macros [chromex.config :refer [gen-default-config]]
                   [chromex.defaults])
  (:require [chromex.error :refer [set-last-error! set-last-error-args!]]
            [chromex.protocols.chrome-port]
            [chromex.protocols.chrome-port-state]
            [chromex.support :refer [call-hook]]
            [cljs.core.async :refer [chan close! promise-chan put!]]
            [goog.object :as gobj]
            [oops.core :refer [oget]]))

; -- logging support --------------------------------------------------------------------------------------------------------

(def log-prefix "[chromex]")

(defn console-log [& args]
  (.apply (.-log js/console) js/console (into-array args)))

(defn console-error [& args]
  (.apply (.-error js/console) js/console (into-array args)))

(defn default-logger [& args]
  (apply console-log log-prefix args))

(defn default-callback-error-reporter [descriptor error]
  (let [function (or (str (namespace (:id descriptor)) "/" (name (:id descriptor))) "an unknown function")
        explanation (oget error "message")
        message (str "an error occurred during the call to " function (if explanation (str ": " explanation)))]
    (console-error log-prefix message "Details:" error)))

; -- callback support -------------------------------------------------------------------------------------------------------
;
; async methods using core.async channels

(defn report-error-if-needed! [config descriptor error]
  (when-let [error-reporter (:callback-error-reporter config)]
    (assert (fn? error-reporter))
    (error-reporter descriptor error)))

(defn normalize-args [args]
  (vec args))

(defn default-callback-fn-factory [config descriptor chan]
  (fn [& args]
    (let [normalized-args (normalize-args args)]
      (if-some [error (oget (:root config) "chrome.runtime.?lastError")]
        (do
          (set-last-error! error)
          (set-last-error-args! normalized-args)
          (report-error-if-needed! config descriptor error)
          (close! chan))
        (do
          (set-last-error! nil)
          (set-last-error-args! nil)
          (put! chan normalized-args))))))

(defn default-callback-channel-factory [_config]
  (promise-chan))

(defn default-event-listener-factory [_config event-id chan]
  (fn [& args]
    (put! chan [event-id (vec args)])))

; -- missing API checks -----------------------------------------------------------------------------------------------------

(defn default-missing-api-check [api obj key]
  (when-not (gobj/containsKey obj key)
    (throw (js/Error. (str "Chromex library tried to access a missing Chrome API object '" api "'.\n"
                           "Your Chrome version might be too old or too recent for running this extension.\n"
                           "This is a failure which probably requires a software update.")))))

; -- ChromeContentSetting ------------------------------------------------------------------------------------------------------

(defn default-chrome-content-setting-callback-fn-factory [config chan]
  (fn [& args]
    (let [last-error (oget (:root config) "chrome" "runtime" "?lastError")]
      (put! chan [(vec args) last-error]))))

(defn default-chrome-content-setting-callback-channel-factory [_config]
  (promise-chan))

; -- ChromeStorageArea ------------------------------------------------------------------------------------------------------

(defn default-chrome-storage-area-callback-fn-factory [config chan]
  (fn [& args]
    (let [last-error (oget (:root config) "chrome" "runtime" "?lastError")]
      (put! chan [(vec args) last-error]))))

(defn default-chrome-storage-area-callback-channel-factory [_config]
  (promise-chan))

; -- ChromePort ------------------------------------------------------------------------------------------------------

(defn default-chrome-port-channel-factory [_config]
  (chan))

(defn default-chrome-port-on-message-fn-factory [config chrome-port]
  (fn [message]
    (if (nil? message)
      (call-hook config :chrome-port-received-nil-message chrome-port)
      (do
        (chromex.protocols.chrome-port-state/put-message! chrome-port message)
        nil))))

(defn default-chrome-port-on-disconnect-fn-factory [_config chrome-port]
  (fn []
    (chromex.protocols.chrome-port-state/close-resources! chrome-port)
    (chromex.protocols.chrome-port-state/set-connected! chrome-port false)
    nil))

(defn default-chrome-port-disconnect-called-on-disconnected-port [_config _chrome-port]
  (assert false "ChromePort: disconnect! called on already disconnected port"))

(defn default-chrome-port-post-message-called-on-disconnected-port [_config _chrome-port]
  (assert false "ChromePort: post-message! called on already disconnected port"))

(defn default-chrome-port-on-disconnect-called-on-disconnected-port [_config _chrome-port]
  (assert false "ChromePort: on-disconnect! called on already disconnected port"))

(defn default-chrome-port-on-message-called-on-disconnected-port [_config _chrome-port]
  (assert false "ChromePort: on-message! called on already disconnected port"))

(defn default-chrome-port-post-message-called-with-nil [_config _chrome-port]
  (assert false "ChromePort: post-message! called with nil message. Nil cannot be delivered via a core.async channel."))

(defn default-chrome-port-received-nil-message [_config _chrome-port]
  (assert false "ChromePort: received a nil message. Nil cannot be delivered via a core.async channel."))

(defn default-chrome-port-put-message-called-on-disconnected-port [_config _chrome-port message]
  (assert false (str "ChromePort: put-message! called on already disconnected port.\n"
                     "message: " message)))

; -- default config ---------------------------------------------------------------------------------------------------------

(def default-config (gen-default-config))
