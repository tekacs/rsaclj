(ns rsaclj.rsa
  (:require [rsaclj.ntheory :as ntheory]
            [rsaclj.prime :as prime]
            [rsaclj.utils :refer [print-names]])
  (:gen-class))

(defn solve [n e words & {:keys [debug]}]
  (let [[p q] (prime/factorise n)
        tot (* (dec p) (dec q))
        d (ntheory/modinv e tot)]
    (when debug (print-names n e words p q tot d))
    (map #(ntheory/modexp % d n) words)))
