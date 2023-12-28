plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
    application
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
}

// Declaring a publicly-available repository
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD_VERSION}")
    }
}
