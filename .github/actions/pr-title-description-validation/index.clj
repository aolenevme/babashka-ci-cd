#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]])

(-> (sh "echo \"Hello $1\"")
    :out
    (trim))