(ns chromex.ext.identity
  "Use the chrome.identity API to get OAuth2 access tokens.

     * available since Chrome 38
     * https://developer.chrome.com/extensions/identity"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-accounts
  "Retrieves a list of AccountInfo objects describing the accounts present on the profile.getAccounts is only supported on dev
   channel.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [accounts] where:

     |accounts| - https://developer.chrome.com/extensions/identity#property-callback-accounts.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/identity#method-getAccounts."
  ([] (gen-call :function ::get-accounts &form)))

(defmacro get-auth-token
  "Gets an OAuth2 access token using the client ID and scopes specified in the oauth2 section of manifest.json.The Identity
   API caches access tokens in memory, so it's ok to call getAuthToken non-interactively any time a token is required. The
   token cache automatically handles expiration.For a good user experience it is important interactive token requests are
   initiated by UI in your app explaining what the authorization is for. Failing to do this will cause your users to get
   authorization requests, or Chrome sign in screens if they are not signed in, with with no context. In particular, do not
   use getAuthToken interactively when your app is first launched.

     |details| - Token options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [token granted-scopes] where:

     |token| - https://developer.chrome.com/extensions/identity#property-callback-token.
     |granted-scopes| - https://developer.chrome.com/extensions/identity#property-callback-grantedScopes.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/identity#method-getAuthToken."
  ([details] (gen-call :function ::get-auth-token &form details))
  ([] `(get-auth-token :omit)))

(defmacro get-profile-user-info
  "Retrieves email address and obfuscated gaia id of the user signed into a profile.Requires the identity.email manifest
   permission. Otherwise, returns an empty result.This API is different from identity.getAccounts in two ways. The information
   returned is available offline, and it only applies to the primary account for the profile.

     |details| - Profile options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [user-info] where:

     |user-info| - https://developer.chrome.com/extensions/identity#property-callback-userInfo.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/identity#method-getProfileUserInfo."
  ([details] (gen-call :function ::get-profile-user-info &form details))
  ([] `(get-profile-user-info :omit)))

(defmacro remove-cached-auth-token
  "Removes an OAuth2 access token from the Identity API's token cache.If an access token is discovered to be invalid, it
   should be passed to removeCachedAuthToken to remove it from the cache. The app may then retrieve a fresh token with
   getAuthToken.

     |details| - Token information.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/identity#method-removeCachedAuthToken."
  ([details] (gen-call :function ::remove-cached-auth-token &form details)))

(defmacro clear-all-cached-auth-tokens
  "Resets the state of the Identity API: - Removes all OAuth2 access tokens from the token cache - Removes user's account
   preferences - De-authorizes the user from all auth flows

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/identity#method-clearAllCachedAuthTokens."
  ([] (gen-call :function ::clear-all-cached-auth-tokens &form)))

(defmacro launch-web-auth-flow
  "Starts an auth flow at the specified URL.This method enables auth flows with non-Google identity providers by launching a
   web view and navigating it to the first URL in the provider's auth flow. When the provider redirects to a URL matching the
   pattern https://&lt;app-id&gt;.chromiumapp.org/*, the window will close, and the final redirect URL will be passed to the
   callback function.For a good user experience it is important interactive auth flows are initiated by UI in your app
   explaining what the authorization is for. Failing to do this will cause your users to get authorization requests with no
   context. In particular, do not launch an interactive auth flow when your app is first launched.

     |details| - WebAuth flow options.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response-url] where:

     |response-url| - https://developer.chrome.com/extensions/identity#property-callback-responseUrl.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/identity#method-launchWebAuthFlow."
  ([details] (gen-call :function ::launch-web-auth-flow &form details)))

(defmacro get-redirect-url
  "Generates a redirect URL to be used in |launchWebAuthFlow|.The generated URLs match the pattern
   https://&lt;app-id&gt;.chromiumapp.org/*.

     |path| - The path appended to the end of the generated URL.

   https://developer.chrome.com/extensions/identity#method-getRedirectURL."
  ([path] (gen-call :function ::get-redirect-url &form path))
  ([] `(get-redirect-url :omit)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-sign-in-changed-events
  "Fired when signin state changes for an account on the user's profile.

   Events will be put on the |channel| with signature [::on-sign-in-changed [account signed-in]] where:

     |account| - https://developer.chrome.com/extensions/identity#property-onSignInChanged-account.
     |signed-in| - https://developer.chrome.com/extensions/identity#property-onSignInChanged-signedIn.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/identity#event-onSignInChanged."
  ([channel & args] (apply gen-call :event ::on-sign-in-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.identity namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.identity",
   :since "38",
   :functions
   [{:id ::get-accounts,
     :name "getAccounts",
     :since "88",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "accounts", :type "[array-of-identity.AccountInfos]"}]}}]}
    {:id ::get-auth-token,
     :name "getAuthToken",
     :callback? true,
     :params
     [{:name "details", :optional? true, :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback
       {:params
        [{:name "token", :optional? true, :type "string"}
         {:name "granted-scopes", :optional? true, :type "[array-of-strings]"}]}}]}
    {:id ::get-profile-user-info,
     :name "getProfileUserInfo",
     :callback? true,
     :params
     [{:name "details", :optional? true, :since "84", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "user-info", :type "object"}]}}]}
    {:id ::remove-cached-auth-token,
     :name "removeCachedAuthToken",
     :callback? true,
     :params [{:name "details", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::clear-all-cached-auth-tokens,
     :name "clearAllCachedAuthTokens",
     :since "future",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::launch-web-auth-flow,
     :name "launchWebAuthFlow",
     :callback? true,
     :params
     [{:name "details", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "response-url", :optional? true, :type "string"}]}}]}
    {:id ::get-redirect-url,
     :name "getRedirectURL",
     :return-type "string",
     :params [{:name "path", :optional? true, :type "string"}]}],
   :events
   [{:id ::on-sign-in-changed,
     :name "onSignInChanged",
     :params [{:name "account", :type "identity.AccountInfo"} {:name "signed-in", :type "boolean"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))