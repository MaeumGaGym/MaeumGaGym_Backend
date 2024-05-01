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

    /**
     * AccessToken으로 암호화.
     *
     * AccessToken으로 각인되며, 토큰의 유효 시간이 미리 설정된 AccessToken의 유효 시간으로 설정됨.
     */
    fun encodeAccessToken(subject: String): String

    /**
     * RefreshToken으로 암호화.
     *
     * RefreshToken으로 각인되며, 토큰의 유효 시간이 미리 설정된 RefreshToken의 유효 시간으로 설정됨.
     */
    fun encodeRefreshToken(subject: String): String
}
