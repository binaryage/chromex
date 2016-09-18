(ns chromex.test.chrome-port
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [cljs.core.async :refer [<!]]
            [chromex.protocols :as protocols :refer [IChromePort IChromePortState]]
            [oops.core :refer [ocall oget]]
            [chromex.test-utils :refer [advanced-mode?]]
            [chromex.playground :refer-macros [get-port]]))

; -- test against mocks -----------------------------------------------------------------------------------------------------

(defn get-port-mock-state [port]
  (-> port
      (protocols/get-native-port)
      (ocall "get-mock-state")))

(defn fire-message-on-port [port message]
  (-> port
      (protocols/get-native-port)
      (oget "onMessage")
      (ocall "fire" message)))

(deftest test-port
  (testing "excercise ChromePort"
    (async done
      (let [port (get-port)]
        (is (= (satisfies? IChromePort port)))
        (is (= (satisfies? IChromePortState port)))
        (let [message-recorder (atom [])
              disconnect-count (atom 0)]
          (protocols/on-message! port (fn [& args] (swap! message-recorder conj args)))
          (protocols/on-disconnect! port (fn [] (swap! disconnect-count inc)))
          (go
            ; --- CONNECTED -------------------------------------------------------------------------------------------------
            (is (= (protocols/get-name port) "I'm a ChromePort"))
            (is (= (protocols/get-sender port) "this port's sender"))
            (when-not advanced-mode?
              (is (thrown-with-msg? js/Error
                                    #"post-message! called with nil message"
                                    (protocols/post-message! port nil))))
            (is (= (get-port-mock-state port) {:connected     true
                                               :sent-messages []}))
            (protocols/post-message! port "outgoing message 1")
            (is (= (get-port-mock-state port) {:connected     true
                                               :sent-messages ["outgoing message 1"]}))
            (protocols/post-message! port "outgoing message 2")
            (is (= (get-port-mock-state port) {:connected     true
                                               :sent-messages ["outgoing message 1"
                                                               "outgoing message 2"]}))
            (protocols/post-message! port "outgoing message 3")
            (is (= (get-port-mock-state port) {:connected     true
                                               :sent-messages ["outgoing message 1"
                                                               "outgoing message 2"
                                                               "outgoing message 3"]}))
            (when-not advanced-mode?
              (is (thrown-with-msg? js/Error
                                    #"received a nil message"
                                    (fire-message-on-port port nil))))
            (fire-message-on-port port "incoming message 1")
            (is (= @message-recorder ['("incoming message 1")]))
            (fire-message-on-port port "incoming message 2")
            (is (= @message-recorder ['("incoming message 1")
                                      '("incoming message 2")]))
            (fire-message-on-port port "incoming message 3")
            (is (= "incoming message 1" (<! port)))
            (is (= @disconnect-count 0))

            ; --- DISCONNECTED ----------------------------------------------------------------------------------------------
            (protocols/disconnect! port)
            (is (= @disconnect-count 1))
            (is (= (get-port-mock-state port) {:connected     false
                                               :sent-messages ["outgoing message 1"
                                                               "outgoing message 2"
                                                               "outgoing message 3"]}))
            (is (= "incoming message 2" (<! port)))
            (when-not advanced-mode?
              (is (thrown-with-msg? js/Error
                                    #"post-message! called on already disconnected port"
                                    (protocols/post-message! port "some message")))
              (is (thrown-with-msg? js/Error
                                    #"disconnect! called on already disconnected port"
                                    (protocols/disconnect! port)))
              (is (thrown-with-msg? js/Error
                                    #"on-message! called on already disconnected port"
                                    (protocols/on-message! port identity)))
              (is (thrown-with-msg? js/Error
                                    #"on-disconnect! called on already disconnected port"
                                    (protocols/on-disconnect! port identity))))
            (is (= @disconnect-count 1))
            (when-not advanced-mode?
              (is (thrown-with-msg? js/Error
                                    #"post-message! called with nil message"
                                    (protocols/post-message! port nil)))
              (is (thrown-with-msg? js/Error
                                    #"received a nil message"
                                    (fire-message-on-port port nil)))
              (is (thrown-with-msg? js/Error
                                    #"put-message! called on already disconnected port"
                                    (fire-message-on-port port "some message"))))
            (is (= (get-port-mock-state port) {:connected     false
                                               :sent-messages ["outgoing message 1"
                                                               "outgoing message 2"
                                                               "outgoing message 3"]}))
            (done)))))))
