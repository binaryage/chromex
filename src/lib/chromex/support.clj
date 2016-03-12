(ns chromex.support)

; -- we don't want to rely on externs ---------------------------------------------------------------------------------------

(defmacro ocall [o name & params]
  `(let [o# ~o]
     (.call (goog.object/get o# ~name) o# ~@params)))

(defmacro oapply [o name param-coll]
  `(let [o# ~o]
     (.apply (goog.object/get o# ~name) o# (into-array ~param-coll))))

(defmacro oget
  ([o k1] `(goog.object/get ~o ~k1))
  ([o k1 k2] `(when-let [o# (goog.object/get ~o ~k1)]
                (goog.object/get o# ~k2)))
  ([o k1 k2 & ks] `(when-let [o# (goog.object/get ~o ~k1)]
                     (oget o# ~k2 ~@ks))))

(defmacro oset [o ks val]
  (let [keys (butlast ks)
        obj-sym (gensym)]
    `(let [~obj-sym ~o
           target# ~(if (seq keys) `(chromex.support/oget ~obj-sym ~@keys) obj-sym)]
       (assert target# (str "unable to locate object path " ~keys " in " ~obj-sym))
       (goog.object/set target# (last ~ks) ~val))))

(defn gen-call-hook [config handler-key & args]
  `(let [config# ~config
         handler-key# ~handler-key
         handler# (handler-key# config#)]
     (assert (fn? handler#) (str "invalid " handler-key# " in chromex config\n" "config: " config#))
     (handler# config# ~@args)))

(defmacro call-hook [config handler-key & args]
  (apply gen-call-hook config handler-key args))

(defn gen-get-hook [config handler-key]
  `(let [config# ~config
         handler-key# ~handler-key
         handler# (handler-key# config#)]
     (assert (fn? handler#) (str "invalid " handler-key# " in chromex config\n" "config: " config#))
     (partial handler# config#)))

(defmacro get-hook [config handler-key]
  (gen-get-hook config handler-key))

; -- helpers ----------------------------------------------------------------------------------------------------------------

(defn get-wrap-symbol [id]
  (symbol (str (namespace id)) (str (name id) "*")))

(defn get-item-by-id [id coll]
  (some #(if (= id (:id %)) %) coll))

(defn get-api-id [api-table descriptor]
  (str (:namespace api-table) "." (:name descriptor)))

(defn get-src-info [form]
  (meta form))

; -- miscelaneous -----------------------------------------------------------------------------------------------------------

(defn get-source-location [src-info]
  (let [{:keys [file line column]} src-info]
    (str "(in '" file ":" line ":" column "')")))

(defn print-warning [static-config src-info msg]
  (if-not (:silence-compilation-warnings static-config)
    (let [println-fn (:compiler-println static-config)]
      (assert println-fn (str ":compiler-println not specified in static-config: " static-config))
      (println-fn "WARNING:" msg (get-source-location src-info)))))

(defn print-debug [msg]
  (binding [*out* *err*]
    (println "DEBUG:" msg)))

; -- logging support --------------------------------------------------------------------------------------------------------

(defn gen-logging-if-verbose [static-config config operation api args-array]
  (if-not (:elide-verbose-logging static-config)                                                                              ; static compile-time switch
    `(let [config# ~config]
       (if (:verbose-logging config#)                                                                                         ; dynamic toggle for verbose logging
         (let [logger# (:logger config#)
               prefix# (cljs.core/array ~operation ~api)]
           (assert (fn? logger#) (str "invalid :logger in chromex config\n" "config:" config#))
           (.apply logger# nil (.concat prefix# ~args-array)))))))

; -- missing API checking ---------------------------------------------------------------------------------------------------

(defn gen-missing-api-check [static-config config api obj-sym key-sym]
  (if-not (:elide-missing-api-checks static-config)
    `(let [config# ~config
           api-check-fn# (:missing-api-check-fn config#)]
       (assert (fn? api-check-fn#) (str "invalid :api-check-fn in chromex config\n" "config:" config#))
       (api-check-fn# ~api ~obj-sym ~key-sym))))

; -- api versioning ---------------------------------------------------------------------------------------------------------

; http://stackoverflow.com/a/12503724/84283
(defn parse-int [s]
  (Integer. (re-find #"\d+" s)))

; here we rely on sane version strings like "24" or "9"
(defn api-version-num [v]
  (case v
    "latest" 1000000
    (num (parse-int (str v)))))

(defn api-version-compare [v1 v2]
  (let [n1 (api-version-num v1)
        n2 (api-version-num v2)]
    (compare n1 n2)))

(defn version-in-range? [current [since until]]
  (let [current (api-version-num current)]
    (and
      (or (nil? since) (not (neg? (api-version-compare current since))))
      (or (nil? until) (not (pos? (api-version-compare current until)))))))

(defn user-friendly-range-str [[left right]]
  (str "[" (or left "*") " - " (or right "*") "]"))

(defn valid-api-version? [static-config & range]
  (let [version (:target-api-version static-config)]
    (version-in-range? version range)))

(defn emit-api-version-warning [static-config src-info api-name range]
  (let [version (:target-api-version static-config)]
    (print-warning static-config src-info
                   (str "The API call to '" api-name "' is not available. "
                        "Target API version '" version "' is not within required range " (user-friendly-range-str range)))))

; -- deprecation warnings ---------------------------------------------------------------------------------------------------

(defn emit-deprecation-warning [static-config src-info api-name details]
  (print-warning static-config src-info
                 (str "The API call to '" api-name "' is deprecated. " details)))