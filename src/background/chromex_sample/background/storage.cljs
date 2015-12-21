(ns chromex-sample.background.storage
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.core.async :refer [<! chan]]
            [chromex.logging :refer-macros [log info warn error group group-end]]
            [chromex.protocols :refer [get set]]
            [chromex.ext.storage :as storage]))

(defn test-storage! []
  (let [local-storage (storage/get-local)]
    (set local-storage #js {"key1" "string"
                            "key2" #js [1 2 3]
                            "key3" true
                            "key4" nil})
    (go
      (let [[items] (<! (get local-storage))]
        (log "fetch all:" items)))
    (go
      (let [[items] (<! (get local-storage "key1"))]
        (log "fetch key1:" items)))
    (go
      (let [[items] (<! (get local-storage #js ["key2" "key3"]))]
        (log "fetch key2 and key3:" items)))))