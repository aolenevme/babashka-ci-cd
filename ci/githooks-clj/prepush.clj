#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(def branch-regex #"^(master|develop|(feat|release|hotfix)\/[a-z0-9._-]+)$")

(defn get-local-branch-name []
  (-> (sh "git" "rev-parse" "--abbrev-ref" "HEAD")
      :out
      (trim)))

(defn valid-branch-name? []
      (re-matches branch-regex (get-local-branch-name)))

(defn print-error-msg []
      (println (format "Branch names in this project must adhere to this contract: %s." branch-regex)))

(defn pre-push []
      (when-not (valid-branch-name?)
                (print-error-msg)
                (System/exit 1)))

(println 1)

(pre-push)