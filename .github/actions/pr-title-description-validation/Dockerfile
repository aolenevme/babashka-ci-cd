FROM alpine:3.7

RUN apk add --no-cache curl
RUN apk add --no-cache bash

RUN curl -s https://raw.githubusercontent.com/borkdude/babashka/master/install -o install-babashka
RUN chmod +x install-babashka && ./install-babashka

COPY index.clj /index.clj

ENTRYPOINT ["/index.clj"]
