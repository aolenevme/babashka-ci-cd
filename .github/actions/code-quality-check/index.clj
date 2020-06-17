(def ^:private lint-code-file-content (first *command-line-args*))
(def ^:private kibit-lint-file-content (second *command-line-args*))

(shell/sh "bb" lint-code-file-content)
(shell/sh "bb" kibit-lint-file-content)
