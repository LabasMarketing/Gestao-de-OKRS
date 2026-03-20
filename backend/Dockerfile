FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 25000

ENTRYPOINT ["java", "-Dserver.port=25000", "-Dserver.address=0.0.0.0", "-jar", "app.jar"]