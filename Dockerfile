
FROM maven:3.9.9-eclipse-temurin-22 AS build
ARG MAVEN_OPTS
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -ntp -B clean package $MAVEN_OPTS


FROM openjdk:22-jdk-slim
WORKDIR /app

COPY --from=build /app/target/GetUsersTestTask-0.0.1-SNAPSHOT.jar app.jar
#to builf from local
#COPY target/GetUsersTestTask-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080 5005
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]