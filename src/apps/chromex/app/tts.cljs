(ns chromex.app.tts (:require-macros [chromex.app.tts :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn speak* [config utterance options]
  (gen-wrap :function ::speak config utterance options))

(defn stop* [config]
  (gen-wrap :function ::stop config))

(defn pause* [config]
  (gen-wrap :function ::pause config))

(defn resume* [config]
  (gen-wrap :function ::resume config))

(defn is-speaking* [config]
  (gen-wrap :function ::is-speaking config))

(defn get-voices* [config]
  (gen-wrap :function ::get-voices config))

