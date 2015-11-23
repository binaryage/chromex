(ns chromex.test.playground
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [cljs.core.async :refer [<! >! timeout chan close!]]
            [chromex.playground :refer-macros [get-something do-something get-some-prop tap-on-something-events
                                               tap-all-events do-something-optional-args]]
            [chromex-lib.chrome-event-channel :refer [make-chrome-event-channel]]))

(defn get-something-mock [param1 callback]
  (go
    (<! (timeout 100))
    (callback (str "answer is " param1))))

(defn do-something-mock [param1]
  (str "got " param1))

(defn do-something-optional-args-mock [& args]
  (str "got " (vec args)))

(def on-something-mock-active (volatile! false))
(def on-something-mock
  #js {"addListener"    (fn [f]
                          (vreset! on-something-mock-active true)
                          (go
                            (dotimes [n 5]
                              (if @on-something-mock-active
                                (f n))
                              (<! (timeout 100)))))
       "removeListener" (fn [f]
                          (vreset! on-something-mock-active false))})

(def on-something-deprecated-mock-active (volatile! false))
(def on-something-deprecated-mock
  #js {"addListener"    (fn [f]
                          (vreset! on-something-deprecated-mock-active true)
                          (go
                            (dotimes [n 5]
                              (if @on-something-deprecated-mock-active
                                (f (str "deprecated-" n)))
                              (<! (timeout 100)))))
       "removeListener" (fn [f]
                          (vreset! on-something-deprecated-mock-active false))})

(aset js/window "chrome" #js {})
(aset js/window.chrome "playground" #js {})

(aset js/window.chrome.playground "getSomething" get-something-mock)
(aset js/window.chrome.playground "doSomething" do-something-mock)
(aset js/window.chrome.playground "doSomethingOptionalArgs" do-something-optional-args-mock)
(aset js/window.chrome.playground "someProp" "prop1val")
(aset js/window.chrome.playground "onSomething" on-something-mock)
(aset js/window.chrome.playground "onSomethingDeprecated" on-something-deprecated-mock)

(deftest test-get-something
  (testing "get something"
    (async done
      (go
        (let [[result] (<! (get-something "p1"))]
          (is (= result "<answer is p1!!!>"))
          (done))))))

(deftest test-do-something
  (testing "do something"
    (let [result (do-something "x1")]
      (is (= result "[got x1!!!]")))))

(deftest test-do-something-with-optional-args
  (testing "do something with optional args"
    (is (= (do-something-optional-args 1 2 3) "got [1 2 3]"))
    (is (= (do-something-optional-args 1 2) "got [1 2]"))
    (is (= (do-something-optional-args 1) "got [1]"))
    (is (= (do-something-optional-args) "got []"))
    (is (= (do-something-optional-args 1 :omit 3) "got [1 3]"))
    (is (= (do-something-optional-args :omit :omit 3) "got [3]"))
    (is (= (do-something-optional-args :omit :omit :omit) "got []"))))

(deftest test-prop
  (testing "read prop"
    (let [result (get-some-prop)]
      (is (= result "@(prop1val)")))))

(deftest test-on-something
  (testing "tap on-something events"
    (async done
      (let [chan (make-chrome-event-channel (chan))]
        (tap-on-something-events chan)
        (go
          (dotimes [n 3]
            (let [[event [item]] (<! chan)]
              (is (= event :chromex.playground/on-something))
              (is (= item (str "~" n)))))
          (close! chan)
          (<! (timeout 100))
          (done))))))

(deftest test-tapping-all-events
  (testing "tap all events"
    (async done
      (let [chan (make-chrome-event-channel (chan))]
        (tap-all-events chan)
        (go
          (dotimes [n 3]
            (let [[event [item]] (<! chan)]
              (is (= event :chromex.playground/on-something))
              (is (= item (str "~" n)))))
          (close! chan)
          (<! (timeout 100))
          (done))))))
