package com.info.maeumgagym.infrastructure.security.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("server.domain")
data class SecurityProperties(

    val frontDomain: String,

    val backDomain: String
)
