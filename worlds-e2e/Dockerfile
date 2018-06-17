FROM node:9.11.2-alpine

COPY package.json yarn.lock .flowconfig /opt/worlds/e2e/
WORKDIR /opt/worlds/e2e

RUN yarn install

