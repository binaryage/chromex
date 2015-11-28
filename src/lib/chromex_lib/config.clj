(ns chromex-lib.config
  (:require [environ.core :refer [env]]
            [chromex-lib.defaults :refer [default-gen-active-config
                                          default-gen-marshalling
                                          default-compiler-println]]))

(def ^:dynamic *target-api-version* (or (env :chromex-target-api-version) "latest"))
(def ^:dynamic *silence-compilation-warnings* (boolean (env :chromex-silence-compilation-warnings)))
(def ^:dynamic *elide-verbose-logging* (boolean (env :chromex-elide-verbose-logging)))
(def ^:dynamic *debug-marshalling* (boolean (env :chromex-debug-marshalling)))
(def ^:dynamic *gen-marshalling* default-gen-marshalling)
(def ^:dynamic *gen-active-config* default-gen-active-config)
(def ^:dynamic *compiler-println* default-compiler-println)

(defn get-static-config []
  {:target-api-version           *target-api-version*
   :silence-compilation-warnings *silence-compilation-warnings*
   :compiler-println             *compiler-println*
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

; config has to be generated via a macro:
; in advanced optimizations in case of :elide-verbose-logging
; we must not mention chromex-lib.defaults/default-logger at any place for it to get removed as a dead code
(defmacro gen-default-config []
  (let [static-config (get-static-config)]
    (merge
      {:callback-channel-factory 'chromex-lib.defaults/default-callback-channel-factory
       :callback-fn-factory      'chromex-lib.defaults/default-callback-fn-factory
       :event-listener-factory   'chromex-lib.defaults/default-event-listener-factory
       :root                     'js/goog.global}
      (if-not (:elide-verbose-logging static-config)
        {:verbose-logging false
         :logger          'chromex-lib.defaults/default-logger}))))

(defmacro with-custom-event-listener-factory [fn-factory & body]
  `(binding [chromex-lib.config/*active-config* (-> (chromex-lib.config/get-active-config)
                                                    (assoc :event-listener-factory ~fn-factory))]
     ~@body))