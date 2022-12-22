FROM gradle:jdk19-alpine as gradle

WORKDIR /appSrc
COPY . .
RUN gradle build

FROM openjdk:19-jdk-slim

COPY --from=gradle /appSrc/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]