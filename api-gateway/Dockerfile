FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml /app/pom.xml
COPY api-gateway/pom.xml /app/api-gateway/pom.xml
RUN mvn -q -pl api-gateway -am dependency:go-offline
COPY api-gateway/src /app/api-gateway/src
RUN mvn -q -pl api-gateway -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/api-gateway/target/api-gateway-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
