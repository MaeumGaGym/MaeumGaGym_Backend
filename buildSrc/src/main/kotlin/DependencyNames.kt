object DependencyNames {

    // kotlin
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.JACKSON_VERSION}"
    const val JACKSON_TYPE =
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${DependencyVersions.JACKSON_VERSION}"

    // starter
    private const val STARTER = "org.springframework.boot:spring-boot-starter"

    // web
    const val SPRING_WEB = "$STARTER-web"

    // database
    const val SPRING_DATA_JPA = "$STARTER-data-jpa:${PluginVersions.SPRING_BOOT_VERSION}"
    const val MYSQL_CONNECTOR = "mysql:mysql-connector-java:${DependencyVersions.MYSQL_VERSION}"
    const val REDIS = "$STARTER-data-redis"

    // test
    const val SPRING_TEST = "$STARTER-test"
    const val JUNIT_JUPITER = "org.junit.jupiter:junit-jupiter"
    const val JUNIT_JUPITER_API = "org.junit.jupiter:junit-jupiter-api"
    const val JUNIT_JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine"
    const val MOCKK = "io.mockk:mockk:${DependencyVersions.MOCKK_VERSION}"

    // sentry
    const val SENTRY = "io.sentry:sentry-spring-boot-starter:${DependencyVersions.SENTRY_VERSION}"

    // validation
    const val SPRING_VALIDATION = "$STARTER-validation"

    // gson
    const val JSON = "org.json:json:${DependencyVersions.JSON_VERSION}"

    // security
    const val SPRING_SECURITY = "$STARTER-security"

    // Jwt
    const val JWT = "io.jsonwebtoken:jjwt:${DependencyVersions.JWT_VERSION}"

    // feign
    const val OPEN_FEIGN = "org.springframework.cloud:spring-cloud-starter-openfeign"
    const val FEIGN_HTTP = "io.github.openfeign:feign-httpclient"

    // cloud
    const val SPRING_CLOUD =
        "org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD_VERSION}"

    // logger
    const val LOGGER = "io.github.microutils:kotlin-logging-jvm:${DependencyVersions.LOGGER_VERSION}"


    // Transaction
    const val SPRING_TRANSACTION = "org.springframework:spring-tx:${DependencyVersions.SPRING_TRANSACTION}"

    // Transaction
    const val QUERY_DSL_JPA = "com.querydsl:querydsl-jpa:${DependencyVersions.QUERY_DSL_VERSION}"
    const val QUERY_DSL_KAPT = "com.querydsl:querydsl-apt:${DependencyVersions.QUERY_DSL_VERSION}"

    // minIO
    const val MINIO = "io.minio:minio:${DependencyVersions.MINIO_VERSION}"

    // Slack
    const val SLACK = "com.slack.api:bolt-jetty:1.40.2"

    const val ANNOTATION_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor"

    const val SPRING_DOC = "org.springdoc:springdoc-openapi-ui:${DependencyVersions.OPEN_API_VERSION}"
    const val SWAGGER = "io.springfox:springfox-swagger2:${DependencyVersions.SWAGGER_VERSION}"

    const val HEALTH_CHECKER = "$STARTER-actuator"
}
