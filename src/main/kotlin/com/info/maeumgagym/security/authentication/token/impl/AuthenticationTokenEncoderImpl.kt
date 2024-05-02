package com.info.maeumgagym.security.authentication.token.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.security.authentication.token.AuthenticationTokenEncoder
import com.info.maeumgagym.security.authentication.token.vo.AuthenticationToken
import com.info.maeumgagym.security.authentication.token.vo.AuthenticationTokenType
import com.info.maeumgagym.security.cryption.Encrypt
import com.info.maeumgagym.security.cryption.type.Cryptography
import com.info.maeumgagym.security.jwt.env.JwtProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
class AuthenticationTokenEncoderImpl(
    private val jwtProperties: JwtProperties,
    private val encrypt: Encrypt,
    private val objectMapper: ObjectMapper
) : AuthenticationTokenEncoder {

    private companion object {
        const val MAEUMGAGYM_TOKEN_PREFIX = "maeumgagym"
    }

    override fun encodeAccessToken(subject: String, request: HttpServletRequest): String {
        val now = LocalDateTime.now()

        val token = AuthenticationToken(
            username = subject,
            ip = request.remoteAddr,
            issuedAt = now,
            expireAt = getAccessTokenExpireAt(now),
            type = AuthenticationTokenType.ACCESS_TOKEN
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    override fun encodeRefreshToken(subject: String, request: HttpServletRequest): String {
        val now = LocalDateTime.now()

        val token = AuthenticationToken(
            username = subject,
            ip = request.remoteAddr,
            issuedAt = now,
            expireAt = getRefreshTokenExpireAt(now),
            type = AuthenticationTokenType.REFRESH_TOKEN
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    private fun getAccessTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(jwtProperties.accessExpiredExp)

    private fun getRefreshTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(jwtProperties.refreshExpiredExp)

    private fun encryptToken(token: AuthenticationToken): String {
        val tokenString = objectMapper.writeValueAsString(token)

        return encrypt.encrypt(tokenString, jwtProperties.secretKey, Cryptography.HS256)
    }

    private fun appendTokenPrefix(token: String) =
        "$MAEUMGAGYM_TOKEN_PREFIX $token"
}
