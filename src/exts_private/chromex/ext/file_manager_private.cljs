(ns chromex.ext.file-manager-private (:require-macros [chromex.ext.file-manager-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn logout-user-for-reauthentication* [config]
  (gen-wrap :function ::logout-user-for-reauthentication config))

(defn cancel-dialog* [config]
  (gen-wrap :function ::cancel-dialog config))

(defn execute-task* [config task-id entries]
  (gen-wrap :function ::execute-task config task-id entries))

(defn set-default-task* [config task-id entries mime-types]
  (gen-wrap :function ::set-default-task config task-id entries mime-types))

(defn get-file-tasks* [config entries]
  (gen-wrap :function ::get-file-tasks config entries))

(defn get-mime-type* [config entry]
  (gen-wrap :function ::get-mime-type config entry))

(defn get-strings* [config]
  (gen-wrap :function ::get-strings config))

(defn add-file-watch* [config entry]
  (gen-wrap :function ::add-file-watch config entry))

(defn remove-file-watch* [config entry]
  (gen-wrap :function ::remove-file-watch config entry))

(defn enable-external-file-scheme* [config]
  (gen-wrap :function ::enable-external-file-scheme config))

(defn grant-access* [config entry-urls]
  (gen-wrap :function ::grant-access config entry-urls))

(defn select-files* [config selected-paths should-return-local-path]
  (gen-wrap :function ::select-files config selected-paths should-return-local-path))

(defn select-file* [config selected-path index for-opening should-return-local-path]
  (gen-wrap :function ::select-file config selected-path index for-opening should-return-local-path))

(defn get-entry-properties* [config entries names]
  (gen-wrap :function ::get-entry-properties config entries names))

(defn pin-drive-file* [config entry pin]
  (gen-wrap :function ::pin-drive-file config entry pin))

(defn resolve-isolated-entries* [config entries]
  (gen-wrap :function ::resolve-isolated-entries config entries))

(defn add-mount* [config source]
  (gen-wrap :function ::add-mount config source))

(defn remove-mount* [config volume-id]
  (gen-wrap :function ::remove-mount config volume-id))

(defn get-volume-metadata-list* [config]
  (gen-wrap :function ::get-volume-metadata-list config))

(defn cancel-file-transfers* [config entries]
  (gen-wrap :function ::cancel-file-transfers config entries))

(defn cancel-all-file-transfers* [config]
  (gen-wrap :function ::cancel-all-file-transfers config))

(defn start-copy* [config entry parent-entry new-name]
  (gen-wrap :function ::start-copy config entry parent-entry new-name))

(defn cancel-copy* [config copy-id]
  (gen-wrap :function ::cancel-copy config copy-id))

(defn get-size-stats* [config volume-id]
  (gen-wrap :function ::get-size-stats config volume-id))

(defn format-volume* [config volume-id]
  (gen-wrap :function ::format-volume config volume-id))

(defn rename-volume* [config volume-id new-name]
  (gen-wrap :function ::rename-volume config volume-id new-name))

(defn get-preferences* [config]
  (gen-wrap :function ::get-preferences config))

(defn set-preferences* [config change-info]
  (gen-wrap :function ::set-preferences config change-info))

(defn search-drive* [config search-params]
  (gen-wrap :function ::search-drive config search-params))

(defn search-drive-metadata* [config search-params]
  (gen-wrap :function ::search-drive-metadata config search-params))

(defn search-files-by-hashes* [config volume-id hash-list]
  (gen-wrap :function ::search-files-by-hashes config volume-id hash-list))

(defn zip-selection* [config parent-entry entries dest-name]
  (gen-wrap :function ::zip-selection config parent-entry entries dest-name))

(defn get-drive-connection-state* [config]
  (gen-wrap :function ::get-drive-connection-state config))

(defn validate-path-name-length* [config parent-entry name]
  (gen-wrap :function ::validate-path-name-length config parent-entry name))

(defn zoom* [config operation]
  (gen-wrap :function ::zoom config operation))

(defn request-access-token* [config refresh]
  (gen-wrap :function ::request-access-token config refresh))

(defn request-web-store-access-token* [config]
  (gen-wrap :function ::request-web-store-access-token config))

(defn get-share-url* [config entry]
  (gen-wrap :function ::get-share-url config entry))

(defn get-download-url* [config entry]
  (gen-wrap :function ::get-download-url config entry))

(defn request-drive-share* [config entry share-type]
  (gen-wrap :function ::request-drive-share config entry share-type))

(defn get-profiles* [config]
  (gen-wrap :function ::get-profiles config))

(defn open-inspector* [config type]
  (gen-wrap :function ::open-inspector config type))

(defn compute-checksum* [config entry]
  (gen-wrap :function ::compute-checksum config entry))

(defn is-uma-enabled* [config]
  (gen-wrap :function ::is-uma-enabled config))

(defn set-entry-tag* [config entry visibility key value]
  (gen-wrap :function ::set-entry-tag config entry visibility key value))

(defn is-piex-loader-enabled* [config]
  (gen-wrap :function ::is-piex-loader-enabled config))

(defn get-providing-extensions* [config]
  (gen-wrap :function ::get-providing-extensions config))

(defn add-provided-file-system* [config extension-id]
  (gen-wrap :function ::add-provided-file-system config extension-id))

(defn configure-volume* [config volume-id]
  (gen-wrap :function ::configure-volume config volume-id))

(defn get-custom-actions* [config entries]
  (gen-wrap :function ::get-custom-actions config entries))

(defn execute-custom-action* [config entries action-id]
  (gen-wrap :function ::execute-custom-action config entries action-id))

(defn get-directory-size* [config entry]
  (gen-wrap :function ::get-directory-size config entry))

(defn get-recent-files* [config restriction]
  (gen-wrap :function ::get-recent-files config restriction))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-mount-completed* [config channel & args]
  (gen-wrap :event ::on-mount-completed config channel args))

(defn on-file-transfers-updated* [config channel & args]
  (gen-wrap :event ::on-file-transfers-updated config channel args))

(defn on-copy-progress* [config channel & args]
  (gen-wrap :event ::on-copy-progress config channel args))

(defn on-directory-changed* [config channel & args]
  (gen-wrap :event ::on-directory-changed config channel args))

(defn on-preferences-changed* [config channel & args]
  (gen-wrap :event ::on-preferences-changed config channel args))

(defn on-drive-connection-status-changed* [config channel & args]
  (gen-wrap :event ::on-drive-connection-status-changed config channel args))

(defn on-device-changed* [config channel & args]
  (gen-wrap :event ::on-device-changed config channel args))

(defn on-drive-sync-error* [config channel & args]
  (gen-wrap :event ::on-drive-sync-error config channel args))

(defn on-apps-updated* [config channel & args]
  (gen-wrap :event ::on-apps-updated config channel args))

