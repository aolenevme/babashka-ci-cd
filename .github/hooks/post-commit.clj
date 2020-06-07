#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(defn get-top-level-directory []
      (-> (sh "git" "rev-parse" "--show-toplevel")
          :out
          (trim)))

(defn path->package-json []
      (str (get-top-level-directory) "/package.json"))


(defn get-version []
      (-> (sh "git" "diff" "HEAD^..HEAD" "--" (path->package-json) "|" "grep" "'^\\+.*version'" "|" "sed" "-s" "'s/[^0-9\\.]//g'")
          :out
          (trim)))

(try
  (when-let [version (get-version)
             is-version-increased? (not= (count version) 0)]
            (sh "git" "tag" "-a" (str "v" version) "-m" version))
  (catch Exception e
    (println e)
    (System/exit 1)))