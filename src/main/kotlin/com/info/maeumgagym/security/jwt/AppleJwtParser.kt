package com.info.maeumgagym.security.jwt

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.core.auth.port.out.AppleJwtParsePort
import com.info.maeumgagym.common.exception.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils

@Component
class AppleJwtParser(
    private val objectMapper: ObjectMapper
) : AppleJwtParsePort {

    companion object {
        private const val IDENTITY_TOKEN_VALUE_DELIMITER = "\\."
        private const val HEADER_INDEX = 0
    }

    @Suppress("unchecked_cast") // String Type으로 Casting 되지 않는 오류
    override fun parseHeaders(token: String): MutableMap<String?, String?> = try {
        // 인코딩된 토큰 슬라이싱
        val encodedHeader: String = token.split(IDENTITY_TOKEN_VALUE_DELIMITER.toRegex())[HEADER_INDEX]

        // url-safe 하도록 Base64 Decoding
        val decodedHeader = String(Base64Utils.decodeFromUrlSafeString(encodedHeader))

        // json데이터를 Map<String?, String?>형태로 cast
        objectMapper.readValue(decodedHeader, MutableMap::class.java) as MutableMap<String?, String?>
    } catch (e: JsonProcessingException) { // json형식의 데이터가 아닐 때
        throw AuthenticationException.INVALID_TOKEN
    } catch (e: ArrayIndexOutOfBoundsException) { // Index가 size를 벗어났을 때
        throw AuthenticationException.INVALID_TOKEN
    }
}
