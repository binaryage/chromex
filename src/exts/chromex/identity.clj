(ns chromex.identity
  "Use the chrome.identity API to get OAuth2 access tokens.
   
     * available since Chrome 29
     * https://developer.chrome.com/extensions/identity"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.apigen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-accounts
  "Retrieves a list of AccountInfo objects describing the accounts present on the profile.getAccounts is only
   supported on dev channel."
  [#_callback]
  (gen-call :function ::get-accounts (meta &form)))

(defmacro get-auth-token
  "Gets an OAuth2 access token using the client ID and scopes specified in the oauth2 section of manifest.json.The
   Identity API caches access tokens in memory, so it's ok to call getAuthToken non-interactively any time a token is
   required. The token cache automatically handles expiration.For a good user experience it is important interactive
   token requests are initiated by UI in your app explaining what the authorization is for. Failing to do this will
   cause your users to get authorization requests, or Chrome sign in screens if they are not signed in, with with no
   context. In particular, do not use getAuthToken interactively when your app is first launched.
   
     |details| - Token options.
     |callback| - Called with an OAuth2 access token as specified by the manifest, or undefined if there was an
                  error."
  [details #_callback]
  (gen-call :function ::get-auth-token (meta &form) details))

(defmacro get-profile-user-info
  "Retrieves email address and obfuscated gaia id of the user signed into a profile.This API is different from
   identity.getAccounts in two ways. The information returned is available offline, and it only applies to the primary
   account for the profile."
  [#_callback]
  (gen-call :function ::get-profile-user-info (meta &form)))

(defmacro remove-cached-auth-token
  "Removes an OAuth2 access token from the Identity API's token cache.If an access token is discovered to be invalid,
   it should be passed to removeCachedAuthToken to remove it from the cache. The app may then retrieve a fresh token
   with getAuthToken.
   
     |details| - Token information.
     |callback| - Called when the token has been removed from the cache."
  [details #_callback]
  (gen-call :function ::remove-cached-auth-token (meta &form) details))

(defmacro launch-web-auth-flow
  "Starts an auth flow at the specified URL.This method enables auth flows with non-Google identity providers by
   launching a web view and navigating it to the first URL in the provider's auth flow. When the provider redirects to
   a URL matching the pattern https://&lt;app-id&gt;.chromiumapp.org/*, the window will close, and the final redirect
   URL will be passed to the callback function.For a good user experience it is important interactive auth flows are
   initiated by UI in your app explaining what the authorization is for. Failing to do this will cause your users to
   get authorization requests with no context. In particular, do not launch an interactive auth flow when your app is
   first launched.
   
     |details| - WebAuth flow options.
     |callback| - Called with the URL redirected back to your application."
  [details #_callback]
  (gen-call :function ::launch-web-auth-flow (meta &form) details))

(defmacro get-redirect-url
  "Generates a redirect URL to be used in |launchWebAuthFlow|.The generated URLs match the pattern
   https://&lt;app-id&gt;.chromiumapp.org/*.
   
     |path| - The path appended to the end of the generated URL."
  [path]
  (gen-call :function ::get-redirect-url (meta &form) path))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-sign-in-changed
  "Fired when signin state changes for an account on the user's profile."
  [channel]
  (gen-call :event ::on-sign-in-changed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.identity",
   :since "29",
   :functions
   [{:id ::get-accounts,
     :name "getAccounts",
     :since "48",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "accounts", :type "[array-of-identity.AccountInfos]"}]}}]}
    {:id ::get-auth-token,
     :name "getAuthToken",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "token", :type "string"}]}}]}
    {:id ::get-profile-user-info,
     :name "getProfileUserInfo",
     :since "37",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "user-info", :type "object"}]}}]}
    {:id ::remove-cached-auth-token,
     :name "removeCachedAuthToken",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :type :callback}]}
    {:id ::launch-web-auth-flow,
     :name "launchWebAuthFlow",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "response-url", :type "string"}]}}]}
    {:id ::get-redirect-url,
     :name "getRedirectURL",
     :since "33",
     :return-type "string",
     :params [{:name "path", :type "string"}]}],
   :events
   [{:id ::on-sign-in-changed,
     :name "onSignInChanged",
     :since "33",
     :params [{:name "account", :type "identity.AccountInfo"} {:name "signed-in", :type "boolean"}]}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))