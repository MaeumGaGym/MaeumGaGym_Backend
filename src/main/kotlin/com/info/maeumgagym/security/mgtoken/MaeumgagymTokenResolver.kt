package com.info.maeumgagym.security.mgtoken

/**
 * 암호화된 상태의 문자열 마음가짐 토큰을 검증하고 subject를 추출
 *
 * 저수준 모듈 집합을 추상화해낸 모듈로, Security 계층 내에서 인증 로직을 위해 사용
 *
 * @see com.info.maeumgagym.security.mgtoken.MaeumgagymTokenDecoder 토큰에서 subject 추출
 * @see com.info.maeumgagym.security.mgtoken.MaeumgagymTokenValidator 토큰 검증 모듈
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
interface MaeumgagymTokenResolver {

    /**
     * @param token 유효성을 확인할 토큰
     * @return OAuthId, 토큰이 유효하지 않을 경우 null
     */
    operator fun invoke(token: String): String?
}
