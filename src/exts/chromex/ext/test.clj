(ns chromex.ext.test
  "  * available since Chrome 38
     * https://developer.chrome.com/extensions/test"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-config
  "Gives configuration options set by the test.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [test-config] where:

     |test-config| - https://developer.chrome.com/extensions/test#property-callback-testConfig.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-getConfig."
  ([] (gen-call :function ::get-config &form)))

(defmacro notify-fail
  "Notifies the browser process that test code running in the extension failed.  This is only used for internal unit testing.

     |message| - https://developer.chrome.com/extensions/test#property-notifyFail-message.

   https://developer.chrome.com/extensions/test#method-notifyFail."
  ([message] (gen-call :function ::notify-fail &form message)))

(defmacro notify-pass
  "Notifies the browser process that test code running in the extension passed.  This is only used for internal unit testing.

     |message| - https://developer.chrome.com/extensions/test#property-notifyPass-message.

   https://developer.chrome.com/extensions/test#method-notifyPass."
  ([message] (gen-call :function ::notify-pass &form message))
  ([] `(notify-pass :omit)))

(defmacro log
  "Logs a message during internal unit testing.

     |message| - https://developer.chrome.com/extensions/test#property-log-message.

   https://developer.chrome.com/extensions/test#method-log."
  ([message] (gen-call :function ::log &form message)))

(defmacro send-message
  "Sends a string message to the browser process, generating a Notification that C++ test code can wait for.

     |message| - https://developer.chrome.com/extensions/test#property-sendMessage-message.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - https://developer.chrome.com/extensions/test#property-callback-response.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-sendMessage."
  ([message] (gen-call :function ::send-message &form message)))

(defmacro callback-added
  "https://developer.chrome.com/extensions/test#method-callbackAdded."
  ([] (gen-call :function ::callback-added &form)))

(defmacro fail
  "  |message| - https://developer.chrome.com/extensions/test#property-fail-message.

   https://developer.chrome.com/extensions/test#method-fail."
  ([message] (gen-call :function ::fail &form message))
  ([] `(fail :omit)))

(defmacro succeed
  "  |message| - https://developer.chrome.com/extensions/test#property-succeed-message.

   https://developer.chrome.com/extensions/test#method-succeed."
  ([message] (gen-call :function ::succeed &form message))
  ([] `(succeed :omit)))

(defmacro get-module-system
  "Returns an instance of the module system for the given context.

     |context| - https://developer.chrome.com/extensions/test#property-getModuleSystem-context.

   https://developer.chrome.com/extensions/test#method-getModuleSystem."
  ([context] (gen-call :function ::get-module-system &form context)))

(defmacro assert-true
  "  |test| - https://developer.chrome.com/extensions/test#property-assertTrue-test.
     |message| - https://developer.chrome.com/extensions/test#property-assertTrue-message.

   https://developer.chrome.com/extensions/test#method-assertTrue."
  ([test message] (gen-call :function ::assert-true &form test message))
  ([test] `(assert-true ~test :omit)))

(defmacro assert-false
  "  |test| - https://developer.chrome.com/extensions/test#property-assertFalse-test.
     |message| - https://developer.chrome.com/extensions/test#property-assertFalse-message.

   https://developer.chrome.com/extensions/test#method-assertFalse."
  ([test message] (gen-call :function ::assert-false &form test message))
  ([test] `(assert-false ~test :omit)))

(defmacro check-deep-eq
  "  |expected| - https://developer.chrome.com/extensions/test#property-checkDeepEq-expected.
     |actual| - https://developer.chrome.com/extensions/test#property-checkDeepEq-actual.

   https://developer.chrome.com/extensions/test#method-checkDeepEq."
  ([expected actual] (gen-call :function ::check-deep-eq &form expected actual))
  ([expected] `(check-deep-eq ~expected :omit))
  ([] `(check-deep-eq :omit :omit)))

(defmacro assert-eq
  "  |expected| - https://developer.chrome.com/extensions/test#property-assertEq-expected.
     |actual| - https://developer.chrome.com/extensions/test#property-assertEq-actual.
     |message| - https://developer.chrome.com/extensions/test#property-assertEq-message.

   https://developer.chrome.com/extensions/test#method-assertEq."
  ([expected actual message] (gen-call :function ::assert-eq &form expected actual message))
  ([expected actual] `(assert-eq ~expected ~actual :omit))
  ([expected] `(assert-eq ~expected :omit :omit))
  ([] `(assert-eq :omit :omit :omit)))

