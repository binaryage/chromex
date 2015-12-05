(ns chromex.playground-mocks
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.core.async :refer [<! >! timeout chan close!]]
            [chromex-lib.support :refer-macros [oset]]))

(def last-event-result (volatile! nil))

(defn set-last-error [error]
  (oset js/window ["chrome" "runtime" "lastError"] error))

; -- chrome API mocks -------------------------------------------------------------------------------------------------------

(defn get-something-mock [param1 callback]
  (go
    (<! (timeout 20))
    (callback (str "answer is " param1))))

(defn do-something-mock [param1]
  (str "got " param1))

(defn do-something-optional-args-mock [& args]
  (str "got " (vec args)))

(defn build-event-object-mock [args-fn]
  (let [active (volatile! false)]
    #js {"addListener"    (fn [listener-fn & extra-args]
                            (vreset! active true)
                            (go-loop [n 0]
                              (when @active
                                (let [result (apply listener-fn (apply args-fn n extra-args))]
                                  (vreset! last-event-result result))
                                (<! (timeout 20))
                                (recur (inc n)))))
         "removeListener" (fn []
                            (vreset! active false))}))

(def on-something-mock (build-event-object-mock
                         (fn [n & args]
                           [(str "something fired! #" n (if (seq args) (str " extra args:" args)))])))
(def on-something-deprecated-mock (build-event-object-mock
                                    (fn [n & args]
                                      [(str "deprecated fired! #" n (if (seq args) (str " extra args:" args)))])))
(def on-something-else-mock (build-event-object-mock
                              (fn [n & args]
                                [(str "something else fired! #" n (if (seq args) (str " extra args:" args))) "second-arg"])))

(defn call-callback-with-delay [callback & args]
  (go
    (<! (timeout 20))
    (set-last-error nil)
    (apply callback args)))

(defn call-error-callback-with-delay [callback error]
  (go
    (<! (timeout 20))
    (set-last-error error)
    (callback)))

(defn get-storage-area-mock []
  (js-obj
    "get" (fn [keys callback]
            (case keys
              "good-lookup" (call-callback-with-delay callback "some value")
              "bad-lookup" (call-callback-with-delay callback)
              "error-lookup" (call-error-callback-with-delay callback "error in lookup")))
    "getBytesInUse" (fn [keys callback]
                      (call-callback-with-delay callback "some 'getBytesInUse' answer"))
    "set" (fn [items callback]
            (call-callback-with-delay callback "some 'set' answer" "additional callback param"))
    "remove" (fn [keys callback]
               (call-callback-with-delay callback "some 'remove' answer"))
    "clear" (fn [callback]
              (call-callback-with-delay callback "some 'clear' answer"))))

; -- init API mocks ---------------------------------------------------------------------------------------------------------

(oset js/window ["chrome"] #js {})
(oset js/window ["chrome" "runtime"] #js {})
(oset js/window ["chrome" "playground"] #js {})

(oset js/window ["chrome" "playground" "getSomething"] get-something-mock)
(oset js/window ["chrome" "playground" "doSomething"] do-something-mock)
(oset js/window ["chrome" "playground" "doSomethingOptionalArgs"] do-something-optional-args-mock)
(oset js/window ["chrome" "playground" "someProp"] "prop1val")
(oset js/window ["chrome" "playground" "onSomething"] on-something-mock)
(oset js/window ["chrome" "playground" "onSomethingDeprecated"] on-something-deprecated-mock)
(oset js/window ["chrome" "playground" "onSomethingElse"] on-something-else-mock)
(oset js/window ["chrome" "playground" "getStorageArea"] get-storage-area-mock)

(oset js/window ["chrome" "runtime" "lastError"] nil)
