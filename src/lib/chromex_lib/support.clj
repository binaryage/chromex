(ns chromex-lib.support)

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
    (binding [*out* *err*]
      (println "WARNING:" msg (get-source-location src-info)))))

(defn print-debug [msg]
  (binding [*out* *err*]
    (println "DEBUG:" msg)))

; -- logging support --------------------------------------------------------------------------------------------------------

(defn gen-logging-if-verbose [static-config config & args]
  (if-not (:elide-verbose-logging static-config)
    `(let [config# ~config]
       (if (:verbose-logging config#)
         (let [logger# (:logger config#)]
           (assert (and logger# (fn? logger#))
             "invalid :logger in chromex config")
           (logger# ~@args))))))

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

(defn emit-api-version-warning [static-config src-info api-name]
  (let [version (:target-api-version static-config)]
    (print-warning static-config src-info
      (str "The API call to '" api-name "' is not available. "
        "Target API version '" version "' is not within required range " (user-friendly-range-str range)))))


; -- deprecation warnings ---------------------------------------------------------------------------------------------------

(defn emit-deprecation-warning [static-config src-info api-name details]
  (print-warning static-config src-info
    (str "The API call to '" api-name "' is deprecated. " details)))