(defmacro assert-no-last-error
  "https://developer.chrome.com/extensions/test#method-assertNoLastError."
  ([] (gen-call :function ::assert-no-last-error &form)))

(defmacro assert-last-error
  "  |expected-error| - https://developer.chrome.com/extensions/test#property-assertLastError-expectedError.

   https://developer.chrome.com/extensions/test#method-assertLastError."
  ([expected-error] (gen-call :function ::assert-last-error &form expected-error)))

(defmacro assert-throws
  "  |fn| - https://developer.chrome.com/extensions/test#property-assertThrows-fn.
     |self| - https://developer.chrome.com/extensions/test#property-assertThrows-self.
     |args| - https://developer.chrome.com/extensions/test#property-assertThrows-args.
     |message| - https://developer.chrome.com/extensions/test#property-assertThrows-message.

   https://developer.chrome.com/extensions/test#method-assertThrows."
  ([fn self args message] (gen-call :function ::assert-throws &form fn self args message))
  ([fn self args] `(assert-throws ~fn ~self ~args :omit)))

(defmacro callback
  "  |func| - https://developer.chrome.com/extensions/test#property-callback-func.
     |expected-error| - https://developer.chrome.com/extensions/test#property-callback-expectedError.

   https://developer.chrome.com/extensions/test#method-callback."
  ([func expected-error] (gen-call :function ::callback &form func expected-error))
  ([func] `(callback ~func :omit))
  ([] `(callback :omit :omit)))

(defmacro listen-once
  "  |event| - https://developer.chrome.com/extensions/test#property-listenOnce-event.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-listenOnce."
  ([event] (gen-call :function ::listen-once &form event)))

(defmacro listen-forever
  "  |event| - https://developer.chrome.com/extensions/test#property-listenForever-event.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-listenForever."
  ([event] (gen-call :function ::listen-forever &form event)))

(defmacro callback-pass
  "This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-callbackPass."
  ([] (gen-call :function ::callback-pass &form)))

(defmacro callback-fail
  "  |expected-error| - https://developer.chrome.com/extensions/test#property-callbackFail-expectedError.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-callbackFail."
  ([expected-error] (gen-call :function ::callback-fail &form expected-error)))

(defmacro run-tests
  "  |tests| - https://developer.chrome.com/extensions/test#property-runTests-tests.

   https://developer.chrome.com/extensions/test#method-runTests."
  ([tests] (gen-call :function ::run-tests &form tests)))

(defmacro get-api-features
  "https://developer.chrome.com/extensions/test#method-getApiFeatures."
  ([] (gen-call :function ::get-api-features &form)))

(defmacro get-api-definitions
  "  |api-names| - https://developer.chrome.com/extensions/test#property-getApiDefinitions-apiNames.

   https://developer.chrome.com/extensions/test#method-getApiDefinitions."
  ([api-names] (gen-call :function ::get-api-definitions &form api-names))
  ([] `(get-api-definitions :omit)))

(defmacro is-processing-user-gesture
  "https://developer.chrome.com/extensions/test#method-isProcessingUserGesture."
  ([] (gen-call :function ::is-processing-user-gesture &form)))

(defmacro run-with-user-gesture
  "Runs the callback in the context of a user gesture.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-runWithUserGesture."
  ([] (gen-call :function ::run-with-user-gesture &form)))

(defmacro wait-for-round-trip
  "Sends a string message one round trip from the renderer to the browser process and back.

     |message| - https://developer.chrome.com/extensions/test#property-waitForRoundTrip-message.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [message] where:

     |message| - https://developer.chrome.com/extensions/test#property-callback-message.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-waitForRoundTrip."
  ([message] (gen-call :function ::wait-for-round-trip &form message)))

(defmacro set-exception-handler
  "Sets the function to be called when an exception occurs. By default this is a function which fails the test. This is reset
   for every test run through test.runTests.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [message exception] where:

     |message| - https://developer.chrome.com/extensions/test#property-callback-message.
     |exception| - https://developer.chrome.com/extensions/test#property-callback-exception.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/test#method-setExceptionHandler."
  ([] (gen-call :function ::set-exception-handler &form)))

