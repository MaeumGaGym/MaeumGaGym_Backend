package com.info.maeumgagym.security.jwt

import com.info.maeumgagym.security.jwt.vo.Jwt

/**
 * 주어진 Jwt 토큰을 복호화해 [Jwt] 객체로 변환
 *
 * @see JwtEncoder
 * @see JwtValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface JwtDecoder {

    /**
     * @param jwt Jwt 토큰. Jwt 토큰 접두사 필요.
     */
    fun decode(jwt: String): Jwt

    fun decode(jwt: String, key: String): Jwt
}
