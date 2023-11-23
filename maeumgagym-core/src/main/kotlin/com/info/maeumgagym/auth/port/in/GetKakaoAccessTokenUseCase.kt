package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.response.KakaoTokenResponse

interface GetKakaoAccessTokenUseCase {
    fun getAccessToken(code: String): KakaoTokenResponse
}
