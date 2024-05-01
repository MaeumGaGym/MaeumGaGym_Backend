package com.info.maeumgagym.security.jwt

/**
 * 주어진 subject를 Jwt 토큰으로 암호화
 *
 * @see JwtDecoder
 * @see JwtValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface JwtEncoder {

    fun encodeAccessToken(subject: String): String

    fun encodeRefreshToken(subject: String): String
}
