(defproject binaryage/chromex "0.1.2-SNAPSHOT"
  :description "Chrome Extensions API wrapper for ClojureScript"
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}

  :scm {:name "git" :url "https://github.com/binaryage/chromex"}

  :dependencies [[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/clojurescript "1.7.170" :scope "provided"]
                 [org.clojure/core.async "0.2.374" :scope "provided"]]

  :clean-targets ^{:protect false} ["target"
                                    "test/_generated"]

  :plugins [[lein-cljsbuild "1.1.1"]]

  :hooks [leiningen.cljsbuild]

  :source-paths ["src/lib"
                 "src/exts"
                 "src/exts_private"
                 "src/exts_internal"]

  :test-paths ["test"]

  :aliases {"test" ["with-profile" "test" "test"]}

  :profiles {:devel  {:cljsbuild {:builds {:dev
                                         {:source-paths ["src/lib"
                                                         "src/exts"
                                                         "src/exts_private"
                                                         "src/exts_internal"]
                                          :compiler     {:output-to     "target/dev/chromex.js"
                                                         :output-dir    "target/dev"
                                                         :optimizations :none
                                                         :source-map    true}}}}}

             :test {:injections [(require 'chromex.test.init-static-config)]
                    :cljsbuild  {:builds        {:test
                                                 {:source-paths ["src/lib"
                                                                 "test"]
                                                  :compiler     {:output-to     "test/_generated/chromex.test.js"
                                                                 :output-dir    "test/_generated"
                                                                 :main          chromex.runner
                                                                 :asset-path    "_generated"
                                                                 :optimizations :none
                                                                 :source-map    false}}}
                                 :test-commands {"unit" ["phantomjs" "test/phantom.js" "test/runner.html"]}}}})