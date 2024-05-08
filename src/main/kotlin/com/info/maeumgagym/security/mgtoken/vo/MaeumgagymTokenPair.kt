package com.info.maeumgagym.security.mgtoken.vo

/**
 * AccessToken, RefreshToken을 함께 전달하기 위한 묶음
 *
 * 각 토큰은 함수간 토큰 교환 정책에 따라 prefix가 부착되어 있음
 *
 * @see com.info.maeumgagym.security.mgtoken.MaeumgagymTokenEncoder
 * @author Daybreak312
 * @since 07-05-2024
 */
data class MaeumgagymTokenPair(
    val accessToken: String,
    val refreshToken: String
)
