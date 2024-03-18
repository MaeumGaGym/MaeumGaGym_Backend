package com.info.maeumgagym.external.feign.oauth.google.dto

import com.info.maeumgagym.auth.dto.response.GoogleInfoResponse

data class GoogleInfoFeignResponse(
    val sub: String,
    val name: String,
    val givenName: String,
    val familyName: String?,
    val picture: String,
    val email: String,
    val emailVerified: Boolean,
    val locale: String
) {

    fun toResponse() = GoogleInfoResponse(
        sub,
        name,
        givenName,
        familyName,
        picture,
        email,
        emailVerified,
        locale
    )
}
