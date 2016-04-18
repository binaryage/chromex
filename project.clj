(defproject binaryage/chromex-sample "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.40"]
                 [org.clojure/core.async "0.2.374"]
                 [binaryage/chromex "0.4.0"]
                 [binaryage/devtools "0.6.1"]
                 [figwheel "0.5.2"]
                 [environ "1.0.2"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.2"]
            [lein-shell "0.5.0"]
            [lein-environ "1.0.2"]
            [lein-cooper "1.2.2"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["target"
                                    "resources/unpacked/compiled"
                                    "resources/release/compiled"]

  :cljsbuild {:builds {}}                                                                                                     ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413

  :profiles {:unpacked
             {:cljsbuild {:builds
                          {:background
                           {:source-paths ["src/dev"
                                           "src/figwheel"
                                           "src/background"]
                            :compiler     {:output-to             "resources/unpacked/compiled/background/chromex-sample.js"
                                           :output-dir            "resources/unpacked/compiled/background"
                                           :asset-path            "compiled/background"
                                           :optimizations         :none
                                           :anon-fn-naming-policy :unmapped
                                           :compiler-stats        true
                                           :cache-analysis        true
                                           :source-map            true
                                           :source-map-timestamp  true}}
                           :popup
                           {:source-paths ["src/dev"
                                           "src/figwheel"
                                           "src/popup"]
                            :compiler     {:output-to             "resources/unpacked/compiled/popup/chromex-sample.js"
                                           :output-dir            "resources/unpacked/compiled/popup"
                                           :asset-path            "compiled/popup"
                                           :optimizations         :none
                                           :anon-fn-naming-policy :unmapped
                                           :compiler-stats        true
                                           :cache-analysis        true
                                           :source-map            true
                                           :source-map-timestamp  true}}
                           :content-script
                           {:source-paths ["src/dev"
                                           "src/content_script"]
                            :compiler     {:output-to             "resources/unpacked/compiled/content_script/chromex-sample.js"
                                           :output-dir            "resources/unpacked/compiled/content_script"
                                           :asset-path            "compiled/content_script"
                                           :optimizations         :whitespace                                                 ; content scripts cannot do eval / load script dynamically
                                           :anon-fn-naming-policy :unmapped
                                           :pretty-print          true
                                           :compiler-stats        true
                                           :cache-analysis        true
                                           :source-map            "resources/unpacked/compiled/content_script/chromex-sample.js.map"
                                           :source-map-timestamp  true}}}}}
             :checkouts
             {:cljsbuild {:builds
                          {:background     {:source-paths ["checkouts/chromex/src/lib"
                                                           "checkouts/chromex/src/exts"]}
                           :popup          {:source-paths ["checkouts/chromex/src/lib"
                                                           "checkouts/chromex/src/exts"]}
                           :content-script {:source-paths ["checkouts/chromex/src/lib"
                                                           "checkouts/chromex/src/exts"]}}}}

             :figwheel
             {:figwheel {:server-port    6888
                         :server-logfile ".figwheel_dirac.log"
                         :repl           false}}
             :release
             {:env       {:chromex-elide-verbose-logging "true"}
              :cljsbuild {:builds
                          {:background
                           {:source-paths ["src/background"]
                            :compiler     {:output-to      "resources/release/compiled/background.js"
                                           :output-dir     "resources/release/compiled/background"
                                           :asset-path     "compiled/background"
                                           :optimizations  :advanced
                                           :elide-asserts  true
                                           :compiler-stats true}}
                           :popup
                           {:source-paths ["src/popup"]
                            :compiler     {:output-to      "resources/release/compiled/popup.js"
                                           :output-dir     "resources/release/compiled/popup"
                                           :asset-path     "compiled/popup"
                                           :optimizations  :advanced
                                           :elide-asserts  true
                                           :compiler-stats true}}
                           :content-script
                           {:source-paths ["src/content_script"]
                            :compiler     {:output-to      "resources/release/compiled/content_script.js"
                                           :output-dir     "resources/release/compiled/content_script"
                                           :asset-path     "compiled/content_script"
                                           :optimizations  :advanced
                                           :elide-asserts  true
                                           :compiler-stats true}}}}}}

  :aliases {"dev-build" ["with-profile" "+unpacked" "cljsbuild" "once" "background" "popup" "content-script"]
            "fig"       ["with-profile" "+unpacked" "figwheel" "background" "popup"]
            "content"   ["with-profile" "+unpacked" "cljsbuild" "auto" "content-script"]
            "devel"     ["do" "clean," "cooper"]
            "release"   ["with-profile" "+release" "do" "clean," "cljsbuild" "once" "background" "popup" "content-script"]
            "package"   ["shell" "scripts/package.sh"]})
