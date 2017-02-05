(ns chromex.version)

(def current-version "0.5.3")                                                                                        ; this should match our project.clj

(defmacro get-current-version []
  current-version)
