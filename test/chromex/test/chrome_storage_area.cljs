(ns chromex.test.chrome-storage-area
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [cljs.core.async :refer [<! >! timeout chan close!]]
            [chromex.test-utils :refer [advanced-mode?]]
            [chromex-lib.protocols :as protocols :refer [IChromeStorageArea]]
            [chromex-lib.chrome-storage-area :refer []]
            [chromex-lib.support :refer-macros [oset]]
            [chromex.playground :refer-macros [get-storage-area]]))

; -- test against mocks -----------------------------------------------------------------------------------------------------

(deftest test-storage-area
  (testing "excercise ChromeStorageArea"
    (async done
      (let [storage-area (get-storage-area)]
        (go
          (is (= (satisfies? IChromeStorageArea storage-area)))
          (is (= [["some value"] nil] (<! (protocols/get storage-area "good-lookup"))))
          (is (= [[] nil] (<! (protocols/get storage-area "bad-lookup"))))
          (is (= [[] "error in lookup"] (<! (protocols/get storage-area "error-lookup"))))
          (is (= [["some 'getBytesInUse' answer"] nil] (<! (protocols/get-bytes-in-use storage-area "lookup"))))
          (is (= [["some 'set' answer" "additional callback param"] nil] (<! (protocols/set storage-area "items"))))
          (is (= [["some 'remove' answer"] nil] (<! (protocols/remove storage-area "keys"))))
          (is (= [["some 'clear' answer"] nil] (<! (protocols/clear storage-area))))
          (done))))))
