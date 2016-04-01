(ns chromex.defaults
  (:require-macros [chromex.config :refer [gen-default-config]]
                   [chromex.support :refer [oget ocall call-hook]])
  (:require [cljs.core.async :refer [put! chan close!]]
            [goog.object :as gobj]
            [chromex.error :refer [set-last-error!]]
            [chromex.protocols :as protocols]))

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
        message (str "an error occured during the call to " function (if explanation (str ": " explanation)))]
    (console-error log-prefix message "Details:" error)))

; -- callback support -------------------------------------------------------------------------------------------------------
;
; async methods using core.async channels

(defn report-error-if-needed! [config descriptor error]
  (when-let [error-reporter (:callback-error-reporter config)]
    (assert (fn? error-reporter))
    (error-reporter descriptor error)))

(defn default-callback-fn-factory [config descriptor chan]
  (fn [& args]
    (if-let [error (oget js/chrome "runtime" "lastError")]
      (do
        (set-last-error! error)
        (report-error-if-needed! config descriptor error))
      (do
        (set-last-error! nil)
        (put! chan (vec args))))
    (close! chan)))

(defn default-callback-channel-factory [_config]
  (chan))

(defn default-event-listener-factory [_config event-id chan]
  (fn [& args]
    (put! chan [event-id (vec args)])))

; -- missing API checks -----------------------------------------------------------------------------------------------------

(defn default-missing-api-check [api obj key]
  (if-not (gobj/containsKey obj key)
    (throw (js/Error. (str "Chromex library tried to access a missing Chrome API object '" api "'.\n"
                           "Your Chrome version might be too old or too recent for running this extension.\n"
                           "This is a failure which probably requires a software update.")))))

; -- ChromeStorageArea ------------------------------------------------------------------------------------------------------

(defn default-chrome-storage-area-callback-fn-factory [config chan]
  (fn [& args]
    (let [last-error (oget (:root config) "chrome" "runtime" "lastError")]
      (put! chan [(vec args) last-error]))))

(defn default-chrome-storage-area-callback-channel-factory [_config]
  (chan))

; -- ChromePort ------------------------------------------------------------------------------------------------------

(defn default-chrome-port-channel-factory [_config]
  (chan))

(defn default-chrome-port-on-message-fn-factory [config chrome-port]
  (fn [message]
    (if (nil? message)
      (call-hook config :chrome-port-received-nil-message chrome-port)
      (do
        (protocols/put-message! chrome-port message)
        nil))))

(defn default-chrome-port-on-disconnect-fn-factory [_config chrome-port]
  (fn []
    (protocols/close-resources! chrome-port)
    (protocols/set-connected! chrome-port false)
    nil))

(defn default-chrome-port-disconnect-called-on-disconnected-port [_config _chrome-port]
  (assert false "ChromePort: disconnect! called on already disconnected port")
  nil)

(defn default-chrome-port-post-message-called-on-disconnected-port [_config _chrome-port]
  (assert false "ChromePort: post-message! called on already disconnected port")
  nil)

(defn default-chrome-port-on-disconnect-called-on-disconnected-port [_config _chrome-port]
  (assert false "ChromePort: on-disconnect! called on already disconnected port")
  nil)

(defn default-chrome-port-on-message-called-on-disconnected-port [_config _chrome-port]
  (assert false "ChromePort: on-message! called on already disconnected port")
  nil)

(defn default-chrome-port-post-message-called-with-nil [_config _chrome-port]
  (assert false "ChromePort: post-message! called with nil message. Nil cannot be delivered via a core.async channel.")
  nil)

(defn default-chrome-port-received-nil-message [_config _chrome-port]
  (assert false "ChromePort: received a nil message. Nil cannot be delivered via a core.async channel.")
  nil)

(defn default-chrome-port-put-message-called-on-disconnected-port [_config _chrome-port message]
  (assert false (str "ChromePort: put-message! called on already disconnected port.\n"
                     "message: " message))
  nil)

; -- default config ---------------------------------------------------------------------------------------------------------

(def default-config (gen-default-config))
