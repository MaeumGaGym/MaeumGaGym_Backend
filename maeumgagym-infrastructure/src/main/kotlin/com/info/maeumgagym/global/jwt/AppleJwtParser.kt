package com.info.maeumgagym.global.jwt

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.auth.port.out.AppleJwtParsePort
import com.info.maeumgagym.global.exception.InvalidTokenException
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils


@Component
class AppleJwtParser(private val objectMapper: ObjectMapper): AppleJwtParsePort {

    companion object {
        private const val IDENTITY_TOKEN_VALUE_DELIMITER = "\\."
        private const val HEADER_INDEX = 0
    }

    @Suppress("unchecked_cast")
    override fun parseHeaders(token: String): MutableMap<String?, String?> {
        return try {
            val encodedHeader: String = token.split(IDENTITY_TOKEN_VALUE_DELIMITER.toRegex())[HEADER_INDEX]
            val decodedHeader = String(Base64Utils.decodeFromUrlSafeString(encodedHeader))
            objectMapper.readValue(decodedHeader, MutableMap::class.java) as MutableMap<String?, String?>
        } catch (e: JsonProcessingException) {
            throw InvalidTokenException
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw InvalidTokenException
        }
    }
}
