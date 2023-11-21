dependencies {
    implementation(project(":maeumgagym-application"))
    implementation(project(":maeumgagym-core"))
    implementation(project(":maeumgagym-presentation"))
    implementation(Dependencies.SPRING_CLOUD)

    implementation(Dependencies.SPRING_WEB)
    implementation(Dependencies.OPEN_FEIGN)
    implementation(Dependencies.FEIGN_HTTP)
    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.JWT)
    implementation(Dependencies.REDIS)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

// Declaring a publicly-available repository
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD_VERSION}")
    }
}
