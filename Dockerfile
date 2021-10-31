FROM gradle:6-jdk8 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --info

FROM openjdk:8-jre-slim as app
EXPOSE 8080
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar
WORKDIR /app
CMD java -jar app.jar
