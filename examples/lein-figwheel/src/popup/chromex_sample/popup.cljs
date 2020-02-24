(ns chromex-sample.popup
  (:require [chromex-sample.popup.core :as core]
            [chromex.support :refer [runonce]]))

(runonce
  (core/init!))
