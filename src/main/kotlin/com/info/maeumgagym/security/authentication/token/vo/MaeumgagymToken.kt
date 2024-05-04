package com.info.maeumgagym.security.authentication.token.vo

import java.time.LocalDateTime

/**
 * 복호화된 상태의 인증용 토큰 정보를 관리하기 위한 VO
 *
 * @param username 토큰을 발급 받은 대상의 [id][com.info.maeumgagym.core.user.model.User.oauthId]
 * @param ip 토큰을 발급 받은 대상의 IP 주소
 * @param issuedAt 토큰이 발급된 날짜와 시간
 * @param expireAt 토큰이 만료될 날짜와 시간
 * @param type 토큰의 [타입][MaeumgagymTokenType]
 * @param tokenId 토큰을 무효화 처리할 때 사용되는 id. [UUID][java.util.UUID]의 문자열
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
    val tokenId: String
) {
    companion object {
        const val PREFIX: String = "maeumgagym"
    }
}
