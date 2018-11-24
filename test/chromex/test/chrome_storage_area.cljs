(ns chromex.test.chrome-storage-area
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [cljs.core.async :refer [<!]]
            [chromex.protocols.chrome-storage-area :refer [IChromeStorageArea]]
            [chromex.playground :refer-macros [get-storage-area]]))

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
