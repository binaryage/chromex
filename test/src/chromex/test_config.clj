(ns chromex.test-config
  (:require [chromex.config :as config]
            [chromex.test-utils :as test-utils]
            [chromex.test.marshalling :as marshalling]))

(test-utils/init!)

(alter-var-root #'config/*gen-marshalling* (constantly marshalling/custom-gen-marshalling))
(alter-var-root #'config/*compiler-println* (constantly test-utils/test-compiler-println))
(alter-var-root #'config/*target-api-version* (constantly "50"))