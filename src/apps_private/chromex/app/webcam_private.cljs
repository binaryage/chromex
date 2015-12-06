(ns chromex.app.webcam-private (:require-macros [chromex.app.webcam-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn open-serial-webcam* [config path protocol]
  (gen-wrap :function ::open-serial-webcam config path protocol))

(defn close-webcam* [config webcam-id]
  (gen-wrap :function ::close-webcam config webcam-id))

(defn get* [config webcam-id]
  (gen-wrap :function ::get config webcam-id))

(defn set* [config webcam-id config]
  (gen-wrap :function ::set config webcam-id config))

(defn reset* [config webcam-id config]
  (gen-wrap :function ::reset config webcam-id config))

