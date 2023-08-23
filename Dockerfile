# syntax=docker/dockerfile:1
FROM gradle:8.3.0-jdk11-jammy AS base
WORKDIR /app
COPY ./gradle ./gradle
COPY ./app/build.gradle.kts ./app/
COPY ./gradle.properties ./settings.gradle.kts ./gradlew .
RUN ./gradlew --version

#------------------------------#
FROM base AS build
WORKDIR /app
COPY --from=base /app .
RUN ./gradlew build

#------------------------------#
FROM eclipse-temurin:11-jdk-jammy
WORKDIR /app
COPY --from=build /app/app/build/libs/app.jar .
CMD ["java", "-jar", "app.jar"]
