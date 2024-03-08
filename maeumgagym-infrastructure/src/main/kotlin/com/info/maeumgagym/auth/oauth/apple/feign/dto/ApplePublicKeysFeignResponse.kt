package com.info.maeumgagym.auth.oauth.apple.feign.dto

import com.info.maeumgagym.auth.dto.response.ApplePublicKeysResponse

data class ApplePublicKeysFeignResponse(
    val keys: MutableList<ApplePublicKeyFeignResponse>
) {

    fun toResponse() = ApplePublicKeysResponse(
        keys.map {
            it.toResponse()
        }.toMutableList()
    )
}
