#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]]
         '[clojure.string :refer [trim]])

(defn validate-branch-name [] (load-file "ci/githooks-clj/pre-push.clj"))

(defn lint-clojure-code []
      (-> (sh "clj-kondo" "--lint" "src")
          :out
          (trim)
          (println)))

(validate-branch-name)
(lint-clojure-code)
