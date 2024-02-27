FROM maven:3-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-ea-14-jdk-slim
COPY --from=build /target/bursary.manager-3.2.2.jar bursary.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "bursary.jar"]