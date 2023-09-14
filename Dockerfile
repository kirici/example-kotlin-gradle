# syntax=docker/dockerfile:1
FROM gradle:8.3.0-jdk17-jammy AS base
ENV GRADLE_USER_HOME="${PWD}"/.gradle

WORKDIR /app
COPY ./gradle ./gradle
COPY ./gradlew .
RUN ./gradlew --version

#------------------------------#
FROM base AS build
ENV GRADLE_USER_HOME="${PWD}"/.gradle

WORKDIR /app
COPY --from=base /app .
COPY . .
RUN ./gradlew build --no-daemon

#------------------------------#
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY --from=build /app/app/build/libs/app.jar .
CMD ["java", "-jar", "app.jar"]
