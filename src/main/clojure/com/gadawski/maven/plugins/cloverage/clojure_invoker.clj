(ns com.gadawski.maven.plugins.cloverage.clojure_invoker
  (:use [cloverage.coverage]
        [clojure.tools.namespace.find])
  (:gen-class))

(defn invoke-cloverage [nss & args]
  (apply -main nss))

(defn find-namespaces-in-dir-string [dir & args]
  (apply list
         (for [element (find-namespaces-in-dir dir)]
           (name element))))
