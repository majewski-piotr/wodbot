FROM adoptopenjdk:8-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "application.jar"]