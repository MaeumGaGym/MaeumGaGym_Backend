package com.info.maeumgagym.external.feign.kakao.dto

import com.info.maeumgagym.auth.dto.response.KakaoInfoResponse
import com.info.maeumgagym.auth.dto.response.PropertiesResponse

data class KakaoInfoFeignResponse(
    val id: String,
    val properties: com.info.maeumgagym.external.feign.kakao.dto.PropertiesFeignResponse
) {

    fun toResponse() = KakaoInfoResponse(
        id,
        PropertiesResponse(properties.nickname)
    )
}
