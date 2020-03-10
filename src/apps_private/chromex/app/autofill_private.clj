(ns chromex.app.autofill-private
  "Use the chrome.autofillPrivate API to add, remove, or update
   autofill data from the settings UI.

     * available since Chrome master"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro save-address
  "Saves the given address. If |address| has an empty string as its ID, it will be assigned a new one and added as a new
   entry.

     |address| - The address entry to save."
  ([address] (gen-call :function ::save-address &form address)))

(defmacro get-country-list
  "Gets the list of all countries.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [countries] where:

     |countries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-country-list &form)))

(defmacro get-address-components
  "Gets the address components for a given country code.

     |country-code| - A two-character string representing the address' country     whose components should be returned. See
                      autofill_country.cc for a     list of valid codes.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [components] where:

     |components| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([country-code] (gen-call :function ::get-address-components &form country-code)))

(defmacro get-address-list
  "Gets the list of addresses.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-address-list &form)))

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

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [validated-phone-numbers] where:

     |validated-phone-numbers| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([params] (gen-call :function ::validate-phone-numbers &form params)))

(defmacro get-credit-card-list
  "Gets the list of credit cards.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-credit-card-list &form)))

(defmacro mask-credit-card
  "Clears the data associated with a wallet card which was saved locally so that the saved copy is masked (e.g., 'Card ending
   in 1234').

     |guid| - GUID of the credit card to mask."
  ([guid] (gen-call :function ::mask-credit-card &form guid)))

(defmacro migrate-credit-cards
  "Triggers local credit cards migration."
  ([] (gen-call :function ::migrate-credit-cards &form)))

(defmacro log-server-card-link-clicked
  "Logs that the server cards edit link was clicked."
  ([] (gen-call :function ::log-server-card-link-clicked &form)))

(defmacro set-credit-card-fido-auth-enabled-state
  "Enables or disables FIDO Authentication for credit card unmasking.

     |enabled| - ?"
  ([enabled] (gen-call :function ::set-credit-card-fido-auth-enabled-state &form enabled)))

(defmacro get-upi-id-list
  "Gets the list of UPI IDs (a.k.a. Virtual Payment Addresses).

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [entries] where:

     |entries| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-upi-id-list &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-personal-data-changed-events
  "Fired when the personal data has changed, meaning that an entry has been added, removed, or changed. |entries| The updated
   list of entries.

   Events will be put on the |channel| with signature [::on-personal-data-changed [address-entries credit-card-entries]]
   where:

     |address-entries| - ?
     |credit-card-entries| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-personal-data-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.autofill-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.autofillPrivate",
   :since "master",
   :functions
   [{:id ::save-address, :name "saveAddress", :params [{:name "address", :type "autofillPrivate.AddressEntry"}]}
    {:id ::get-country-list,
     :name "getCountryList",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "countries", :type "[array-of-objects]"}]}}]}
    {:id ::get-address-components,
     :name "getAddressComponents",
     :callback? true,
     :params
     [{:name "country-code", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "components", :type "object"}]}}]}
    {:id ::get-address-list,
     :name "getAddressList",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "entries", :type "[array-of-autofillPrivate.AddressEntrys]"}]}}]}
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
    {:id ::get-credit-card-list,
     :name "getCreditCardList",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "entries", :type "[array-of-autofillPrivate.CreditCardEntrys]"}]}}]}
    {:id ::mask-credit-card, :name "maskCreditCard", :params [{:name "guid", :type "string"}]}
    {:id ::migrate-credit-cards, :name "migrateCreditCards"}
    {:id ::log-server-card-link-clicked, :name "logServerCardLinkClicked"}
    {:id ::set-credit-card-fido-auth-enabled-state,
     :name "setCreditCardFIDOAuthEnabledState",
     :params [{:name "enabled", :type "boolean"}]}
    {:id ::get-upi-id-list,
     :name "getUpiIdList",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "entries", :type "[array-of-strings]"}]}}]}],
   :events
   [{:id ::on-personal-data-changed,
     :name "onPersonalDataChanged",
     :params
     [{:name "address-entries", :type "[array-of-autofillPrivate.AddressEntrys]"}
      {:name "credit-card-entries", :type "[array-of-autofillPrivate.CreditCardEntrys]"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))