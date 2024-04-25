package com.info.maeumgagym.infrastructure.env.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "redis")
data class RedisProperties(
    val host: String,
    val port: Int
)
