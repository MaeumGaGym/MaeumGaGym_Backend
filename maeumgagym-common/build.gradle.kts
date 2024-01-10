plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.KLINT_VERSION
}

dependencies {
    implementation(Dependencies.SPRING_TRANSACTION)
    implementation(Dependencies.SPRING_WEB)
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}
