package com.info.maeumgagym.env.config.kakao

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("kakao")
data class KakaoProperties(
    val grantType: String,
    val clientId: String,
    val redirectUri: String
)
