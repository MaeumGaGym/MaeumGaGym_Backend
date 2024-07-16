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
    implementation(DependencyNames.KOTLIN_REFLECT)
    implementation(DependencyNames.KOTLIN_JDK)

    // Cloud
    implementation(DependencyNames.HEALTH_CHECKER)
    implementation(DependencyNames.SPRING_CLOUD)

    // Spring Web
    implementation(DependencyNames.SPRING_WEB)
    implementation(DependencyNames.JACKSON)
    implementation(DependencyNames.SPRING_VALIDATION)

    // External
    implementation(DependencyNames.OPEN_FEIGN)
    implementation(DependencyNames.FEIGN_HTTP)
    implementation(DependencyNames.MINIO)

    implementation(DependencyNames.SLACK)

    // Security
    implementation(DependencyNames.SPRING_SECURITY)

    // logger
    implementation(DependencyNames.LOGGER)

    // Docs
    implementation(DependencyNames.SPRING_DOC)
    implementation(DependencyNames.SWAGGER)

    // jpa
    implementation(DependencyNames.SPRING_DATA_JPA)
    implementation(DependencyNames.SPRING_TRANSACTION)
    kapt(DependencyNames.ANNOTATION_PROCESSOR)

    // MySQL
    implementation(DependencyNames.MYSQL_CONNECTOR)

    // redis
    implementation(DependencyNames.REDIS)

    // jwt
    implementation(DependencyNames.JWT)

    // test
    testImplementation(DependencyNames.SPRING_TEST)
    testImplementation(DependencyNames.MOCKK)
    testImplementation(DependencyNames.JUNIT_JUPITER)
    testImplementation(DependencyNames.JUNIT_JUPITER_API)
    testRuntimeOnly(DependencyNames.JUNIT_JUPITER_ENGINE)
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
