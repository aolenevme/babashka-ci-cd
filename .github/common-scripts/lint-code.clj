(let [lint-res (shell/sh "clj-kondo" "--lint" "src" ".github")
      exit (:exit lint-res)
      out (:out lint-res)]
  (println out)
  (when-not (or (= exit 2) (zero? exit)) (System/exit 1)))
