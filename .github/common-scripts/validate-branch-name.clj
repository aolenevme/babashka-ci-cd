(def ^:private branch-regex #"^(master|develop|(feat|release|hotfix)\/[a-z0-9._-]+)$")


(defn- get-current-branch-name
  []
  (-> (shell/sh "git" "rev-parse" "--abbrev-ref" "HEAD")
      :out
      (str/trim)))


(defn- valid-branch-name?
  []
  (re-matches branch-regex (get-current-branch-name)))


(defn- print-error-msg
  []
  (println (format "Branch names in this project must adhere to this contract: %s." branch-regex)))


(when-not (valid-branch-name?)
  (print-error-msg)
  (System/exit 1))
