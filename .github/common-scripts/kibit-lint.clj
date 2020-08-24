(let [lint-res (shell/sh "lein" "kibit" "src" ".github")
      exit (:exit lint-res)
      out (:out lint-res)]
  (println out)
  (when-not (zero? exit) (System/exit 1)))
