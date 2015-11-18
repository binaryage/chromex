(ns api-distiller.writer
  (:require
    [clojure.data.json :refer [pprint]]
    [clojure.java.io :as io]))

(defn write-edn [file-name data]
  (with-open [out (io/writer file-name :encoding "UTF-8")]
    (binding [*out* out]
      (pprint data))))