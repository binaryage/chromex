(ns chromex-sample.content-script
  (:require-macros [chromex.support :refer [runonce]])
  (:require [chromex-sample.content-script.core :as core]))

(runonce
  (core/init!))
