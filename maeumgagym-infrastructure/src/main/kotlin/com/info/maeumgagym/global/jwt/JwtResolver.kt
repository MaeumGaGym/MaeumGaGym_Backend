package com.info.maeumgagym.global.jwt

import com.info.maeumgagym.domain.auth.repository.AccessTokenRepository
import com.info.maeumgagym.global.env.jwt.JwtProperties
import com.info.maeumgagym.global.exception.InvalidTokenException
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class JwtResolver(
    private val jwtProperties: JwtProperties,
    private val accessTokenRepository: AccessTokenRepository
) {
    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.let {

            if (it.startsWith(jwtProperties.prefix)) {

                val token = it.substring(jwtProperties.prefix.length)

                accessTokenRepository.findByAccessToken(token)?.subject ?: throw InvalidTokenException

            } else throw InvalidTokenException
        }
}
