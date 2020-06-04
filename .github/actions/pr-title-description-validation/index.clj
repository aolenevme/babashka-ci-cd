#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]]
         '[clojure.string :as str])

(defn get-title-regex [] (re-pattern (first *command-line-args*)))
(defn get-title [] (second *command-line-args*))
(defn get-ref-name [] (second (next *command-line-args*)))

(defn valid-title? [] (re-matches (get-title-regex) (get-title)))

(defn title-starts-with-ref-name? [] (str/starts-with? (get-title) (get-ref-name)))

(valid-title?)
(title-starts-with-ref-name?)