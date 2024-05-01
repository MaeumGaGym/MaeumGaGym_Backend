package com.info.maeumgagym.security.jwt.vo

import com.info.maeumgagym.common.exception.CriticalException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header

data class Jwt(
    val header: Header<*>,
    val body: Claims,
    val type: JwtType
)

enum class JwtType(
    val value: String
) {
    ACCESS_TOKEN("access"),
    REFRESH_TOKEN("refresh");

    companion object {
        fun of(string: String) =
            when (string) {
                ACCESS_TOKEN.value -> ACCESS_TOKEN

                REFRESH_TOKEN.value -> REFRESH_TOKEN

                else -> throw CriticalException(500, "Wrong Jwt Type")
            }
    }

}
