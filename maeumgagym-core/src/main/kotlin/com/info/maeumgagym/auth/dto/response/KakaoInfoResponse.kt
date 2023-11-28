package com.info.maeumgagym.auth.dto.response

data class KakaoInfoResponse(
    val oauthId: String,
    val properties: Properties
)

data class Properties(
    val nickname: String
)
