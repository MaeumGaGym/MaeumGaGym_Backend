package com.info.maeumgagym.security.authentication.token.impl

import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.security.authentication.token.AuthenticationTokenValidator
import com.info.maeumgagym.security.authentication.token.vo.AuthenticationToken
import com.info.maeumgagym.security.authentication.token.vo.AuthenticationTokenType
import com.info.maeumgagym.security.jwt.env.JwtProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
class AuthenticationTokenValidatorImpl(
    private val jwtProperties: JwtProperties
) : AuthenticationTokenValidator {

    override fun validate(authenticationToken: AuthenticationToken, request: HttpServletRequest) {
        if (!authenticationToken.isValid) {
            throw SecurityException.INVALID_TOKEN
        }

        when (authenticationToken.type) {
            AuthenticationTokenType.ACCESS_TOKEN -> {
                if (authenticationToken.expireAt != getAccessTokenExpireAt(authenticationToken.issuedAt)) {
                    throw SecurityException.INVALID_TOKEN
                }
            }

            AuthenticationTokenType.REFRESH_TOKEN -> {
                if (authenticationToken.expireAt != getRefreshTokenExpireAt(authenticationToken.issuedAt)) {
                    throw SecurityException.INVALID_TOKEN
                }
            }
        }

        if (authenticationToken.expireAt.isBefore(LocalDateTime.now())) {
            throw SecurityException.EXPIRED_TOKEN
        }

        if (request.remoteAddr != authenticationToken.ip) {
            throw SecurityException.WRONG_USER_TOKEN
        }
    }

    private fun getAccessTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(jwtProperties.accessExpiredExp)

    private fun getRefreshTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(jwtProperties.refreshExpiredExp)
}
