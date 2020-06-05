#!/usr/bin/env bb

(require '[clojure.string :as str])

(def title-regex (re-pattern (first *command-line-args*)))
(def ref-name (second *command-line-args*))
(def title (second (next *command-line-args*)))
(def description (second (next (next *command-line-args*))))

(defn valid-title? [] (re-matches title-regex title))

(defn title-starts-with-ref-name? [] (str/starts-with? title ref-name))

(defn is-description-not-empty? []
      (let [description-length (count description)]
           (> description-length 0)))

(when-not (valid-title?)
          (do (println "The title of the pull request does not match regex pattern: " title-regex)
              (System/exit 1)))

(when-not (title-starts-with-ref-name?)
          (do (println "The title of the pull request has to start with " ref-name)
              (System/exit 1)))

(when-not (is-description-not-empty?)
          (do (println "Pull request description is empty.")
              (System/exit 1)))