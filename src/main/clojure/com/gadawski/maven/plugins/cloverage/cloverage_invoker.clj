(ns com.gadawski.maven.plugins.cloverage.cloverage_invoker
  (:use [cloverage.coverage])
  (:gen-class))

(defn main [nss & args]
  (apply -main nss))