package com.info.maeumgagym.core.auth.port.out

/**
 * 현재 요청의 토큰을 무효화
 *
 * @see GenerateJwtPort
 * @see ReissuePort
 *
 * @author Daybreak312
 * @since 20-11-2023
 * @since 추상화 : 04-12-2023
 */
interface RevokeTokensPort {

    fun revoke()
}
