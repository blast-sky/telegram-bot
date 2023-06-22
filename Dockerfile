FROM openjdk:17-alpine

WORKDIR /app

RUN apk add --no-cache ffmpeg

ARG JAR_FILE=app.jar
COPY build/libs/${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
