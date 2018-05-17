FROM node:9.11.1-alpine

WORKDIR /prod/newworld/e2e
ADD . /prod/newworld/e2e/

RUN apk add --no-cache openssl

ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN yarn install
CMD dockerize -wait tcp://worlds-e2e-backend:80 -wait tcp://worlds-e2e-webapp:80 -timeout 30s yarn start -e docker
