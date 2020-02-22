(ns chromex.test.chrome-content-setting
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [chromex.playground :refer-macros [get-content-setting]]
            [chromex.protocols.chrome-content-setting :refer [IChromeContentSetting]]
            [cljs.core.async :refer [<!]]
            [cljs.test :refer-macros [async deftest is testing]]))

(deftest test-content-setting
  (testing "exercise ChromeContentSetting"
    (async done
           (let [content-setting (get-content-setting)]
             (go
               (is (= (satisfies? IChromeContentSetting content-setting)))
               (is (= [["blocked"] nil] (<! (chromex.protocols.chrome-content-setting/get content-setting
                                                           #js {"primaryPattern" "https://www.google.com/*"}))))
               (is (= [["allowed"] nil] (<! (chromex.protocols.chrome-content-setting/get content-setting
                                                           #js {"primaryPattern" "foobar"}))))
               (is (= [["some 'getResourceIdentifiers' answer"] nil]
                      (<! (chromex.protocols.chrome-content-setting/get-resource-identifiers content-setting))))
               (is (= [[] nil] (<! (chromex.protocols.chrome-content-setting/set content-setting #js {"primaryPattern" "foobar"}))))
               (is (= [[] nil] (<! (chromex.protocols.chrome-content-setting/clear content-setting #js {}))))
               (is (= [[] "no details"] (<! (chromex.protocols.chrome-content-setting/clear content-setting nil))))
               (done))))))
