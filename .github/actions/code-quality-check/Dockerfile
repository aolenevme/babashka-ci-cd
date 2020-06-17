FROM borkdude/babashka

COPY ./ ./

ENTRYPOINT ["bb", "-f", "./.github/actions/code-quality-check/index.clj"]
