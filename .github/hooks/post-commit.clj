#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :as s])

(def version-substr-length 12)

(defn get-top-level-directory []
      (-> (sh "git" "rev-parse" "--show-toplevel")
          :out
          (s/trim)))

(defn path->package-json []
      (str (get-top-level-directory) "/package.json"))


(defn get-version []
      (-> (sh "git" "diff" "HEAD^..HEAD" "--" (path->package-json))
          :out
          (s/trim)))

(defn is-version-incremented? []
      (try
        (let [new-version-substr-begin (+ (s/index-of (get-version) "+  \"version\": \"") version-substr-length)
              new-version-comma-end (s/index-of package-json-diff "," new-version-substr-begin)
              new-version (subs package-json-diff new-version-substr-begin new-version-comma-end)]
             new-version)
        (catch Exception _ "")))

(println (is-version-incremented?))

;(try
;  (when-let [version (get-version)
;             is-version-increased? (not= (count version) 0)]
;            (sh "git" "tag" "-a" (str "v" version) "-m" version))
;  (catch Exception e
;    (println e)
;    (System/exit 1)))