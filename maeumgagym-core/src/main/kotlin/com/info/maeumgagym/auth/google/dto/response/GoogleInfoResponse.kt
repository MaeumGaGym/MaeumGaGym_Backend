package com.info.maeumgagym.auth.google.dto.response

data class GoogleInfoResponse(
    val sub: String,
    val name: String,
    val givenName: String,
    val familyName: String?,
    val picture: String,
    val email: String,
    val emailVerified: Boolean,
    val locale: String
)
