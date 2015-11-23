(ns chromex-lib.logging)

; ---------------------------------------------------------------------------------------------------------------------------
; logging - these need to be macros to preserve source location for devtools

(defmacro log [& args]
  `(do (.log js/console ~@args) nil))

(defmacro info [& args]
  `(do (.info js/console ~@args) nil))

(defmacro error [& args]
  `(do (.error js/console ~@args) nil))

(defmacro warn [& args]
  `(do (.warn js/console ~@args) nil))

(defmacro group [& args]
  `(do (.group js/console ~@args) nil))

(defmacro group-end [& args]
  `(do (.groupEnd js/console ~@args) nil))

(defmacro with-group [title & body]
  `(try
     (.group js/console ~title)
     (let [res# ~@body]
       (.groupEnd js/console)
       res#)
     (catch :default e#
       (do
         (.groupEnd js/console)
         (throw e#)))))

(defmacro with-group-collapsed [title & body]
  `(try
     (.groupCollapsed js/console ~title)
     (let [res# ~@body]
       (.groupEnd js/console)
       res#)
     (catch :default e#
       (do
         (.groupEnd js/console)
         (throw e#)))))

(defmacro with-profile [title & body]
  `(try
     (.profile js/console ~title)
     (let [res# ~@body]
       (.profileEnd js/console)
       res#)
     (catch :default e#
       (do
         (.profileEnd js/console)
         (throw e#)))))