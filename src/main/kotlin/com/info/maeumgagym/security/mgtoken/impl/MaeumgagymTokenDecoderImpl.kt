package com.info.maeumgagym.security.mgtoken.impl

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.security.cryption.Decrypt
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenDecoder
import com.info.maeumgagym.security.mgtoken.env.MaeumgagymTokenProperties
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
internal class MaeumgagymTokenDecoderImpl(
    private val decrypt: Decrypt,
    private val maeumgagymTokenProperties: MaeumgagymTokenProperties,
    private val objectMapper: ObjectMapper
) : MaeumgagymTokenDecoder {

    override fun decode(token: String): MaeumgagymToken {
        val prefixResolved = resolveTokenPrefix(token)

        val decrypted =
            try {
                decrypt.decrypt(prefixResolved, maeumgagymTokenProperties.secretKey)
            } catch (e: Exception) {
                throw AuthenticationException.INVALID_TOKEN
            }

        return stringTokenToVO(decrypted)
    }

    private fun resolveTokenPrefix(token: String): String {
        if (!token.startsWith("${maeumgagymTokenProperties.prefix} ")) {
            throw AuthenticationException.NOT_A_MAEUMGAGYM_TOKEN
        }

        return token.removePrefix(maeumgagymTokenProperties.prefix).trim()
    }

    private fun stringTokenToVO(token: String): MaeumgagymToken {
        try {
            return objectMapper.readValue(token, MaeumgagymToken::class.java)
        } catch (e: JsonProcessingException) {
            throw AuthenticationException.INVALID_TOKEN
        } catch (e: JsonMappingException) {
            throw AuthenticationException.INVALID_TOKEN
        }
    }
}
