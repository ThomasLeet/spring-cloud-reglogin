FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/gateway-1.0.jar gateway.jar
ENTRYPOINT ["java","-jar","gateway.jar"]