plugins {
    // Kotlin 2.0.x (required for Quarkus 3.15+)
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    kotlin("plugin.jpa") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"

    // Quarkus
    id("io.quarkus") version "3.15.1"

    // Code style
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

group = "com.stackdev"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    // Quarkus BOM
    implementation(enforcedPlatform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))

    // Kotlin + Coroutines
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.21")

    // REST with Jackson (recommended for coroutines)
    // Add this instead
    implementation("io.quarkus:quarkus-rest-jackson")

    // Hibernate ORM with Panache
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")

    // JDBC - PostgreSQL
    implementation("io.quarkus:quarkus-jdbc-postgresql")

    // Dependency Injection
    implementation("io.quarkus:quarkus-arc")

    // OpenAPI
    implementation("io.quarkus:quarkus-smallrye-openapi")

    // Testing
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
        javaParameters = true
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xcontext-receivers",
        )
    }
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
    annotation("jakarta.transaction.Transactional")
}
