package com.info.maeumgagym.core.auth.port.`in`

interface KakaoSignupUseCase {
    fun signup(accessToken: String, nickname: String)
}
