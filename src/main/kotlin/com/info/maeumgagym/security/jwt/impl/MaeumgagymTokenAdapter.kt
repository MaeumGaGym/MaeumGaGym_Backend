package com.info.maeumgagym.security.jwt.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.core.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.core.auth.port.out.ReissuePort
import com.info.maeumgagym.core.auth.port.out.RevokeTokensPort
import com.info.maeumgagym.infrastructure.request.context.RequestContext
import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenDecoder
import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenEncoder
import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenRevoker
import com.info.maeumgagym.security.authentication.token.MaeumgagymTokenValidator
import org.springframework.stereotype.Component

/**
 * Maeumgagym Token에 관련된 Port들의 구현체.
 *
 * @see GenerateJwtPort
 * @see ReissuePort
 * @see RevokeTokensPort
 *
 * @author Daybreak312, gurdl0525
 * @since 20-11-2023
 * @since 추상화 : 28-12-2023
 */
@Component
class MaeumgagymTokenAdapter(
    private val maeumgagymTokenEncoder: MaeumgagymTokenEncoder,
    private val maeumgagymTokenDecoder: MaeumgagymTokenDecoder,
    private val maeumgagymTokenValidator: MaeumgagymTokenValidator,
    private val maeumgagymTokenRevoker: MaeumgagymTokenRevoker,
    private val requestContext: RequestContext
) : GenerateJwtPort, ReissuePort, RevokeTokensPort {

    // 모든 토큰 발급
    override fun generateTokens(subject: String): Pair<String, String> {
        // access_token 발급
        val access = maeumgagymTokenEncoder.encodeAccessToken(subject)

        // refresh_token 발급
        val refresh = maeumgagymTokenEncoder.encodeRefreshToken(subject)

        // tokens dto에 담아 반환
        return Pair(access, refresh)
    }

    override fun revoke() {
        val token = requestContext.getCurrentToken()
            ?: throw CriticalException(500, "Token is NULL In Revoke(Only Authenticated Can Access)")

        maeumgagymTokenRevoker.revoke(token)
    }

    // 토큰 재발급
    override fun reissue(refreshToken: String): Pair<String, String> {
        // refresh_token을 redis에서 불러오기
        val decodedToken = maeumgagymTokenDecoder.decode(refreshToken)

        maeumgagymTokenValidator.validate(decodedToken)

        revoke()

        // 토큰 재발급 및 반환
        return generateTokens(decodedToken.username)
    }
}
