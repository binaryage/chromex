(ns api-gen.writer
  (:require [clojure.data.json :refer [pprint]]
            [clojure.java.io :as io]))

(defn write-sources [outdir file-list]
  (doseq [[file-spec file-content] file-list]
    (let [path-spec (if (vector? file-spec) file-spec [file-spec])
          full-path (apply io/file outdir path-spec)]
      (io/make-parents full-path)
      (spit full-path file-content))))