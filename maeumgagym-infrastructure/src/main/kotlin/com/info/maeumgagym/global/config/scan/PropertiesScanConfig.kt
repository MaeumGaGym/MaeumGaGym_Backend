package com.info.maeumgagym.global.config.scan

import com.info.maeumgagym.global.env.google.GoogleProperty
import com.info.maeumgagym.global.env.jwt.JwtProperties
import com.info.maeumgagym.global.env.redis.RedisProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@ConfigurationPropertiesScan(
    basePackageClasses = [
        GoogleProperty::class,
        JwtProperties::class,
        RedisProperties::class
    ]
)
@Configuration
class PropertiesScanConfig
