(ns chromex.app.wallpaper (:require-macros [chromex.app.wallpaper :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn set-wallpaper* [config details]
  (gen-wrap :function ::set-wallpaper config details))

