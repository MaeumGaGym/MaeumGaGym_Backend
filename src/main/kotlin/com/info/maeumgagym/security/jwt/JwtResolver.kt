package com.info.maeumgagym.security.jwt

/**
 * 주어진 Jwt 토큰을 검증하고 subject를 추출
 *
 * @see JwtDecoder Jwt 토큰에서 subject 추출
 * @see JwtValidator Jwt 토큰 검증 모듈
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
interface JwtResolver {

    /**
     * @param token 유효성을 확인할 토큰
     * @return OAuthId, 토큰이 유효하지 않을 경우 null
     */
    operator fun invoke(token: String): String?
}
