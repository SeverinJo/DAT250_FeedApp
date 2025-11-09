# ---------- BUILD STAGE ----------
FROM gradle:8.14.3-jdk21-alpine AS builder
WORKDIR /home/gradle/app

COPY settings.gradle.kts build.gradle.kts ./
COPY gradle gradle
COPY src src

RUN gradle clean bootJar --no-daemon

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -g 1000 app && adduser -u 1000 -G app -D app
USER app

COPY --from=builder /home/gradle/app/build/libs/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
