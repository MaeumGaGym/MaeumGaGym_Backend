package com.info.maeumgagym.security.authentication.token.revoked

import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken

/**
 * 무효화된 토큰을 모아둔 컨텍스트
 *
 * [Redis][com.info.maeumgagym.security.authentication.token.revoked.RevokedMGTokenRepository]를 통해 관리
 *
 * @author Daybreak312
 * @since 04-05-2024
 */
interface RevokedMGTokenContext {

    /**
     * 토큰을 무효화 처리
     */
    fun revoke(token: MaeumgagymToken)

    /**
     * 토큰이 무효화되었는지 확인
     *
     * 토큰이 만료되었을 경우, 무효화되지 않은 것으로 반환됨
     */
    fun checkRevoked(tokenId: String): Boolean
}
