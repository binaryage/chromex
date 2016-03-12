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

(def ^:const NS-PREFIX "chromex")
(def ^:const CHROMEX-FOLDER "chromex")
(def ^:const MAX-COLUMNS 126)                                                                                                 ; for nice github look
(def shared-template-data {:enter "\n"})

; ---------------------------------------------------------------------------------------------------------------------------

(defn pprint-api-table-as-str [api-table]
  (string/replace (pprint-edn-as-str api-table (- MAX-COLUMNS 8)) #":id :" ":id ::"))                                         ; HACK: turn ids into fully qualified keywords

(defn build-path [folder name ext]
  (let [parts (string/split (snake-case name) #"\.")
        parts-ext (concat (drop-last parts) [(str (munge-if-reserved (last parts)) ext)])]
    (vec (concat folder parts-ext))))

(defn join-subns-and-name [subns name]
  (if (empty? subns)
    name
    (str subns "." name)))

(defn build-cljs-ns-file-path [name subns]
  (build-path [CHROMEX-FOLDER] (join-subns-and-name subns name) ".cljs"))

(defn build-clj-ns-file-path [name subns]
  (build-path [CHROMEX-FOLDER] (join-subns-and-name subns name) ".clj"))

(defn build-ns-name [name subns]
  (string/join "." (remove empty? [NS-PREFIX subns (kebab-case (munge-if-reserved name))])))

(defn build-ns-link [name]
  (str "https://developer.chrome.com/extensions/" name))

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

(defn safe-link [url hash]
  (str url "#" (encode-url-param hash)))

(defn build-context-link [context]
  (let [{:keys [ns-name property-name function-name event-name param-name]} context
        ns-link (build-ns-link ns-name)]
    (cond
      param-name (safe-link ns-link (str "property-"
                                         (first (keep identity [property-name function-name event-name]))
                                         "-"
                                         param-name))
      property-name (safe-link ns-link (str "property-" property-name))
      function-name (safe-link ns-link (str "method-" function-name))
      event-name (safe-link ns-link (str "event-" event-name))
      :else ns-link)))

(defn link-doc [context]
  (str "See " (build-context-link context) "."))

(defn build-param-doc [context indent columns parameter]
  (if-not (:is-callback parameter)
    (let [{:keys [name description]} parameter
          context (assoc context :param-name name)
          prefix (str "  " (wrap-param-doc name) " - ")
          plain-description (if description (plain-doc description) (link-doc context))
          description-indent (count prefix)
          wrapped-description (wrap-text description-indent (- columns indent) plain-description)]
      [(str prefix wrapped-description)])))

(defn build-docstring [context indent description parameters & extras]
  (let [columns (- MAX-COLUMNS indent)
        desc (if description (wrap-text 0 columns (plain-doc description)) "")
        params (string/join "\n" (mapcat (partial build-param-doc context 2 columns) parameters))
        parts (remove empty? [desc params])
        docstring (apply str (string/join "\n\n" parts) extras)]
    (wrap-docstring indent MAX-COLUMNS docstring)))

(defn build-docstring-with-link [context & args]
  (apply build-docstring context (concat args [(str "\n\n" (link-doc context))])))

(defn build-ns-docstring [name intro-list]
  (if-let [intro (plain-doc (string/join "\n\n" (mapcat build-intro-item intro-list)))]
    (let [intro-with-link (str intro "\n  * " (build-ns-link name))]
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

(defn build-callback-signature [callback]
  (let [{:keys [parameters]} callback
        names (keep :name parameters)]
    (str "[" (apply str (interpose " " names)) "]")))

(defn build-callback-param-docs [context callback]
  (let [{:keys [parameters]} callback]
    (mapcat (partial build-param-doc context 2 MAX-COLUMNS) parameters)))

(defn build-callback-docstring [context callback]
  (let [intro "\n\nThis function returns a core.async channel which will eventually receive a result value and closes."
        signature (str "\nSignature of the result value put on the channel is " (build-callback-signature callback))
        context (assoc context :property-name (:name callback))                                                               ; generated IDs in official docs are not unique, they can shadow properties under some circumstances
        param-docs (build-callback-param-docs context callback)
        rest (if (empty? param-docs) "." (str " where:\n\n" (apply str (string/join "\n" param-docs))))]
    (str intro signature rest)))

(defn build-function-docstring [context description parameters callback]
  (build-docstring-with-link context 2 description parameters (if callback (build-callback-docstring context callback) "")))

(defn build-event-docstring [context description parameters]
  (let [extra-args-doc "\nEvents will be put on the |channel|.\n\nNote: |args| will be passed as additional parameters into Chrome event's .addListener call."]
    (build-docstring-with-link context 2 description parameters extra-args-doc)))

(defn build-property-docstring [context description parameters]
  (build-docstring-with-link context 2 description parameters))

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

(defn build-api-table-function [context data]
  (let [{:keys [name description parameters returns callback]} data
        context (assoc context :function-name name)]
    (with-meta
      {:id          (build-id name)
       :name        name
       :since       (extract-avail-since data)
       :until       (extract-avail-until data)
       :deprecated  (extract-deprecated data)
       :callback?   (some? callback)
       :return-type (if returns (extract-type returns))
       :params      (build-api-table-function-params parameters callback)}
      {:doc             (build-function-docstring context description parameters callback)
       :user-param-list (build-param-list parameters build-param-comment-out-callback)
       :param-list      (build-param-list parameters build-param-no-callback)})))

(defn build-api-table-functions [context function-names data]
  (let [* (fn [name]
            (let [function-data (get-in data [:by-name (keyword name)])]
              (assert function-data (str "unable to lookup function by name '" (keyword name)
                                         "' in\n" (keys (get data :by-name))))
              (build-api-table-function context function-data)))]
    (vec (map * function-names))))

(defn build-api-table-property [context data]
  (let [{:keys [name description parameters]} data
        context (assoc context :property-name name)]
    (with-meta
      {:id          (build-id name)
       :name        name
       :since       (extract-avail-since data)
       :until       (extract-avail-until data)
       :deprecated  (extract-deprecated data)
       :return-type (extract-type data)}
      {:doc (build-property-docstring context description parameters)})))

(defn build-api-table-properties [context data]
  (vec (map (partial build-api-table-property context) data)))

(defn build-api-table-event [context data]
  (let [{:keys [name description parameters]} data
        callback-data (get-in data [:by-name :add-listener :callback])
        context (assoc context :event-name name)]
    (with-meta
      {:id         (build-id name)
       :name       name
       :since      (extract-avail-since data)
       :until      (extract-avail-until data)
       :deprecated (extract-deprecated data)
       :params     (:params (build-api-table-callback-info callback-data))}
      {:doc (build-event-docstring context description parameters)})))

(defn build-api-table-events [context data]
  (vec (map (partial build-api-table-event context) data)))

(defn build-namespace-api-table [data]
  (let [{:keys [namespace functions properties events name intro-list]} data
        function-names (map (comp kebab-case :name) functions)
        context {:ns-name name}]
    (with-meta
      {:namespace  namespace
       :since      (extract-namespace-since data)
       :deprecated (extract-deprecated data)
       :properties (build-api-table-properties context properties)
       :functions  (build-api-table-functions context function-names data)
       :events     (build-api-table-events context events)}
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
  (let [{:keys [name]} data
        {:keys [doc]} (meta data)]
    {:event-name      (kebab-case name)
     :event-docstring doc}))

; ---------------------------------------------------------------------------------------------------------------------------

(defn generate-cljs-ns [api-table]
  (let [{:keys [functions properties events]} api-table
        {:keys [name doc subns]} (meta api-table)
        file-path (build-cljs-ns-file-path name subns)
        ns-name (build-ns-name name subns)
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
        {:keys [name doc subns]} (meta api-table)
        file-path (build-clj-ns-file-path name subns)
        ns-name (build-ns-name name subns)
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
          {:keys [name subns]} (meta api-table)]
      {:js-namespace  namespace
       :clj-namespace (build-ns-name name subns)
       :doc-section   name
       :file-name     (string/join "/" (build-clj-ns-file-path name subns))})))

(defn build-require [api-table columns]
  (let [{:keys [functions properties events]} api-table
        {:keys [name subns]} (meta api-table)
        clj-namespace (build-ns-name name subns)
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

(defn build-api-table [subns ns]
  (println (str "preparing '" (:name ns) "'"))
  (let [api-table (-> ns
                      (build-namespace-api-table)
                      (vary-meta assoc :subns subns)
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

(defn build-api-tables [data tag subns]
  (let [filtered-data (filter #(matches-filter? % tag) data)]
    (keep (comp (partial build-api-table subns) second) filtered-data)))

; ---------------------------------------------------------------------------------------------------------------------------

(defn generate-files [chromium-sha api-tables]
  (conj (mapcat generate-files-for-table api-tables) (generate-readme chromium-sha api-tables)))