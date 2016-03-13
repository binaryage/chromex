(ns api-gen.helpers
  (:require [clojure.walk :refer [postwalk]]
            [clojure.string :as string]
            [clojure.pprint :refer :all]
            [camel-snake-kebab.core :refer :all]
            [clostache.parser :refer :all]
            [cuerdas.core :as cuerdas]
            [api-gen.word-wrap :refer [wrap]]
            [cljfmt.core :as cljfmt]
            [clojure.java.io :as io])
  (import [java.net URLEncoder]))

(def ^:const INDENT-CHAR " ")

; ---------------------------------------------------------------------------------------------------------------------------

; taken from https://github.com/clojure/clojurescript/blob/master/src/main/clojure/cljs/analyzer.cljc
(def js-reserved
  #{"arguments" "abstract" "boolean" "break" "byte" "case"
    "catch" "char" "class" "const" "continue"
    "debugger" "default" "delete" "do" "double"
    "else" "enum" "export" "extends" "final"
    "finally" "float" "for" "function" "goto" "if"
    "implements" "import" "in" "instanceof" "int"
    "interface" "let" "long" "native" "new"
    "package" "private" "protected" "public"
    "return" "short" "static" "super" "switch"
    "synchronized" "this" "throw" "throws"
    "transient" "try" "typeof" "var" "void"
    "volatile" "while" "with" "yield" "methods"
    "null" "constructor"})

; to prevent :munged-namespace clojurescript compiler warnings
(defn munge-if-reserved [name]
  (if (js-reserved name)
    (str name "_api")
    name))

(defn pprint-edn-as-str [code columns]
  (binding [*print-right-margin* columns]
    (with-out-str
      (pprint code))))

(defn indent-str [indent]
  (apply str (repeat indent INDENT-CHAR)))

(defn fix-quotes [s]
  (string/replace s #"\"" "'"))

(defn replace-refs [s]
  (-> s
      (string/replace #"\$\(ref\:(.*?)\)" "'$1'")
      (string/replace #"\$ref\:" "")))

(defn left-whitespace-count [line]
  (- (count line) (count (cuerdas/ltrim line))))

(defn empty-line? [line]
  (empty? (cuerdas/trim line)))

(defn reset-indent [text]
  (let [lines (cuerdas/lines text)
        count (apply min (map left-whitespace-count (remove empty-line? lines)))]
    (assert (not (neg? count)))
    (if (pos? count)
      (->> lines
           (map #(if-not (empty-line? %) (subs % count) %))
           (cuerdas/unlines))
      text)))

(defn prefix-block [prefix lines]
  (concat [(first lines)] (map #(if (empty? %) "" (str prefix %)) (rest lines))))

(defn prefix-text [prefix text]
  (->> text
       (cuerdas/lines)
       (prefix-block prefix)
       (cuerdas/unlines)))

(defn wrap-text [indent columns text]
  (let [prefix (indent-str indent)]
    (->> (wrap text (- columns indent)) (prefix-text prefix))))

(defn quote-text [text]
  (cuerdas/quote (fix-quotes text)))

(defn wrap-docstring [indent columns text]
  (if (empty? text)
    " "
    (let [prefix (indent-str indent)
          wrapped-text (wrap-text (inc indent) columns text)
          quoted-wrapped-text (quote-text wrapped-text)]
      (str "\n" prefix quoted-wrapped-text "\n" prefix))))

(defn load-partials [partials]
  (let [load-partial (fn [pt] [pt (slurp (io/resource (str "templates/" (name pt) ".mustache")))])]
    (into {} (map load-partial partials))))

(defn reformat [source]
  (try
    (cljfmt/reformat-string source {:remove-consecutive-blank-lines? false
                                    :indents                         cljfmt/default-indents})
    (catch Exception e
      (println "====== !!! formatting error !!! ======\n" source)
      (throw e))))

(defn fix-clostaches [content]
  ; clostaches is escaping backslashes in our content in some cases
  ; (even although explictely instructed not to escape anything)
  ; counter it here...
  (string/replace content "\\\\\"" "\\\""))

(defn remove-consecutive-blank-lines [s]
  (string/replace s #"[\n\r|\n]{2,}" "\n\n"))

(defn post-process [content]
  (-> content
      (fix-clostaches)
      (reformat)
      (remove-consecutive-blank-lines)))

(defn patch-snake-case [s]
  (string/replace s #"_([0-9])" "$1"))                                                                                        ; we don't want numbers to be treated as separate

(defn snake-case [s]
  (if (and (string? s) (not (empty? s)))
    (patch-snake-case (->snake_case s))
    (throw (Exception. (str "snake-case: expected non-empty string: '" s "'" (type s))))))

(defn patch-kebab-case [s]
  (string/replace s #"-([0-9])" "$1"))                                                                                        ; we don't want numbers to be treated as separate

(defn kebab-case [s]
  (if (and (string? s) (not (empty? s)))
    (patch-kebab-case (->kebab-case s))
    (throw (Exception. (str "kebab-case: expected non-empty string: '" s "'" (type s))))))

(defn safe-empty? [v]
  (try
    (empty? v)                                                                                                                ; v might throw because it does not implement ISeq
    (catch Exception _
      false)))

(defn emptyish? [v]
  (or
    (nil? v)
    (false? v)
    (safe-empty? v)))

(defn remove-emptyish-values [data]
  (let [* (fn [item]
            (if (map? item)
              (with-meta (into {} (remove (comp emptyish? second) item)) (meta item))
              item))]
    (postwalk * data)))

(defn has-any? [coll]
  (not (empty? coll)))

(defn encode-url-param [param]
  (URLEncoder/encode param))