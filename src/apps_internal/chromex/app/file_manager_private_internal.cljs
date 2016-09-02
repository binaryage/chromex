(ns chromex.app.file-manager-private-internal (:require-macros [chromex.app.file-manager-private-internal :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn resolve-isolated-entries* [config urls]
  (gen-wrap :function ::resolve-isolated-entries config urls))

(defn get-entry-properties* [config urls names]
  (gen-wrap :function ::get-entry-properties config urls names))

(defn add-file-watch* [config url]
  (gen-wrap :function ::add-file-watch config url))

(defn remove-file-watch* [config url]
  (gen-wrap :function ::remove-file-watch config url))

(defn get-custom-actions* [config urls]
  (gen-wrap :function ::get-custom-actions config urls))

(defn execute-custom-action* [config urls action-id]
  (gen-wrap :function ::execute-custom-action config urls action-id))

(defn compute-checksum* [config url]
  (gen-wrap :function ::compute-checksum config url))

(defn get-mime-type* [config url]
  (gen-wrap :function ::get-mime-type config url))

(defn pin-drive-file* [config url pin]
  (gen-wrap :function ::pin-drive-file config url pin))

(defn execute-task* [config task-id urls]
  (gen-wrap :function ::execute-task config task-id urls))

(defn set-default-task* [config task-id urls mime-types]
  (gen-wrap :function ::set-default-task config task-id urls mime-types))

(defn get-file-tasks* [config urls]
  (gen-wrap :function ::get-file-tasks config urls))

(defn get-share-url* [config url]
  (gen-wrap :function ::get-share-url config url))

(defn get-download-url* [config url]
  (gen-wrap :function ::get-download-url config url))

(defn request-drive-share* [config url share-type]
  (gen-wrap :function ::request-drive-share config url share-type))

(defn set-entry-tag* [config url visibility key value]
  (gen-wrap :function ::set-entry-tag config url visibility key value))

(defn cancel-file-transfers* [config urls]
  (gen-wrap :function ::cancel-file-transfers config urls))

(defn start-copy* [config url parent-url new-name]
  (gen-wrap :function ::start-copy config url parent-url new-name))

(defn zip-selection* [config parent-url urls dest-name]
  (gen-wrap :function ::zip-selection config parent-url urls dest-name))

(defn validate-path-name-length* [config parent-url name]
  (gen-wrap :function ::validate-path-name-length config parent-url name))

(defn get-directory-size* [config url]
  (gen-wrap :function ::get-directory-size config url))

