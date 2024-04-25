package com.info.maeumgagym.core.auth.port.`in`

interface GoogleSignupUseCase {

    fun signup(accessToken: String, nickname: String)
}
