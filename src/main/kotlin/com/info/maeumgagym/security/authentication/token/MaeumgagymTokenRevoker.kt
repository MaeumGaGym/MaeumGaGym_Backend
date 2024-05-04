package com.info.maeumgagym.security.authentication.token

import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken

/**
 * [MaeumgagymToken]을 무효화.
 *
 * [MaeumgagymToken.tokenId]를 [Redis][com.info.maeumgagym.security.authentication.token.revoked.RevokedMGTokenRepository]를 통해 저장
 *
 * @author Daybreak312
 * @since 04-05-2024
 */
interface MaeumgagymTokenRevoker {

    fun revoke(token: MaeumgagymToken)
}
