FROM borkdude/babashka

COPY ./ ./

RUN curl -sLO https://raw.githubusercontent.com/borkdude/clj-kondo/master/script/install-clj-kondo && \
    chmod +x install-clj-kondo && \
    sh ./install-clj-kondo


ENTRYPOINT ["bb", "-f", "./.github/actions/code-quality-check/index.clj"]
