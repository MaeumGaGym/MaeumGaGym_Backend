import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    // Spring
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION

    // Gradle Manager
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION

    // Kotlint
    id("org.jetbrains.kotlin.kapt") version PluginVersions.KAPT_VERSION

    // Java
    kotlin("jvm") version PluginVersions.JVM_VERSION

    // Docs
    id("org.jetbrains.dokka") version "1.9.20"

    // Lint
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.KLINT_VERSION
//    application
}

group = "com.info.maeumgagym"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

tasks.bootJar {
    archiveFileName.set("maeumgagym-server.jar")
}

dependencies {

    // Kotlin
    implementation(Dependencies.KOTLIN_REFLECT)
    implementation(Dependencies.KOTLIN_JDK)

    // Cloud
    implementation(Dependencies.HEALTH_CHECKER)
    implementation(Dependencies.SPRING_CLOUD)

    // Spring Web
    implementation(Dependencies.SPRING_WEB)
    implementation(Dependencies.JACKSON)
    implementation(Dependencies.SPRING_VALIDATION)

    // External
    implementation(Dependencies.OPEN_FEIGN)
    implementation(Dependencies.FEIGN_HTTP)
    implementation(Dependencies.MINIO)

    // Security
    implementation(Dependencies.SPRING_SECURITY)

    // logger
    implementation(Dependencies.LOGGER)

    // Docs
    implementation(Dependencies.SPRING_DOC)
    implementation(Dependencies.SWAGGER)

    // jpa
    implementation(Dependencies.SPRING_DATA_JPA)
    implementation(Dependencies.SPRING_TRANSACTION)
    kapt(Dependencies.ANNOTATION_PROCESSOR)

    // MySQL
    implementation(Dependencies.MYSQL_CONNECTOR)

    // redis
    implementation(Dependencies.REDIS)

    // jwt
    implementation(Dependencies.JWT)

    // test
    testImplementation(Dependencies.SPRING_TEST)
    testImplementation(Dependencies.MOCKK)
    testImplementation(Dependencies.JUNIT_JUPITER)
    testImplementation(Dependencies.JUNIT_JUPITER_API)
    testRuntimeOnly(Dependencies.JUNIT_JUPITER_ENGINE)
}

// Declaring a publicly-available repository
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD_VERSION}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "local")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
    annotations("org.springframework.data.redis.core.RedisHash")

    annotation("com.info.common.UseCase")
    annotation("org.springframework.stereotype.Service")
    annotation("com.info.common.PersistenceAdapter")
    annotation("com.info.common.WebAdapter")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
    annotations("org.springframework.data.redis.core.RedisHash")
}
