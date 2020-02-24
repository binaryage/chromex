(ns chromex-sample.content-script
  (:require [chromex-sample.content-script.core :as core]
            [chromex.support :refer [runonce]]))

(runonce
  (core/init!))
