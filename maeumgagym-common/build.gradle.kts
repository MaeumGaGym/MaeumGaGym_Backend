dependencies {
    implementation(Dependencies.STARTER)
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
