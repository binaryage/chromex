(ns chromex.console)

(defmacro with-captured-console [& forms]
  `(try
     (chromex.console/reset-captured-content!)
     (chromex.console/capture-console-as-feedback!)
     ~@forms
     (finally
       (chromex.console/uncapture-console-as-feedback!))))
