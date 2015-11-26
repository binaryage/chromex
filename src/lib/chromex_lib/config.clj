(ns chromex-lib.config
  (:require [chromex-lib.defaults :refer [default-gen-active-config default-gen-marshalling]]))

(def ^:dynamic *target-api-version* "latest")
(def ^:dynamic *silence-compilation-warnings* false)
(def ^:dynamic *gen-marshalling* default-gen-marshalling)
(def ^:dynamic *gen-active-config* default-gen-active-config)
(def ^:dynamic *elide-verbose-logging* false)
(def ^:dynamic *debug-marshalling* false)

(defn get-static-config []
  {:target-api-version           *target-api-version*
   :silence-compilation-warnings *silence-compilation-warnings*
   :gen-marshalling              *gen-marshalling*
   :gen-active-config            *gen-active-config*
   :elide-verbose-logging        *elide-verbose-logging*
   :debug-marshalling            *debug-marshalling*})

(defn gen-active-config [static-config]
  (let [gen-fn (:gen-active-config static-config)]
    (assert (and gen-fn (fn? gen-fn))
            (str "invalid :gen-active-config in chromex static config\n"
                 "static-config: " static-config))
    (gen-fn)))

; config has to be generated via macro, for advanced optimizations
; in case of :elide-verbose-logging we must not mention chromex-lib.defaults/default-logger in any place
; for it get removed as a dead code
(defmacro gen-default-config []
  (let [static-config (get-static-config)]
    (merge
      {:callback-channel-factory 'chromex-lib.defaults/default-callback-channel-factory
       :callback-fn-factory      'chromex-lib.defaults/default-callback-fn-factory
       :event-fn-factory         'chromex-lib.defaults/default-event-fn-factory
       :root                     'js/goog.global}
      (if-not (:elide-verbose-logging static-config)
        {:verbose-logging false
         :logger          'chromex-lib.defaults/default-logger}))))