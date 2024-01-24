plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
}

dependencies {
    implementation(project(":maeumgagym-common"))

    implementation(Dependencies.SPRING_TRANSACTION)
    implementation(Dependencies.JWT)

    implementation(Dependencies.SPRING_VALIDATION)
}

allOpen {
    annotation("com.info.common.UseCase")
    annotation("org.springframework.stereotype.Service")
    annotation("com.info.common.PersistenceAdapter")
    annotation("com.info.common.WebAdapter")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}
