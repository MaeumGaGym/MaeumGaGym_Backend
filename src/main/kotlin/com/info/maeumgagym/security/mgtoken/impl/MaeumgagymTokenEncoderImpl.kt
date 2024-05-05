package com.info.maeumgagym.security.mgtoken.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenEncoder
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymTokenType
import com.info.maeumgagym.security.cryption.Encrypt
import com.info.maeumgagym.security.mgtoken.env.MaeumgagymTokenProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
internal class MaeumgagymTokenEncoderImpl(
    private val encrypt: Encrypt,
    private val currentRequestContext: CurrentRequestContext,
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
            ip = currentRequestContext.getCurrentRequest().remoteAddr,
            issuedAt = now,
            expireAt = getAccessTokenExpireAt(now),
            type = MaeumgagymTokenType.ACCESS_TOKEN,
            tokenId = UUID.randomUUID().toString()
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    override fun encodeRefreshToken(subject: String): String {
        val now = LocalDateTime.now()

        val token = MaeumgagymToken(
            username = subject,
            ip = currentRequestContext.getCurrentRequest().remoteAddr,
            issuedAt = now,
            expireAt = getRefreshTokenExpireAt(now),
            type = MaeumgagymTokenType.REFRESH_TOKEN,
            tokenId = UUID.randomUUID().toString()
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

        return Base64.getEncoder().encodeToString(
            encrypt.encrypt(tokenString.encodeToByteArray(), maeumgagymTokenProperties.secretKey)
        )
    }

    private fun appendTokenPrefix(token: String) =
        "$MAEUMGAGYM_TOKEN_PREFIX $token"
}
