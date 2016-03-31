(ns chromex.error)

; Chrome sets chrome.runtime.lastError when error occured.
;   Read https://developer.chrome.com/apps/runtime#property-lastError
;   * this value is available only during callbacks
;   * chrome checks its access and reports unchecked errors to javascript console
;
; Since we convert callbacks to core.async channels, we need a way how to translate this into chromex.
;
; By default:
; * chromex-generated callbacks always check chrome.runtime.lastError and set it into chromex.error/last-error.
; * chromex callbacks optionally print errors using provided error reporter (see :callback-error-reporter config key)
;
; see chromex.defaults/default-callback-fn-factory for default implementation.

(def last-error (atom nil))

; -- error api --------------------------------------------------------------------------------------------------------------

(defn set-last-error! [error]
  (reset! last-error error))

(defn get-last-error []
  @last-error)