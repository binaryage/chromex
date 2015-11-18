(ns api-distiller.transformer
  (:require [clojure.walk :refer [postwalk]]))

(defn safe-empty? [v]
  (try
    (empty? v)                                                                                                        ; v might throw because it does not implement ISeq
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
              (into {} (remove (comp emptyish? second) item))
              item))]
    (postwalk * data)))

(defn blacklisted-key? [k]
  (case k
    ("last" "id" "parentName" "samples" "asFunction") true
    false))

(defn remove-blacklisted-keys [data]
  (let [* (fn [item]
            (if (map? item)
              (into {} (remove (comp blacklisted-key? first) item))
              item))]
    (postwalk * data)))

(defn extension-api? [name]
  (re-matches #"extensions\/.*" name))

(defn filter-extension-apis [data]
  (into {} (filter (comp extension-api? first) data)))

(defn transform [data]
  (->> data
    ;(filter-extension-apis)
    (remove-emptyish-values)
    (remove-blacklisted-keys)))