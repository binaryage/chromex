(defproject binaryage/api-distiller "0.1.0-SNAPSHOT"
  :description "api pre-processing for chromex library"
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [org.clojure/data.json "0.2.6"]]
  :source-paths ["src"]
  :main api-distiller.core)