# Базовый образ с Java 25 (Eclipse Temurin JRE)
FROM eclipse-temurin:25-jre

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем собранный JAR-файл в образ
COPY target/ChronoMind-1.0-SNAPSHOT.jar app.jar

# Порт, который будет слушать приложение (обычно 8080)
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]