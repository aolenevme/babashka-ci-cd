(defn- validate-branch-name
  []
  (load-file ".github/hooks/pre-push.clj"))


(defn- format-code
  []
  (let [format-res (shell/sh "clj"
                             "-Sdeps"
                             "{:deps {mvxcvi/cljstyle {:git/url \"https://github.com/greglook/cljstyle.git\", :sha \"c8bc620aeadd022136bb333970c03edf41627417\"}}}"
                             "-m"
                             "cljstyle.main"
                             "fix"
                             "src"
                             ".github")
        git-add-res (shell/sh "git" "add" ".")
        format-exit (:exit format-res)
        git-add-exit (:exit git-add-res)
        format-out (:out format-res)
        git-add-out (:out format-res)]
    (println format-out git-add-out)
    (when-not (and
                (or (= format-exit 2) (zero? format-exit))
                (or (= git-add-exit 2) (zero? git-add-exit)))
      (System/exit 1))))


(defn- lint-code
  []
  (let [lint-res (shell/sh "clj-kondo" "--lint" "src" ".github")
        exit (:exit lint-res)
        out (:out lint-res)]
    (println out)
    (when-not (or (= exit 2) (zero? exit)) (System/exit 1))))


(defn- kibit-lint
  []
  (let [lint-res (shell/sh "lein" "kibit" "src" ".github")
        exit (:exit lint-res)
        out (:out lint-res)]
    (println out)
    (when-not (zero? exit) (System/exit 1))))


(try (validate-branch-name)
     (format-code)
     (lint-code)
     (kibit-lint)
     (catch Exception _
       (System/exit 1)))
