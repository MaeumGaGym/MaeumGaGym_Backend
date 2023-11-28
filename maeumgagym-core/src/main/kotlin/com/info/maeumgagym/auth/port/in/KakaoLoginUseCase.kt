package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.response.TokenResponse

interface KakaoLoginUseCase {
    fun login(accessToken: String): TokenResponse
}
