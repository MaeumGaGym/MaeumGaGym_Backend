package com.info.maeumgagym.infrastructure.external.feign.oauth.kakao.dto

import com.info.maeumgagym.auth.dto.response.KakaoProfileResponse
import com.info.maeumgagym.auth.dto.response.PropertiesResponse

data class KakaoProfileFeignResponse(
    val id: String,
    val properties: PropertiesFeignResponse
) {

    fun toResponse() = KakaoProfileResponse(
        id,
        PropertiesResponse(properties.nickname)
    )
}
