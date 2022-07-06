FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/admin-gateway-1.0.jar admin-gateway.jar
ENTRYPOINT ["java","-jar","admin-gateway.jar"]