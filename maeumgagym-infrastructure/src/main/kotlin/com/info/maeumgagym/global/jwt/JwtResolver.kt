package com.info.maeumgagym.global.jwt

import com.info.maeumgagym.global.jwt.repository.AccessTokenRepository
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
        // header에서 토큰을 찾을 수 없다면 null반환
        request.getHeader(jwtProperties.header)?.let {
            // WHEN : 정해진 prefix로 시작한다면
            if (it.startsWith(jwtProperties.prefix)) {
                // prefix slicing
                val token = it.substring(jwtProperties.prefix.length).trimStart()

                // 만료된 access_token인지 확인 및 반환, 예외처리
                accessTokenRepository.findByAccessToken(token)?.subject ?: throw InvalidTokenException
            } else {
                throw InvalidTokenException // 정해진 prefix로 시작하지 않을 경우 Error반환
            }
        }
}
