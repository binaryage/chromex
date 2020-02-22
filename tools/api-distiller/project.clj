(defproject binaryage/api-distiller "0.1.0-SNAPSHOT"
  :description "api pre-processing for chromex library"
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.cli "1.0.194"]
                 [org.clojure/data.json "1.0.0"]]
  :source-paths ["src"]
  :main api-distiller.core)
