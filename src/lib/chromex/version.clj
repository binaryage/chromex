(ns chromex.version
  (:require [clojure.string :as string]))

(def current-version "0.5.7-SNAPSHOT")                                                                                        ; this should match our project.clj

(defmacro get-current-version []
  current-version)

(defn do-check-env! []
  (let [class-path (System/getProperty "java.class.path")]
    (if (some? (string/index-of class-path "clojurescript-1.9.494.jar"))
      (binding [*out* *err*]
        (println
          (str "WARNING: Broken ClojureScript version detected on your class-path.\n"
               "         ClojureScript 1.9.494 suffers from http://dev.clojure.org/jira/browse/CLJS-1954.\n"
               "         A solution is to downgrade to ClojureScript 1.9.473"))))))

(defmacro check-env! []
  (do-check-env!)
  nil)
