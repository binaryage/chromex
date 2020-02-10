(ns chromex-sample.content-script.refresh
  (:require-macros [chromex.support :refer [runonce]])
  (:require [cljs.core.async :refer [<! go go-loop]]
            [chromex-sample.content-script.state :as state]
            [chromex.protocols.chrome-port :refer [post-message!]]
            [cljs.reader :as reader]
            [goog.object :as gobj]))

; this code attempts to keep content script code up-to-date even on page refresh
; the problem is described here: https://bugs.chromium.org/p/chromium/issues/detail?id=104610
; the idea is to record all hot-reloads done by shadow-cljs and replay them on initial page load
; recording is done through background page because content script had no persistence

(defonce orig-do-js-load js/shadow.cljs.devtools.client.browser.do_js_load)

; we have to implement custom serialization because pr-str on whole structure fails reading back js string content
; it contains sourceMappingURL= encoded via data url and that chokes cljs reader
(defn serialize-source [source]
  #js {"name" (:resource-name source)
       "js"   (:js source)
       "rest" (pr-str (dissoc source :js :resource-name))})

(defn serialize-sources [sources]
  (let [a (array)]
    (doseq [source sources]
      (.push a (serialize-source source)))
    a))

(defn unserialize-source [serialized-source]
  (-> (reader/read-string (gobj/get serialized-source "rest"))
      (assoc :js (gobj/get serialized-source "js")
             :resource-name (gobj/get serialized-source "name"))))

(defn unserialize-sources [serialized-sources]
  (mapv unserialize-source serialized-sources))

(defn my-do-js-load [sources]
  (when-not (empty? sources)
    ;(js/console.log "RECORD" sources)
    (post-message! @state/background-port-atom #js {:command "record-sources-load"
                                                    :sources (serialize-sources sources)})
    (orig-do-js-load sources)))

(set! js/shadow.cljs.devtools.client.browser.do_js_load my-do-js-load)

(defn really-replay-reloads! [serialized-sources]
  (let [sources (unserialize-sources serialized-sources)]
    ;(js/console.log "REPLAY" sources)
    (orig-do-js-load sources)))

(defn replay-reloads! [sources-list]
  ; for some reason we have to return to event loop for this to work
  ; otherwise we would receive 'Error: Namespace "..." already declared.' errors
  (js/setTimeout #(really-replay-reloads! sources-list) 0))

(defn ^:dev/after-load reload! []
  ; intentionally no-op to silence shadow-cljs complaining about missing :dev/after-load
  )
