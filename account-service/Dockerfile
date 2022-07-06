FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/account-service-1.0.jar account-service.jar
ENTRYPOINT ["java","-jar","account-service.jar"]