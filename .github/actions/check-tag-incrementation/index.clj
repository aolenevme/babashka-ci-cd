#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(-> (sh "git checkout master")
    :out
    (trim)
    (println))