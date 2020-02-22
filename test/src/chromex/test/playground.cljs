(ns chromex.test.playground
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [chromex.chrome-event-channel :refer [make-chrome-event-channel]]
            [chromex.config :refer-macros [with-custom-config with-custom-event-listener-factory with-muted-error-reporting]]
            [chromex.error :refer [get-last-error set-last-error!]]
            [chromex.playground :refer-macros [call-future-api call-master-api do-something do-something-missing
                                               do-something-optional-args get-some-missing-prop get-some-prop
                                               get-something get-something-causing-error tap-all-events
                                               tap-on-something-else-events tap-on-something-events tap-on-something-missing-events]]
            [chromex.playground-mocks :refer [last-event-result]]
            [chromex.test-utils :refer [advanced-mode?] :refer-macros [valid-api-version?]]
            [cljs.core.async :refer [<! >! chan close! timeout]]
            [cljs.test :refer-macros [async deftest is testing]]
            [clojure.string :as string]
            [chromex.console :refer [with-captured-console get-captured-console-content]]
            [oops.core :refer [oapply ocall oget oset!]]))

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

(deftest test-api-call-causing-error
  (testing "get something causing error"
    (async done
      (go
        (with-muted-error-reporting
          (is (= (get-last-error) nil))
          (let [result (<! (get-something-causing-error "param"))]
            (is (= result nil) "result channel should close when errored")
            (let [last-error (get-last-error)]
              (is (= (oget last-error "message") "get-something caused an error"))
              (is (= (oget last-error "code") 666))
              (set-last-error! nil))
            (done)))))))

(deftest test-reset-last-error
  (testing "non-error call should reset last error"
    (async done
      (go
        (with-muted-error-reporting
          (is (= (get-last-error) nil))
          (let [result (<! (get-something-causing-error "param"))]
            (is (= result nil) "result channel should close when errored")
            (is (some? (get-last-error)))
            (<! (get-something "param"))
            (is (= (get-last-error) nil))                                                                                     ; last error got resetted to nil
            (done)))))))

(deftest test--custom-error-reporter
  (testing "exercise custom error reporter"
    (async done
      (go
        (let [expected-report (str "[{:id :chromex.playground/get-something-causing-error, "
                                   ":name \"getSomethingCausingError\", "
                                   ":callback? true, "
                                   ":params [{:name \"param1\", :type \"some-type\"} "
                                   "{:name \"callback\", :type :callback, :callback {:params []}}], "
                                   ":function? true} "
                                   "#js {:message \"get-something caused an error\", :code 666}]")
              reported (volatile! [])
              reporter (fn [descriptor error]
                         (vswap! reported conj (pr-str [descriptor error])))]
          (with-custom-config #(assoc % :callback-error-reporter reporter)
            (is (= (get-last-error) nil))
            (<! (get-something-causing-error "param"))
            (is (= (string/join @reported) expected-report))
            (set-last-error! nil)
            (done)))))))

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

(deftest test-using-missing-apis
  (when-not advanced-mode?
    (testing "try access missing property"
      (with-captured-console
        (is (= nil (get-some-missing-prop))))
      (let [console-content (get-captured-console-content)]
        (is (= (count console-content) 1))
        (is (= (first (first console-content)) :log))))
    (testing "try call missing function"
      (with-captured-console
        (do-something-missing))
      (let [console-content (get-captured-console-content)]
        (is (= (count console-content) 1))
        (is (= (first (first console-content)) :error))
        (is (some? (re-find #"library tried to access a missing Chrome API object" (second (first console-content)))))))
    (testing "try tap missing event"
      (with-captured-console
        (let [chan (make-chrome-event-channel (chan))]
          (tap-on-something-missing-events chan)))
      (let [console-content (get-captured-console-content)]
        (is (= (count console-content) 1))
        (is (= (first (first console-content)) :error))
        (is (some? (re-find #"library tried to access a missing Chrome API object" (second (first console-content)))))))))

(deftest test-api-version-checking
  (testing "valid-api-version?"
    (is (= (valid-api-version? "latest" "master" "master") true))
    (is (= (valid-api-version? "latest" "master" nil) true))
    (is (= (valid-api-version? "latest" "100" "200") false))
    (is (= (valid-api-version? "latest" "50" nil) true))
    (is (= (valid-api-version? "latest" nil nil) true))
    (is (= (valid-api-version? "latest" nil "10") false))
    (is (= (valid-api-version? "master" "master" "master") true))
    (is (= (valid-api-version? "master" "master" nil) true))
    (is (= (valid-api-version? "master" "100" "200") false))
    (is (= (valid-api-version? "master" "50" nil) true))
    (is (= (valid-api-version? "master" nil nil) true))
    (is (= (valid-api-version? "master" nil "10") false))
    (is (= (valid-api-version? "50" nil nil) true))
    (is (= (valid-api-version? "50" "50" nil) true))
    (is (= (valid-api-version? "50" "51" nil) false))
    (is (= (valid-api-version? "50" nil "49") false))
    (is (= (valid-api-version? "50" nil "50") true))
    (is (= (valid-api-version? "50" "50" "50") true))
    (is (= (valid-api-version? "50" "49" "50") true)))
  (testing "call future api"
    (is (= (call-future-api) nil)))
  (testing "call master api"
    (is (= (call-master-api) nil))))
