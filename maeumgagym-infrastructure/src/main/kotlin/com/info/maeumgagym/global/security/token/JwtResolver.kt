package com.info.maeumgagym.global.security.token

import com.info.maeumgagym.global.env.jwt.JwtProperties
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class JwtResolver(
    val jwtProperties: JwtProperties
) {
    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.also {
            if (it.startsWith(jwtProperties.prefix)) {
                return it.substring(jwtProperties.prefix.length)
            }
        }
}
