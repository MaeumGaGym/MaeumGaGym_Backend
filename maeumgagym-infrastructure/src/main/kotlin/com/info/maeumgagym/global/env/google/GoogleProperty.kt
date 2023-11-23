package com.info.maeumgagym.global.env.google

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "oauth.google")
data class GoogleProperty(
    val clientId: String,
    val clientSecret: String,
    val redirectUrl: String,
    val grantType: String
)
