package com.info.maeumgagym.security.jwt

import com.info.maeumgagym.security.jwt.vo.AuthenticationToken

/**
 * 문자열 형태의 암호화된 토큰을 복호화해 [AuthenticationToken] 객체로 변환
 *
 * @see AuthenticationTokenEncoder
 * @see AuthenticationTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface AuthenticationTokenDecoder {

    /**
     * 미리 설정된 암호화 키를 이용해 토큰 복호화
     *
     * @param token 암호화된 상태의 인증용 토큰. 토큰 형태에 따라 그에 해당하는 접두사 필요.
     * @return 토큰의 정보를 담은 VO
     */
    fun decode(token: String): AuthenticationToken

    /**
     * 입력된 임의의 암호화 키를 이용해 복호화
     *
     * @param token 암호화된 상태의 인증용 토큰. 토큰 형태에 따라 그에 해당하는 접두사 필요.
     * @param key 암호화 키
     *
     * @return 토큰의 정보를 담은 VO
     */
    fun decode(token: String, key: String): AuthenticationToken
}
