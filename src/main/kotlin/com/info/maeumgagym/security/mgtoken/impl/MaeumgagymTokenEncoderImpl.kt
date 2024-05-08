package com.info.maeumgagym.security.mgtoken.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext
import com.info.maeumgagym.security.cryption.Encrypt
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenEncoder
import com.info.maeumgagym.security.mgtoken.env.MaeumgagymTokenProperties
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymTokenPair
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymTokenType
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

    override fun encode(username: String): MaeumgagymTokenPair {
        val tokenId = UUID.randomUUID().toString()

        val now = LocalDateTime.now()

        return MaeumgagymTokenPair(
            encodeToAccessToken(username, tokenId, now),
            encodeToRefreshToken(username, tokenId, now)
        )
    }

    private fun encodeToAccessToken(subject: String, tokenId: String, now: LocalDateTime): String {
        val token = MaeumgagymToken(
            username = subject,
            ip = currentRequestContext.getCurrentRequest().remoteAddr,
            issuedAt = now,
            expireAt = getAccessTokenExpireAt(now),
            type = MaeumgagymTokenType.ACCESS_TOKEN,
            tokenId = tokenId
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    private fun getAccessTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(maeumgagymTokenProperties.accessExpiredExp)

    private fun encodeToRefreshToken(subject: String, tokenId: String, now: LocalDateTime): String {
        val token = MaeumgagymToken(
            username = subject,
            ip = currentRequestContext.getCurrentRequest().remoteAddr,
            issuedAt = now,
            expireAt = getRefreshTokenExpireAt(now),
            type = MaeumgagymTokenType.REFRESH_TOKEN,
            tokenId = tokenId
        )

        return appendTokenPrefix(
            encryptToken(token)
        )
    }

    private fun getRefreshTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(maeumgagymTokenProperties.refreshExpiredExp)

    private fun encryptToken(token: MaeumgagymToken): String {
        val tokenString = objectMapper.writeValueAsString(token)

        return encrypt.encrypt(tokenString, maeumgagymTokenProperties.secretKey)
    }

    private fun appendTokenPrefix(token: String) =
        "${maeumgagymTokenProperties.prefix} $token"
}
