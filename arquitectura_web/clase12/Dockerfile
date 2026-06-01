# Buildear código fuente
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /build
# Descargar librería json
RUN wget -O json.jar https://repo1.maven.org/maven2/org/json/json/20240303/json-20240303.jar
COPY main.java .
RUN javac -cp json.jar main.java

# Ejecutar
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /build/json.jar .
COPY --from=build /build/main.class .
COPY frases.json /app/frases.json
EXPOSE 8080
CMD ["java", "-cp", ".:json.jar", "main"]