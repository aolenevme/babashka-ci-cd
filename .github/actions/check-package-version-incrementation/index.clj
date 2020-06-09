#!/usr/bin/env bb

(def package-json-diff (first *command-line-args*))
(def version-substr-length 12)

(defn is-version-incremented? []
      (try
        (let [old-version-substr-begin (+ (str/index-of package-json-diff "-  \"version\": \"") version-substr-length)
              new-version-substr-begin (+ (str/index-of package-json-diff "+  \"version\": \"") version-substr-length)
              old-version-comma-end (str/index-of package-json-diff "," old-version-substr-begin)
              new-version-comma-end (str/index-of package-json-diff "," new-version-substr-begin)
              old-version (subs package-json-diff old-version-substr-begin old-version-comma-end)
              new-version (subs package-json-diff new-version-substr-begin new-version-comma-end)
              splitted-old-version (str/split old-version #"\.")
              splitted-new-version (str/split new-version #"\.")]
             (= (compare splitted-new-version splitted-old-version) 1))
        (catch Exception _ false)))

(defn print-error-msg []
      (println "Please, increment version in package.json before the merging."))

(when-not (is-version-incremented?)
          (print-error-msg)
          (System/exit 1))