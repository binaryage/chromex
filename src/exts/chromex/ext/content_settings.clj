(ns chromex.ext.content-settings
  "Use the chrome.contentSettings API to change settings that control whether websites can use features such as cookies,
   JavaScript, and plugins. More generally speaking, content settings allow you to customize Chrome's behavior on a per-site
   basis instead of globally.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/contentSettings"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- properties -------------------------------------------------------------------------------------------------------------

(defmacro get-cookies
  "Whether to allow cookies and other local data to be set by websites. One ofallow: Accept cookies,block: Block
   cookies,session_only: Accept cookies only for the current session. Default is allow.The primary URL is the URL representing
   the cookie origin. The secondary URL is the URL of the top-level frame.

   https://developer.chrome.com/extensions/contentSettings#property-cookies."
  ([] (gen-call :property ::cookies &form)))

(defmacro get-images
  "Whether to show images. One ofallow: Show images,block: Don't show images. Default is allow.The primary URL is the URL of
   the top-level frame. The secondary URL is the URL of the image.

   https://developer.chrome.com/extensions/contentSettings#property-images."
  ([] (gen-call :property ::images &form)))

(defmacro get-javascript
  "Whether to run JavaScript. One ofallow: Run JavaScript,block: Don't run JavaScript. Default is allow.The primary URL is the
   URL of the top-level frame. The secondary URL is not used.

   https://developer.chrome.com/extensions/contentSettings#property-javascript."
  ([] (gen-call :property ::javascript &form)))

(defmacro get-location
  "Whether to allow Geolocation. One of allow: Allow sites to track your physical location,block: Don't allow sites to track
   your physical location,ask: Ask before allowing sites to track your physical location. Default is ask.The primary URL is
   the URL of the document which requested location data. The secondary URL is the URL of the top-level frame (which may or
   may not differ from the requesting URL).

   https://developer.chrome.com/extensions/contentSettings#property-location."
  ([] (gen-call :property ::location &form)))

(defmacro get-plugins
  "Whether to run plugins. One ofallow: Run plugins automatically,block: Don't run plugins
   automatically,detect_important_content: Only run automatically those plugins that are detected as the website's main
   content.The primary URL is the URL of the top-level frame. The secondary URL is not used.

   https://developer.chrome.com/extensions/contentSettings#property-plugins."
  ([] (gen-call :property ::plugins &form)))

(defmacro get-popups
  "Whether to allow sites to show pop-ups. One ofallow: Allow sites to show pop-ups,block: Don't allow sites to show pop-ups.
   Default is block.The primary URL is the URL of the top-level frame. The secondary URL is not used.

   https://developer.chrome.com/extensions/contentSettings#property-popups."
  ([] (gen-call :property ::popups &form)))

(defmacro get-notifications
  "Whether to allow sites to show desktop notifications. One ofallow: Allow sites to show desktop notifications,block: Don't
   allow sites to show desktop notifications,ask: Ask when a site wants to show desktop notifications. Default is ask.The
   primary URL is the URL of the document which wants to show the notification. The secondary URL is not used.

   https://developer.chrome.com/extensions/contentSettings#property-notifications."
  ([] (gen-call :property ::notifications &form)))

(defmacro get-fullscreen
  "Deprecated. No longer has any effect. Fullscreen permission is now automatically granted for all sites. Value is always
   allow.

   https://developer.chrome.com/extensions/contentSettings#property-fullscreen."
  ([] (gen-call :property ::fullscreen &form)))

(defmacro get-mouselock
  "Deprecated. No longer has any effect. Mouse lock permission is now automatically granted for all sites. Value is always
   allow.

   https://developer.chrome.com/extensions/contentSettings#property-mouselock."
  ([] (gen-call :property ::mouselock &form)))

(defmacro get-microphone
  "Whether to allow sites to access the microphone. One of allow: Allow sites to access the microphone,block: Don't allow
   sites to access the microphone,ask: Ask when a site wants to access the microphone. Default is ask.The primary URL is the
   URL of the document which requested microphone access. The secondary URL is not used.NOTE: The 'allow' setting is not valid
   if both patterns are ''.

   https://developer.chrome.com/extensions/contentSettings#property-microphone."
  ([] (gen-call :property ::microphone &form)))

(defmacro get-camera
  "Whether to allow sites to access the camera. One of allow: Allow sites to access the camera,block: Don't allow sites to
   access the camera,ask: Ask when a site wants to access the camera. Default is ask.The primary URL is the URL of the
   document which requested camera access. The secondary URL is not used.NOTE: The 'allow' setting is not valid if both
   patterns are ''.

   https://developer.chrome.com/extensions/contentSettings#property-camera."
  ([] (gen-call :property ::camera &form)))

(defmacro get-unsandboxed-plugins
  "Whether to allow sites to run plugins unsandboxed. One of allow: Allow sites to run plugins unsandboxed,block: Don't allow
   sites to run plugins unsandboxed,ask: Ask when a site wants to run a plugin unsandboxed. Default is ask.The primary URL is
   the URL of the top-level frame. The secondary URL is not used.

   https://developer.chrome.com/extensions/contentSettings#property-unsandboxedPlugins."
  ([] (gen-call :property ::unsandboxed-plugins &form)))

(defmacro get-automatic-downloads
  "Whether to allow sites to download multiple files automatically. One of allow: Allow sites to download multiple files
   automatically,block: Don't allow sites to download multiple files automatically,ask: Ask when a site wants to download
   files automatically after the first file. Default is ask.The primary URL is the URL of the top-level frame. The secondary
   URL is not used.

   https://developer.chrome.com/extensions/contentSettings#property-automaticDownloads."
  ([] (gen-call :property ::automatic-downloads &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.content-settings namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.contentSettings",
   :since "38",
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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))