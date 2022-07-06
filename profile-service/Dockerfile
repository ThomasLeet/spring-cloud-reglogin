FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/profile-service-1.0.jar profile-service.jar
ENTRYPOINT ["java","-jar","profile-service.jar"]