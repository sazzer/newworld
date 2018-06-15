FROM openjdk:10.0.1-10-jdk-slim-sid

RUN apt update && apt install -y curl

ARG JAR_FILE
RUN echo JAR_FILE=${JAR_FILE}

ENV PG_URI=

ADD ${JAR_FILE} /opt/worlds/service.jar
WORKDIR /opt/worlds
CMD java -Dspring.datasource.url="$PG_URI" -Dspring.profiles.active=prod -jar service.jar

HEALTHCHECK CMD curl -f http://localhost:8080/actuator/health || exit 1
