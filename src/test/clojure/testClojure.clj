(ns testClojure
 (:use clojure.test))

(run-all-tests)

(deftest add-x-to-y
  (is (= 5 (+ 2 3))))
  
 (deftest minus-d-2
 	(is (= 4 (- 10 6))))
  
(defn foo [a b]
	(println (str "super " a b)))
