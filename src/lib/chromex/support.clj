(ns chromex.support)

; -- hooks ------------------------------------------------------------------------------------------------------------------

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

(defmacro runonce [& body]
  (let [code (cons 'do body)
        code-string (pr-str code)
        code-hash (hash code-string)
        name (symbol (str "runonce_" code-hash))]
    `(defonce ~name {:value ~code
                     :code  ~code-string})))

; -- miscellaneous ----------------------------------------------------------------------------------------------------------

(defn get-source-location [src-info]
  (let [{:keys [file line column]} src-info]
    (str "(in '" file ":" line ":" column "')")))

(defn print-warning [static-config src-info msg]
  (when-not (:silence-compilation-warnings static-config)
    (let [println-fn (:compiler-println static-config)]
      (assert println-fn (str ":compiler-println not specified in static-config: " static-config))
      (println-fn "WARNING:" msg (get-source-location src-info)))))

(defn print-debug [msg]
  (binding [*out* *err*]
    (println "DEBUG:" msg)))

; -- logging support --------------------------------------------------------------------------------------------------------

(defn gen-logging-if-verbose [static-config config operation api args-array]
  (when-not (:elide-verbose-logging static-config)                                                                            ; static compile-time switch
    `(let [config# ~config]
       (if (:verbose-logging config#)                                                                                         ; dynamic toggle for verbose logging
         (let [logger# (:logger config#)
               prefix# (cljs.core/array ~operation ~api)]
           (assert (fn? logger#) (str "invalid :logger in chromex config\n" "config:" config#))
           (.apply logger# nil (.concat prefix# ~args-array)))))))

; -- missing API checking ---------------------------------------------------------------------------------------------------

(defn gen-missing-api-check [static-config config api obj-sym key-sym]
  (when-not (:elide-missing-api-checks static-config)
    `(let [config# ~config
           api-check-fn# (:missing-api-check-fn config#)]
       (assert (fn? api-check-fn#) (str "invalid :api-check-fn in chromex config\n" "config:" config#))
       (api-check-fn# ~api ~obj-sym ~key-sym))))

; -- api versioning ---------------------------------------------------------------------------------------------------------

(def ^:dynamic latest-api-version 1000000)
(def ^:dynamic future-api-version 1000001)                                                                                    ; apis scheduled for future introduction, only available on dev channels

; http://stackoverflow.com/a/12503724/84283
(defn parse-int [s]
  (Integer. (re-find #"\d+" s)))

; here we rely on sane version strings like "24" or "9"
(defn api-version-num [v]
  (case v
    "latest" latest-api-version
    ; sometimes newly introduced apis temporarily specify :since "master" instead of specific Chrome version
    ; e.g. https://github.com/binaryage/chromex/commit/5add1479ed0b1491b8c8b6ae5f00a690de4e5416#diff-a8df69996aa69518c645721ecd31a3bdR328
    "master" latest-api-version                                                                                               ; only "latest" target-api-version will match "master"
    "future" future-api-version
    (num (parse-int (str v)))))

(defn api-version-compare [v1 v2]
  (let [n1 (api-version-num v1)
        n2 (api-version-num v2)]
    (compare n1 n2)))

(defn version-in-range? [current [since until]]
  (and
    (or (nil? since) (not (neg? (api-version-compare current since))))
    (or (nil? until) (not (pos? (api-version-compare current until))))))

(defn user-friendly-range-str [[left right]]
  (str "[" (or left "*") " - " (or right "*") "]"))

(defn valid-api-version? [static-config & range]
  (let [version (:target-api-version static-config)]
    (version-in-range? version range)))

(defn blacklisted-api? [_static-config api-descriptor]
  ; although onConnectNative is included in the api table, currently it is supported only on Chrome OS
  ; https://developer.chrome.com/extensions/runtime#event-onConnectNative
  (some? (#{:chromex.ext.runtime/on-connect-native} (:id api-descriptor))))

(defn emit-api-version-warning [static-config src-info api-name range]
  (let [version (:target-api-version static-config)]
    (print-warning static-config src-info
                   (str "The API call to '" api-name "' is not available. "
                        "Target API version '" version "' is not within required range " (user-friendly-range-str range)))))

; -- deprecation warnings ---------------------------------------------------------------------------------------------------

(defn emit-deprecation-warning [static-config src-info api-name details]
  (print-warning static-config src-info
                 (str "The API call to '" api-name "' is deprecated. " details)))
