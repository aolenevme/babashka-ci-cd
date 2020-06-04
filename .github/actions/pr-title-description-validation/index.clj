#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

;(println (first *command-line-args*))
(println *command-line-args*)

(defn valid-title? []
      (let [title-regex (re-pattern (first *command-line-args*))
            title (second *command-line-args*)]
           (re-matches title-regex title)))

(valid-title?)