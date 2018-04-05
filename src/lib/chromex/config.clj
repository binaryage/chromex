(ns chromex.config
  (:require [environ.core :refer [env]]
            [chromex.defaults :refer [default-gen-active-config
                                      default-gen-marshalling
                                      default-compiler-println]]))

(def ^:dynamic *target-api-version* (or (env :chromex-target-api-version) "latest"))
(def ^:dynamic *silence-compilation-warnings* (boolean (env :chromex-silence-compilation-warnings)))
(def ^:dynamic *elide-verbose-logging* (boolean (env :chromex-elide-verbose-logging)))
(def ^:dynamic *elide-missing-api-checks* (boolean (env :chromex-elide-missing-api-checks)))
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
   :debug-marshalling            *debug-marshalling*
   :elide-missing-api-checks     *elide-missing-api-checks*})

(defn gen-active-config [static-config]
  (let [gen-fn (:gen-active-config static-config)]
    (assert (and gen-fn (fn? gen-fn))
            (str "invalid :gen-active-config in chromex static config\n"
                 "static-config: " static-config))
    (gen-fn)))

(def fixed-config
  {:root                                                  'goog/global
   :callback-channel-factory                              'chromex.defaults/default-callback-channel-factory
   :callback-fn-factory                                   'chromex.defaults/default-callback-fn-factory
   :callback-error-reporter                               'chromex.defaults/default-callback-error-reporter
   :event-listener-factory                                'chromex.defaults/default-event-listener-factory
   :chrome-storage-area-callback-channel-factory          'chromex.defaults/default-chrome-storage-area-callback-channel-factory
   :chrome-storage-area-callback-fn-factory               'chromex.defaults/default-chrome-storage-area-callback-fn-factory
   :chrome-port-channel-factory                           'chromex.defaults/default-chrome-port-channel-factory
   :chrome-port-on-message-fn-factory                     'chromex.defaults/default-chrome-port-on-message-fn-factory
   :chrome-port-on-disconnect-fn-factory                  'chromex.defaults/default-chrome-port-on-disconnect-fn-factory
   :chrome-port-disconnect-called-on-disconnected-port    'chromex.defaults/default-chrome-port-disconnect-called-on-disconnected-port
   :chrome-port-post-message-called-on-disconnected-port  'chromex.defaults/default-chrome-port-post-message-called-on-disconnected-port
   :chrome-port-on-disconnect-called-on-disconnected-port 'chromex.defaults/default-chrome-port-on-disconnect-called-on-disconnected-port
   :chrome-port-on-message-called-on-disconnected-port    'chromex.defaults/default-chrome-port-on-message-called-on-disconnected-port
   :chrome-port-post-message-called-with-nil              'chromex.defaults/default-chrome-port-post-message-called-with-nil
   :chrome-port-received-nil-message                      'chromex.defaults/default-chrome-port-received-nil-message
   :chrome-port-put-message-called-on-disconnected-port   'chromex.defaults/default-chrome-port-put-message-called-on-disconnected-port})


; config has to be generated via a macro:
; in advanced optimizations in case of :elide-verbose-logging
; we must not mention chromex.defaults/default-logger at any place for it to get removed as a dead code
(defmacro gen-default-config []
  (let [static-config (get-static-config)]
    (merge
      fixed-config
      (if-not (:elide-verbose-logging static-config)
        {:verbose-logging false
         :logger          'chromex.defaults/default-logger})
      (if-not (:elide-missing-api-checks static-config)
        {:missing-api-check-fn 'chromex.defaults/default-missing-api-check}))))

(defmacro with-custom-config [f & body]
  `(binding [chromex.config/*active-config* (~f (chromex.config/get-active-config))]
     ~@body))

(defmacro with-custom-event-listener-factory [fn-factory & body]
  `(chromex.config/with-custom-config #(assoc % :event-listener-factory ~fn-factory) ~@body))

(defmacro with-muted-error-reporting [& body]
  `(chromex.config/with-custom-config #(dissoc % :callback-error-reporter) ~@body))
