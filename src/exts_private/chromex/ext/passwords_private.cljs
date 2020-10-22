(ns chromex.ext.passwords-private (:require-macros [chromex.ext.passwords-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn record-passwords-page-access-in-settings* [config]
  (gen-wrap :function ::record-passwords-page-access-in-settings config))

(defn change-saved-password* [config ids new-username new-password]
  (gen-wrap :function ::change-saved-password config ids new-username new-password))

(defn remove-saved-password* [config id]
  (gen-wrap :function ::remove-saved-password config id))

(defn remove-saved-passwords* [config ids]
  (gen-wrap :function ::remove-saved-passwords config ids))

(defn remove-password-exception* [config id]
  (gen-wrap :function ::remove-password-exception config id))

(defn remove-password-exceptions* [config ids]
  (gen-wrap :function ::remove-password-exceptions config ids))

(defn undo-remove-saved-password-or-exception* [config]
  (gen-wrap :function ::undo-remove-saved-password-or-exception config))

(defn request-plaintext-password* [config id reason]
  (gen-wrap :function ::request-plaintext-password config id reason))

(defn get-saved-password-list* [config]
  (gen-wrap :function ::get-saved-password-list config))

(defn get-password-exception-list* [config]
  (gen-wrap :function ::get-password-exception-list config))

(defn move-password-to-account* [config ids]
  (gen-wrap :function ::move-password-to-account config ids))

(defn import-passwords* [config]
  (gen-wrap :function ::import-passwords config))

(defn export-passwords* [config]
  (gen-wrap :function ::export-passwords config))

(defn request-export-progress-status* [config]
  (gen-wrap :function ::request-export-progress-status config))

(defn cancel-export-passwords* [config]
  (gen-wrap :function ::cancel-export-passwords config))

(defn is-opted-in-for-account-storage* [config]
  (gen-wrap :function ::is-opted-in-for-account-storage config))

(defn opt-in-for-account-storage* [config opt-in]
  (gen-wrap :function ::opt-in-for-account-storage config opt-in))

(defn get-compromised-credentials* [config]
  (gen-wrap :function ::get-compromised-credentials config))

(defn get-weak-credentials* [config]
  (gen-wrap :function ::get-weak-credentials config))

(defn get-plaintext-insecure-password* [config credential reason]
  (gen-wrap :function ::get-plaintext-insecure-password config credential reason))

(defn change-insecure-credential* [config credential new-password]
  (gen-wrap :function ::change-insecure-credential config credential new-password))

(defn remove-insecure-credential* [config credential]
  (gen-wrap :function ::remove-insecure-credential config credential))

(defn start-password-check* [config]
  (gen-wrap :function ::start-password-check config))

(defn stop-password-check* [config]
  (gen-wrap :function ::stop-password-check config))

(defn get-password-check-status* [config]
  (gen-wrap :function ::get-password-check-status config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-saved-passwords-list-changed* [config channel & args]
  (gen-wrap :event ::on-saved-passwords-list-changed config channel args))

(defn on-password-exceptions-list-changed* [config channel & args]
  (gen-wrap :event ::on-password-exceptions-list-changed config channel args))

(defn on-passwords-file-export-progress* [config channel & args]
  (gen-wrap :event ::on-passwords-file-export-progress config channel args))

(defn on-account-storage-opt-in-state-changed* [config channel & args]
  (gen-wrap :event ::on-account-storage-opt-in-state-changed config channel args))

(defn on-compromised-credentials-changed* [config channel & args]
  (gen-wrap :event ::on-compromised-credentials-changed config channel args))

(defn on-weak-credentials-changed* [config channel & args]
  (gen-wrap :event ::on-weak-credentials-changed config channel args))

(defn on-password-check-status-changed* [config channel & args]
  (gen-wrap :event ::on-password-check-status-changed config channel args))

