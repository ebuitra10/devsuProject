# Etapa 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY msvc-clientes/pom.xml msvc-clientes/pom.xml
COPY msvc-cuentas/pom.xml msvc-cuentas/pom.xml
COPY msvc-cuentas/src msvc-cuentas/src
RUN mvn -pl msvc-cuentas -am clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/msvc-cuentas/target/*.jar app.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "app.jar"]