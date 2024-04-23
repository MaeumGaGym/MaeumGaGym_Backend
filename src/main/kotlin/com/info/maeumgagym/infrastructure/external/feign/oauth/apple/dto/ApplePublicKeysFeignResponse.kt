package com.info.maeumgagym.infrastructure.external.feign.oauth.apple.dto

import com.info.maeumgagym.core.auth.dto.response.ApplePublicKeysResponse

data class ApplePublicKeysFeignResponse(
    val keys: MutableList<ApplePublicKeyFeignResponse>
) {

    fun toResponse() = com.info.maeumgagym.core.auth.dto.response.ApplePublicKeysResponse(
        keys.map {
            it.toResponse()
        }.toMutableList()
    )
}
