package com.info.maeumgagym.security.mgtoken.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("mg-token")
data class MaeumgagymTokenProperties(
    val secretKey: String,
    val accessExpiredExp: Long,
    val refreshExpiredExp: Long,
    val header: String,
    val prefix: String
)
