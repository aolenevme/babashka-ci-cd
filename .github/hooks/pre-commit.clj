(defn- validate-branch-name
  []
  (load-file ".github/hooks/pre-push.clj"))


(defn- format-code
  []
  (load-file ".github/common-scripts/format-code.clj"))


(defn- lint-code
  []
  (load-file ".github/common-scripts/lint-code.clj"))


(defn- kibit-lint
  []
  (load-file ".github/common-scripts/lint-code.clj"))


(try (validate-branch-name)
     (format-code)
     (lint-code)
     (kibit-lint)
     (catch Exception _
       (System/exit 1)))
