FROM gradle:7.5.1-jdk-18 AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM openjdk:18-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/libs/auto-downloader.jar

ENTRYPOINT ["java", "-jar", "/app/libs/auto-downloader.jar"]