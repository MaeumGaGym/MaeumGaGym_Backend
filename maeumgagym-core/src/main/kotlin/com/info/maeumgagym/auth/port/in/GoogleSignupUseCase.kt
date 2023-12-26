package com.info.maeumgagym.auth.port.`in`

interface GoogleSignupUseCase {

    fun signup(accessToken: String, nickname: String)
}
