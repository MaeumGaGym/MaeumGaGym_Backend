package com.info.maeumgagym.infrastructure.security.jwt.impl

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.infrastructure.security.jwt.JwtResolver
import com.info.maeumgagym.infrastructure.security.jwt.repository.AccessTokenRepository
import com.info.maeumgagym.infrastructure.security.jwt.env.JwtProperties
import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
@Component
class JwtResolverImpl(
    private val jwtProperties: JwtProperties,
    private val accessTokenRepository: AccessTokenRepository
) : JwtResolver {
    override fun invoke(token: String): String? =
        // 토큰이 JWT 토큰일 경우 = 특정 Prefix로 시작하는 토큰일 경우
        if (token.startsWith(jwtProperties.prefix)) {
            // Prefix 제거
            val slicedToken = token.substring(jwtProperties.prefix.length).trimStart()

            // 만료된 access_token인지 확인 및 반환, 예외처리
            accessTokenRepository.findByAccessToken(slicedToken)?.subject
                ?: throw AuthenticationException.INVALID_TOKEN
        } else {
            throw AuthenticationException.INVALID_TOKEN // 정해진 Prefix로 시작하지 않을 경우 Exception 발생
        }
}
