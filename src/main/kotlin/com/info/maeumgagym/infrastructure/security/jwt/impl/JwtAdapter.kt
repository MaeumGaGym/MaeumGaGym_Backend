package com.info.maeumgagym.infrastructure.security.jwt.impl

import com.info.maeumgagym.core.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.core.auth.port.out.GetJwtBodyPort
import com.info.maeumgagym.core.auth.port.out.ReissuePort
import com.info.maeumgagym.core.auth.port.out.RevokeTokensPort
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.infrastructure.security.jwt.entity.AccessTokenRedisEntity
import com.info.maeumgagym.infrastructure.security.jwt.entity.RefreshTokenRedisEntity
import com.info.maeumgagym.infrastructure.security.jwt.repository.AccessTokenRepository
import com.info.maeumgagym.infrastructure.security.jwt.repository.RefreshTokenRepository
import com.info.maeumgagym.infrastructure.security.jwt.env.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.security.PublicKey
import java.util.*

/**
 * Jwt Token에 관련된 Port들의 통합 구현체.
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
class JwtAdapter(
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val accessTokenRepository: AccessTokenRepository
) : GenerateJwtPort, ReissuePort, GetJwtBodyPort, RevokeTokensPort {

    // 모든 토큰 발급
    override fun generateTokens(subject: String): Pair<String, String> {
        // access_token 발급
        val access = generateAccessToken()

        // refresh_token 발급
        val refresh = generateRefreshToken()

        // access_token cache에 저장
        // 만약 이전에 cache에 저장된 토큰이 있다 해도 id(subject)가 같으므로 update 쿼리가 나감
        accessTokenRepository.save(
            AccessTokenRedisEntity(
                subject = subject,
                accessToken = access,
                ttl = jwtProperties.accessExpiredExp
            )
        )

        // refresh_token cache에 저장
        // 만약 이전에 cache에 저장된 토큰이 있다 해도 id(subject)가 같으므로 update 쿼리가 나감
        refreshTokenRepository.save(
            RefreshTokenRedisEntity(
                subject = subject,
                rfToken = refresh,
                ttl = jwtProperties.refreshExpiredExp
            )
        )

        // tokens dto에 담아 반환
        return Pair(access, refresh)
    }

    // access_token 발급
    private fun generateAccessToken(): String {
        // 현재 시각
        val now = Date()

        // 토큰 발급 및 반환
        return Jwts.builder()
            .setHeaderParam("role", "access")
            .setIssuedAt(now)
            .setExpiration(Date(now.time.plus(jwtProperties.accessExpiredExp))) // exp 설정
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .compact()
    }

    // refresh_token 발급
    private fun generateRefreshToken(): String {
        // 현재 시각
        val now = Date()

        // 토큰 발급 및 반환
        return Jwts.builder()
            .setHeaderParam("role", "refresh")
            .setIssuedAt(now)
            .setExpiration(Date(now.time.plus(jwtProperties.refreshExpiredExp))) // exp 설정
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .compact()
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

    // Apple id_token parsing 함수
    override fun getJwtBody(token: String, publicKey: PublicKey): Claims =
        try {
            // body 불러오기
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw AuthenticationException.EXPIRED_TOKEN
                else -> throw AuthenticationException.INVALID_TOKEN
            }
        }
}