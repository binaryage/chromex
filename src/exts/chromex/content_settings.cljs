(ns chromex.content-settings (:require-macros [chromex.content-settings :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -----------------------------------------------------------------------------------------------------

(defn cookies* [config]
  (gen-wrap :property ::cookies config))

(defn images* [config]
  (gen-wrap :property ::images config))

(defn javascript* [config]
  (gen-wrap :property ::javascript config))

(defn location* [config]
  (gen-wrap :property ::location config))

(defn plugins* [config]
  (gen-wrap :property ::plugins config))

(defn popups* [config]
  (gen-wrap :property ::popups config))

(defn notifications* [config]
  (gen-wrap :property ::notifications config))

(defn fullscreen* [config]
  (gen-wrap :property ::fullscreen config))

(defn mouselock* [config]
  (gen-wrap :property ::mouselock config))

(defn microphone* [config]
  (gen-wrap :property ::microphone config))

(defn camera* [config]
  (gen-wrap :property ::camera config))

(defn unsandboxed-plugins* [config]
  (gen-wrap :property ::unsandboxed-plugins config))

(defn automatic-downloads* [config]
  (gen-wrap :property ::automatic-downloads config))

