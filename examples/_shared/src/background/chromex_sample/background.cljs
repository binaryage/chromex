(ns chromex-sample.background
  (:require [chromex-sample.background.core :as core]
            [chromex.support :refer [runonce]]))

(runonce
  (core/init!))
