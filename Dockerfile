# ========== GIAI ĐOẠN 1: BUILD ==========
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml
COPY pom.xml .

# Tải dependencies + plugins
RUN mvn dependency:go-offline dependency:resolve-plugins -B

# Copy source code
COPY src ./src

# Cấu hình Maven để xử lý encoding đúng cách
RUN mkdir -p /root/.m2
RUN echo "<settings><profiles><profile><id>utf8</id><properties><project.build.sourceEncoding>UTF-8</project.build.sourceEncoding><project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding><maven.compiler.encoding>UTF-8</maven.compiler.encoding></properties></profile></profiles><activeProfiles><activeProfile>utf8</activeProfile></activeProfiles></settings>" > /root/.m2/settings.xml

# Build (bỏ qua test)
RUN mvn clean package -Dmaven.test.skip=true -Dfile.encoding=UTF-8

# ========== GIAI ĐOẠN 2: RUN ==========
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy file JAR
COPY --from=build /app/target/*.jar app.jar

# Cài đặt timezone
RUN apk add --no-cache tzdata
ENV TZ=Asia/Ho_Chi_Minh
ENV SIGNER_KEY=fl2uMhT7nBMWd9ATqBh9A69ILTyw6zzL9Wipie5LAdE
ENV APP_JWT_EXPIRATION_MS=10800000

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
