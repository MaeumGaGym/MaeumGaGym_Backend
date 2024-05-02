package com.info.maeumgagym.security.authentication.token.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.infrastructure.request.context.RequestContext
import com.info.maeumgagym.security.authentication.token.MaeumGaGymTokenEncoder
import com.info.maeumgagym.security.authentication.token.vo.MaeumGaGymToken
import com.info.maeumgagym.security.authentication.token.vo.MaeumGaGymTokenType
import com.info.maeumgagym.security.cryption.Encrypt
import com.info.maeumgagym.security.cryption.type.Cryptography
import com.info.maeumgagym.security.jwt.env.JwtProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
internal class MaeumGaGymTokenEncoderImpl(
    private val encrypt: Encrypt,
    private val requestContext: RequestContext,
    private val jwtProperties: JwtProperties,
    private val objectMapper: ObjectMapper
) : MaeumGaGymTokenEncoder {

    private companion object {
        const val MAEUMGAGYM_TOKEN_PREFIX = "maeumgagym"
    }

    override fun encodeAccessToken(subject: String): String {
        val now = LocalDateTime.now()

        val token = MaeumGaGymToken(
            username = subject,
            ip = requestContext.getCurrentRequest().remoteAddr,
            issuedAt = now,
            expireAt = getAccessTokenExpireAt(now),
            type = MaeumGaGymTokenType.ACCESS_TOKEN
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    override fun encodeRefreshToken(subject: String): String {
        val now = LocalDateTime.now()

        val token = MaeumGaGymToken(
            username = subject,
            ip = requestContext.getCurrentRequest().remoteAddr,
            issuedAt = now,
            expireAt = getRefreshTokenExpireAt(now),
            type = MaeumGaGymTokenType.REFRESH_TOKEN
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    private fun getAccessTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(jwtProperties.accessExpiredExp)

    private fun getRefreshTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(jwtProperties.refreshExpiredExp)

    private fun encryptToken(token: MaeumGaGymToken): String {
        val tokenString = objectMapper.writeValueAsString(token)

        return encrypt.encrypt(tokenString, jwtProperties.secretKey, Cryptography.HS256)
    }

    private fun appendTokenPrefix(token: String) =
        "$MAEUMGAGYM_TOKEN_PREFIX $token"
}
