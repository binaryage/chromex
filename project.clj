(defproject binaryage/chromex-sample "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.2.374"]
                 [binaryage/chromex "0.0.1-SNAPSHOT"]
                 [prismatic/dommy "1.1.0"]
                 [binaryage/devtools "0.4.1"]
                 [figwheel "0.5.0-1"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-1"]]

  :figwheel
  {:server-port    7000
   :nrepl-port     7777
   :server-logfile ".tmp/figwheel_server.log"
   :css-dirs       []}

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["target"
                                    "resources/unpacked/compiled"]
  :cljsbuild {:builds
              {:background
               {:source-paths ["src/dev"
                               "src/figwheel"
                               "src/background"
                               "checkouts/chromex/src/lib"]
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
                               "src/popup"
                               "checkouts/chromex/src/lib"]
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
                               :optimizations         :whitespace                                                     ; content scripts cannot do eval / load script dynamically
                               :anon-fn-naming-policy :unmapped
                               :pretty-print          true
                               :compiler-stats        true
                               :cache-analysis        true
                               :source-map            "resources/unpacked/compiled/content_script/chromex-sample.js.map"
                               :source-map-timestamp  true}}}}

  :aliases {"build" ["cljsbuild" "once" "background" "popup" "content-script"]
            "fig"   ["figwheel" "background" "popup"]
            "content" ["cljsbuild" "auto" "content-script"]})
