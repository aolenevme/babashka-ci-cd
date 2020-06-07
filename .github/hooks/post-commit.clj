#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :as s])


(defn get-top-level-directory []
      (-> (sh "git" "rev-parse" "--show-toplevel")
          :out
          (s/trim)))

(defn path->package-json []
      (str (get-top-level-directory) "/package.json"))


(defn get-package-json-diff []
      (-> (sh "git" "diff" "HEAD^..HEAD" "--" (path->package-json))
          :out
          (s/trim)))

(def version-substr-length 15)

(defn get-version []
      (try
        (let [new-version-substr-begin (+ (s/index-of (get-package-json-diff) "+  \"version\": \"") version-substr-length)
              new-version-comma-end (s/index-of (get-package-json-diff) "\"," new-version-substr-begin)
              new-version (subs (get-package-json-diff) new-version-substr-begin new-version-comma-end)]
             new-version)
        (catch Exception _ "")))

(try
  (when-let [version (get-version)
             is-version-increased? (not= (count version) 0)]
            (-> (sh "git" "tag" "-a" (str "v" version) "-m" version)
                :out
                (s/trim)))
  (catch Exception e
    (println e)
    (System/exit 1)))