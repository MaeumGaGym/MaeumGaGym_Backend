package com.info.maeumgagym.global.env.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "redis")
data class RedisProperty(
    val host: String,
    val port: Int
)