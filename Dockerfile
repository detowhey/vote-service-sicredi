FROM amazoncorretto:21
MAINTAINER Henrique de Freitas Almeida
ADD build/libs/*.jar vote-service-sicredi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "vote-service-sicredi.jar"]