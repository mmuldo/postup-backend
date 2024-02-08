FROM maven:3.9.6-eclipse-temurin-21-jammy as build

WORKDIR /app/src
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim as runner

WORKDIR /app/bin
ARG JAR_FILE=/app/src/target/*.jar
COPY --from=build $JAR_FILE runner.jar
CMD [ "java", "-jar", "runner.jar" ]

