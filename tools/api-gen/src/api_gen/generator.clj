(ns api-gen.generator
  (:require [clojure.walk :refer [postwalk]]
            [clojure.string :as string]
            [clojure.pprint :refer :all]
            [camel-snake-kebab.core :refer :all]
            [clostache.parser :refer :all]
            [cuerdas.core :as cuerdas]
            [api-gen.word-wrap :refer [wrap]]
            [clj-time.core :as time]
            [clj-time.format :as time-format]
            [api-gen.helpers :refer :all]))

(def ^:const NS-PREFIX "chromex.")
(def ^:const MAX-COLUMNS 126)                                                                                                 ; for nice github look
(def shared-template-data {:enter "\n"})
(def chromex-folder "chromex")

; ---------------------------------------------------------------------------------------------------------------------------

(defn pprint-api-table-as-str [api-table]
  (string/replace (pprint-edn-as-str api-table (- MAX-COLUMNS 8)) #":id :" ":id ::"))                                         ; HACK: turn ids into fully qualified keywords

(defn build-path [folder name ext]
  (let [parts (string/split (snake-case name) #"\.")
        parts-ext (concat (drop-last parts) [(str (last parts) ext)])]
    (vec (concat folder parts-ext))))

(defn build-cljs-ns-file-path [name]
  (build-path [chromex-folder] name ".cljs"))

(defn build-clj-ns-file-path [name]
  (build-path [chromex-folder] name ".clj"))

(defn build-ns-name [name]
  (str NS-PREFIX (kebab-case name)))

(defn build-intro-item [item]
  (let [{:keys [title content]} item
        {:keys [text version]} (first content)]
    (case title
      "Description" (if-not (or (empty? text) (= text "none")) [(reset-indent text)] [])
      "Availability" (if version [(str "  * available since Chrome " version)] [])
      [])))

(defn build-param-no-callback [parameter]
  (let [{:keys [name is-callback]} parameter
        param-name (kebab-case name)]
    (if-not is-callback
      [param-name])))

(defn build-param-comment-out-callback [parameter]
  (let [{:keys [name is-callback]} parameter
        param-name (kebab-case name)]
    (if is-callback
      [(str "#_" param-name)]
      [param-name])))

(defn build-param-list [parameters f]
  (let [params (string/join " " (mapcat f parameters))]
    (or params "")))

(defn wrap-param-doc [param]
  (str "|" param "|"))

(defn plain-doc [docstring]
  (-> docstring
    (cuerdas/strip-tags)
    (replace-refs)))

(defn build-param-doc [indent columns parameter]
  (if-let [description (:description parameter)]
    (let [{:keys [name]} parameter
          prefix (str "  " (wrap-param-doc name) " - ")
          plain-description (plain-doc description)
          description-indent (count prefix)
          wrapped-description (wrap-text description-indent (- columns indent) plain-description)]
      [(str prefix wrapped-description)])))

(defn build-docstring [indent description parameters & extras]
  (let [columns (- MAX-COLUMNS indent)
        desc (if description (wrap-text 0 columns (plain-doc description)) "")
        params (string/join "\n" (mapcat (partial build-param-doc 2 columns) parameters))
        parts (remove empty? [desc params])
        docstring (apply str (string/join "\n\n" parts) extras)]
    (wrap-docstring indent MAX-COLUMNS docstring)))

(defn build-ns-docstring [name intro-list]
  (if-let [intro (plain-doc (string/join "\n\n" (mapcat build-intro-item intro-list)))]
    (let [intro-with-link (str intro "\n  * https://developer.chrome.com/extensions/" name)]
      (wrap-docstring 2 MAX-COLUMNS intro-with-link))))

(declare extract-type)

(defn extract-compound-type [data]
  (if (seq data)
    (string/join (interpose "-or-" (map extract-type data)))))

(defn extract-ref [data]
  (get-in data [:link :ref]))

(defn extract-simple-type [data]
  (or (extract-ref data) (:simple-type data) "unknown-type"))

(defn extract-array-type [data]
  (if-let [array (:array data)]
    (str "[array-of-" (extract-simple-type array) "s]")))

(defn extract-type [data]
  (or
    (extract-compound-type (:choices data))
    (extract-array-type data)
    (extract-simple-type data)))

(defn extract-avail-since [data]
  (if-let [version (get-in data [:availability :version])]
    (str version)))

(defn extract-avail-until [_data])                                                                                            ; note: it looks like nothing has been removed

(defn extract-namespace-since [data]
  (let [{:keys [intro-list]} data]
    (if-let [availability (some #(if (= (:title %) "Availability") %) intro-list)]
      (str (get-in availability [:content 0 :version])))))

(defn extract-deprecated [data]
  (if-let [deprecated (:deprecated data)]
    (plain-doc deprecated)))

(defn build-id [name]
  (keyword (kebab-case name)))

(defn build-function-docstring [description parameters callback?]
  (let [callback-doc "\n\nNote: Instead of passing a callback function, you receive a core.async channel as return value."]
    (build-docstring 2 description parameters (if callback? callback-doc ""))))

; ---------------------------------------------------------------------------------------------------------------------------

(declare build-api-table-param-info)

(defn build-api-table-callback-info [callback-data]
  (let [{:keys [parameters]} callback-data]
    {:params (vec (map (partial build-api-table-param-info nil) parameters))}))

(defn build-api-table-param-info [callback-data data]
  (let [{:keys [name is-callback optional]} data
        param-name (kebab-case name)
        base-part {:name      param-name
                   :optional? optional}
        flexible-part (if-not is-callback
                        {:type (extract-type data)}
                        {:type     :callback
                         :callback (build-api-table-callback-info callback-data)})]
    (merge base-part flexible-part)))

(defn build-api-table-function-params [parameters callback-data]
  (vec (map (partial build-api-table-param-info callback-data) parameters)))

(defn build-api-table-function [data]
  (let [{:keys [name description parameters returns callback]} data
        callback? (not (nil? callback))]
    (with-meta
      {:id          (build-id name)
       :name        name
       :since       (extract-avail-since data)
       :until       (extract-avail-until data)
       :deprecated  (extract-deprecated data)
       :callback?   callback?
       :return-type (if returns (extract-type returns))
       :params      (build-api-table-function-params parameters callback)}
      {:doc             (build-function-docstring description parameters callback?)
       :user-param-list (build-param-list parameters build-param-comment-out-callback)
       :param-list      (build-param-list parameters build-param-no-callback)})))

(defn build-api-table-functions [function-names data]
  (let [* (fn [name]
            (let [function-data (get-in data [:by-name (keyword name)])]
              (assert function-data (str "unable to lookup function by name '" (keyword name)
                                      "' in\n" (keys (get data :by-name))))
              (build-api-table-function function-data)))]
    (vec (map * function-names))))

(defn build-api-table-property [data]
  (let [{:keys [name description parameters]} data]
    (with-meta
      {:id          (build-id name)
       :name        name
       :since       (extract-avail-since data)
       :until       (extract-avail-until data)
       :deprecated  (extract-deprecated data)
       :return-type (extract-type data)}
      {:doc (build-docstring 2 description parameters)})))

(defn build-api-table-properties [data]
  (vec (map build-api-table-property data)))

(defn build-api-table-event [data]
  (let [{:keys [name description parameters]} data
        callback-data (get-in data [:by-name :add-listener :callback])
        filters (:filters data)]
    (with-meta
      {:id               (build-id name)
       :name             name
       :since            (extract-avail-since data)
       :until            (extract-avail-until data)
       :deprecated       (extract-deprecated data)
       :supports-filters (not (empty? filters))
       :params           (:params (build-api-table-callback-info callback-data))}
      {:doc (build-docstring 2 description parameters)})))

(defn build-api-table-events [data]
  (vec (map build-api-table-event data)))

(defn build-namespace-api-table [data]
  (let [{:keys [namespace functions properties events name intro-list]} data
        function-names (map (comp kebab-case :name) functions)]
    (with-meta
      {:namespace  namespace
       :since      (extract-namespace-since data)
       :deprecated (extract-deprecated data)
       :properties (build-api-table-properties properties)
       :functions  (build-api-table-functions function-names data)
       :events     (build-api-table-events events)}
      {:name name
       :doc  (build-ns-docstring name intro-list)})))

; ---------------------------------------------------------------------------------------------------------------------------

(defn number-of-trailing-optional-arguments-except-callbacks [params]
  (let [is-callback? (fn [param] (= (:type param) :callback))]
    (count (take-while :optional? (reverse (remove is-callback? params))))))

(defn build-optional-arity [fn-name param-tokens param-count total-count]
  (let [params (take param-count param-tokens)
        param-list (string/join " " params)
        tilde-params (map #(str "~" %) params)
        omit-count (- total-count param-count)
        omit-params (repeat omit-count ":omit")
        call-params (concat tilde-params omit-params)
        arg-list (string/join " " call-params)]
    (str "([" param-list "] `(" fn-name " " arg-list "))")))

(defn build-optional-arities [fn-name param-list opt-args-count]
  (let [param-tokens (string/split param-list #" ")
        total-count (count param-tokens)]
    (string/join "\n"
      (reverse
        (for [cnt (range (- total-count opt-args-count) total-count)]
          (build-optional-arity fn-name param-tokens cnt total-count))))))

(defn prepare-function-data [data]
  (let [{:keys [name params]} data
        {:keys [doc user-param-list param-list]} (meta data)
        optional-args-count (number-of-trailing-optional-arguments-except-callbacks params)
        has-optionals (pos? optional-args-count)
        fn-name (kebab-case name)]
    {:fn-name            fn-name
     :fn-docstring       doc
     :has-optionals      has-optionals
     :additional-arities (if has-optionals (build-optional-arities fn-name param-list optional-args-count))
     :user-param-list    user-param-list
     :param-list         param-list}))

(defn prepare-property-data [data]
  (let [{:keys [name]} data
        {:keys [doc]} (meta data)]
    {:prop-name      (kebab-case name)
     :prop-docstring doc}))

(defn prepare-event-data [data]
  (let [{:keys [name supports-filters]} data
        {:keys [doc]} (meta data)]
    {:event-name      (kebab-case name)
     :event-docstring doc
     :supports-filters supports-filters}))

; ---------------------------------------------------------------------------------------------------------------------------

(defn generate-cljs-ns [api-table]
  (let [{:keys [functions properties events]} api-table
        {:keys [name doc]} (meta api-table)
        file-path (build-cljs-ns-file-path name)
        ns-name (build-ns-name name)
        properties (map prepare-property-data properties)
        functions (map prepare-function-data functions)
        events (map prepare-event-data events)
        template-data (merge
                        shared-template-data
                        {:ns-name        ns-name
                         :ns-docstring   doc
                         :properties     properties
                         :has-properties (has-any? properties)
                         :functions      functions
                         :has-functions  (has-any? functions)
                         :events         events
                         :has-events     (has-any? events)})
        partials (load-partials [:cljs-function
                                 :cljs-property
                                 :cljs-event])
        content (render-resource "templates/cljs-ns.mustache" template-data partials)]
    [file-path (post-process content)]))

; ---------------------------------------------------------------------------------------------------------------------------

(defn generate-clj-ns [api-table]
  (let [{:keys [functions properties events]} api-table
        {:keys [name doc]} (meta api-table)
        file-path (build-clj-ns-file-path name)
        ns-name (build-ns-name name)
        properties (map prepare-property-data properties)
        functions (map prepare-function-data functions)
        events (map prepare-event-data events)
        template-data (merge
                        shared-template-data
                        {:ns-name        ns-name
                         :api-table      (pprint-api-table-as-str api-table)
                         :ns-docstring   doc
                         :properties     properties
                         :has-properties (has-any? properties)
                         :functions      functions
                         :has-functions  (has-any? functions)
                         :events         events
                         :has-events     (has-any? events)})
        partials (load-partials [:clj-function
                                 :clj-property
                                 :clj-event])
        content (render-resource "templates/clj-ns.mustache" template-data partials)]
    [file-path (post-process content)]))

; ---------------------------------------------------------------------------------------------------------------------------

(defn generate-stats [api-tables]
  (let [table-stats (fn [table]
                      {:namespace  (:namespace table)
                       :functions  (count (:functions table))
                       :properties (count (:properties table))
                       :events     (count (:events table))})
        * (fn [stats table]
            (let [ts (table-stats table)]
              (-> stats
                (update :namespaces conj ts)
                (update :total-functions + (:functions ts))
                (update :total-properties + (:properties ts))
                (update :total-events + (:events ts)))))]
    (reduce * {:namespaces       []
               :total-functions  0
               :total-properties 0
               :total-events     0} api-tables)))

; ---------------------------------------------------------------------------------------------------------------------------

(def generation-date-formatter (time-format/formatters :date))

(defn build-api-files [api-tables]
  (for [api-table api-tables]
    (let [{:keys [namespace]} api-table
          {:keys [name]} (meta api-table)]
      {:js-namespace  namespace
       :clj-namespace (build-ns-name name)
       :doc-section   name
       :file-name     (string/join "/" (build-clj-ns-file-path name))})))

(defn build-require [api-table columns]
  (let [{:keys [functions properties events]} api-table
        {:keys [name]} (meta api-table)
        clj-namespace (build-ns-name name)
        prefix (str "[" clj-namespace " refer-macros:[\n")
        prefix2 "  "
        prefix-length 2
        postfix (str "]]")
        function-names (map #(kebab-case (:name %)) functions)
        property-names (map #(str "get-" (kebab-case (:name %))) properties)
        event-names (map #(str "tap-" (kebab-case (:name %))) events)
        convenience-names ["tap-all-events"]
        names (string/join " " (concat property-names function-names event-names convenience-names))
        wrapped-text (wrap-text prefix-length columns names)]
    (str prefix prefix2 wrapped-text postfix)))

(defn build-requires [api-tables]
  (let [l1 "(ns your.project\n"
        l2 "  (:require\n"
        l3 "    "
        le "))"
        indent 4
        columns (- (- MAX-COLUMNS 20) indent)
        text (string/join "\n\n" (map #(build-require % columns) api-tables))
        prefixed-text (prefix-text (indent-str indent) text)]
    (str l1 l2 l3 prefixed-text le)))

(defn sort-api-tables [api-tables]
  (sort #(compare (str (:namespace %1)) (str (:namespace %2))) api-tables))

(defn generate-readme [chromium-sha api-tables]
  (let [api-tables (sort-api-tables api-tables)
        stats (generate-stats api-tables)
        {:keys [namespaces total-functions total-properties total-events]} stats
        stats-table (with-out-str (print-table [:namespace :properties :functions :events] namespaces))
        template-data (merge
                        shared-template-data
                        {:generation-date   (time-format/unparse generation-date-formatter (time/now))
                         :generation-source (str "[Chromium @ " (subs chromium-sha 0 7)
                                              "](https://chromium.googlesource.com/chromium/src.git/+/" chromium-sha ")")
                         :requires          (build-requires api-tables)
                         :files             (build-api-files api-tables)
                         :total-properties  total-properties
                         :total-functions   total-functions
                         :total-events      total-events
                         :total-namespaces  (count namespaces)
                         :stats-table       (prefix-text "    " stats-table)})
        content (render-resource "templates/readme.mustache" template-data)]
    ["readme.md" content]))

; ---------------------------------------------------------------------------------------------------------------------------

(defn generate-files-for-table [api-table]
  (println (str "generating files for '" (:namespace api-table) "'"))
  [(generate-cljs-ns api-table)
   (generate-clj-ns api-table)])

; ---------------------------------------------------------------------------------------------------------------------------

(defn build-api-table [ns]
  (println (str "preparing '" (:name ns) "'"))
  (let [api-table (-> ns
                    (build-namespace-api-table)
                    (remove-emptyish-values))
        {:keys [functions properties events]} api-table
        cnt (+ (count functions) (count properties) (count events))]
    (if (pos? cnt)
      api-table)))

(defn build-tag [k]
  (let [ext? (re-matches #"\:extensions\/.*" k)
        app? (re-matches #"\:apps\/.*" k)
        private? (re-matches #".*-private" k)
        internal? (re-matches #".*-internal" k)
        items (vector (if ext? "exts") (if app? "apps") (if private? "private") (if internal? "internal"))]
    (string/join "-" (remove nil? items))))

(defn matches-filter? [item tag]
  (if (or (not tag) (empty? tag))
    true
    (let [k (str (first item))
          item-tag (build-tag k)]
      (= tag item-tag))))

(defn build-api-tables [data tag]
  (let [filtered-data (filter #(matches-filter? % tag) data)]
    (keep (comp build-api-table second) filtered-data)))

; ---------------------------------------------------------------------------------------------------------------------------

(defn generate-files [chromium-sha api-tables]
  (conj (mapcat generate-files-for-table api-tables) (generate-readme chromium-sha api-tables)))