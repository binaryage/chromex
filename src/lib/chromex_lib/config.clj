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