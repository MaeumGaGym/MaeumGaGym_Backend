package com.info.maeumgagym.security.authentication.token.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.infrastructure.request.context.RequestContext
import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenEncoder
import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken
import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymTokenType
import com.info.maeumgagym.security.cryption.Encrypt
import com.info.maeumgagym.security.cryption.type.Cryptography
import com.info.maeumgagym.security.jwt.env.MaeumgagymTokenProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
internal class MaeumgagymTokenEncoderImpl(
    private val encrypt: Encrypt,
    private val requestContext: RequestContext,
    private val maeumgagymTokenProperties: MaeumgagymTokenProperties,
    private val objectMapper: ObjectMapper
) : MaeumgagymTokenEncoder {

    private companion object {
        const val MAEUMGAGYM_TOKEN_PREFIX = "maeumgagym"
    }

    override fun encodeAccessToken(subject: String): String {
        val now = LocalDateTime.now()

        val token = MaeumgagymToken(
            username = subject,
            ip = requestContext.getCurrentRequest().remoteAddr,
            issuedAt = now,
            expireAt = getAccessTokenExpireAt(now),
            type = MaeumgagymTokenType.ACCESS_TOKEN
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    override fun encodeRefreshToken(subject: String): String {
        val now = LocalDateTime.now()

        val token = MaeumgagymToken(
            username = subject,
            ip = requestContext.getCurrentRequest().remoteAddr,
            issuedAt = now,
            expireAt = getRefreshTokenExpireAt(now),
            type = MaeumgagymTokenType.REFRESH_TOKEN
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    private fun getAccessTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(maeumgagymTokenProperties.accessExpiredExp)

    private fun getRefreshTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(maeumgagymTokenProperties.refreshExpiredExp)

    private fun encryptToken(token: MaeumgagymToken): String {
        val tokenString = objectMapper.writeValueAsString(token)

        return encrypt.encrypt(tokenString, maeumgagymTokenProperties.secretKey, Cryptography.HS256)
    }

    private fun appendTokenPrefix(token: String) =
        "$MAEUMGAGYM_TOKEN_PREFIX $token"
}
