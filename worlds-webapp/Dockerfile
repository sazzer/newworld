FROM nginx:1.15.0-alpine


ENV REACT_APP_AUTH_URL=
ENV REACT_APP_AUTH_CLIENT=
ENV REACT_APP_AUTH_CALLBACK=
ENV REACT_APP_API_BASE=

RUN apk --no-cache add curl

COPY build /usr/share/nginx/html
COPY src/main/docker/start.sh /start.sh
RUN chmod +x /start.sh

CMD /start.sh

HEALTHCHECK CMD curl -f http://localhost || exit 1
