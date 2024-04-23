package com.info.maeumgagym.auth.port.out

/**
 * Refresh Token을 가지고 Token들을 다시 생성
 *
 * @see GenerateJwtPort
 * @see RevokeTokensPort
 *
 * @author gurdl0525
 * @since 20-11-2023
 * @since 추상화 : 04-12-2024
 */
interface ReissuePort {

    fun reissue(refreshToken: String): Pair<String, String>
}
