# syntax=docker/dockerfile:1
# stage 1
FROM gradle:7.6.2-jdk11-jammy AS builder

WORKDIR /app

COPY . .

RUN ./gradlew check ; ./gradlew fatJar

# stage 2
FROM eclipse-temurin:11-jdk-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/app-fat.jar .

CMD ["java", "-jar", "app-fat.jar"]
