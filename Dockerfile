FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY build/libs/*-all.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
