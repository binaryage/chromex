(ns api-gen.core
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.pprint :refer [print-table]]
            [api-gen.reader :refer [read-json]]
            [api-gen.writer :refer [write-sources]]
            [api-gen.generator :refer [generate-files build-api-tables generate-stats]])
  (:gen-class))

(def cli-options
  [["-i" "--input " "Input API JSON file" :default "apis-filtered.json"]
   ["-o" "--outdir " "Output dir for generated sources" :default "api"]
   ["-s" "--chromium-sha " "Chromium source git SHA" :default "?"]
   ["-f" "--filter " "Filter items by type" :default ""]
   ["-h" "--help"]])

(defn usage [options-summary]
  (string/join \newline ["Takes distilled API JSON file and generates Chromex files."
                         ""
                         "Usage: api-gen [options]"
                         ""
                         "Options:"
                         options-summary]))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
    (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn print-stats [stats]
  (let [{:keys [namespaces total-functions total-properties total-events]} stats
        namespaces-count (count namespaces)]
    (println)
    (println "STATS:")
    (print-table [:namespace :properties :functions :events] namespaces)
    (println "------------------------------------------------------")
    (println "RESULT:"
      namespaces-count "namespaces containing"
      total-properties "properties"
      total-functions "functions" "and"
      total-events "events.")))

(defn run-job! [options]
  (let [{:keys [input outdir chromium-sha filter]} options
        data (read-json input)
        ;data (filter #(contains? #{"runtime" "commands" "events" "tabs"} (:name (second %))) data)
        ;data (filter #(contains? #{"extension"} (:name (second %))) data)
        api-tables (build-api-tables data filter)]
    (->> api-tables
      (generate-files chromium-sha)
      (write-sources outdir))
    (print-stats (generate-stats api-tables))))

(defn -main [& args]
  (let [{:keys [options errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors)))
    (run-job! options)))