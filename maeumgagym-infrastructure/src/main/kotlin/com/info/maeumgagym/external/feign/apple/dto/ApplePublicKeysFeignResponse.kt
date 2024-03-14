package com.info.maeumgagym.external.feign.apple.dto

import com.info.maeumgagym.auth.dto.response.ApplePublicKeysResponse

data class ApplePublicKeysFeignResponse(
    val keys: MutableList<com.info.maeumgagym.external.feign.apple.dto.ApplePublicKeyFeignResponse>
) {

    fun toResponse() = ApplePublicKeysResponse(
        keys.map {
            it.toResponse()
        }.toMutableList()
    )
}
