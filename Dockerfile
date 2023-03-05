FROM openjdk:17-jdk-slim-buster

WORKDIR /app

ARG JAR_FILE=app.jar
COPY build/libs/${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
