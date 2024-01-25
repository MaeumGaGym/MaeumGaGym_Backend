plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
    application
}

tasks.named<Jar>("jar") {
    enabled = true
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.jar {
    archiveBaseName.set("maeumgagym-server")
    archiveVersion.set("1.0.0")
}

application {
    mainClass.set("com.info.maeumgagym.MaeumGaGymApplicationKt")
}

dependencies {
    implementation(project(":maeumgagym-application"))
    implementation(project(":maeumgagym-core"))
    implementation(project(":maeumgagym-presentation"))
    implementation(project(":maeumgagym-common"))

    implementation(Dependencies.SPRING_CLOUD)
    implementation(Dependencies.SPRING_WEB)
    implementation(Dependencies.OPEN_FEIGN)
    implementation(Dependencies.FEIGN_HTTP)
    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.JWT)
    implementation(Dependencies.REDIS)
    implementation(Dependencies.LOGGER)
    implementation(Dependencies.SPRING_VALIDATION)

    implementation(Dependencies.SPRING_DATA_JPA)
    implementation("com.querydsl:querydsl-jpa:${PluginVersions.QUERY_DSL}")
    kapt("com.querydsl:querydsl-apt:${PluginVersions.QUERY_DSL}:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation(Dependencies.SPRING_DOC)
    implementation(Dependencies.SWAGGER)

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

allOpen {
    annotations("org.springframework.data.redis.core.RedisHash")
}

noArg {
    annotations("org.springframework.data.redis.core.RedisHash")
}
