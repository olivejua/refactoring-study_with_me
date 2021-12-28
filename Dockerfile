FROM openjdk:11 AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY secret secret

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar

EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app.jar"]