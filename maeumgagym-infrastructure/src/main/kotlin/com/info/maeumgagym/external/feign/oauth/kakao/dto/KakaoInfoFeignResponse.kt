package com.info.maeumgagym.external.feign.oauth.kakao.dto

import com.info.maeumgagym.auth.dto.response.KakaoInfoResponse
import com.info.maeumgagym.auth.dto.response.PropertiesResponse

data class KakaoInfoFeignResponse(
    val id: String,
    val properties: PropertiesFeignResponse
) {

    fun toResponse() = KakaoInfoResponse(
        id,
        PropertiesResponse(properties.nickname)
    )
}
