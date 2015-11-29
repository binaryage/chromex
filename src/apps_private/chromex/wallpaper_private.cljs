(ns chromex.wallpaper-private (:require-macros [chromex.wallpaper-private :refer [gen-wrap]])
    (:require [chromex-lib.core]))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-wallpaper-changed-by3rd-party* [config channel & args]
  (gen-wrap :event ::on-wallpaper-changed-by3rd-party config channel args))

