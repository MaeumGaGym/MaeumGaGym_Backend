package com.info.maeumgagym.security.jwt

import com.info.maeumgagym.security.jwt.vo.Jwt

interface JwtDecoder {

    fun decode(jwt: String): Jwt

    fun decode(jwt: String, key: String): Jwt
}
