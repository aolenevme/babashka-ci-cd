(defn- get-current-branch-name
  []
  (-> (shell/sh "git" "rev-parse" "--abbrev-ref" "HEAD")
      :out
      (str/trim)))


(defn- create-commit-msg-regex
  []
  (re-pattern (str "^" (str/replace-first (get-current-branch-name) #"\/" "\\\\/") ":{1} .{1,}$")))


(defn- get-current-commit-msg
  []
  (-> (shell/sh "cat" ".git/COMMIT_EDITMSG")
      :out
      (str/trim)))


(defn- valid-commit-msg?
  []
  (re-matches (create-commit-msg-regex) (get-current-commit-msg)))


(defn- print-error-msg
  []
  (println "Your commit message must follow this regex:")
  (println (create-commit-msg-regex)))


(defn- commit-msg
  []
  (when-not (valid-commit-msg?)
    (print-error-msg)
    (System/exit 1)))


(commit-msg)
