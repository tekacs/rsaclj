(ns rsaclj.core
  (:require [clojure.string :refer [join split]]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.edn :as edn]
            [clj-yaml.core :as yaml]
            [rsaclj.rsa :as rsa]
            [rsaclj.utils :as utils])
  (:gen-class))

(def cli-options
  "Declarative structure of command line options."
  [["-n" nil "RSA n" :id "n" :parse-fn biginteger]
   ["-e" nil "RSA e" :id "e" :parse-fn biginteger]
   ["-c" nil "RSA string" :id "c" :parse-fn edn/read-string]
   ["-a" "--ascii" "Convert resulting integers from ASCII?" :default true]
   ["-y" "--yaml FILE" "YAML file to read all parameters from." :id "yaml-file"]
   ["-e" "--edn FILE" "EDN file to read all parameters from." :id "edn-file"]
   ["-d" "--debug" "Whether to print debugging information."]
   ["-h" "--help"]])

(defn exit
  "Exit with a status code, printing a reason for exit."
  [code reason]
  (cond
    (string? reason) (println reason)
    (sequential? reason) (println (join " " reason))
    true (println (str reason)))
  (System/exit code))

(defn handle-args
  "Handle command-line arguments. The ugly parts of -main."
  [args]
  (let [opt* (parse-opts args cli-options)
        {:keys [options arguments errors summary]} opt*
        {:keys [yaml-file edn-file]} opt*
        yaml (if nil? yaml-file) {} (yaml/parse-string (slurp yaml-file))
        edn (if nil? edn-file) {} (edn/read-string (slurp edn-file))]
    (cond
      (:help options) (exit 0 summary)
      errors (exit 1 errors)
      true (merge yaml edn options))))

(defn solve
  "Run RSA solver"
  [& {:keys [n e c ascii debug]}]
  {:pre [(every? #(not (nil? %)) [n e c])]}
  (let [solved (rsa/solve n e c :debug debug)
        string (if ascii
                 (join (map char solved))
                 (join " " solved))]
    string))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (-> args handle-args solve println))
