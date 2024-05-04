package com.info.maeumgagym.security.jwt.impl

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.core.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.core.auth.port.out.ReissuePort
import com.info.maeumgagym.core.auth.port.out.RevokeTokensPort
import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenEncoder
import com.info.maeumgagym.security.jwt.entity.AccessTokenRedisEntity
import com.info.maeumgagym.security.jwt.entity.RefreshTokenRedisEntity
import com.info.maeumgagym.security.jwt.env.MaeumgagymTokenProperties
import com.info.maeumgagym.security.jwt.repository.AccessTokenRepository
import com.info.maeumgagym.security.jwt.repository.RefreshTokenRepository
import org.springframework.stereotype.Component

/**
 * Maeumgagym Token에 관련된 Port들의 구현체.
 *
 * @see GenerateJwtPort
 * @see ReissuePort
 * @see RevokeTokensPort
 *
 * @author Daybreak312, gurdl0525
 * @since 20-11-2023
 * @since 추상화 : 28-12-2023
 */
@Component
class MaeumgagymTokenAdapter(
    private val maeumgagymTokenProperties: MaeumgagymTokenProperties,
    private val maeumgagymTokenEncoder: MaeumgagymTokenEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val accessTokenRepository: AccessTokenRepository
) : GenerateJwtPort, ReissuePort, RevokeTokensPort {

    // 모든 토큰 발급
    override fun generateTokens(subject: String): Pair<String, String> {
        // access_token 발급
        val access = maeumgagymTokenEncoder.encodeAccessToken(subject)

        // refresh_token 발급
        val refresh = maeumgagymTokenEncoder.encodeRefreshToken(subject)

        // access_token cache에 저장
        // 만약 이전에 cache에 저장된 토큰이 있다 해도 id(subject)가 같으므로 update 쿼리가 나감
        accessTokenRepository.save(
            AccessTokenRedisEntity(
                subject = subject,
                accessToken = access,
                ttl = maeumgagymTokenProperties.accessExpiredExp
            )
        )

        // refresh_token cache에 저장
        // 만약 이전에 cache에 저장된 토큰이 있다 해도 id(subject)가 같으므로 update 쿼리가 나감
        refreshTokenRepository.save(
            RefreshTokenRedisEntity(
                subject = subject,
                rfToken = refresh,
                ttl = maeumgagymTokenProperties.refreshExpiredExp
            )
        )

        // tokens dto에 담아 반환
        return Pair(access, refresh)
    }

    override fun revoke(subject: String) {
        accessTokenRepository.deleteById(subject)
        refreshTokenRepository.deleteById(subject)
    }

    // 토큰 재발급
    override fun reissue(refreshToken: String): Pair<String, String> {
        // refresh_token을 redis에서 불러오기
        val rfToken = refreshTokenRepository.findByRfToken(refreshToken)
            ?: throw AuthenticationException.INVALID_TOKEN

        // 토큰 재발급 및 반환
        return generateTokens(rfToken.subject)
    }
}
