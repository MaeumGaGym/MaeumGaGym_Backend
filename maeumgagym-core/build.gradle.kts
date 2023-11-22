plugins {
    kotlin("plugin.allopen") version PluginVersions.ALLOPEN_VERSION
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
