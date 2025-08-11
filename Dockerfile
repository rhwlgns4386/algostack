# 빌드 스테이지 (Gradle + JDK 24)
FROM gradle:jdk24-ubi AS build
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN gradle build -x test

# 런타임 스테이지 (JDK 24 slim)
FROM openjdk:24-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]