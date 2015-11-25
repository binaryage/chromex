(ns api-distiller.reader
  (:require
    [clojure.data.json :as json]))

(defn read-json [file-name]
  (-> file-name
      (slurp)
      (json/read-str)))