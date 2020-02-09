(ns chromex-sample.background
  (:require-macros [chromex.support :refer [runonce]])
  (:require [chromex-sample.background.core :as core]))

(runonce
  (core/init!))
