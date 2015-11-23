(ns chromex.cryptotoken-private (:require-macros [chromex.cryptotoken-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn can-origin-assert-app-id* [config security-origin app-id-url]
  (gen-wrap :function ::can-origin-assert-app-id config security-origin app-id-url))

