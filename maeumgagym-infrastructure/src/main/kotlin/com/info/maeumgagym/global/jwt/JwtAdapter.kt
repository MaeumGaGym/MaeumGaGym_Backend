package com.info.maeumgagym.global.jwt

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.GetJwtBodyPort
import com.info.maeumgagym.auth.port.out.ReissuePort
import com.info.maeumgagym.global.jwt.entity.AccessTokenRedisEntity
import com.info.maeumgagym.global.jwt.entity.RefreshTokenRedisEntity
import com.info.maeumgagym.global.jwt.repository.AccessTokenRepository
import com.info.maeumgagym.global.jwt.repository.RefreshTokenRepository
import com.info.maeumgagym.global.env.jwt.JwtProperties
import com.info.maeumgagym.global.exception.ExpiredTokenException
import com.info.maeumgagym.global.exception.InvalidTokenException
import com.info.maeumgagym.global.security.principle.CustomUserDetailService
import com.info.maeumgagym.global.security.principle.CustomUserDetails
import io.jsonwebtoken.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.PublicKey
import java.util.*

@Component
class JwtAdapter(
    private val jwtProperties: JwtProperties,
    private val customUserDetailService: CustomUserDetailService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val accessTokenRepository: AccessTokenRepository
) : GenerateJwtPort, ReissuePort, GetJwtBodyPort {
    override fun generateTokens(subject: String): TokenResponse {

        val access = generateAccessToken()
        val refresh = generateRefreshToken()

        accessTokenRepository.findByIdOrNull(subject)?.let {
            accessTokenRepository.save(
                AccessTokenRedisEntity(
                    it.subject,
                    access,
                    jwtProperties.accessExpiredExp
                )
            )
        } ?: accessTokenRepository.save(
            AccessTokenRedisEntity(
                subject,
                access,
                jwtProperties.accessExpiredExp
            )
        )

        refreshTokenRepository.findByIdOrNull(subject)?.let {
            refreshTokenRepository.save(
                RefreshTokenRedisEntity(
                    it.subject,
                    refresh,
                    jwtProperties.refreshExpiredExp
                )
            )
        } ?: refreshTokenRepository.save(
            RefreshTokenRedisEntity(
                subject,
                refresh,
                jwtProperties.refreshExpiredExp
            )
        )

        return TokenResponse(
            access,
            refresh
        )
    }

    private fun generateAccessToken(): String {
        val now = Date()
        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(Date(now.time + jwtProperties.accessExpiredExp))
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .compact()
    }

    private fun generateRefreshToken(): String {
        val now = Date()
        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(Date(now.time.plus(jwtProperties.refreshExpiredExp)))
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .compact()
    }

    override fun reissue(refreshToken: String): TokenResponse {

        val rfToken = refreshTokenRepository.findByRfToken(refreshToken)
            ?: throw InvalidTokenException

        return generateTokens(rfToken.subject)
    }

    fun getAuthentication(subject: String): Authentication {

        val authDetails = customUserDetailService.loadUserByUsername(subject) as CustomUserDetails

        return UsernamePasswordAuthenticationToken(authDetails, null, authDetails.authorities)
    }

    override fun getJwtBody(token: String, publicKey: PublicKey): Claims =
        try {
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw ExpiredTokenException
                else -> throw InvalidTokenException
            }
        }

    override fun getJwtBody(token: String): Claims =
        try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            when (e) {
                is ExpiredJwtException -> throw ExpiredTokenException
                else -> throw InvalidTokenException
            }
        }
}
