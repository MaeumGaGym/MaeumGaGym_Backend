package com.info.maeumgagym.global.jwt

import com.info.maeumgagym.global.env.jwt.JwtProperties
import com.info.maeumgagym.global.exception.InvalidTokenException
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class JwtResolver(
    val jwtProperties: JwtProperties
) {
    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.let {
            if (it.startsWith(jwtProperties.prefix)) {
                return it.substring(jwtProperties.prefix.length)
            } else {
                throw InvalidTokenException
            }
        }
}
