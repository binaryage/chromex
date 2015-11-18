(defproject binaryage/api-gen "0.1.0-SNAPSHOT"
  :description "api generator for chromex library"
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [org.clojure/data.json "0.2.6"]
                 [camel-snake-kebab "0.3.2"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [funcool/cuerdas "0.6.0"]
                 [clj-time "0.8.0"]
                 [cljfmt "0.3.0"]]
  :source-paths ["src"]
  :main api-gen.core)