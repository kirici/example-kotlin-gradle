# syntax=docker/dockerfile:1
# stage 1
FROM gradle:7.6.2-jdk11-jammy AS builder

WORKDIR /app

COPY . .

RUN ./gradlew build --no-daemon

# stage 2
FROM eclipse-temurin:11-jdk-jammy

WORKDIR /app

COPY --from=builder /app/app/build/libs/app.jar .

CMD ["java", "-jar", "app.jar"]
