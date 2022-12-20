FROM openjdk:11
ADD target/*.jar vote-service-sicredi.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "vote-service-sicredi.jar"]