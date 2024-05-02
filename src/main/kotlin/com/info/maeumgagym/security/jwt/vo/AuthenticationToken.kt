package com.info.maeumgagym.security.jwt.vo

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header

/**
 * 복호화된 상태의 인증용 토큰 정보를 관리하기 위한 VO
 *
 * @see com.info.maeumgagym.security.jwt.AuthenticationTokenEncoder
 * @see com.info.maeumgagym.security.jwt.AuthenticationTokenDecoder
 * @see com.info.maeumgagym.security.jwt.AuthenticationTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
data class AuthenticationToken(
    val header: Header<*>,
    val body: Claims,
    val type: AuthenticationTokenType
)
