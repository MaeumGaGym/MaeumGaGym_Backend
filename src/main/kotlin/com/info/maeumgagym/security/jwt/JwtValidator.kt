package com.info.maeumgagym.security.jwt

import com.info.maeumgagym.security.jwt.vo.Jwt

/**
 * 주어진 Jwt 토큰의 유효성을 검증
 *
 * 검증하는 유효성
 * - 토큰 발급자
 * - 토큰 유효 기간
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface JwtValidator {

    fun validate(jwt: Jwt)
}
