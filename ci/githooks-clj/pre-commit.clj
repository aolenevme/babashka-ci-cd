#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(defn validate-branch-name [] (load-file "ci/githooks-clj/pre-push.clj"))

(defn lint-clojure-code []
      (let [lint-res (sh "clj-kondo" "--lint" "src" "ci")
            exit (:exit lint-res)
            out (:out lint-res)]
           (println out)
           (when-not (or (= exit 2) (= exit 0)) (System/exit 1))))

(validate-branch-name)
(lint-clojure-code)
