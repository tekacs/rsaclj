(ns rsaclj.prime
  "Primality testing and succession.
  Passthroughs in Java, which has these methods on BigInteger."
  (:refer-clojure :exclude [next])
  (:require [clojure.set :as set]
            [clojure.math.numeric-tower :as nt]
            [rsaclj.utils :refer [posint?]])
  (:gen-class))

;; Java uses 100 for its builtins.
;; ntheory.rb uses 25, so use that for comparative benchmarks.
(def ^:dynamic *certainty* 100)

(defn prime?
  "Determine probable primality of n (with certainty (1 - (1/2)^k))"
  [n]
  {:pre [(posint? n)]}
  (.isProbablePrime (biginteger n) *certainty*))

(defn next
  "Get the next probable prime above n (or 2, with no arguments)."
  ([] 2)
  ([n]
   {:pre [(posint? n)]}
   (.nextProbablePrime (biginteger n))))

(def all-primes
  "Infinite lazy sequence of all primes."
  (iterate next (next)))

(defn factorise
  "Factorise the product of two primes."
  [n]
  {:pre [(posint? n)]
   :post [(= n (apply * %))]}
  (let [[root _] (nt/exact-integer-sqrt n)]
   (let [fact (->>
               all-primes
               (take-while #(<= % root))
               (filter #(zero? (mod n %)))
               (first))]
     (if (nil? fact)
       [1 n]
       [fact (/ n fact)]))))

;; HACK: I assume there's a simpler builtin way to do this.
(defn- decompose [mapper elems]
  (loop [elemset (set elems)]
    (let [nextset (reduce set/union (map #(->> % mapper set) elemset))]
      (if (= elemset nextset)
        nextset
        (recur nextset)))))

(defn factorise-fully
  "Yield all _prime_ factors of n, as a set."
  [n]
  (decompose factorise [n]))
