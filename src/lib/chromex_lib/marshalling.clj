(ns chromex-lib.marshalling)

(defn marshall-in [_id type param]
  (case type
    param))

(defn marshall-out [_id type param]
  (case type
    param))

(defn gen-marshalling [direction id type param]
  (case direction
    :in (marshall-in id type param)
    :out (marshall-out id type param)))
