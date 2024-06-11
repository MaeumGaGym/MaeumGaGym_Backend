package com.info.maeumgagym.security.mgtoken.impl

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenValidator
import com.info.maeumgagym.security.mgtoken.env.MaeumgagymTokenProperties
import com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenContext
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymTokenType
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
    private val currentRequestContext: CurrentRequestContext,
    private val maeumgagymTokenProperties: MaeumgagymTokenProperties
) : MaeumgagymTokenValidator {

    override fun validate(maeumgagymToken: MaeumgagymToken) {
        if (revokedMGTokenContext.checkRevoked(maeumgagymToken.tokenId)) {
            throw AuthenticationException.REVOKED_TOKEN
        }

        when (maeumgagymToken.type) {
            MaeumgagymTokenType.ACCESS_TOKEN -> {
                if (maeumgagymToken.expireAt != getAccessTokenExpireAt(maeumgagymToken.issuedAt)) {
                    throw AuthenticationException.INVALID_TOKEN
                }
            }

            MaeumgagymTokenType.REFRESH_TOKEN -> {
                if (maeumgagymToken.expireAt != getRefreshTokenExpireAt(maeumgagymToken.issuedAt)) {
                    throw AuthenticationException.INVALID_TOKEN
                }
            }
        }

        if (maeumgagymToken.expireAt.isBefore(LocalDateTime.now())) {
            throw AuthenticationException.EXPIRED_TOKEN
        }

//        if (currentRequestContext.getCurrentRequest().remoteAddr != maeumgagymToken.ip) {
//            throw AuthenticationException.WRONG_USER_TOKEN
//        }
    }

    private fun getAccessTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(maeumgagymTokenProperties.accessExpiredExp)

    private fun getRefreshTokenExpireAt(baseTime: LocalDateTime): LocalDateTime =
        baseTime.plusSeconds(maeumgagymTokenProperties.refreshExpiredExp)
}
