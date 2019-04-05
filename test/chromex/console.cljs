(ns chromex.console
  (:require-macros [chromex.console])
  (:require [oops.core :refer [gcall! gget gset! oapply ocall oget oset!]]
            [clojure.string :as string]))

(defonce original-console-api (atom nil))
(defonce captured-console-content (atom []))

; -- capturing console output -----------------------------------------------------------------------------------------------

(defn handle-console-call [orig kind & args]
  (.apply orig js/console (to-array args))
  (swap! captured-console-content conj [kind (string/join " " args)]))

(defn store-console-api []
  {"log"   (gget "console.log")
   "warn"  (gget "console.warn")
   "info"  (gget "console.info")
   "error" (gget "console.error")})

(defn captured-console-api [original-api]
  {"log"   (partial handle-console-call (get original-api "log") :log)
   "warn"  (partial handle-console-call (get original-api "warn") :warn)
   "info"  (partial handle-console-call (get original-api "info") :info)
   "error" (partial handle-console-call (get original-api "error") :error)})

(defn set-console-api! [api]
  (gset! "console.log" (get api "log"))
  (gset! "console.warn" (get api "warn"))
  (gset! "console.info" (get api "info"))
  (gset! "console.error" (get api "error")))

(defn capture-console-as-feedback! []
  {:pre [(nil? @original-console-api)]}
  (reset! original-console-api (store-console-api))
  (set-console-api! (captured-console-api @original-console-api)))

(defn uncapture-console-as-feedback! []
  {:pre [(some? @original-console-api)]}
  (set-console-api! @original-console-api)
  (reset! original-console-api nil))

(defn reset-captured-content! []
  (reset! captured-console-content []))

(defn get-captured-console-content []
  @captured-console-content)