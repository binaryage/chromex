(ns chromex.autofill-private
  "Use the chrome.autofillPrivate API to add, remove, or update
   autofill data from the settings UI.
   
     * available since Chrome 47
     * https://developer.chrome.com/extensions/autofillPrivate"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro save-address
  "Saves the given address. If |address| has an empty string as its ID, it will be assigned a new one and added as a new
   entry.
   
     |address| - The address entry to save."
  ([address] (gen-call :function ::save-address &form address)))

(defmacro get-address-components
  "Gets the address components for a given country code.
   
     |countryCode| - A two-character string representing the address' country     whose components should be returned. See
                     autofill_country.cc for a     list of valid codes.
     |callback| - Callback which will be called with components.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([country-code #_callback] (gen-call :function ::get-address-components &form country-code)))

(defmacro save-credit-card
  "Saves the given credit card. If |card| has an empty string as its ID, it will be assigned a new one and added as a new
   entry.
   
     |card| - The card entry to save."
  ([card] (gen-call :function ::save-credit-card &form card)))

(defmacro remove-entry
  "Removes the entry (address or credit card) with the given ID.
   
     |guid| - ID of the entry to remove."
  ([guid] (gen-call :function ::remove-entry &form guid)))

(defmacro validate-phone-numbers
  "Validates a newly-added phone number and invokes the callback with a list of validated numbers. Note that if the
   newly-added number was invalid, it will not be returned in the list of valid numbers.
   
     |params| - The parameters to this function.
     |callback| - Callback which will be called with validated phone numbers.
   
   Note: Instead of passing a callback function, you receive a core.async channel as return value."
  ([params #_callback] (gen-call :function ::validate-phone-numbers &form params)))

(defmacro mask-credit-card
  "Clears the data associated with a wallet card which was saved locally so that the saved copy is masked (e.g., 'Card ending
   in 1234').
   
     |guid| - GUID of the credit card to mask."
  ([guid] (gen-call :function ::mask-credit-card &form guid)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-address-list-changed-events
  "Fired when the address list has changed, meaning that an entry has been added, removed, or changed.  |entries| The updated
   list of entries.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-address-list-changed &form channel args)))
(defmacro tap-on-credit-card-list-changed-events
  "Fired when the credit card list has changed, meaning that an entry has been added, removed, or changed.  |entries| The
   updated list of entries.
   Events will be put on the |channel|.
   
   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-credit-card-list-changed &form channel args)))

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
  {:namespace "chrome.autofillPrivate",
   :since "47",
   :functions
   [{:id ::save-address, :name "saveAddress", :params [{:name "address", :type "autofillPrivate.AddressEntry"}]}
    {:id ::get-address-components,
     :name "getAddressComponents",
     :callback? true,
     :params
     [{:name "country-code", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "components", :type "object"}]}}]}
    {:id ::save-credit-card, :name "saveCreditCard", :params [{:name "card", :type "autofillPrivate.CreditCardEntry"}]}
    {:id ::remove-entry, :name "removeEntry", :params [{:name "guid", :type "string"}]}
    {:id ::validate-phone-numbers,
     :name "validatePhoneNumbers",
     :callback? true,
     :params
     [{:name "params", :type "object"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "validated-phone-numbers", :type "[array-of-strings]"}]}}]}
    {:id ::mask-credit-card, :name "maskCreditCard", :params [{:name "guid", :type "string"}]}],
   :events
   [{:id ::on-address-list-changed,
     :name "onAddressListChanged",
     :params [{:name "entries", :type "[array-of-autofillPrivate.AddressEntrys]"}]}
    {:id ::on-credit-card-list-changed,
     :name "onCreditCardListChanged",
     :params [{:name "entries", :type "[array-of-autofillPrivate.CreditCardEntrys]"}]}]})

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