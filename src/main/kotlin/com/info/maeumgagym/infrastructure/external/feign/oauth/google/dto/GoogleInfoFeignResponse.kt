package com.info.maeumgagym.infrastructure.external.feign.oauth.google.dto

import com.info.maeumgagym.core.auth.dto.response.GoogleInfoResponse

data class GoogleInfoFeignResponse(
    val sub: String,
    val name: String,
    val givenName: String,
    val familyName: String?,
    val picture: String,
    val email: String,
    val emailVerified: Boolean
) {

    fun toResponse() = GoogleInfoResponse(
        sub,
        name,
        givenName,
        familyName,
        picture,
        email,
        emailVerified
    )
}
