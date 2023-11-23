package com.info.maeumgagym.global.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "base")
data class BaseProperty(
    val url: String
)
