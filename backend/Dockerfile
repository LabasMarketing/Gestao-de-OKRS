FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o JAR que você acabou de gerar no Codespace
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 25000

ENTRYPOINT ["java", "-Dserver.port=25000", "-Dserver.address=0.0.0.0", "-jar", "app.jar"]