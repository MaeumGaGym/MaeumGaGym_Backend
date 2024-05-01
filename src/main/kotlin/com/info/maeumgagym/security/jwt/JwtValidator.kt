package com.info.maeumgagym.security.jwt

import com.info.maeumgagym.security.jwt.vo.Jwt

interface JwtValidator {

    fun validate(jwt: Jwt)
}
