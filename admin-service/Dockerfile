FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/admin-service-1.0.jar admin-service.jar
ENTRYPOINT ["java","-jar","admin-service.jar"]