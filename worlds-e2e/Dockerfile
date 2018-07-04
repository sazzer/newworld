FROM openjdk:10.0.1-10-jdk-slim-sid

RUN apt-get update && apt-get install -y wget

ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz


ARG JAR_FILE
RUN echo JAR_FILE=${JAR_FILE}

ENV PG_URI=
ENV PG_HOST=
ENV WEBAPP_HOST=
ENV API_HOST=
ENV SELENIUM_HOST=

ADD ${JAR_FILE} /opt/worlds/tests.jar
WORKDIR /opt/worlds
CMD dockerize -wait tcp://${PG_HOST}:5432 -wait http://${WEBAPP_HOST} -wait http://${API_HOST}:8080/actuator/health -wait http://${SELENIUM_HOST}:4444 -timeout 30s java -Dspring.datasource.url="$PG_URI" -Dwebapp.url=http://${WEBAPP_HOST} -Dselenium.url=http://${SELENIUM_HOST}:4444/wd/hub -jar tests.jar
