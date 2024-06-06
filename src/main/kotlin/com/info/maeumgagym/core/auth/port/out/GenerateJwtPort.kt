package com.info.maeumgagym.core.auth.port.out

/**
 * 유저의 특정 식별 값을 가지고, Access Token과 Refresh Token을 생성해 반환
 *
 * @see ReissuePort
 * @see RevokeTokensPort
 *
 * @author gurdl0525
 * @since 20-11-2023
 * @since 추상화 : 04-12-2023
 */
interface GenerateJwtPort {

    /**
     * @param subject 유저의 식별 값, 기본적으로 [User.oauthId][com.info.maeumgagym.core.user.model.User.oauthId]를 사용
     * @return 생성된 Token들, Access Token : Refresh Token
     */
    fun generateTokens(subject: String): Pair<String, String>
}
