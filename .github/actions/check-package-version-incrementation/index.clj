#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :as s])

(def package-json-diff (first *command-line-args*))
(def version-substr-length 12)

(println package-json-diff)
(println version-substr-length)

(def old-version-substr-begin (+ (s/index-of package-json-diff "-  version: ") version-substr-length))
(def new-version-substr-begin (+ (s/index-of package-json-diff "+  version: ") version-substr-length))

(def old-version-comma-end (s/index-of package-json-diff "," old-version-substr-begin))
(def new-version-comma-end (s/index-of package-json-diff "," new-version-substr-begin))

(def old-version (subs package-json-diff old-version-substr-begin old-version-comma-end))
(def new-version (subs package-json-diff new-version-substr-begin new-version-comma-end))

(def splitted-old-version (s/split old-version #"\."))
(def splitted-new-version (s/split new-version #"\."))

(def is-version-incremented? (= (compare splitted-new-version splitted-old-version) 1))

(defn print-error-msg []
      (println (format "Please, increment version in package.json before the merging. Current version is %s." old-version )))

(when-not is-version-incremented?
          (print-error-msg)
          (System/exit 1))