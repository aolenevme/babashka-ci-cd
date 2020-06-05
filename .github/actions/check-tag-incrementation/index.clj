#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(def package-json-diff (first *command-line-args*))

(println package-json-diff)

;(-> (sh "git diff origin/master:package.json HEAD:package.json")
;    :out
;    (trim)
;    (println))