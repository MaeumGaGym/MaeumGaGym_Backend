package com.info.maeumgagym.security.jwt

import com.info.maeumgagym.security.jwt.vo.Jwt

/**
 * 주어진 Jwt 토큰의 유효성을 검증
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface JwtValidator {

    /**
     * 주어진 Jwt 토큰의 유효성을 검증
     *
     * 검증하는 유효성 목록
     * - 토큰 발급자
     * - 토큰 유효 기간
     *
     * @param jwt Jwt 토큰
     *
     * @throws com.info.maeumgagym.common.exception.SecurityException 무효한 토큰일 경우
     */
    fun validate(jwt: Jwt)
}
