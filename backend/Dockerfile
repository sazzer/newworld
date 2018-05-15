FROM node:9.11.1-alpine

WORKDIR /prod/newworld/backend
ADD . /prod/newworld/backend/

RUN apk add --no-cache openssl

ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN yarn install
RUN yarn test
CMD dockerize -wait tcp://$POSTGRES_HOST:$POSTGRES_PORT yarn start
