FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} geofence.jar
ENTRYPOINT ["java","-jar","/geofence.jar"]
