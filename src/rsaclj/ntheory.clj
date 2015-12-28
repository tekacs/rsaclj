(ns rsaclj.ntheory
  (:gen-class))

(defn gcd
  "Determine the greatest common divisor of a and b."
  [a b]
  (.gcd (biginteger a) (biginteger b)))

(defn coprime
  "Determine if two values a and b share any common factors."
  [a b]
  (= (gcd a b) 1))

(defn totient
  "Calculate Euler's totient of a value a, ϕ(a)."
  [a]
  (let [orig-even (even? a)
        even-check #(not (and orig-even (even? %)))]
    (inc (->> (range 2 a) (filter even-check) (filter #(coprime a %)) count))))

(defn modexp
  "(base ^ pow) % mod performed efficiently."
  [base exp mod]
  (.modPow (biginteger base) (biginteger exp) (biginteger mod)))

(defn modinv
  "Return the modular inverse of i modulo m"
  [i m]
  (.modInverse (biginteger i) (biginteger m)))
