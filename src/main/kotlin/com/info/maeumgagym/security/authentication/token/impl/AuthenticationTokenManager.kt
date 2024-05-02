package com.info.maeumgagym.security.authentication.token.impl

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.security.authentication.token.AuthenticationTokenDecoder
import com.info.maeumgagym.security.authentication.token.AuthenticationTokenEncoder
import com.info.maeumgagym.security.authentication.token.AuthenticationTokenValidator
import com.info.maeumgagym.security.authentication.token.vo.AuthenticationToken
import com.info.maeumgagym.security.authentication.token.vo.AuthenticationTokenType
import com.info.maeumgagym.security.cryption.Decrypt
import com.info.maeumgagym.security.cryption.Encrypt
import com.info.maeumgagym.security.cryption.type.Cryptography
import com.info.maeumgagym.security.jwt.env.JwtProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

/**
 * AuthenticationToken 관련 모듈의 구현체.
 *
 * 각 함수는 상위 인터페이스의 Docs를 참고.
 *
 * @see AuthenticationTokenEncoder
 * @see AuthenticationTokenDecoder
 * @see AuthenticationTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
class AuthenticationTokenManager(
    private val jwtProperties: JwtProperties,
    private val encrypt: Encrypt,
    private val decrypt: Decrypt,
    private val objectMapper: ObjectMapper
) : AuthenticationTokenEncoder,
    AuthenticationTokenDecoder,
    AuthenticationTokenValidator {

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

    override fun decode(token: String): AuthenticationToken {
        val decrypted = decrypt.decrypt(resolveTokenPrefix(token), jwtProperties.secretKey, Cryptography.HS256)

        return stringTokenToVO(decrypted)
    }

    override fun decode(token: String, key: String): AuthenticationToken {
        val decrypted = decrypt.decrypt(resolveTokenPrefix(token), key, Cryptography.HS256)

        return stringTokenToVO(decrypted)
    }

    private fun resolveTokenPrefix(token: String): String {
        if (!token.startsWith("$MAEUMGAGYM_TOKEN_PREFIX ")) {
            throw SecurityException.NOT_A_MAEUMGAGYM_TOKEN
        }

        return token.removePrefix(MAEUMGAGYM_TOKEN_PREFIX).trim()
    }

    private fun stringTokenToVO(token: String): AuthenticationToken {
        try {
            return objectMapper.readValue(token, AuthenticationToken::class.java)
        } catch (e: JsonProcessingException) {
            throw SecurityException.INVALID_TOKEN
        } catch (e: JsonMappingException) {
            throw SecurityException.INVALID_TOKEN
        }
    }

    override fun validate(authenticationToken: AuthenticationToken, request: HttpServletRequest) {
        if (!authenticationToken.isValid) {
            throw SecurityException.INVALID_TOKEN
        }

        when (authenticationToken.type) {
            AuthenticationTokenType.ACCESS_TOKEN -> {
                if (authenticationToken.expireAt != getAccessTokenExpireAt(authenticationToken.issuedAt)) {
                    throw SecurityException.INVALID_TOKEN
                }
            }

            AuthenticationTokenType.REFRESH_TOKEN -> {
                if (authenticationToken.expireAt != getRefreshTokenExpireAt(authenticationToken.issuedAt)) {
                    throw SecurityException.INVALID_TOKEN
                }
            }
        }

        if (authenticationToken.expireAt.isBefore(LocalDateTime.now())) {
            throw SecurityException.EXPIRED_TOKEN
        }

        if (request.remoteAddr != authenticationToken.ip) {
            throw SecurityException.WRONG_USER_TOKEN
        }
    }
}
