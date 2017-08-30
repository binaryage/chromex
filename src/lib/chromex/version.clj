(ns chromex.version
  (:require [clojure.string :as string]
            [cljs.util]))

(def current-version "0.5.10")                                                                                        ; this should match our project.clj

(defmacro get-current-version []
  current-version)

(defn get-full-clojurescript-version []
  (string/join "." ((juxt :major :minor :qualifier) cljs.util/*clojurescript-version*)))

(defn broken-clojurescript? [version]
  (or (= version "1.9.493")
      (= version "1.9.494")
      (= version "1.9.495")))

(defn detect-broken-clojurescript! []
  (let [clojurescript-version (get-full-clojurescript-version)]
    (if (broken-clojurescript? clojurescript-version)
      (binding [*out* *err*]
        (println
          (str "WARNING: Broken ClojureScript version detected on your class-path.\n"
               "         ClojureScript " clojurescript-version " suffers from http://dev.clojure.org/jira/browse/CLJS-1954.\n"
               "         A solution is to downgrade to ClojureScript 1.9.473 or upgrade to 1.9.518."))))))

(defmacro check-env! []
  (detect-broken-clojurescript!)
  nil)
