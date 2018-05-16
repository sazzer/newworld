# Stage 1 - build the Javascript into the static files
FROM node:9.11.1-alpine

WORKDIR /prod/newworld/webapp
ADD . /prod/newworld/webapp/

RUN yarn install
RUN CI=1 yarn test
RUN yarn build

# Stage 2 - build the Nginx container containing the now-build static files
FROM nginx:1.13.12-alpine
COPY --from=0 /prod/newworld/webapp/build /usr/share/nginx/html

