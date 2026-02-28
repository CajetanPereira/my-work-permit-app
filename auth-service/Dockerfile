FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml /app/pom.xml
COPY auth-service/pom.xml /app/auth-service/pom.xml
RUN mvn -q -pl auth-service -am dependency:go-offline
COPY auth-service/src /app/auth-service/src
RUN mvn -q -pl auth-service -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/auth-service/target/auth-service-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
