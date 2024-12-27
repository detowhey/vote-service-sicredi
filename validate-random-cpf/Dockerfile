FROM gradle:8.12.0-jdk21 AS builder
WORKDIR /app
COPY . .

RUN ./gradlew build -x test
FROM amazoncorretto:21

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar vote-service-sicredi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "vote-service-sicredi.jar"]
