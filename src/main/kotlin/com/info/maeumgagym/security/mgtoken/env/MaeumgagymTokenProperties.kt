package com.info.maeumgagym.security.mgtoken.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("mgtoken")
data class MaeumgagymTokenProperties(
    val secretKey: String,
    val accessExpiredExp: Long,
    val refreshExpiredExp: Long,
    val prefix: String
)
