(ns chromex-lib.support)

; -- helpers --------------------------------------------------------------------------------------------------------

(defn get-wrap-symbol [id]
  (symbol (str (namespace id)) (str (name id) "*")))

(defn get-item-by-id [id coll]
  (some #(if (= id (:id %)) %) coll))

(defn get-api-id [api-table descriptor]
  (str (:namespace api-table) "." (:name descriptor)))

; -- miscelaneous ---------------------------------------------------------------------------------------------------

(defn get-source-location [src-info]
  (let [{:keys [file line column]} src-info]
    (str "(in '" file ":" line ":" column "')")))

(defn print-compile-time-warning [src-info msg]
  (binding [*out* *err*]
    (println "WARNING:" msg (get-source-location src-info))))

; -- logging support ------------------------------------------------------------------------------------------------

(defn log-if-verbose [config & args]
  `(let [config# ~config]
     (if (:verbose-logging config#)
       (let [logger# (:logger config#)]
         (assert (and logger# (fn? logger#))
           "invalid :logger in chromex config")
         (logger# ~@args)))))

; -- api versioning -------------------------------------------------------------------------------------------------

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

(defn check-api-version [static-config src-info api-name & range]
  (let [version (:target-api-version static-config)]
    (if-not (version-in-range? version range)
      (print-compile-time-warning src-info
        (str "The API call to '" api-name "' is not available. "
          "Target API version '" version "' is not within required range " (user-friendly-range-str range)))))
  nil)

; -- deprecation warnings -------------------------------------------------------------------------------------------

(defn check-deprecated [_static-config src-info api-name deprecated]
  (if deprecated
    (print-compile-time-warning src-info
      (str "The API call to '" api-name "' is deprecated. " deprecated)))
  nil)