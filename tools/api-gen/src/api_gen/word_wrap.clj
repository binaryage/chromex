; taken from https://github.com/trikitrok/WordWrapInClojure
(ns api-gen.word-wrap
  (:require [clojure.string :as string]))

(def ^:private to-trimmed-string
  (comp string/trim (partial apply str)))

(def ^:private rest-of-line
  (comp to-trimmed-string (partial drop)))

(defn- wrap-line-at [index line]
  (str (to-trimmed-string (take index line)) \newline))

(defn- index-of-last-fitting-space [max-columns start-index line]
  (let [res (.lastIndexOf (drop start-index (take max-columns line)) \space)]
    (if (neg? res)
      res
      (+ start-index res))))

(defn- ^:private valid-index? [index]
  (not (neg? index)))

(defn- fits? [line max-columns]
  (<= (count line) max-columns))

(defn- compute-wrapping-index [line max-columns]
  (if (fits? line max-columns)
    -1
    (let [index (index-of-last-fitting-space max-columns 0 line)
          mid-index (int (/ max-columns 2))]
      (if (valid-index? index)
        (if (> index mid-index)
          index
          (index-of-last-fitting-space 100000000 mid-index line))
        (index-of-last-fitting-space 100000000 0 line)))))

(defn- line->wrapped-lines [wrapped-lines line max-columns]
  (let [index (compute-wrapping-index line max-columns)]
    (if-not (valid-index? index)
      (conj wrapped-lines line)
      (recur (conj wrapped-lines (wrap-line-at index line))
             (rest-of-line index line)
             max-columns))))

(defn- wrap-line [line max-columns]
  (apply str (line->wrapped-lines [] line max-columns)))

(defn- extract-lines [text]
  (string/split text #"\n"))

(def ^:private join-lines (partial string/join \newline))

(defn wrap [text max-columns]
  (->> text
       extract-lines
       (map #(wrap-line % max-columns))
       join-lines))