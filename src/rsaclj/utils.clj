(ns rsaclj.utils
  (:require [clojure.string :refer [join]])
  (:gen-class))

(defmacro ? [expr]
  `(let [form# '~expr
         val# ~expr]
     (prn form# '~'is val#)
     val#))

(defmacro name-map [& exprs]
  `(let [keys# '[~@exprs]
         vals# [~@exprs]]
     (zipmap keys# vals#)))

(defmacro print-names [& exprs]
  `(prn (name-map ~@exprs)))

(defn posint?
  [n]
  (and (integer? n) (pos? n)))
