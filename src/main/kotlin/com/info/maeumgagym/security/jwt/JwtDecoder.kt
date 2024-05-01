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
     * 미리 설정된 암호화 키를 이용해 복호화
     *
     * @param jwt Jwt 토큰. Jwt 토큰 접두사 필요.
     */
    fun decode(jwt: String): Jwt

    /**
     * 입력된 임의의 암호화 키를 이용해 복호화
     *
     * @param jwt Jwt 토큰. Jwt 토큰 접두사 필요.
     * @param key 암호화 키
     */
    fun decode(jwt: String, key: String): Jwt
}
