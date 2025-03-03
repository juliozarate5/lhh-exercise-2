FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/ms-token-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]