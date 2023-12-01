package com.info.maeumgagym.auth.port.`in`

import com.info.maeumgagym.auth.dto.response.TokenResponse

interface GoogleLoginUseCase {

    fun googleLogin(accessToken: String): TokenResponse
}
