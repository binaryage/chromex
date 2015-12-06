(ns chromex.test-config
  (:require [chromex.config :as config]
            [chromex.test.marshalling :as marshalling]
            [chromex.test-utils :as test-utils]))

(test-utils/init!)

(alter-var-root #'config/*gen-marshalling* (constantly marshalling/custom-gen-marshalling))
(alter-var-root #'config/*compiler-println* (constantly test-utils/test-compiler-println))