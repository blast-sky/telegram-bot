FROM openjdk:17-jdk-slim-buster

WORKDIR /app

RUN [
  "sudo", "apt", "update", "&&",
  "sudo", "apt", "upgrade", "&&",
  "sudo", "apt", "install", "ffmpeg"
]

ARG JAR_FILE=app.jar
COPY build/libs/${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
