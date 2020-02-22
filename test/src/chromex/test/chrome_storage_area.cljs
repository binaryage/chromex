(ns chromex.test.chrome-storage-area
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [chromex.playground :refer-macros [get-storage-area]]
            [chromex.protocols.chrome-storage-area :refer [IChromeStorageArea]]
            [cljs.core.async :refer [<!]]
            [cljs.test :refer-macros [async deftest is testing]]))

; -- test against mocks -----------------------------------------------------------------------------------------------------

(deftest test-storage-area
  (testing "exercise ChromeStorageArea"
    (async done
      (let [storage-area (get-storage-area)]
        (go
          (is (= (satisfies? IChromeStorageArea storage-area)))
          (is (= [["some value"] nil] (<! (chromex.protocols.chrome-storage-area/get storage-area "good-lookup"))))
          (is (= [[] nil] (<! (chromex.protocols.chrome-storage-area/get storage-area "bad-lookup"))))
          (is (= [[] "error in lookup"] (<! (chromex.protocols.chrome-storage-area/get storage-area "error-lookup"))))
          (is (= [["some 'getBytesInUse' answer"] nil] (<! (chromex.protocols.chrome-storage-area/get-bytes-in-use storage-area "lookup"))))
          (is (= [["some 'set' answer" "additional callback param"] nil] (<! (chromex.protocols.chrome-storage-area/set storage-area "items"))))
          (is (= [["some 'remove' answer"] nil] (<! (chromex.protocols.chrome-storage-area/remove storage-area "keys"))))
          (is (= [["some 'clear' answer"] nil] (<! (chromex.protocols.chrome-storage-area/clear storage-area))))
          (done))))))
