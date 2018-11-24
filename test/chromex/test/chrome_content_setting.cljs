(ns chromex.test.chrome-content-setting
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [cljs.core.async :refer [<!]]
            [chromex.chrome-content-setting :as protocols :refer [IChromeContentSetting]]
            [chromex.playground :refer-macros [get-content-setting]]))

(deftest test-content-setting
  (testing "excercise ChromeContentSetting"
    (async done
           (let [content-setting (get-content-setting)]
             (go
               (is (= (satisfies? IChromeContentSetting content-setting)))
               (is (= [["blocked"] nil] (<! (protocols/get content-setting
                                                           #js {"primaryPattern" "https://www.google.com/*"}))))
               (is (= [["allowed"] nil] (<! (protocols/get content-setting
                                                           #js {"primaryPattern" "foobar"}))))
               (is (= [["some 'getResourceIdentifiers' answer"] nil]
                      (<! (protocols/get-resource-identifiers content-setting))))
               (is (= [[] nil] (<! (protocols/set content-setting #js {"primaryPattern" "foobar"}))))
               (is (= [[] nil] (<! (protocols/clear content-setting #js {}))))
               (is (= [[] "no details"] (<! (protocols/clear content-setting))))
               (done))))))
