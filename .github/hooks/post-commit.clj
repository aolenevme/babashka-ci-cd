(defn- get-top-level-directory
  []
  (-> (shell/sh "git" "rev-parse" "--show-toplevel")
      :out
      (str/trim)))


(defn- path->package-json
  []
  (str (get-top-level-directory) "/package.json"))


(defn- get-package-json-diff
  []
  (-> (shell/sh "git" "diff" "HEAD^..HEAD" "--" (path->package-json))
      :out
      (str/trim)))


(def ^:private version-substr-length 15)


(defn- get-version
  []
  (try
    (let [new-version-substr-begin (+ (str/index-of (get-package-json-diff) "+  \"version\": \"") version-substr-length)
          new-version-comma-end (str/index-of (get-package-json-diff) "\"," new-version-substr-begin)
          new-version (subs (get-package-json-diff) new-version-substr-begin new-version-comma-end)]
      new-version)
    (catch Exception _ "")))


(try
  (let [version (get-version)
        is-version-increased? (not= (count version) 0)]
    (when is-version-increased?
      (-> (shell/sh "git" "tag" "-a" (str "v" version) "-m" version)
          :out
          (str/trim)
          (println))))
  (catch Exception e
    (println e)
    (System/exit 1)))
