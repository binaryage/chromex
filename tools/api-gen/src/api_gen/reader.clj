(ns api-gen.reader
  (:require [clojure.data.json :as json]
            [camel-snake-kebab.core :refer :all]
            [api-gen.helpers :refer [kebab-case]]))

(defn keywordize-key [s]
  (-> s
      kebab-case
      keyword))

(defn read-json [file-name]
  (-> file-name
      (slurp)
      (json/read-str :key-fn keywordize-key)))