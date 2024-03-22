plugins {
    kotlin("jvm") version PluginVersions.JVM_VERSION
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.KLINT_VERSION
    id("org.jetbrains.dokka") version "1.9.20"
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

    apply(plugin = "org.jetbrains.dokka")

    dependencies {

        // kotlin
        implementation(Dependencies.KOTLIN_REFLECT)
        implementation(Dependencies.KOTLIN_JDK)
        implementation(Dependencies.JACKSON)

        implementation(Dependencies.HEALTH_CHECKER)
    }
}

allprojects {
    group = "com.info"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "jacoco")

    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
        version = PluginVersions.KLINT_VERSION
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }

        compileJava {
            sourceCompatibility = JavaVersion.VERSION_17.majorVersion
        }

        test {
            useJUnitPlatform()
        }
    }

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
    }
}

tasks.test {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "local")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
