#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/rtc-0.0.1-SNAPSHOT-jar-with-dependencies.jar /usr/local/lib/rtc.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/rtc.jar"]