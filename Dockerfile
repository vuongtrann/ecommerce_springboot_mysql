# Sử dụng OpenJDK 21
FROM openjdk:21-jdk-slim

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Copy file JAR vào container
COPY target/myapp.jar app.jar

# Mở cổng 8080 cho ứng dụng
EXPOSE 8080

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
