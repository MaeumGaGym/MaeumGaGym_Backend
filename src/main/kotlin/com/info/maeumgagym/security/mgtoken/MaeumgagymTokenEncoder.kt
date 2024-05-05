package com.info.maeumgagym.security.mgtoken

/**
 * 주어진 User subject를 토큰으로 암호화
 *
 * @see MaeumgagymTokenDecoder
 * @see MaeumgagymTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface MaeumgagymTokenEncoder {

    /**
     * AccessToken으로 암호화.
     *
     * 토큰의 타입이 AccessToken으로 각인되며, 토큰의 유효 시간 또한 미리 설정된 AccessToken의 유효 시간으로 설정됨.
     *
     * @return 암호화된 문자열 형태의 토큰
     */
    fun encodeAccessToken(subject: String): String

    /**
     * RefreshToken으로 암호화.
     *
     * 토큰의 타입이 RefreshToken으로 각인되며, 토큰의 유효 시간 또한 미리 설정된 RefreshToken의 유효 시간으로 설정됨.
     *
     * @return 암호화된 문자열 형태의 토큰
     */
    fun encodeRefreshToken(subject: String): String
}
