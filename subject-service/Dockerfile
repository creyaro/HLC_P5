FROM openjdk:21-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR construido en el directorio target a la imagen
COPY target/*.jar subject-service.jar

# Expone el puerto 8761 (puerto predeterminado de Eureka)
EXPOSE 8763

# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "subject-service.jar"]