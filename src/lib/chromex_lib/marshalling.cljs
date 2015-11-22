(ns chromex-lib.marshalling
  (:require [chromex-lib.protocols :as protocols]
            [chromex-lib.chrome-port :refer [make-chrome-port]]))

(defn from-native-chrome-port [native-chrome-port]
  (.log js/console "marshalling from native chrome port" native-chrome-port)
  (make-chrome-port native-chrome-port))

(defn to-native-chrome-port [chrome-port]
  {:pre [(satisfies? protocols/IChromePort chrome-port)]}
  (.log js/console "marshalling to native chrome port" chrome-port)
  (protocols/get-native-port chrome-port))
