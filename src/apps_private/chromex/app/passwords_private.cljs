(ns chromex.app.passwords-private (:require-macros [chromex.app.passwords-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn remove-saved-password* [config login-pair]
  (gen-wrap :function ::remove-saved-password config login-pair))

(defn remove-password-exception* [config exception-url]
  (gen-wrap :function ::remove-password-exception config exception-url))

(defn request-plaintext-password* [config login-pair]
  (gen-wrap :function ::request-plaintext-password config login-pair))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-saved-passwords-list-changed* [config channel & args]
  (gen-wrap :event ::on-saved-passwords-list-changed config channel args))
(defn on-password-exceptions-list-changed* [config channel & args]
  (gen-wrap :event ::on-password-exceptions-list-changed config channel args))
(defn on-plaintext-password-retrieved* [config channel & args]
  (gen-wrap :event ::on-plaintext-password-retrieved config channel args))

