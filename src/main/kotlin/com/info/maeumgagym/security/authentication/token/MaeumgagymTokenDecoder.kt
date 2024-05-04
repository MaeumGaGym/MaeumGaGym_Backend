package com.info.maeumgagym.security.authentication.token

import com.info.maeumgagym.security.authentication.token.vo.MaeumgagymToken

/**
 * 문자열 형태의 암호화된 토큰을 복호화해 [MaeumgagymToken] 객체로 변환
 *
 * @see MaeumgagymTokenEncoder
 * @see MaeumgagymTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface MaeumgagymTokenDecoder {

    /**
     * 미리 설정된 암호화 키를 이용해 토큰 복호화
     *
     * @param token 암호화된 상태의 인증용 토큰. 토큰 형태에 따라 그에 해당하는 접두사 필요.
     * @return 토큰의 정보를 담은 VO
     */
    fun decode(token: String): MaeumgagymToken

    /**
     * 입력된 임의의 암호화 키를 이용해 복호화
     *
     * @param token 암호화된 상태의 인증용 토큰. 토큰 형태에 따라 그에 해당하는 접두사 필요.
     * @param key 암호화 키
     *
     * @return 토큰의 정보를 담은 VO
     */
    fun decode(token: String, key: String): MaeumgagymToken
}
