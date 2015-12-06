(ns chromex.app.test
  "  * available since Chrome 5
     * https://developer.chrome.com/extensions/test"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-config
  "Gives configuration options set by the test.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::get-config &form)))

(defmacro notify-fail
  "Notifies the browser process that test code running in the extension failed.  This is only used for internal unit testing."
  ([message] (gen-call :function ::notify-fail &form message)))

(defmacro notify-pass
  "Notifies the browser process that test code running in the extension passed.  This is only used for internal unit testing."
  ([message] (gen-call :function ::notify-pass &form message))
  ([] `(notify-pass :omit)))

(defmacro log
  "Logs a message during internal unit testing."
  ([message] (gen-call :function ::log &form message)))

(defmacro send-message
  "Sends a string message to the browser process, generating a Notification that C++ test code can wait for.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([message #_callback] (gen-call :function ::send-message &form message)))

(defmacro callback-added ([] (gen-call :function ::callback-added &form)))

(defmacro run-next-test ([] (gen-call :function ::run-next-test &form)))

(defmacro fail ([message] (gen-call :function ::fail &form message))
  ([] `(fail :omit)))

(defmacro succeed ([message] (gen-call :function ::succeed &form message))
  ([] `(succeed :omit)))

(defmacro run-with-natives-enabled
  "Runs the given function with access to native methods enabled.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::run-with-natives-enabled &form)))

(defmacro get-module-system
  "Returns an instance of the module system for the given context."
  ([context] (gen-call :function ::get-module-system &form context)))

(defmacro assert-true ([test message] (gen-call :function ::assert-true &form test message))
  ([test] `(assert-true ~test :omit)))

(defmacro assert-false ([test message] (gen-call :function ::assert-false &form test message))
  ([test] `(assert-false ~test :omit)))

(defmacro assert-bool ([test expected message] (gen-call :function ::assert-bool &form test expected message))
  ([test expected] `(assert-bool ~test ~expected :omit)))

(defmacro check-deep-eq ([expected actual] (gen-call :function ::check-deep-eq &form expected actual))
  ([expected] `(check-deep-eq ~expected :omit))
  ([] `(check-deep-eq :omit :omit)))

(defmacro assert-eq ([expected actual message] (gen-call :function ::assert-eq &form expected actual message))
  ([expected actual] `(assert-eq ~expected ~actual :omit))
  ([expected] `(assert-eq ~expected :omit :omit))
  ([] `(assert-eq :omit :omit :omit)))

(defmacro assert-no-last-error ([] (gen-call :function ::assert-no-last-error &form)))

(defmacro assert-last-error ([expected-error] (gen-call :function ::assert-last-error &form expected-error)))

(defmacro assert-throws
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([self args message #_fn] (gen-call :function ::assert-throws &form self args message))
  ([self args] `(assert-throws ~self ~args :omit)))

(defmacro callback
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([expected-error #_func] (gen-call :function ::callback &form expected-error))
  ([] `(callback :omit)))

(defmacro listen-once
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([event #_func] (gen-call :function ::listen-once &form event)))

(defmacro listen-forever
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([event #_func] (gen-call :function ::listen-forever &form event)))

(defmacro callback-pass
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_func] (gen-call :function ::callback-pass &form)))

(defmacro callback-fail
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([expected-error #_func] (gen-call :function ::callback-fail &form expected-error)))

(defmacro run-tests ([tests] (gen-call :function ::run-tests &form tests)))

(defmacro get-api-features ([] (gen-call :function ::get-api-features &form)))

(defmacro get-api-definitions ([api-names] (gen-call :function ::get-api-definitions &form api-names))
  ([] `(get-api-definitions :omit)))

(defmacro is-processing-user-gesture ([] (gen-call :function ::is-processing-user-gesture &form)))

(defmacro run-with-user-gesture
  "Runs the callback in the context of a user gesture.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::run-with-user-gesture &form)))

(defmacro run-without-user-gesture
  "
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::run-without-user-gesture &form)))

(defmacro wait-for-round-trip
  "Sends a string message one round trip from the renderer to the browser process and back.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([message #_callback] (gen-call :function ::wait-for-round-trip &form message)))

(defmacro set-exception-handler
  "Sets the function to be called when an exception occurs. By default this is a function which fails the test. This is reset
   for every test run through test.runTests.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([#_callback] (gen-call :function ::set-exception-handler &form)))

(defmacro get-wake-event-page
  "Returns the wake-event-page API function, which can be called to wake up the extension's event page."
  ([] (gen-call :function ::get-wake-event-page &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-message-events
  "Used to test sending messages to extensions.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-message &form channel args)))

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
  {:namespace "chrome.test",
   :since "5",
   :functions
   [{:id ::get-config,
     :name "getConfig",
     :since "9",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "test-config", :type "object"}]}}]}
    {:id ::notify-fail, :name "notifyFail", :params [{:name "message", :type "string"}]}
    {:id ::notify-pass, :name "notifyPass", :params [{:name "message", :optional? true, :type "string"}]}
    {:id ::log, :name "log", :params [{:name "message", :type "string"}]}
    {:id ::send-message,
     :name "sendMessage",
     :since "6",
     :callback? true,
     :params
     [{:name "message", :type "string"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "response", :type "string"}]}}]}
    {:id ::callback-added, :name "callbackAdded", :since "27"}
    {:id ::run-next-test, :name "runNextTest", :since "27"}
    {:id ::fail, :name "fail", :since "27", :params [{:name "message", :optional? true, :type "any"}]}
    {:id ::succeed, :name "succeed", :since "27", :params [{:name "message", :optional? true, :type "any"}]}
    {:id ::run-with-natives-enabled,
     :name "runWithNativesEnabled",
     :since "46",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::get-module-system,
     :name "getModuleSystem",
     :since "46",
     :return-type "any",
     :params [{:name "context", :type "any"}]}
    {:id ::assert-true,
     :name "assertTrue",
     :since "27",
     :params [{:name "test", :type "string-or-boolean"} {:name "message", :optional? true, :type "string"}]}
    {:id ::assert-false,
     :name "assertFalse",
     :since "27",
     :params [{:name "test", :type "string-or-boolean"} {:name "message", :optional? true, :type "string"}]}
    {:id ::assert-bool,
     :name "assertBool",
     :since "27",
     :params
     [{:name "test", :type "string-or-boolean"}
      {:name "expected", :type "boolean"}
      {:name "message", :optional? true, :type "string"}]}
    {:id ::check-deep-eq,
     :name "checkDeepEq",
     :since "27",
     :params [{:name "expected", :optional? true, :type "any"} {:name "actual", :optional? true, :type "any"}]}
    {:id ::assert-eq,
     :name "assertEq",
     :since "27",
     :params
     [{:name "expected", :optional? true, :type "any"}
      {:name "actual", :optional? true, :type "any"}
      {:name "message", :optional? true, :type "string"}]}
    {:id ::assert-no-last-error, :name "assertNoLastError", :since "27"}
    {:id ::assert-last-error, :name "assertLastError", :since "27", :params [{:name "expected-error", :type "string"}]}
    {:id ::assert-throws,
     :name "assertThrows",
     :since "29",
     :callback? true,
     :params
     [{:name "self", :optional? true, :type "object"}
      {:name "args", :type "[array-of-anys]"}
      {:name "message", :optional? true, :type "string-or-RegExp"}
      {:name "fn", :type :callback}]}
    {:id ::callback,
     :name "callback",
     :since "27",
     :callback? true,
     :params
     [{:name "expected-error", :optional? true, :type "string"} {:name "func", :optional? true, :type :callback}]}
    {:id ::listen-once,
     :name "listenOnce",
     :since "27",
     :callback? true,
     :params [{:name "event", :type "any"} {:name "func", :type :callback}]}
    {:id ::listen-forever,
     :name "listenForever",
     :since "27",
     :callback? true,
     :params [{:name "event", :type "any"} {:name "func", :type :callback}]}
    {:id ::callback-pass,
     :name "callbackPass",
     :since "27",
     :callback? true,
     :params [{:name "func", :optional? true, :type :callback}]}
    {:id ::callback-fail,
     :name "callbackFail",
     :since "27",
     :callback? true,
     :params [{:name "expected-error", :type "string"} {:name "func", :optional? true, :type :callback}]}
    {:id ::run-tests, :name "runTests", :since "27", :params [{:name "tests", :type "[array-of-functions]"}]}
    {:id ::get-api-features, :name "getApiFeatures", :since "29"}
    {:id ::get-api-definitions,
     :name "getApiDefinitions",
     :since "27",
     :params [{:name "api-names", :optional? true, :type "[array-of-strings]"}]}
    {:id ::is-processing-user-gesture, :name "isProcessingUserGesture", :since "32"}
    {:id ::run-with-user-gesture,
     :name "runWithUserGesture",
     :since "32",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::run-without-user-gesture,
     :name "runWithoutUserGesture",
     :since "32",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::wait-for-round-trip,
     :name "waitForRoundTrip",
     :since "35",
     :callback? true,
     :params
     [{:name "message", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "message", :type "string"}]}}]}
    {:id ::set-exception-handler,
     :name "setExceptionHandler",
     :since "36",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "message", :type "string"} {:name "exception", :type "any"}]}}]}
    {:id ::get-wake-event-page, :name "getWakeEventPage", :since "46", :return-type "function"}],
   :events [{:id ::on-message, :name "onMessage", :params [{:name "info", :type "object"}]}]})

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