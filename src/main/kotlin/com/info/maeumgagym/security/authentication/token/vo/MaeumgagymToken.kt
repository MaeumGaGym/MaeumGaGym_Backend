package com.info.maeumgagym.security.authentication.token.vo

import java.time.LocalDateTime

/**
 * 복호화된 상태의 인증용 토큰 정보를 관리하기 위한 VO
 *
 * @see com.info.maeumgagym.security.authentication.token.MaeumgagymTokenEncoder
 * @see com.info.maeumgagym.security.authentication.token.MaeumgagymTokenDecoder
 * @see com.info.maeumgagym.security.authentication.token.MaeumgagymTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
data class MaeumgagymToken(
    val username: String,
    val ip: String,
    val issuedAt: LocalDateTime,
    val expireAt: LocalDateTime,
    val type: MaeumgagymTokenType,
    val isValid: Boolean = true
) {
    companion object {
        const val PREFIX: String = "maeumgagym"
    }
}
