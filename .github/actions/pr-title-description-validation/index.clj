(def ^:private title-regex (re-pattern (first *command-line-args*)))
(def ^:private ref-name (second *command-line-args*))
(def ^:private title (second (next *command-line-args*)))
(def ^:private description (second (nnext *command-line-args*)))


(defn- valid-title?
  []
  (re-matches title-regex title))


(defn- title-starts-with-ref-name?
  []
  (str/starts-with? title ref-name))


(defn- is-description-not-empty?
  []
  (let [description-length (count description)]
    (pos? description-length)))


(when-not (valid-title?)
  (println "The title of the pull request does not match regex pattern: " title-regex)
  (System/exit 1))


(when-not (title-starts-with-ref-name?)
  (println "The title of the pull request has to start with " ref-name)
  (System/exit 1))


(when-not (is-description-not-empty?)
  (println "Pull request description is empty.")
  (System/exit 1))
