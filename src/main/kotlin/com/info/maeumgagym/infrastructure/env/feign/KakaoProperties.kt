package com.info.maeumgagym.infrastructure.env.feign

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("kakao")
data class KakaoProperties(
    val clientId: String,
    val secretKey: String,
    val redirectionUri: String
)
