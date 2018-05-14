FROM node:9.11.1-alpine

WORKDIR /prod/newworld/backend
ADD . /prod/newworld/backend/

RUN yarn install
RUN yarn test
CMD yarn start
