(def clojurescript-version (or (System/getenv "CANARY_CLOJURESCRIPT_VERSION") "1.11.4"))
(defproject binaryage/chromex "0.9.2"
  :description "A ClojureScript wrapper for writing Chrome Extensions and Apps."
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}

  :scm {:name "git" :url "https://github.com/binaryage/chromex"}

  :dependencies [[org.clojure/clojure "1.10.3" :scope "provided"]
                 [org.clojure/clojurescript ~clojurescript-version :scope "provided"]
                 [org.clojure/core.async "1.5.648"]
                 [binaryage/oops "0.7.2"]
                 [environ "1.2.0"]]

  :clean-targets ^{:protect false} ["target"
                                    "test/resources/.compiled"]

  :plugins [[lein-cljsbuild "1.1.8"]]

  :resource-paths ^:replace []
  :source-paths ["src/lib"
                 "src/exts"
                 "src/exts_private"
                 "src/exts_internal"
                 "src/apps"
                 "src/apps_private"
                 "src/apps_internal"]
  :test-paths ["test/src"]

  :jar-exclusions [#"readme\.md"]

  :cljsbuild {:builds {}}                                                                                                     ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413

  :profiles {:test-none
             {:cljsbuild {:builds {:tests
                                   {:source-paths ["src/lib"
                                                   "test/src"]
                                    :compiler     {:output-to     "test/resources/.compiled/optimizations_none/chromex.test.js"
                                                   :output-dir    "test/resources/.compiled/optimizations_none"
                                                   :asset-path    ".compiled/optimizations_none"
                                                   :main          chromex.runner
                                                   :optimizations :none}}}}}

             :test-advanced
             {:cljsbuild {:builds {:tests
                                   {:source-paths ["src/lib"
                                                   "test/src"]
                                    :compiler     {:output-to      "test/resources/.compiled/optimizations_advanced/chromex.test.js"
                                                   :output-dir     "test/resources/.compiled/optimizations_advanced"
                                                   :asset-path     ".compiled/optimizations_advanced"
                                                   :main           chromex.runner
                                                   :optimizations  :advanced
                                                   :checked-arrays :warn
                                                   :elide-asserts  true}}}}}})
