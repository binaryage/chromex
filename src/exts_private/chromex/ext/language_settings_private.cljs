(ns chromex.ext.language-settings-private (:require-macros [chromex.ext.language-settings-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-language-list* [config]
  (gen-wrap :function ::get-language-list config))

(defn enable-language* [config language-code]
  (gen-wrap :function ::enable-language config language-code))

(defn disable-language* [config language-code]
  (gen-wrap :function ::disable-language config language-code))

(defn set-enable-translation-for-language* [config language-code enable]
  (gen-wrap :function ::set-enable-translation-for-language config language-code enable))

(defn move-language* [config language-code move-type]
  (gen-wrap :function ::move-language config language-code move-type))

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

(defn retry-download-dictionary* [config language-code]
  (gen-wrap :function ::retry-download-dictionary config language-code))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-spellcheck-dictionaries-changed* [config channel & args]
  (gen-wrap :event ::on-spellcheck-dictionaries-changed config channel args))

(defn on-custom-dictionary-changed* [config channel & args]
  (gen-wrap :event ::on-custom-dictionary-changed config channel args))

(defn on-input-method-added* [config channel & args]
  (gen-wrap :event ::on-input-method-added config channel args))

(defn on-input-method-removed* [config channel & args]
  (gen-wrap :event ::on-input-method-removed config channel args))

