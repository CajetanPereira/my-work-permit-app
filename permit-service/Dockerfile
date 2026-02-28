FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml /app/pom.xml
COPY permit-service/pom.xml /app/permit-service/pom.xml
RUN mvn -q -pl permit-service -am dependency:go-offline
COPY permit-service/src /app/permit-service/src
RUN mvn -q -pl permit-service -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/permit-service/target/permit-service-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
