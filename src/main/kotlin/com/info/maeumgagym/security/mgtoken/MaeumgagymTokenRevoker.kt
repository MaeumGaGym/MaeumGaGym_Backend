package com.info.maeumgagym.security.mgtoken

import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken

/**
 * [MaeumgagymToken]을 무효화.
 *
 * 토큰의 검증 과정은 거치지 않고, 단순히 [tokenId][MaeumgagymToken.tokenId]를 무효화된 토큰들을 담아두는 [Redis][com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenRepository]를 통해 저장
 *
 * [RevokedMGTokenContext][com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenContext]를 통해 [Redis][com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenRepository]에 접근
 *
 * @see com.info.maeumgagym.security.mgtoken.revoked.RevokedMGTokenContext
 *
 * @see MaeumgagymTokenEncoder
 * @see MaeumgagymTokenDecoder
 * @see MaeumgagymTokenValidator
 *
 * @author Daybreak312
 * @since 04-05-2024
 */
interface MaeumgagymTokenRevoker {

    fun revoke(token: MaeumgagymToken)
}
