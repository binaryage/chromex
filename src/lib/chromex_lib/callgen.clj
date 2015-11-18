(ns chromex-lib.callgen
  (:require [chromex-lib.support :refer [check-api-version check-deprecated get-star-call-symbol get-api-id
                                         get-item-by-id]]))

; -------------------------------------------------------------------------------------------------------------------

(defn gen-star-call [static-config src-info api-table descriptor config & args]
  (let [{:keys [id]} descriptor
        api (get-api-id api-table descriptor)
        since (or (:since descriptor) (:since api-table))
        until (or (:until descriptor) (:until api-table))
        deprecated (or (:deprecated descriptor) (:deprecated api-table))
        star-call (get-star-call-symbol id)]
    `(do
       ~(check-api-version static-config src-info api since until)
       ~(check-deprecated static-config src-info api deprecated)
       (~star-call ~config ~@args))))

(defn gen-call-from-group [collection tag singular static-config api-table item-id src-info config & args]
  (if-let [descriptor (get-item-by-id item-id (collection api-table))]
    (apply gen-star-call static-config src-info api-table (assoc descriptor tag true) config args)
    (assert false (str "unable to find " singular " with id " item-id "in:\n" api-table))))

(defn gen-property-call [static-config api-table item-id src-info config & args]
  (apply gen-call-from-group :properties :property? "property" static-config api-table item-id src-info config args))

(defn gen-function-call [static-config api-table item-id src-info config & args]
  (apply gen-call-from-group :functions :function "function" static-config api-table item-id src-info config args))

(defn gen-event-call [static-config api-table item-id src-info config & args]
  (apply gen-call-from-group :events :event? "event" static-config api-table item-id src-info config args))

(defn gen-call-from-table [static-config api-table kind item-id src-info config & args]
  (case kind
    :function (apply gen-function-call static-config api-table item-id src-info config args)
    :property (apply gen-property-call static-config api-table item-id src-info config args)
    :event (apply gen-event-call static-config api-table item-id src-info config args)))

; -------------------------------------------------------------------------------------------------------------------

(defn gen-tap-all-call [static-config api-table src-info config chan]
  (let [chan-sym (gensym "chan")
        config-sym (gensym "config")
        event-ids (map :id (:events api-table))
        taps (map #(gen-event-call static-config api-table % src-info config-sym chan-sym) event-ids)]
    `(let [~chan-sym ~chan
           ~config-sym ~config]
       ~@taps)))
