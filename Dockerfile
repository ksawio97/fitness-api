FROM gradle:8.7.0-jdk17-alpine AS build
WORKDIR /app-build/

COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN gradle bootJar --no-daemon

FROM openjdk:21

WORKDIR /app/

COPY --from=build /app-build/build/libs/fitness-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar