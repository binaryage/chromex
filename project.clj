(def clojurescript-version (or (System/getenv "CANARY_CLOJURESCRIPT_VERSION") "1.10.439"))
(defproject binaryage/chromex "0.7.0"
  :description "A ClojureScript wrapper for writing Chrome Extensions and Apps."
  :url "https://github.com/binaryage/chromex"
  :license {:name         "MIT License"
            :url          "http://opensource.org/licenses/MIT"
            :distribution :repo}

  :scm {:name "git" :url "https://github.com/binaryage/chromex"}

  :dependencies [[org.clojure/clojure "1.9.0" :scope "provided"]
                 [org.clojure/clojurescript ~clojurescript-version :scope "provided"]
                 [org.clojure/core.async "0.4.490"]
                 [binaryage/oops "0.6.4"]
                 [environ "1.1.0"]]

  :clean-targets ^{:protect false} ["target"
                                    "test/.compiled"]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-environ "1.1.0"]
            [lein-shell "0.5.0"]]

  ; this is just for IntelliJ + Cursive to play well, see :lib profile for real source paths
  :resource-paths ^:replace []
  :source-paths ["src/lib"
                 "src/exts"
                 "src/exts_private"
                 "src/exts_internal"
                 "src/apps"
                 "src/apps_private"
                 "src/apps_internal"
                 "scripts"]
  :test-paths ["test"]

  :jar-exclusions [#"readme\.md"]

  :cljsbuild {:builds {}}                                                                                                     ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413

  :profiles {:nuke-aliases
             {:aliases ^:replace {}}

             :lib
             ^{:pom-scope :provided}                                                                                          ; ! to overcome default jar/pom behaviour, our :dependencies replacement would be ignored for some reason
             [:nuke-aliases
              {:source-paths   ^:replace ["src/lib"
                                          "src/exts"
                                          "src/exts_private"
                                          "src/exts_internal"
                                          "src/apps"
                                          "src/apps_private"
                                          "src/apps_internal"]
               :resource-paths ^:replace []
               :test-paths     ^:replace []}]

             :dev-ext
             {:cljsbuild {:builds {:dev
                                   {:source-paths ["src/lib"
                                                   "src/exts"
                                                   "src/exts_private"
                                                   "src/exts_internal"]
                                    :compiler     {:output-to      "target/dev-ext/chromex.js"
                                                   :output-dir     "target/dev-ext"
                                                   :optimizations  :none
                                                   :checked-arrays :warn
                                                   :source-map     true}}}}}
             :dev-app
             {:cljsbuild {:builds {:dev
                                   {:source-paths ["src/lib"
                                                   "src/apps"
                                                   "src/apps_private"
                                                   "src/apps_internal"]
                                    :compiler     {:output-to      "target/dev-app/chromex.js"
                                                   :output-dir     "target/dev-app"
                                                   :optimizations  :none
                                                   :checked-arrays :warn
                                                   :source-map     true}}}}}
             :test
             {:env       {:running-dev-test "true"}
              :cljsbuild {:builds {:test-dev
                                   {:source-paths ["src/lib"
                                                   "test"]
                                    :compiler     {:output-to     "test/.compiled/optimizations_none/chromex.test.js"
                                                   :output-dir    "test/.compiled/optimizations_none"
                                                   :asset-path    ".compiled/optimizations_none"
                                                   :main          chromex.runner
                                                   :optimizations :none
                                                   :source-map    false}}}}}

             :test-advanced
             {:env       {:running-advanced-test            "true"
                          :chromex-elide-verbose-logging    "true"
                          :chromex-elide-missing-api-checks "true"}
              :cljsbuild {:builds {:test-advanced
                                   {:source-paths ["src/lib"
                                                   "test"]
                                    :compiler     {:output-to      "test/.compiled/optimizations_advanced/chromex.test.js"
                                                   :output-dir     "test/.compiled/optimizations_advanced"
                                                   :asset-path     ".compiled/optimizations_advanced"
                                                   :main           chromex.runner
                                                   :optimizations  :advanced
                                                   :checked-arrays :warn
                                                   :elide-asserts  true
                                                   :source-map     "test/.compiled/optimizations_advanced/chromex.test.js.map"}}}}}}

  :aliases {"test"          ["with-profile" "test" "do"
                             ["cljsbuild" "test"]
                             ["shell" "phantomjs" "test/phantom.js" "test/runner_none.html"]]
            "test-advanced" ["with-profile" "test-advanced" "do"
                             ["cljsbuild" "test"]
                             ["shell" "phantomjs" "test/phantom.js" "test/runner_advanced.html"]]
            "test-all"      ["do"
                             ["test"]
                             ["test-advanced"]]
            "install"       ["do"
                             ["shell" "scripts/prepare-jar.sh"]
                             ["shell" "scripts/local-install.sh"]]
            "jar"           ["shell" "scripts/prepare-jar.sh"]
            "release"       ["do"
                             ["clean"]
                             ["shell" "scripts/check-versions.sh"]
                             ["shell" "scripts/prepare-jar.sh"]
                             ["shell" "scripts/check-release.sh"]
                             ["shell" "scripts/deploy-clojars.sh"]]
            "deploy"        ["shell" "scripts/deploy-clojars.sh"]})
