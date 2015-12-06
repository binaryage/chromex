(ns chromex.debug
  (:require [clojure.pprint :refer :all]))

(defn print-to-err [& args]
  (binding [*out* *err*]
    (apply println "------------------------------------------------------------\n" args "\n\n")))

(defn pprint-code [code]
  (binding [;*print-suppress-namespaces* true
            *print-right-margin* 200]
    (with-out-str
      (with-pprint-dispatch code-dispatch
                            (pprint code)))))

(defn print-code [code]
  (print-to-err (pprint-code code)))

