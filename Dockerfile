
FROM maven:3.9.9-eclipse-temurin-22 AS build
ARG MAVEN_OPTS
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -ntp -B clean package $MAVEN_OPTS


FROM openjdk:22-jdk-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /app/target/*.jar *.jar
#to builf from local
#COPY target/*.jar *.jar
EXPOSE 8080 5005
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar *.jar"]

#HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 CMD curl -s -f http://app:8080/api/actuator/health || exit 1
