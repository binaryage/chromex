(ns chromex.test-utils
  (:require-macros [chromex.test-utils :refer [test-mode]]))

(def advanced-mode? (= (test-mode) "advanced"))

