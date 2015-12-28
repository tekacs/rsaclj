(defproject rsaclj "0.1.0-SNAPSHOT"
  :description "Simple RSA solver in Clojure. Port of my own tekacs/simple-rsa-solver."
  :author "tekacs"
  :url "https://github.com/tekacs/rsaclj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [circleci/clj-yaml "0.5.5"]
                 [criterium "0.4.3"]]
  :main ^:skip-aot rsaclj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
