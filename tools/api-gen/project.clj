(defproject binaryage/api-gen "0.1.0-SNAPSHOT"
  :description "api generator for chromex library"
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "0.4.2"]
                 [org.clojure/data.json "0.2.6"]
                 [camel-snake-kebab "0.4.0"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [funcool/cuerdas "2.2.0"]
                 [clj-time "0.15.1"]
                 [cljfmt "0.6.4"]]
  :source-paths ["src"]
  :main api-gen.core)