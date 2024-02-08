FROM maven:3.9.6-eclipse-temurin-21-jammy as base

WORKDIR /app/src
COPY . .

FROM base as tester
CMD ["mvn", "clean", "package"]

FROM base as builder
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim as runner

WORKDIR /app/bin
ARG JAR_FILE=/app/src/target/*.jar
COPY --from=builder $JAR_FILE runner.jar
CMD [ "java", "-jar", "runner.jar" ]