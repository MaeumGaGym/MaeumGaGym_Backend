package com.info.maeumgagym.auth.port.out

/**
 * 유저의 특정 식별 값을 바탕으로 발급된 Access Token과 Refresh Token을 무효화
 *
 * @see GenerateJwtPort
 * @see ReissuePort
 *
 * @author gurdl0525
 * @since 20-11-2023
 * @since 추상화 : 04-12-2023
 */
interface RevokeTokensPort {

    /**
     * @param subject 유저의 식별 값, 기본적으로 [User.oauthId][com.info.maeumgagym.user.model.User.oauthId]를 사용
     */
    fun revoke(subject: String)
}
