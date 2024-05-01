package com.info.maeumgagym.security.jwt

interface JwtEncoder {

    fun encodeAccessToken(subject: String): String

    fun encodeRefreshToken(subject: String): String
}
