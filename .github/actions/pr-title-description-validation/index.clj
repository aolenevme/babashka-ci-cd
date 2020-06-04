#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :as str])

(def title-regex (re-pattern (first *command-line-args*)))
(def ref-name (second *command-line-args*))
(def title (second (next *command-line-args*)))
(def description (second (next (next *command-line-args*))))

(defn valid-title? [] (re-matches title-regex title))

(defn title-starts-with-ref-name? [] (str/starts-with? title ref-name))

(defn is-description-not-empty? []
      (let [description-length (count description)]
           (> description-length 0)))

(valid-title?)
(title-starts-with-ref-name?)
(is-description-not-empty?)