FROM borkdude/babashka

COPY ./ ./

RUN cat
RUN ls

COPY index.clj /index.clj

ENTRYPOINT ["bb", "-f", "/index.clj"]
