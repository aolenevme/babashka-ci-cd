#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(def package-json-diff (first *command-line-args*))

(println package-json-diff)

;(-> (sh "git checkout master")
;    :out
;    (trim)
;    (println))f