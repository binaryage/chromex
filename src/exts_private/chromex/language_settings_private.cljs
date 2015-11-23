(ns chromex.language-settings-private (:require-macros [chromex.language-settings-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-language-list* [config]
  (gen-wrap :function ::get-language-list config))

(defn set-language-list* [config language-codes]
  (gen-wrap :function ::set-language-list config language-codes))

(defn get-spellcheck-dictionary-statuses* [config]
  (gen-wrap :function ::get-spellcheck-dictionary-statuses config))

(defn get-spellcheck-words* [config]
  (gen-wrap :function ::get-spellcheck-words config))

(defn add-spellcheck-word* [config word]
  (gen-wrap :function ::add-spellcheck-word config word))

(defn remove-spellcheck-word* [config word]
  (gen-wrap :function ::remove-spellcheck-word config word))

(defn get-translate-target-language* [config]
  (gen-wrap :function ::get-translate-target-language config))

(defn get-input-method-lists* [config]
  (gen-wrap :function ::get-input-method-lists config))

(defn add-input-method* [config input-method-id]
  (gen-wrap :function ::add-input-method config input-method-id))

(defn remove-input-method* [config input-method-id]
  (gen-wrap :function ::remove-input-method config input-method-id))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-spellcheck-dictionaries-changed* [config channel]
  (gen-wrap :event ::on-spellcheck-dictionaries-changed config channel))

(defn on-custom-dictionary-changed* [config channel]
  (gen-wrap :event ::on-custom-dictionary-changed config channel))

(defn on-input-method-added* [config channel]
  (gen-wrap :event ::on-input-method-added config channel))

(defn on-input-method-removed* [config channel]
  (gen-wrap :event ::on-input-method-removed config channel))

