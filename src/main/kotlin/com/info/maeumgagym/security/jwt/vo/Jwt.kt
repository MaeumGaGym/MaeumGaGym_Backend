package com.info.maeumgagym.security.jwt.vo

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header

/**
 * 복호화된 상태의 Jwt 토큰을 관리하기 위한 VO
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
data class Jwt(
    val header: Header<*>,
    val body: Claims,
    val type: JwtType
)