(defmacro get-wake-event-page
  "Returns the wake-event-page API function, which can be called to wake up the extension's event page.

   https://developer.chrome.com/extensions/test#method-getWakeEventPage."
  ([] (gen-call :function ::get-wake-event-page &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-message-events
  "Used to test sending messages to extensions.

   Events will be put on the |channel| with signature [::on-message [info]] where:

     |info| - https://developer.chrome.com/extensions/test#property-onMessage-info.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/extensions/test#event-onMessage."
  ([channel & args] (apply gen-call :event ::on-message &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.test namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.test",
   :since "38",
   :functions
   [{:id ::get-config,
     :name "getConfig",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "test-config", :type "object"}]}}]}
    {:id ::notify-fail, :name "notifyFail", :params [{:name "message", :type "string"}]}
    {:id ::notify-pass, :name "notifyPass", :params [{:name "message", :optional? true, :type "string"}]}
    {:id ::log, :name "log", :params [{:name "message", :type "string"}]}
    {:id ::send-message,
     :name "sendMessage",
     :callback? true,
     :params
     [{:name "message", :type "string"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "response", :type "string"}]}}]}
    {:id ::callback-added, :name "callbackAdded"}
    {:id ::fail, :name "fail", :params [{:name "message", :optional? true, :type "any"}]}
    {:id ::succeed, :name "succeed", :params [{:name "message", :optional? true, :type "any"}]}
    {:id ::get-module-system,
     :name "getModuleSystem",
     :since "46",
     :return-type "any",
     :params [{:name "context", :type "any"}]}
    {:id ::assert-true,
     :name "assertTrue",
     :params [{:name "test", :type "string-or-boolean"} {:name "message", :optional? true, :type "string"}]}
    {:id ::assert-false,
     :name "assertFalse",
     :params [{:name "test", :type "string-or-boolean"} {:name "message", :optional? true, :type "string"}]}
    {:id ::check-deep-eq,
     :name "checkDeepEq",
     :params [{:name "expected", :optional? true, :type "any"} {:name "actual", :optional? true, :type "any"}]}
    {:id ::assert-eq,
     :name "assertEq",
     :params
     [{:name "expected", :optional? true, :type "any"}
      {:name "actual", :optional? true, :type "any"}
      {:name "message", :optional? true, :type "string"}]}
    {:id ::assert-no-last-error, :name "assertNoLastError"}
    {:id ::assert-last-error, :name "assertLastError", :params [{:name "expected-error", :type "string"}]}
    {:id ::assert-throws,
     :name "assertThrows",
     :params
     [{:name "fn", :type "function"}
      {:name "self", :optional? true, :type "object"}
      {:name "args", :type "[array-of-anys]"}
      {:name "message", :optional? true, :type "string-or-RegExp"}]}
    {:id ::callback,
     :name "callback",
     :params
     [{:name "func", :optional? true, :type "function"} {:name "expected-error", :optional? true, :type "string"}]}
    {:id ::listen-once,
     :name "listenOnce",
     :callback? true,
     :params [{:name "event", :type "any"} {:name "func", :type :callback}]}
    {:id ::listen-forever,
     :name "listenForever",
     :callback? true,
     :params [{:name "event", :type "any"} {:name "func", :type :callback}]}
    {:id ::callback-pass,
     :name "callbackPass",
     :callback? true,
     :params [{:name "func", :optional? true, :type :callback}]}
    {:id ::callback-fail,
     :name "callbackFail",
     :callback? true,
     :params [{:name "expected-error", :type "string"} {:name "func", :optional? true, :type :callback}]}
    {:id ::run-tests, :name "runTests", :params [{:name "tests", :type "[array-of-functions]"}]}
    {:id ::get-api-features, :name "getApiFeatures"}
    {:id ::get-api-definitions,
     :name "getApiDefinitions",
     :params [{:name "api-names", :optional? true, :type "[array-of-strings]"}]}
    {:id ::is-processing-user-gesture, :name "isProcessingUserGesture"}
    {:id ::run-with-user-gesture,
     :name "runWithUserGesture",
     :callback? true,
     :params [{:name "callback", :type :callback}]}
    {:id ::wait-for-round-trip,
     :name "waitForRoundTrip",
     :callback? true,
     :params
     [{:name "message", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "message", :type "string"}]}}]}
    {:id ::set-exception-handler,
     :name "setExceptionHandler",
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
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))