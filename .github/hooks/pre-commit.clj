#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(defn validate-branch-name [] (load-file ".github/hooks/pre-push.clj"))

(defn lint-code []
      (let [lint-res (sh "clj-kondo" "--lint" "src")
            exit (:exit lint-res)
            out (:out lint-res)]
           (println out)
           (when-not (or (= exit 2) (= exit 0)) (System/exit 1))))

(defn format-code []
      (let [
            format-res (sh "clj"
                           "-Sdeps" "'{:deps {mvxcvi/cljstyle {:git/url \\\"https://github.com/greglook/cljstyle.git\\\", :sha \\\"c8bc620aeadd022136bb333970c03edf41627417\\\"}}}'"
                           "-m"
                           "cljstyle.main"
                           "fix"
                           "src")
            git-add-res (sh "git" "add" "src")
            format-exit (:exit format-res)
            git-add-exit (:exit git-add-res)
            format-out (:out format-res)
            git-add-out (:out format-res)]
           (println format-out)
           (println (sh "git" "add" "src"))
           (println git-add-out)
           (when-not (and
                       (or (= format-exit 2) (= format-exit 0))
                       (or (= git-add-exit 2) (= git-add-exit 0)))
                     (System/exit 1))))

(sh "clj"
    "-Sdeps" "'{:deps {mvxcvi/cljstyle {:git/url \\\"https://github.com/greglook/cljstyle.git\\\", :sha \\\"c8bc620aeadd022136bb333970c03edf41627417\\\"}}}'"
    "-m"
    "cljstyle.main"
    "fix"
    "src")
(validate-branch-name)
;(format-code)
(lint-code)