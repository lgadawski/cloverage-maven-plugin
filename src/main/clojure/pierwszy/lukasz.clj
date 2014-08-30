(ns pierwszy.lukasz
  (:gen-class 
    :name lukasz
    :methods [#^{:static true} [aplusb [int int] int]]))

(defn aplusb
  "Calculates a+b"
  [a b & args]
  (+ a b))

(defn main [& args]
  (println "Hello cruel world from clojure extra file!")
  (println (str "(aplusb 100 200): " (aplusb 100 200)))
  (println (str "(aplusb 200 200): " (aplusb 200 200))))