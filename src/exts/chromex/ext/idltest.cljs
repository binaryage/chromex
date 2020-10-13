(ns chromex.ext.idltest (:require-macros [chromex.ext.idltest :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn send-array-buffer* [config input]
  (gen-wrap :function ::send-array-buffer config input))

(defn send-array-buffer-view* [config input]
  (gen-wrap :function ::send-array-buffer-view config input))

(defn get-array-buffer* [config]
  (gen-wrap :function ::get-array-buffer config))

(defn nocompile-func* [config switch]
  (gen-wrap :function ::nocompile-func config switch))

