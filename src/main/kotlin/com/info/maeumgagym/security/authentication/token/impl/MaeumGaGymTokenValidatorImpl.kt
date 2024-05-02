package com.info.maeumgagym.security.authentication.token.impl

import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.infrastructure.request.context.RequestContext
import com.info.maeumgagym.security.authentication.token.MaeumGaGymTokenValidator
import com.info.maeumgagym.security.authentication.token.vo.MaeumGaGymToken
import com.info.maeumgagym.security.authentication.token.vo.MaeumGaGymTokenType
import com.info.maeumgagym.security.jwt.env.JwtProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
internal class MaeumGaGymTokenValidatorImpl(
    private val requestContext: RequestContext,
    private val jwtProperties: JwtProperties
) : MaeumGaGymTokenValidator {

    override fun validate(maeumGaGymToken: MaeumGaGymToken) {
        if (!maeumGaGymToken.isValid) {
            throw SecurityException.INVALID_TOKEN
        }

        when (maeumGaGymToken.type) {
            MaeumGaGymTokenType.ACCESS_TOKEN -> {
                if (maeumGaGymToken.expireAt != getAccessTokenExpireAt(maeumGaGymToken.issuedAt)) {
                    throw SecurityException.INVALID_TOKEN
                }
            }

            MaeumGaGymTokenType.REFRESH_TOKEN -> {
                if (maeumGaGymToken.expireAt != getRefreshTokenExpireAt(maeumGaGymToken.issuedAt)) {
                    throw SecurityException.INVALID_TOKEN
                }
            }
        }

        if (maeumGaGymToken.expireAt.isBefore(LocalDateTime.now())) {
            throw SecurityException.EXPIRED_TOKEN
        }

        if (requestContext.getCurrentRequest().remoteAddr != maeumGaGymToken.ip) {
            throw SecurityException.WRONG_USER_TOKEN
        }
    }

    private fun getAccessTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(jwtProperties.accessExpiredExp)

    private fun getRefreshTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(jwtProperties.refreshExpiredExp)
}
