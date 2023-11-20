plugins {
    id("java")
}

group = "com.info"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation(project(":maeumgagym-core"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}