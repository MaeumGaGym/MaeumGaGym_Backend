plugins {
    kotlin("jvm") version PluginVersions.JVM_VERSION
}

allprojects {
    group = "com.info"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        version = PluginVersions.JVM_VERSION
    }

    apply {
        plugin("org.jetbrains.kotlin.kapt")
        version = PluginVersions.KAPT_VERSION
    }

    dependencies {

        // kotlin
        implementation(Dependencies.KOTLIN_REFLECT)
        implementation(Dependencies.KOTLIN_JDK)
        implementation(Dependencies.JACKSON)

        // test
        implementation(Dependencies.SPRING_TEST)
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}