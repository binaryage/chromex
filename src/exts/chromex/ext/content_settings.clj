(ns chromex.ext.content-settings
  "Use the chrome.contentSettings API to change settings that control whether websites can use features such as cookies,
   JavaScript, and plugins. More generally speaking, content settings allow you to customize Chrome's behavior on a per-site
   basis instead of globally.
   
     * available since Chrome 16
     * https://developer.chrome.com/extensions/contentSettings"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-cookies
  "Whether to allow cookies and other local data to be set by websites. One ofallow: Accept cookies,block: Block
   cookies,session_only: Accept cookies only for the current session. Default is allow.The primary URL is the URL representing
   the cookie origin. The secondary URL is the URL of the top-level frame."
  ([] (gen-call :property ::cookies &form)))

(defmacro get-images
  "Whether to show images. One ofallow: Show images,block: Don't show images. Default is allow.The primary URL is the URL of
   the top-level frame. The secondary URL is the URL of the image."
  ([] (gen-call :property ::images &form)))

(defmacro get-javascript
  "Whether to run JavaScript. One ofallow: Run JavaScript,block: Don't run JavaScript. Default is allow.The primary URL is the
   URL of the top-level frame. The secondary URL is not used."
  ([] (gen-call :property ::javascript &form)))

(defmacro get-location
  "Whether to allow Geolocation. One of allow: Allow sites to track your physical location,block: Don't allow sites to track
   your physical location,ask: Ask before allowing sites to track your physical location. Default is ask.The primary URL is
   the URL of the document which requested location data. The secondary URL is the URL of the top-level frame (which may or
   may not differ from the requesting URL)."
  ([] (gen-call :property ::location &form)))

(defmacro get-plugins
  "Whether to run plugins. One ofallow: Run plugins automatically,block: Don't run plugins
   automatically,detect_important_content: Only run automatically those plugins that are detected as the website's main
   content.The primary URL is the URL of the top-level frame. The secondary URL is not used."
  ([] (gen-call :property ::plugins &form)))

(defmacro get-popups
  "Whether to allow sites to show pop-ups. One ofallow: Allow sites to show pop-ups,block: Don't allow sites to show pop-ups.
   Default is block.The primary URL is the URL of the top-level frame. The secondary URL is not used."
  ([] (gen-call :property ::popups &form)))

(defmacro get-notifications
  "Whether to allow sites to show desktop notifications. One ofallow: Allow sites to show desktop notifications,block: Don't
   allow sites to show desktop notifications,ask: Ask when a site wants to show desktop notifications. Default is ask.The
   primary URL is the URL of the document which wants to show the notification. The secondary URL is not used."
  ([] (gen-call :property ::notifications &form)))

(defmacro get-fullscreen
  "Whether to allow sites to toggle the fullscreen mode. One ofallow: Allow sites to toggle the fullscreen mode,ask: Ask when
   a site wants to toggle the fullscreen mode. Default is ask.The primary URL is the URL of the document which requested to
   toggle the fullscreen mode. The secondary URL is the URL of the top-level frame (which may or may not differ from the
   requesting URL)."
  ([] (gen-call :property ::fullscreen &form)))

(defmacro get-mouselock
  "Whether to allow sites to disable the mouse cursor. One of allow: Allow sites to disable the mouse cursor,block: Don't
   allow sites to disable the mouse cursor,ask: Ask when a site wants to disable the mouse cursor. Default is ask.The primary
   URL is the URL of the top-level frame. The secondary URL is not used."
  ([] (gen-call :property ::mouselock &form)))

(defmacro get-microphone
  "Whether to allow sites to access the microphone. One of allow: Allow sites to access the microphone,block: Don't allow
   sites to access the microphone,ask: Ask when a site wants to access the microphone. Default is ask.The primary URL is the
   URL of the document which requested microphone access. The secondary URL is not used.NOTE: The 'allow' setting is not valid
   if both patterns are ''."
  ([] (gen-call :property ::microphone &form)))

(defmacro get-camera
  "Whether to allow sites to access the camera. One of allow: Allow sites to access the camera,block: Don't allow sites to
   access the camera,ask: Ask when a site wants to access the camera. Default is ask.The primary URL is the URL of the
   document which requested camera access. The secondary URL is not used.NOTE: The 'allow' setting is not valid if both
   patterns are ''."
  ([] (gen-call :property ::camera &form)))

(defmacro get-unsandboxed-plugins
  "Whether to allow sites to run plugins unsandboxed. One of allow: Allow sites to run plugins unsandboxed,block: Don't allow
   sites to run plugins unsandboxed,ask: Ask when a site wants to run a plugin unsandboxed. Default is ask.The primary URL is
   the URL of the top-level frame. The secondary URL is not used."
  ([] (gen-call :property ::unsandboxed-plugins &form)))

(defmacro get-automatic-downloads
  "Whether to allow sites to download multiple files automatically. One of allow: Allow sites to download multiple files
   automatically,block: Don't allow sites to download multiple files automatically,ask: Ask when a site wants to download
   files automatically after the first file. Default is ask.The primary URL is the URL of the top-level frame. The secondary
   URL is not used."
  ([] (gen-call :property ::automatic-downloads &form)))

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
  {:namespace "chrome.contentSettings",
   :since "16",
   :properties
   [{:id ::cookies, :name "cookies", :return-type "contentSettings.ContentSetting"}
    {:id ::images, :name "images", :return-type "contentSettings.ContentSetting"}
    {:id ::javascript, :name "javascript", :return-type "contentSettings.ContentSetting"}
    {:id ::location, :name "location", :since "42", :return-type "contentSettings.ContentSetting"}
    {:id ::plugins, :name "plugins", :return-type "contentSettings.ContentSetting"}
    {:id ::popups, :name "popups", :return-type "contentSettings.ContentSetting"}
    {:id ::notifications, :name "notifications", :return-type "contentSettings.ContentSetting"}
    {:id ::fullscreen, :name "fullscreen", :since "42", :return-type "contentSettings.ContentSetting"}
    {:id ::mouselock, :name "mouselock", :since "42", :return-type "contentSettings.ContentSetting"}
    {:id ::microphone, :name "microphone", :since "46", :return-type "contentSettings.ContentSetting"}
    {:id ::camera, :name "camera", :since "46", :return-type "contentSettings.ContentSetting"}
    {:id ::unsandboxed-plugins, :name "unsandboxedPlugins", :since "42", :return-type "contentSettings.ContentSetting"}
    {:id ::automatic-downloads, :name "automaticDownloads", :since "42", :return-type "contentSettings.ContentSetting"}]})

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