(ns chromex-sample.dev.devtools
  (:require [chromex-lib.logging :refer-macros [log info warn error group group-end]]
            [devtools.core :as devtools]))

; -------------------------------------------------------------------------------------------------------------------

(log "installing cljs-devtools")

(devtools/set-pref! :install-sanity-hints true)
(devtools/install!)
