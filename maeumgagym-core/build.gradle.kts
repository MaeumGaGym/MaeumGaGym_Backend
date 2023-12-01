plugins {
    kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.KLINT_VERSION
}

dependencies {
    implementation(project(":maeumgagym-common"))

    implementation(Dependencies.SPRING_TRANSACTION)
    implementation(Dependencies.JWT)
}

allOpen {
    annotation("com.info.common.UseCase")
    annotation("org.springframework.stereotype.Service")
    annotation("com.info.common.PersistenceAdapter")
    annotation("com.info.common.WebAdapter")
}
