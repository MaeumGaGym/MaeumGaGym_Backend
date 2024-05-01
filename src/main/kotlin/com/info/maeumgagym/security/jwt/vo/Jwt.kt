package com.info.maeumgagym.security.jwt.vo

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header

data class Jwt(
    val header: Header<*>,
    val body: Claims,
    val type: JwtType
)
