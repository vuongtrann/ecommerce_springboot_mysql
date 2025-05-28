## Sử dụng OpenJDK 21
#FROM openjdk:21-jdk-slim
#
## Thiết lập thư mục làm việc trong container
#WORKDIR /app
#
## Copy file JAR vào container
#COPY target/myapp.jar app.jar
#
## Mở cổng 8080 cho ứng dụng
#EXPOSE 8080
#
## Chạy ứng dụng Spring Boot
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Giai đoạn 1: Build ứng dụng với Maven và Java 21
FROM maven:3.9.5-eclipse-temurin-21 AS builder

# Thư mục làm việc trong container
WORKDIR /build

# Copy toàn bộ mã nguồn vào container
COPY . .

# Build file JAR
RUN mvn clean package -DskipTests

# Giai đoạn 2: Chạy ứng dụng bằng OpenJDK
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy file JAR đã build từ stage 1
COPY --from=builder /build/target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
