(ns chromex.test.playground
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [cljs.core.async :refer [<! >! timeout chan close!]]
            [chromex.playground :refer-macros [get-something do-something get-some-prop tap-on-something-events
                                               tap-all-events]]
            [chromex-lib.chrome-event-channel :refer [make-chrome-event-channel]]))

(defn get-something-mock [param1 callback]
  (go
    (<! (timeout 100))
    (callback (str "answer is " param1))))

(defn do-something-mock [param1]
  (str "got " param1))

(def on-something-mock
  #js {"addListener"    (fn [f]
                          (go
                            #_(println "add listener" f)
                            (dotimes [n 5]
                              (f n)
                              (<! (timeout 1000)))))
       "removeListener" (fn [f]
                          #_(println "remove listener" f))})

(aset js/window "chrome" #js {})
(aset js/window.chrome "playground" #js {})

(aset js/window.chrome.playground "getSomething" get-something-mock)
(aset js/window.chrome.playground "doSomething" do-something-mock)
(aset js/window.chrome.playground "someProp" "prop1val")
(aset js/window.chrome.playground "onSomething" on-something-mock)

(defn gs []
  (get-something "p1"))

(deftest test-get-something
  (testing "get something"
    (async done
      (go
        (let [[result] (<! (gs))]
          (is (= result "<answer is p1!!!>"))
          (done))))))

(deftest test-do-something
  (testing "do something"
    (let [result (do-something "x1")]
      (is (= result "[got x1!!!]")))))

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
          (<! (timeout 1000))
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
          (<! (timeout 1000))
          (done))))))
