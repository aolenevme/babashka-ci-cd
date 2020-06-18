(try (load-file ".github/common-scripts/validate-branch-name.clj")
     (load-file ".github/common-scripts/format-code.clj")
     (load-file ".github/common-scripts/lint-code.clj")
     (load-file ".github/common-scripts/kibit-lint.clj")
     (catch Exception _
       (System/exit 1)))
