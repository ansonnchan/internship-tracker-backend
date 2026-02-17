FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies first for faster rebuilds.
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
