package com.info.maeumgagym.global.env.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("server.domain")
data class SecurityProperties(

    val frontLocal: String,

    val backLocal: String,

    val backDomain: String
)
