package com.info.maeumgagym.auth.port.`in`

interface KakaoSignupUseCase {
    fun signup(accessToken: String, nickname: String)
}
