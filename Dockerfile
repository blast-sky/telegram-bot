FROM openjdk:17-alpine

WORKDIR /app

RUN [
  "apt", "update", "&&",
  "apt", "upgrade", "&&",
  "apt", "install", "ffmpeg"
]

ARG JAR_FILE=app.jar
COPY build/libs/${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
