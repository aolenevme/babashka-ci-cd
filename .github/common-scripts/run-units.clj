(let [test-res (shell/sh  "shadow-cljs" "release" "test")
      exit (:exit test-res)
      out (:out test-res)]
  (println out)
  (when-not (and (zero? exit) (not (str/includes? out "0 failures, 0 errors"))) (System/exit 1)))
