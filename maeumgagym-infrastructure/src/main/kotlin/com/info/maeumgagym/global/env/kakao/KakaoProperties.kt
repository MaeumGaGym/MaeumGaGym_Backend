package com.info.maeumgagym.global.env.kakao

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "kakao")
class KakaoProperties(
    val grantType: String,
    val clientId: String,
    val redirectUri: String
)
