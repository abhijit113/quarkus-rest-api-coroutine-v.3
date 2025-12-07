# =========================
# BUILD STAGE
# =========================
FROM gradle:8.10.2-jdk21 AS build
WORKDIR /workspace

# Copy Gradle wrapper & build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .

RUN ./gradlew build -x test --no-daemon || true

COPY src src

RUN ./gradlew build -Dquarkus.package.type=fast-jar -x test --no-daemon

# =========================
# RUNTIME STAGE
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd -m appuser
USER appuser

COPY --from=build /workspace/build/quarkus-app /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/quarkus-run.jar"]
