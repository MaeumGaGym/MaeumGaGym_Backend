object Dependencies {

    // kotlin
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.JACKSON_VERSION}"
    const val JACKSON_TYPE = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${DependencyVersions.JACKSON_VERSION}"

    // web
    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"

    // database
    const val SPRING_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa:${PluginVersions.SPRING_BOOT_VERSION}"
    const val MYSQL_CONNECTOR = "mysql:mysql-connector-java:${DependencyVersions.MYSQL}"

    // test
    const val SPRING_TEST = "org.springframework.boot:spring-boot-starter-test:${PluginVersions.SPRING_BOOT_VERSION}"

    // open feign
    const val OPEN_FEIGN = "org.springframework.cloud:spring-cloud-starter-openfeign:${DependencyVersions.OPEN_FEIGN_VERSION}"

    // sentry
    const val SENTRY = "io.sentry:sentry-spring-boot-starter:${DependencyVersions.SENTRY_VERSION}"

    // validation
    const val SPRING_VALIDATION = "org.springframework.boot:spring-boot-starter-validation"

    // gson
    const val JSON = "org.json:json:${DependencyVersions.JSON_VERSION}"

    // security
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"

    // Jwt
    const val JWT = "io.jsonwebtoken:jjwt:${DependencyVersions.JWT_VERSION}"

}
