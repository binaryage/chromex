(ns chromex.privacy (:require-macros [chromex.privacy :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- properties -------------------------------------------------------------------------------------------------------------

(defn network* [config]
  (gen-wrap :property ::network config))

(defn services* [config]
  (gen-wrap :property ::services config))

(defn websites* [config]
  (gen-wrap :property ::websites config))

