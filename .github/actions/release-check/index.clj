(def ^:private package-json-diff (first *command-line-args*))


(defn- is-version-incremented?
  []
  (try
    (let [version-substr-length 12
          old-version-substr-begin (+ (str/index-of package-json-diff "-  \"version\": \"") version-substr-length)
          new-version-substr-begin (+ (str/index-of package-json-diff "+  \"version\": \"") version-substr-length)
          old-version-comma-end (str/index-of package-json-diff "," old-version-substr-begin)
          new-version-comma-end (str/index-of package-json-diff "," new-version-substr-begin)
          old-version (subs package-json-diff old-version-substr-begin old-version-comma-end)
          new-version (subs package-json-diff new-version-substr-begin new-version-comma-end)
          splitted-old-version (str/split old-version #"\.")
          splitted-new-version (str/split new-version #"\.")]
      (= (compare splitted-new-version splitted-old-version) 1))
    (catch Exception _ false)))


(when-not (is-version-incremented?)
  (println "Please, increment version in package.json before the merging.")
  (System/exit 1))
