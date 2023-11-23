plugins {
    kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.KLINT_VERSION
}

dependencies {
    implementation(project(":maeumgagym-common"))

    implementation(Dependencies.SPRING_TRANSACTION)
}

allOpen {
    annotation("com.info.common.UseCase")
    annotation("com.info.common.PersistenceAdapter")
    annotation("com.info.common.WebAdapter")
}
