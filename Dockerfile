FROM openjdk:11
MAINTAINER Henrique de Freitas Almeida
ADD build/libs/*.jar vote-service-sicredi.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "vote-service-sicredi.jar"]