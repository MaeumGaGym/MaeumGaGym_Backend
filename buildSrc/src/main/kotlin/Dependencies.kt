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
    const val REDIS = "org.springframework.boot:spring-boot-starter-data-redis"

    // test
    const val SPRING_TEST = "org.springframework.boot:spring-boot-starter-test:${PluginVersions.SPRING_BOOT_VERSION}"
    const val JUNIT_JUPITER = "org.junit.jupiter:junit-jupiter"
    const val JUNIT_JUPITER_API = "org.junit.jupiter:junit-jupiter-api"
    const val JUNIT_JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine"
    const val MOCKK = "io.mockk:mockk:${DependencyVersions.MOCKK_VERSION}"

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

    // feign
    const val OPEN_FEIGN = "org.springframework.cloud:spring-cloud-starter-openfeign"
    const val FEIGN_HTTP = "io.github.openfeign:feign-httpclient"

    // cloud
    const val SPRING_CLOUD = "org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD_VERSION}"

    // logger
    const val LOGGER = "io.github.microutils:kotlin-logging-jvm:${DependencyVersions.LOGGER_VERSION}"

    // starter
    const val STARTER = "org.springframework.boot:spring-boot-starter";

    // Transaction
    const val SPRING_TRANSACTION = "org.springframework:spring-tx:${DependencyVersions.SPRING_TRANSACTION}"

    // Transaction
    const val QUERY_DSL_JPA = "com.querydsl:querydsl-jpa:${DependencyVersions.QUERY_DSL}"
    const val QUERY_DSL_KAPT = "com.querydsl:querydsl-apt:${DependencyVersions.QUERY_DSL}"

    const val ANNOTATION_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor"

    const val SPRING_DOC = "org.springdoc:springdoc-openapi-ui:1.6.11"
    const val SWAGGER = "io.springfox:springfox-swagger2:2.9.2"
}
