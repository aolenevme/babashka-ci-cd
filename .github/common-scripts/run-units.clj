(let [test-res (shell/sh  "npx" "shadow-cljs" "release" "tests")
      exit (:exit test-res)
      out (:out test-res)]
  (println out)
  (when-not (and (zero? exit) (str/includes? out "0 failures, 0 errors")) (System/exit 1)))


(println
  (shell/sh "rm" "build/tests.js")
  "Deleted compiled tests at build/tests.js")
