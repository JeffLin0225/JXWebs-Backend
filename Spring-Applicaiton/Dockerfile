# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# 将项目根目录的所有文件复制到容器中的 /app 目录
COPY . .

# 将 application-docker.properties 文件复制到容器的正确位置
COPY src/main/resources/application-docker.properties /app/src/main/resources/application-docker.properties

RUN mvn clean package -DskipTests

# Final image
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 设置 Spring Boot 以加载指定的 application-docker.properties 配置文件
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=classpath:/application-docker.properties"]