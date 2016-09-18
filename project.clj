(defproject binaryage/chromex-sample "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]
                 [org.clojure/clojurescript "1.9.229"]
                 [org.clojure/core.async "0.2.391"]
                 [binaryage/oops "0.1.0"]
                 [binaryage/chromex "0.4.2"]
                 [binaryage/devtools "0.8.2"]
                 [figwheel "0.5.7"]
                 [environ "1.1.0"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-figwheel "0.5.7"]
            [lein-shell "0.5.0"]
            [lein-environ "1.1.0"]
            [lein-cooper "1.2.2"]]

  :source-paths ["src/background"
                 "src/content_script"
                 "src/dev"
                 "src/figwheel"
                 "src/popup"]

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
                            :compiler     {:output-to     "resources/unpacked/compiled/background/chromex-sample.js"
                                           :output-dir    "resources/unpacked/compiled/background"
                                           :asset-path    "compiled/background"
                                           :optimizations :none
                                           :source-map    true}}
                           :popup
                           {:source-paths ["src/dev"
                                           "src/figwheel"
                                           "src/popup"]
                            :compiler     {:output-to     "resources/unpacked/compiled/popup/chromex-sample.js"
                                           :output-dir    "resources/unpacked/compiled/popup"
                                           :asset-path    "compiled/popup"
                                           :optimizations :none
                                           :source-map    true}}
                           :content-script
                           {:source-paths ["src/dev"
                                           "src/content_script"]
                            :compiler     {:output-to     "resources/unpacked/compiled/content_script/chromex-sample.js"
                                           :output-dir    "resources/unpacked/compiled/content_script"
                                           :asset-path    "compiled/content_script"
                                           :optimizations :whitespace                                                         ; content scripts cannot do eval / load script dynamically
                                           :pretty-print  true
                                           :source-map    "resources/unpacked/compiled/content_script/chromex-sample.js.map"}}}}}
             :checkouts
             ; DON'T FORGET TO UPDATE scripts/ensure-checkouts.sh
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

             :cooper
             {:cooper {"content-dev" ["lein" "content-dev"]
                       "fig-dev"     ["lein" "fig-dev"]
                       "browser"     ["scripts/launch-test-browser.sh"]}}

             :release
             {:env       {:chromex-elide-verbose-logging "true"}
              :cljsbuild {:builds
                          {:background
                           {:source-paths ["src/background"]
                            :compiler     {:output-to     "resources/release/compiled/background.js"
                                           :output-dir    "resources/release/compiled/background"
                                           :asset-path    "compiled/background"
                                           :optimizations :advanced
                                           :elide-asserts true}}
                           :popup
                           {:source-paths ["src/popup"]
                            :compiler     {:output-to     "resources/release/compiled/popup.js"
                                           :output-dir    "resources/release/compiled/popup"
                                           :asset-path    "compiled/popup"
                                           :optimizations :advanced
                                           :elide-asserts true}}
                           :content-script
                           {:source-paths ["src/content_script"]
                            :compiler     {:output-to     "resources/release/compiled/content_script.js"
                                           :output-dir    "resources/release/compiled/content_script"
                                           :asset-path    "compiled/content_script"
                                           :optimizations :advanced
                                           :elide-asserts true}}}}}}

  :aliases {"dev-build"   ["with-profile" "+unpacked,+checkouts" "cljsbuild" "once" "background" "popup" "content-script"]
            "fig"         ["with-profile" "+unpacked,+figwheel" "figwheel" "background" "popup"]
            "content"     ["with-profile" "+unpacked" "cljsbuild" "auto" "content-script"]
            "fig-dev"     ["with-profile" "+unpacked,+figwheel,+checkouts" "figwheel" "background" "popup"]
            "content-dev" ["with-profile" "+unpacked,+checkouts" "cljsbuild" "auto" "content-script"]
            "devel"       ["with-profile" "+cooper" "do"                                                                      ; for mac only
                           ["shell" "scripts/ensure-checkouts.sh"]
                           ["cooper"]]
            "release"     ["with-profile" "+release" "do"
                           ["clean"]
                           ["cljsbuild" "once" "background" "popup" "content-script"]]
            "package"     ["shell" "scripts/package.sh"]})
