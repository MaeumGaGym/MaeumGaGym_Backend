package com.info.maeumgagym.infrastructure.security.jwt

/**
 * 주어진 토큰의 유효성을 확인하고, 그 여부에 따라 토큰과 연결된 OAuthId를 반환
 *
 * 유효성 : AccessToken인지, JWT 토큰인지, 해당 서버에서 발급했으며 저장이 되어 있는지(유효 기간이 지나지 않았는지)
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
interface JwtResolver {

    /**
     * @param token 유효성을 확인할 토큰
     * @return OAuthId, 토큰이 유효하지 않을 경우 null
     */
    operator fun invoke(token: String): String?
}
