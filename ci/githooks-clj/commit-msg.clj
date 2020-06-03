#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]]
         '[clojure.string :as s])

(defn get-current-branch-name []
      (-> (sh "git" "rev-parse" "--abbrev-ref" "HEAD")
          :out
          (trim)))

(defn create-commit-msg-regex []
      (re-pattern (str "^" (s/replace-first (get-current-branch-name) #"\/" "\\\\/") ":{1} .{1,}$")))

(defn get-current-commit-msg []
      (-> (sh "cat" ".git/COMMIT_EDITMSG")
          :out
          (trim)))

(defn valid-commit-msg? []
      (re-matches (create-commit-msg-regex) (get-current-commit-msg)))

(defn print-error-msg []
      (println "Your commit message must follow this regex:")
      (println (create-commit-msg-regex)))

(defn commit-msg []
      (when-not (valid-commit-msg?)
                (print-error-msg)
                (System/exit 1)))

(println (get-current-commit-msg))

(commit-msg)
