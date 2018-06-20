FROM node:9.11.2-alpine

RUN apk add --no-cache openssl

ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

COPY package.json yarn.lock .flowconfig .babelrc .eslintrc.js /opt/worlds/e2e/
WORKDIR /opt/worlds/e2e

RUN yarn install
COPY src /opt/worlds/e2e/src

CMD dockerize -wait tcp://postgres:5432 -wait http://api.worlds.com:8080/actuator/health -wait http://www.worlds.com -timeout 30s yarn start
