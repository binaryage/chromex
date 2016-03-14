(defproject binaryage/chromex "0.3.1-SNAPSHOT"
  :description "A ClojureScript wrapper for writing Chrome Extensions and Apps."
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}

  :scm {:name "git" :url "https://github.com/binaryage/chromex"}

  :dependencies [[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/clojurescript "1.7.170" :scope "provided"]
                 [org.clojure/core.async "0.2.374" :scope "provided"]
                 [environ "1.0.2" :scope "provided"]]

  :clean-targets ^{:protect false} ["target"
                                    "test/_generated"]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-environ "1.0.2"]]

  :hooks [leiningen.cljsbuild]

  :source-paths ["src/lib"
                 "src/exts"
                 "src/exts_private"
                 "src/exts_internal"
                 "src/apps"
                 "src/apps_private"
                 "src/apps_internal"]

  :jar-exclusions [#"readme\.md"]

  :test-paths ["test"]

  :cljsbuild {:builds {}}                                                                                                     ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413

  :profiles {:dev-ext       {:cljsbuild {:builds {:dev
                                                  {:source-paths ["src/lib"
                                                                  "src/exts"
                                                                  "src/exts_private"
                                                                  "src/exts_internal"]
                                                   :compiler     {:output-to     "target/dev-ext/chromex.js"
                                                                  :output-dir    "target/dev-ext"
                                                                  :optimizations :none
                                                                  :source-map    true}}}}}
             :dev-app       {:cljsbuild {:builds {:dev
                                                  {:source-paths ["src/lib"
                                                                  "src/apps"
                                                                  "src/apps_private"
                                                                  "src/apps_internal"]
                                                   :compiler     {:output-to     "target/dev-app/chromex.js"
                                                                  :output-dir    "target/dev-app"
                                                                  :optimizations :none
                                                                  :source-map    true}}}}}
             :test          {:env       {:running-dev-test "true"}
                             :cljsbuild {:builds        {:test-dev
                                                         {:source-paths ["src/lib"
                                                                         "test"]
                                                          :compiler     {:output-to     "test/_generated/optimizations_none/chromex.test.js"
                                                                         :output-dir    "test/_generated/optimizations_none"
                                                                         :asset-path    "_generated/optimizations_none"
                                                                         :main          chromex.runner
                                                                         :optimizations :none
                                                                         :source-map    false}}}
                                         :test-commands {"unit" ["phantomjs" "test/phantom.js" "test/runner_none.html"]}}}

             :test-advanced {:env       {:running-advanced-test            "true"
                                         :chromex-elide-verbose-logging    "true"
                                         :chromex-elide-missing-api-checks "true"}
                             :cljsbuild {:builds        {:test-advanced
                                                         {:source-paths ["src/lib"
                                                                         "test"]
                                                          :compiler     {:output-to     "test/_generated/optimizations_advanced/chromex.test.js"
                                                                         :output-dir    "test/_generated/optimizations_advanced"
                                                                         :asset-path    "_generated/optimizations_advanced"
                                                                         :main          chromex.runner
                                                                         :optimizations :advanced
                                                                         :elide-asserts true
                                                                         :source-map    "test/_generated/optimizations_advanced/chromex.test.js.map"}}}
                                         :test-commands {"unit" ["phantomjs" "test/phantom.js" "test/runner_advanced.html"]}}}}

  :aliases {"test"          ["with-profile" "test" "cljsbuild" "test" "unit"]
            "test-advanced" ["with-profile" "test-advanced" "cljsbuild" "test" "unit"]
            "test-all"      ["do" "test," "test-advanced"]})