package com.info.maeumgagym.security.authentication.token.impl

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.security.authentication.token.AuthenticationTokenDecoder
import com.info.maeumgagym.security.authentication.token.vo.AuthenticationToken
import com.info.maeumgagym.security.cryption.Decrypt
import com.info.maeumgagym.security.cryption.type.Cryptography
import com.info.maeumgagym.security.jwt.env.JwtProperties
import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
class AuthenticationTokenDecoderImpl(
    private val decrypt: Decrypt,
    private val jwtProperties: JwtProperties,
    private val objectMapper: ObjectMapper
) : AuthenticationTokenDecoder {

    override fun decode(token: String): AuthenticationToken {
        val decrypted = decrypt.decrypt(resolveTokenPrefix(token), jwtProperties.secretKey, Cryptography.HS256)

        return stringTokenToVO(decrypted)
    }

    override fun decode(token: String, key: String): AuthenticationToken {
        val decrypted = decrypt.decrypt(resolveTokenPrefix(token), key, Cryptography.HS256)

        return stringTokenToVO(decrypted)
    }

    private fun resolveTokenPrefix(token: String): String {
        if (!token.startsWith("${AuthenticationToken.PREFIX} ")) {
            throw SecurityException.NOT_A_MAEUMGAGYM_TOKEN
        }

        return token.removePrefix(AuthenticationToken.PREFIX).trim()
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
}
