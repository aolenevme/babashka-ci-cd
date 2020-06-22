FROM openjdk:8-alpine as builder

RUN apk add --update nodejs yarn

WORKDIR /app

COPY ./ ./

RUN yarn install --frozen-lockfile

RUN ./node_modules/shadow-cljs/cli/runner.js release app


FROM node:12-alpine as runner

WORKDIR /app

COPY --from=builder /app/build .

CMD ["sh", "-c", "node ./app.js"]