#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(defn get-current-branch-name []
      (-> (sh "git" "rev-parse" "--abbrev-ref" "HEAD")
          :out
          (trim)))

(defn create-commit-msg-regex [] (re-pattern (str "^" (get-current-branch-name) ":{1} .{1,}$")))

(defn get-current-commit-msg []
      (-> (sh "cat" "$1")
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

(commit-msg)
