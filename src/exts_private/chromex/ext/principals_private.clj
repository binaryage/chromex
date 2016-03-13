(ns chromex.ext.principals-private
  "Experimental APIs to trigger Chrome sign in actions.
   Only enabled if the flag 'new-profile-management' is set.
   
     * available since Chrome 32
     * https://developer.chrome.com/extensions/principalsPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro sign-out
  "Triggers Chrome sign out. Only enabled if the flag 'new-profile-management' is set.
   
   See https://developer.chrome.com/extensions/principalsPrivate#method-signOut."
  ([] (gen-call :function ::sign-out &form)))

(defmacro show-avatar-bubble
  "Shows the avatar bubble. Only enabled if the flag 'new-profile-management' is set.
   
   See https://developer.chrome.com/extensions/principalsPrivate#method-showAvatarBubble."
  ([] (gen-call :function ::show-avatar-bubble &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.principalsPrivate",
   :since "32",
   :functions [{:id ::sign-out, :name "signOut"} {:id ::show-avatar-bubble, :name "showAvatarBubble"}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))