(ns chromex.app.wallpaper-private (:require-macros [chromex.app.wallpaper-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-wallpaper-changed-by3rd-party* [config channel & args]
  (gen-wrap :event ::on-wallpaper-changed-by3rd-party config channel args))

(defn on-close-preview-wallpaper* [config channel & args]
  (gen-wrap :event ::on-close-preview-wallpaper config channel args))

