FROM gradle:4.10.3-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle test --no-daemon
RUN gradle shadowjar

FROM openjdk:8-jre-slim

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar

CMD java -jar /app/application.jar