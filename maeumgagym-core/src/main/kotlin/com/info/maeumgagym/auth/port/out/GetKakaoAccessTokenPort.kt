package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.KakaoTokenResponse

interface GetKakaoAccessTokenPort {
    fun getAccessToken(code: String): KakaoTokenResponse
}
