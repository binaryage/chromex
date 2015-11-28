(defproject binaryage/chromex "0.1.2-SNAPSHOT"
  :description "Chrome Extensions API wrapper for ClojureScript"
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}

  :scm {:name "git" :url "https://github.com/binaryage/chromex"}

  :dependencies [[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/clojurescript "1.7.170" :scope "provided"]
                 [org.clojure/core.async "0.2.374" :scope "provided"]
                 [environ "1.0.1" :scope "provided"]]

  :clean-targets ^{:protect false} ["target"
                                    "test/_generated"]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-environ "1.0.1"]]

  :hooks [leiningen.cljsbuild]

  :source-paths ["src/lib"
                 "src/exts"
                 "src/exts_private"
                 "src/exts_internal"]

  :test-paths ["test"]

  :aliases {"test"          ["with-profile" "test" "cljsbuild" "test" "unit"]
            "test-advanced" ["with-profile" "test-advanced" "cljsbuild" "test" "unit"]
            "test-all" ["do" "test," "test-advanced"]}

  :profiles {:devel         {:cljsbuild {:builds {:dev
                                                  {:source-paths ["src/lib"
                                                                  "src/exts"
                                                                  "src/exts_private"
                                                                  "src/exts_internal"]
                                                   :compiler     {:output-to     "target/dev/chromex.js"
                                                                  :output-dir    "target/dev"
                                                                  :optimizations :none
                                                                  :source-map    true}}}}}

             :test          {:injections [(require 'chromex.test.init-static-config)]
                             :cljsbuild  {:builds        {:test-dev
                                                          {:source-paths ["src/lib"
                                                                          "test"]
                                                           :compiler     {:output-to     "test/_generated/optimizations_none/chromex.test.js"
                                                                          :output-dir    "test/_generated/optimizations_none"
                                                                          :asset-path    "_generated/optimizations_none"
                                                                          :main          chromex.runner
                                                                          :optimizations :none
                                                                          :source-map    false}}}
                                          :test-commands {"unit" ["phantomjs" "test/phantom.js" "test/runner_none.html"]}}}

             :test-advanced {:injections [(require 'chromex.test.init-static-config)]
                             :cljsbuild  {:builds        {:test-advanced
                                                          {:source-paths ["src/lib"
                                                                          "test"]
                                                           :compiler     {:output-to     "test/_generated/optimizations_advanced/chromex.test.js"
                                                                          :output-dir    "test/_generated/optimizations_advanced"
                                                                          :asset-path    "_generated/optimizations_advanced"
                                                                          :main          chromex.runner
                                                                          :optimizations :advanced
                                                                          :source-map    "test/_generated/optimizations_advanced/chromex.test.js.map"}}}
                                          :test-commands {"unit" ["phantomjs" "test/phantom.js" "test/runner_advanced.html"]}}}})