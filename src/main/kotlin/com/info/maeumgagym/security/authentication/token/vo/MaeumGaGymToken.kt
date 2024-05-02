package com.info.maeumgagym.security.authentication.token.vo

import java.time.LocalDateTime

/**
 * 복호화된 상태의 인증용 토큰 정보를 관리하기 위한 VO
 *
 * @see com.info.maeumgagym.security.authentication.token.MaeumGaGymTokenEncoder
 * @see com.info.maeumgagym.security.authentication.token.MaeumGaGymTokenDecoder
 * @see com.info.maeumgagym.security.authentication.token.MaeumGaGymTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
data class MaeumGaGymToken(
    val username: String,
    val ip: String,
    val issuedAt: LocalDateTime,
    val expireAt: LocalDateTime,
    val type: MaeumGaGymTokenType,
    val isValid: Boolean = true
) {
    companion object {
        const val PREFIX: String = "maeumgagym"
    }
}
