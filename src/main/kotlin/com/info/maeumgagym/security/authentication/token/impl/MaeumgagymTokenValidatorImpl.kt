package com.info.maeumgagym.security.authentication.token.impl

import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.infrastructure.request.context.RequestContext
import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenValidator
import com.info.maeumgagym.security.authentication.token.revoked.RevokedMGTokenContext
import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken
import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymTokenType
import com.info.maeumgagym.security.jwt.env.MaeumgagymTokenProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Docs는 상위 타입에 존재.
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
internal class MaeumgagymTokenValidatorImpl(
    private val revokedMGTokenContext: RevokedMGTokenContext,
    private val requestContext: RequestContext,
    private val maeumgagymTokenProperties: MaeumgagymTokenProperties
) : MaeumgagymTokenValidator {

    override fun validate(maeumGaGymToken: MaeumgagymToken) {
        if (revokedMGTokenContext.checkRevoked(maeumGaGymToken.tokenId)) {
            throw SecurityException.INVALID_TOKEN
        }

        when (maeumGaGymToken.type) {
            MaeumgagymTokenType.ACCESS_TOKEN -> {
                if (maeumGaGymToken.expireAt != getAccessTokenExpireAt(maeumGaGymToken.issuedAt)) {
                    throw SecurityException.INVALID_TOKEN
                }
            }

            MaeumgagymTokenType.REFRESH_TOKEN -> {
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
        baseTime.plusSeconds(maeumgagymTokenProperties.accessExpiredExp)

    private fun getRefreshTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(maeumgagymTokenProperties.refreshExpiredExp)
}
