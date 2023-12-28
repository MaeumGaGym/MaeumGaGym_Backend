plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
}

sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}

dependencies {
    // impl project
    implementation(project(":maeumgagym-core"))
    implementation(project(":maeumgagym-common"))

    // MySQL
    implementation(Dependencies.MYSQL_CONNECTOR)
    implementation(Dependencies.REDIS)

    // jpa
    implementation(Dependencies.SPRING_DATA_JPA)
    implementation("com.querydsl:querydsl-jpa:${PluginVersions.QUERY_DSL}")
    kapt("com.querydsl:querydsl-apt:${PluginVersions.QUERY_DSL}:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}
