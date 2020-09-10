(defproject binaryage/api-gen "0.1.0-SNAPSHOT"
  :description "api generator for chromex library"
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.cli "1.0.194"]
                 [org.clojure/data.json "1.0.0"]
                 [camel-snake-kebab "0.4.1"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [funcool/cuerdas "2020.03.26-3"]
                 [clj-time "0.15.2"]
                 [cljfmt "0.7.0"]]
  :source-paths ["src"]
  :main api-gen.core)
