# Usamos la imagen oficial de OpenJDK 17
FROM openjdk:17

# Copiar el archivo JAR generado por Gradle al contenedor
COPY build/libs/main-0.0.1-SNAPSHOT.jar /main-0.0.1-SNAPSHOT.jar

EXPOSE 8090

# Comando de inicio de la aplicación
CMD ["java", "-jar", "/main-0.0.1-SNAPSHOT.jar"]
