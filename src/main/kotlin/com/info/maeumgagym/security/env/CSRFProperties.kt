package com.info.maeumgagym.security.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("csrf")
data class CSRFProperties(
    val header: String,
    val cookie: String,
    val parameter: String
)
