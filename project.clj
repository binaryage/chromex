(def clojurescript-version (or (System/getenv "CANARY_CLOJURESCRIPT_VERSION") "1.10.520"))
(defproject binaryage/chromex "0.8.4"
  :description "A ClojureScript wrapper for writing Chrome Extensions and Apps."
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}

  :scm {:name "git" :url "https://github.com/binaryage/chromex"}

  :dependencies [[org.clojure/clojure "1.10.1" :scope "provided"]
                 [org.clojure/clojurescript ~clojurescript-version :scope "provided"]
                 [org.clojure/core.async "0.4.500"]
                 [binaryage/oops "0.7.0"]
                 [environ "1.1.0"]]

  :clean-targets ^{:protect false} ["target"
                                    "test/.compiled"]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :resource-paths ^:replace []
  :source-paths ["src/lib"
                 "src/exts"
                 "src/exts_private"
                 "src/exts_internal"
                 "src/apps"
                 "src/apps_private"
                 "src/apps_internal"]
  :test-paths ["test"]

  :jar-exclusions [#"readme\.md"]

  :cljsbuild {:builds {}}                                                                                                     ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413

  :profiles {:test-none
             {:cljsbuild {:builds {:tests
                                   {:source-paths ["src/lib"
                                                   "test"]
                                    :compiler     {:output-to     "test/.compiled/optimizations_none/chromex.test.js"
                                                   :output-dir    "test/.compiled/optimizations_none"
                                                   :asset-path    ".compiled/optimizations_none"
                                                   :main          chromex.runner
                                                   :optimizations :none
                                                   :source-map    false}}}}}

             :test-advanced
             {:cljsbuild {:builds {:tests
                                   {:source-paths ["src/lib"
                                                   "test"]
                                    :compiler     {:output-to      "test/.compiled/optimizations_advanced/chromex.test.js"
                                                   :output-dir     "test/.compiled/optimizations_advanced"
                                                   :asset-path     ".compiled/optimizations_advanced"
                                                   :main           chromex.runner
                                                   :optimizations  :advanced
                                                   :checked-arrays :warn
                                                   :elide-asserts  true}}}}}})
