(ns chromex.test.playground
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [cljs.core.async :refer [<! >! timeout chan close!]]
            [chromex.test-utils :refer [advanced-mode?]]
            [chromex-lib.support :refer-macros [oset]]
            [chromex-lib.config :refer-macros [with-custom-event-listener-factory]]
            [chromex.playground :refer-macros [get-something do-something get-some-prop tap-on-something-events
                                               tap-all-events do-something-optional-args tap-on-something-else-events
                                               get-some-missing-prop do-something-missing tap-on-something-missing-events]]
            [chromex-lib.chrome-event-channel :refer [make-chrome-event-channel]]))

(def last-event-result (volatile! nil))

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

; -- init API mocks ---------------------------------------------------------------------------------------------------------

(oset js/window ["chrome"] #js {})
(oset js/window ["chrome" "playground"] #js {})

(oset js/window ["chrome" "playground" "getSomething"] get-something-mock)
(oset js/window ["chrome" "playground" "doSomething"] do-something-mock)
(oset js/window ["chrome" "playground" "doSomethingOptionalArgs"] do-something-optional-args-mock)
(oset js/window ["chrome" "playground" "someProp"] "prop1val")
(oset js/window ["chrome" "playground" "onSomething"] on-something-mock)
(oset js/window ["chrome" "playground" "onSomethingDeprecated"] on-something-deprecated-mock)
(oset js/window ["chrome" "playground" "onSomethingElse"] on-something-else-mock)

; -- test against mocks -----------------------------------------------------------------------------------------------------

(deftest test-plain-api-call
  (testing "do something"
    (is (= (do-something "param") "from-native[got to-native[param]]"))))

(deftest test-api-call-with-callback
  (testing "get something"
    (async done
      (go
        (let [[result] (<! (get-something "param"))]
          (is (= result "from-native[answer is to-native[param]]"))
          (done))))))

(deftest test-optional-args
  (testing "do something with optional args"
    (is (= (do-something-optional-args 1 2 3) "got [1 \"to-native[2]\" 3]"))
    (is (= (do-something-optional-args 1 2) "got [1 \"to-native[2]\"]"))
    (is (= (do-something-optional-args 1) "got [1]"))
    (is (= (do-something-optional-args) "got []"))
    (is (= (do-something-optional-args 1 :omit 3) "got [1 3]"))
    (is (= (do-something-optional-args :omit 2 :omit) "got [\"to-native[2]\"]"))
    (is (= (do-something-optional-args :omit :omit 3) "got [3]"))
    (is (= (do-something-optional-args :omit :omit :omit) "got []")))
  (if-not advanced-mode?
    (testing "try to omit non-optional arg"
      (is (thrown-with-msg? js/Error #"cannot be omitted" (do-something :omit))))))

(deftest test-property-access
  (testing "read prop"
    (let [result (get-some-prop)]
      (is (= result "from-native[prop1val]")))))

(deftest test-events
  (testing "tap on-something events"
    (async done
      (let [chan (make-chrome-event-channel (chan))]
        (tap-on-something-events chan)
        (go
          (dotimes [n 3]
            (let [[event [item]] (<! chan)]
              (is (= event :chromex.playground/on-something))
              (is (= item (str "from-native[something fired! #" n "]")))))
          (close! chan)
          (<! (timeout 30))
          (done))))))

(deftest test-passing-extra-args-to-events
  (testing "tap event with extra args"
    (async done
      (let [chan (make-chrome-event-channel (chan))]
        (tap-on-something-events chan 1 2 3)
        (go
          (dotimes [n 3]
            (let [[event [item]] (<! chan)]
              (is (= event :chromex.playground/on-something))
              (is (= item (str "from-native[something fired! #" n " extra args:(1 2 3)]")))))
          (close! chan)
          (<! (timeout 100))
          (done))))))

(deftest test-tapping-all-events
  (testing "tap all events"
    (async done
      (let [chan (make-chrome-event-channel (chan))]
        (tap-all-events chan)
        (go
          (dotimes [_ 3]
            (let [[event _] (<! chan)]
              (is (not (= event :chromex.playground/on-something-deprecated)))))                                              ; we should be receiving only non-deprecated events
          (close! chan)
          (<! (timeout 30))
          (done))))))

(deftest test-sync-events
  (testing "tap on-something events with-custom-event-listener-factory"
    (async done
      (let [chan (make-chrome-event-channel (chan))
            storage (atom [])]
        (with-custom-event-listener-factory (fn []
                                              (fn [& args]
                                                (swap! storage conj (str "sync:" args))
                                                "return val"))
          (tap-on-something-events chan))
        (go
          (<! (timeout 30))                                                                                                   ; give event source some time to fire at least one event
          (is (= (first @storage) "sync:(\"from-native[something fired! #0]\")"))
          (is (= @last-event-result "return val"))
          (close! chan)
          (done))))))

(if-not advanced-mode?
  (deftest test-using-missing-apis
    (testing "try access missing property"
      (is (thrown-with-msg? js/Error #"library tried to access a missing Chrome API object" (get-some-missing-prop))))
    (testing "try call missing function"
      (is (thrown-with-msg? js/Error #"library tried to access a missing Chrome API object" (do-something-missing))))
    (testing "try tap missing event"
      (let [chan (make-chrome-event-channel (chan))]
        (is (thrown-with-msg? js/Error
                              #"library tried to access a missing Chrome API object"
                              (tap-on-something-missing-events chan)))))))