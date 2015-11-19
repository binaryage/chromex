(ns api-distiller.core
  (:require
    [clojure.string :as string]
    [clojure.tools.cli :refer [parse-opts]]
    [api-distiller.reader :refer [read-json]]
    [api-distiller.writer :refer [write-json]]
    [api-distiller.transformer :refer [transform]])
  (:gen-class))

(def cli-options
  [["-i" "--input " "Input API JSON file" :default "apis.json"]
   ["-o" "--output " "Output API EDN file" :default "apis-filtered.json"]
   ["-h" "--help"]])

(defn usage [options-summary]
  (string/join \newline ["Converts raw API JSON file from server2 cache to filtered/normalized json file for further processing."
                         ""
                         "Usage: api-distiller [options]"
                         ""
                         "Options:"
                         options-summary]))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
    (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn run-job! [options]
  (let [{:keys [input output]} options]
    (->> input
      (read-json)
      (transform)
      (write-json output))))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors)))
    (run-job! options)))