#!/usr/bin/env bb

(defn validate-branch-name [] (load-file "ci/githooks-clj/pre-push.clj"))

(validate-branch-name)